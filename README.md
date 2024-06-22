## Description 

The application aims to enable passengers and drivers to arrange shared rides to offices.
The application assumes the regularity of trips. The service allows for arranging pickup points, adding routes, and managing profiles (changing photos, changing personal data, adding cars).

## Features

- Logging in is enabled through Google email. To achieve this, we are using a free Google API key. This way, we do not have to store secure data in our database.

- Reserving a seat from the list of available rides. When the application starts, its default view is the list of available rides.
Each route contains the start and endpoint, details about the driver, a map showing the route, and the car that will be used for the ride. After logging in, we are able to reserve a seat.

![main-view](https://github.com/ProjektZespolowyPWR/ride-mate-finder/assets/140521815/5a2a66b6-019d-4db2-982d-56c46423820f)

When making a reservation, we specify the city and the exact pickup location we want to suggest to the driver. We have the ability to check exactly where our point will be marked on the map.

![add-stop](https://github.com/ProjektZespolowyPWR/ride-mate-finder/assets/140521815/a87c468f-7cba-420b-ba3c-621dd34ee0cf)

The driver sees on their profile that they have a new request to join the ride.

![profile](https://github.com/ProjektZespolowyPWR/ride-mate-finder/assets/140521815/71e510b8-4d86-4f0b-b101-aa4c683953aa)

The driver has the ability to preview how their current route would change if they decided to agree to the pickup point proposed by the passenger.

![accept](https://github.com/ProjektZespolowyPWR/ride-mate-finder/assets/140521815/979c0139-5a63-4214-922a-6f681edabdd1)

- Automatic change of the route view upon accepting a passenger.

![Screenshot from 2024-06-21 14-55-19](https://github.com/ProjektZespolowyPWR/ride-mate-finder/assets/140521815/e7c51fe5-dd0c-47e8-9c68-8c55d29319b6)

## Technologies

![Screenshot from 2024-06-21 15-05-18](https://github.com/ProjektZespolowyPWR/ride-mate-finder/assets/140521815/a29636f6-4712-4e3b-814f-3d017ce9c121)

- Docker: The application has two containers; one hosts our database (PostgreSQL) and the other contains our app.
The application connects containers via a Docker bridge, creating a network that enables the applications to function.
- Spring Boot: A framework that enables us to run our app in a browser.
- Thymeleaf: A framework that allows us to integrate HTML, CSS, and JavaScript into our app, enabling us to create a user interface.
- Spring Data JPA: A framework that facilitates basic CRUD operations on the database without the need for manual implementation.
- Spring Security: A framework that secures certain endpoints, making them inaccessible to users who are not logged in.
- HTML/CSS/JS: Programming languages that enable us to create a responsive user interface.
