FROM openjdk:11

# Default to UTF-8 file.encoding
ENV LANG C.UTF-8

RUN mkdir -p /opt
ADD maven/opt/beadando-complex.jar /opt/beadando-complex.jar


EXPOSE 8080

ENTRYPOINT java -jar /opt/beadando-complex.jar -Djava.net.preferIPv4Stack=true
