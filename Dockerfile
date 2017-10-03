FROM openjdk:8
ADD . /
EXPOSE 4567
CMD ["java", "-jar", "./build/libs/restful_assessment-1.9.jar", "&"]
