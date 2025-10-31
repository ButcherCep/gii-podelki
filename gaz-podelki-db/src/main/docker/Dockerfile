#FROM postgres:15-alpine
#
## Установка переменных окружения
#ENV POSTGRES_DB=gaz_podelki
#ENV POSTGRES_USER=gaz_user
#ENV POSTGRES_PASSWORD=gaz_password
#
## Копируем SQL скрипты для инициализации
#COPY ./src/main/docker/init-scripts/ /docker-entrypoint-initdb.d/
#
## Открываем порт PostgreSQL
#EXPOSE 5432
#
## Здоровье контейнера
#HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
#    CMD pg_isready -U $POSTGRES_USER -d $POSTGRES_DB