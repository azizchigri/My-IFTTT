FROM httpd:2.4
EXPOSE 8081
COPY ./front /usr/local/apache2/htdocs
COPY ./front/config/httpd.conf /usr/local/apache2/conf/httpd.conf
RUN ln -sf /app/Area/app/build/outputs/apk/release/app-release.apk htdocs/client.apk