FROM kong
COPY kong.yml /usr/local/kong/declarative/kong.yml

ENV KONG_DATABASE=off
ENV KONG_DECLARATIVE_CONFIG=/usr/local/kong/declarative/kong.yml
ENV KONG_ADMIN_LISTEN="0.0.0.0:8001, 0.0.0.0:8444 ssl"

EXPOSE 8000 8001 8443 8444