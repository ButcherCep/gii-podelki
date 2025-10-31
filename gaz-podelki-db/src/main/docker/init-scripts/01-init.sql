-- Создание дополнительных баз данных если нужно
CREATE DATABASE gaz_podelki_test;

-- Создание дополнительных ролей
CREATE ROLE gaz_readonly WITH LOGIN PASSWORD 'readonly_pass' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;
CREATE ROLE gaz_readwrite WITH LOGIN PASSWORD 'readwrite_pass' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

-- Выдаем права на основную базу
GRANT CONNECT ON DATABASE gaz_podelki TO gaz_readonly, gaz_readwrite;
GRANT CONNECT ON DATABASE gaz_podelki_test TO gaz_readonly, gaz_readwrite;

-- Настройка расширений
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Логирование
ALTER SYSTEM SET log_statement = 'all';
ALTER SYSTEM SET log_duration = on;
SELECT pg_reload_conf();