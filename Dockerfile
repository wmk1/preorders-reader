FROM openjdk:10-jre-slim
COPY ./target/preorder-1.0.jar /usr/src/preorder/
WORKDIR /usr/src/preorder
EXPOSE 8080
CMD ["java", "-jar", "preorder-1.0.jar"]