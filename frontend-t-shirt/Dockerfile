FROM nginx@sha256:fd9f8ce722ab13edb2e47ebdd16b843939280457bf1567a6cd155203f9ce98d8

COPY default.conf /etc/nginx/conf.d/default.conf

RUN rm -rf /usr/share/nginx/html/*

COPY index.html /usr/share/nginx/html/
COPY *.css /usr/share/nginx/html/
COPY *.js /usr/share/nginx/html/

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
