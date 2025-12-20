# Dockerfile in Frontend folder
FROM nginx:alpine
# 1. Definer base-image med hash-pinning (Supply Chain Security)
# Dette sikrer, at I altid bruger præcis denne version af Nginx Alpine
FROM nginx@sha256:fd9f8ce722ab13edb2e47ebdd16b843939280457bf1567a6cd155203f9ce98d8
# Kopier den nye konfigurationsfil for at opsætte API-proxy
COPY default.conf /etc/nginx/conf.d/default.conf

# Copy your frontend files to Nginx root
COPY . /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
