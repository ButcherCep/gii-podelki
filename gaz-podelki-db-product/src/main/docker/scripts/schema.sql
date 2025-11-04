-- Создание enum типов
CREATE TYPE client_category AS ENUM ('STANDARD', 'VIP', 'PREMIUM');
CREATE TYPE order_status AS ENUM ('PENDING', 'PROCESSING', 'COMPLETED', 'CANCELLED');
CREATE TYPE product_category AS ENUM ('ELECTRONICS', 'CLOTHING', 'BOOKS', 'FOOD');

-- Таблица клиентов
CREATE TABLE IF NOT EXISTS clients (
                                       id BIGSERIAL PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    registration_date DATE NOT NULL,
    category client_category NOT NULL DEFAULT 'STANDARD',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Таблица продуктов
CREATE TABLE IF NOT EXISTS products (
                                        id BIGSERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    category product_category NOT NULL,
    base_price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Таблица заказов
CREATE TABLE IF NOT EXISTS orders (
                                      id BIGSERIAL PRIMARY KEY,
                                      client_id BIGINT NOT NULL REFERENCES clients(id) ON DELETE CASCADE,
    order_date TIMESTAMP NOT NULL,
    status order_status NOT NULL DEFAULT 'PENDING',
    total_amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Таблица элементов заказа
CREATE TABLE IF NOT EXISTS order_items (
                                           id BIGSERIAL PRIMARY KEY,
                                           order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Индексы для улучшения производительности
CREATE INDEX IF NOT EXISTS idx_clients_email ON clients(email);
CREATE INDEX IF NOT EXISTS idx_clients_category ON clients(category);
CREATE INDEX IF NOT EXISTS idx_orders_client_id ON orders(client_id);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders(status);
CREATE INDEX IF NOT EXISTS idx_orders_date ON orders(order_date);
CREATE INDEX IF NOT EXISTS idx_products_category ON products(category);
CREATE INDEX IF NOT EXISTS idx_order_items_order_id ON order_items(order_id);
CREATE INDEX IF NOT EXISTS idx_order_items_product_id ON order_items(product_id);

-- Представление для статистики покупок
CREATE OR REPLACE VIEW purchase_stats_view AS
SELECT
    COUNT(DISTINCT o.id) as total_orders,
    AVG(o.total_amount) as average_order_value,
    ARRAY_AGG(DISTINCT p.category) as purchased_categories,
    ARRAY_AGG(DISTINCT EXTRACT(MONTH FROM o.order_date)) as active_months
FROM orders o
         JOIN order_items oi ON o.id = oi.order_id
         JOIN products p ON oi.product_id = p.id
WHERE o.status = 'COMPLETED';

-- Представление для анализа клиентской воронки
CREATE OR REPLACE VIEW client_funnel_view AS
SELECT
    c.id as client_id,
    c.name as client_name,
    c.email as client_email,
    c.category as client_category,
    ARRAY_AGG(DISTINCT p.category ORDER BY p.category) as purchase_pattern,
    EXISTS (
        SELECT 1 FROM orders o2
                          JOIN order_items oi2 ON o2.id = oi2.order_id
                          JOIN products p2 ON oi2.product_id = p2.id
        WHERE o2.client_id = c.id
          AND o2.order_date > (SELECT MAX(order_date) FROM orders WHERE client_id = c.id) - INTERVAL '3 months'
            AND p2.base_price > (SELECT AVG(base_price) FROM products) * 1.5
    ) as is_upgrade_spender,
    (MAX(o.order_date) < CURRENT_DATE - INTERVAL '3 months') as is_lost_client,
    MAX(o.order_date) as last_order_date
FROM clients c
         LEFT JOIN orders o ON c.id = o.client_id
         LEFT JOIN order_items oi ON o.id = oi.order_id
         LEFT JOIN products p ON oi.product_id = p.id
GROUP BY c.id, c.name, c.email, c.category;