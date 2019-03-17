# Preorders reader


## Info

This project is a REST API server that fetches information from Kinguin API docs and shows preorder list.

## Solution details

When I started this project, I got to get known about Kinguin API official documentation. I quickly made a conclusion, that because it already contains possibility to page 

## How to start project?

1. Go with your terminal to the directory of the project

    
    /cd/to/your/path

2. Type

    
    mvn clean install

3. After successful build, run


    docker-compose up

Docker contains RabbitMQ and core itself.

4. After successful running docker containers, run in your terminal


    mvn spring-boot:run

5. When Spring Boot will run, in your browser go to page and run


    localhost:8080/preorders/{pageNumber}/{pageSize}

Where pageNumber and pageSize is paremeters you can customize page that , you should be able to see page that contains list of preorders in HAL format. Each preorder contains link for showing it as an individual preorder.


## Functionalities



##Issues

##Conclusion
I found this task very attractive for me as I had a possibility to work with consuming external endpoints and working on them.