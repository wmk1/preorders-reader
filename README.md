# Preorders reader


## Info

This project is a REST API server that fetches information from Kinguin API and shows preorder list.

Each preorder contains properties described below:

   * Image (String) - URL that redirects to preorder image.
   * Regular price (double) - Preorder regular price.
   * Minimum price (double) - Preorder minimum price.
   * Entity ID (int) - Preorder unique ID that makes each of preorder individual
   * Name (String) - Preorder name.
   
Each preorder is a game, and image is a game cover image.  

## Solution details

When I started this project, I got to get known about Kinguin API official documentation. I quickly made a conclusion, that because it already contains possibility to page 

## How to start project?

1. Go with your terminal to the directory of the project

    
    /cd/to/your/path

2. When you are in the main directory of project, write:

    
    mvn clean install

3. This project uses RabbitMQ, therefore we need to start it first. In order to do it, after successful build, run


    docker-compose up

Docker contains RabbitMQ and core itself.

4. After successful running docker containers, run in your terminal


    mvn spring-boot:run

5. When Spring Boot will run, in your browser go to page and run


    localhost:8080/preorders/{pageNumber}/{pageSize}

Where pageNumber and pageSize are paremeters with which you can customize page or preoders. You should be able to see page that contains list of preorders in HAL format. Each preorder contains link for showing it as an individual preorder.


## Functionalities

This service contains two methods:

###List preorders: 

URL for method

    /preorders/{pageNumber}/{pageSize}/{sortingParam} 

Parameters
    
   * Page number (int) - Number of page that contains preorders
   * Page size (int) - Maximum amount of preorders that can be stored in page
   * Sorting param (String) - Sorting preorders by specified parameter. By now, it's only possible to sort by `name`      and `regular_price`

###Show detailed preorder

URL for method

    /preorders/preorder/{preorderId}
    
Parameters

   * Preorder ID (int) - Number of specified 

##Issues, important notes

Please note that if you cannot show more than 46 preorders. Therefore, please keep in mind that if product of multiplying page number with page size exceeds 46, you won't get any result.

In terms of showing detailed preorder, I had to think about how to get detailed preorder information if Kinguin API does not have endpoint for showing detailed preorder. I decided to make it by filtering list of preorders by `entity_id`, and then returning specified preorder. Perhaps, maybe there is a possibility to to not going every time through list (as getting only specified preorder requires from server to get full list of preorders first) but you will be the judge of that. 

##Conclusion
I really enjoyed solving this task.