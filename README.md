# demo_challeng_x
Projeto de avaliação de conhecimentos


#### Topic about this project
This POC allows you to create, update, remove, and search for a Person.
To improve my tech skills, I built this system, and it gave me more knowledge in some technologies.
For the backend, I used Java 21 with GraalVM, gradle, Spring Boot, Hibernate to communicate with the H2 database,
FlyWay Migration, Spring Data JPA, Spring actuator, Bean Validation e Hibernate Validation, Spring Web,
JUnit 5, Mockito.
On the frontend, I integrated Spring Thymeleaf with Bootstrap 5, Thymeleaf is very similar to the PrimeFaces
framework from the Java EE specification and makes it easier to render pages. It has tags that allow you to
bind the values of returned objects. Through the Model object, the application automatically understands that
it needs to go to the "templates" directory and look for an HTML file with the name defined in the string
returned from the controller.
However, for my specific use case, I preferred to receive the object as JSON to display the result on the screen.
It became more efficient to use AJAX along with my endpoint methods and to manipulate the fields on my pages.



### How to work
- To run this project, make sure you have Java 21, GraalVM, and Gradle installed.
  Previously, I assumed building the frontend would be hard, haha. I didn't expect to find such a great frontend!


### I tell you how the system works.

1 - DivibleNumber
This system write in console application Java, which counts from 1 to 20 print values like this: if the number is
divisible by 3, print “A” if it is divisible by 5, print “B”. If it is divisible by 3 and by 5, print “C”.

In this task, I implemented the Functional Interface to perform conditional validations. It improves productivity
and simplifies the implementation.



2 - SortTheWords
This system has the objective of:
Write a console application in Java which:
a)	reads the standard input,
b)	breaks the string read into an array,
c)	prints the array in the format { el1, el2, el3 } to the standard output,
d)	sorts the elements of the array and prints it using the same format used before,
e)	prints the array as a single string.
Example:
Type anything: My test
Original Array: { O, n,  , w, e, e, k }
Sorted Array : { , O, W, e, e, k, n }
Final String :  OWeekn


In this task, I used Scanner.class. This feature gets the text input from the user, and after pressing Enter, the
system processes it and returns answers.



3 - Create a CRUD web application using Spring Boot. This application will be used to Search, Create, Read, Update and
Delete the entity Person.

a - It's mandatory to build the aplication with this dependencies:
