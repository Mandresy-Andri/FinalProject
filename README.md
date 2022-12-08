# GAME STORE SERVICE
#### Mandresy Andriamasinoro

---
A simple back-end REST inventory management web service for a video game store, developed using agile techniques in a group setting.

## API Features

---
This system manages an inventory of video games, game consoles, and T-shirts.

### Games:
* Perform standard CRUD operations for games.
* Search for games by studio.
* Search for games by ESRB rating.
* Search for games by title.

### Consoles:
* Perform standard CRUD operations for consoles.
* Search for consoles by manufacturer.

### T-shirts:
* Perform standard CRUD operations for T-shirts.
* Search for T-shirts by color.
* Search for T-shirts by size.

### Invoices:
Create an invoice by supplying the following information to the endpoint:
* Name
* Street
* City
* State
* Zip
* Item type
* Item ID
* Quantity

## Dependencies

---
* Spring Boot
* Spring MVC
* JPA
* MySQL Connector
* JSR303
* JUnit
* MockMVC
* SpringDoc

## Executing program

---
* The service can be accessed at this url: http://gamestoreservice-env.eba-rgrvd8ti.us-east-1.elasticbeanstalk.com﻿
* The endpoints are:
  * /games
  * /console
  * /tshirt
  * /invoice

## Contributors

---

* [@Avineesh Kompella](https://github.com/avithesun)  
* [@Michael Aguadze](https://github.com/MichaelAguadze)
* [Joshua DeVille](https://github.com/devillejoshua14)