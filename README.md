# spring-kotlin-rest-tdd

The project contained in this repository was part of the module "Modernizando seu Backend com Kotlin e Spring Boot" (Modernizing your Backend with Kotlin and Spring Boot, as personally translated) from the Code Update TQI - Backend with Kotlin and Java course, offered by the Digital Innovation One team.

The goal this time was to improve upon a previous project, adding unit and integration tests to an already running application.
From the video lessons, a little less than half of the codebase was covered by the tests, and it was my job to complete the other half.

Since I was focused on watching the lessons before going hands on to the IDE, I got myself a chance to work on one of the most important good practices in the entire development cycle, and one that is most controversial and sometimes even despised by entry level developers: Test Driven Development, TDD for short.

The result of my attempt at TDD is the repository before your eyes. The application was built using Java SDK version 17 and tested using AssertJ, JUnit and MockK libraries.

The endpoints are displayed and can be tested on the URL http://localhost:8080/swagger-ui/index.html when running the project locally.