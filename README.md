# Java Reflection example

This repo shows an example of how to use Reflection to create string representations of an object.

# How to run

The main method is not implemented, but you can check if the project is ok by running the tests:

`````shell
./gradlew test
`````

The command above will actually fail. Checkout the Challenge section below for more details on how to fix this.

## Requirements

- Java 19

# Context

We are stuck with a legacy .jar file (`/libs/LegacyModel-1.0.jar`) that has some POJOs without any `toString` implementation.

This creates the problem where every time we try to log any object from this library, it prints something like this:

````shell
dev.degallant.legacy.Customer@54bedef2
````

But, we need something like this:

````shell
Customer[code=039550934435358943, name=Jhon Doe, registrationDate=2007-12-03]
````

Reflection is a prefect candidate to solve this problem.

# Reflection

To start using reflection we call the ``getClass`` method in an object. With this, we have access to a range of methods to query any information we need from this object without knowing its signature at runtime.

## Examples

````java
Customer customer = new Customer();

//getting the class name
customer.getClass().getSimpleName(); //Customer
        
//getting all of its declared fields
customer.getClass().getDeclaredFields(); //[id, name, email]

//getting a single field
Field field = custoemr.getClass().getField("name");
field.getName(); //name
field.getType(); //String
field.get(customer); //email@gmail.com


````

# Challenge

There is still a problem with this code. On test suite ``src/test/java/dev/degallant/print/PrintLegacyObjectsTests.java`` there is still a failing test case that you need to fix by updating the implementation at `src/main/java/dev/degallant/print/ObjectParser.java`.

The ``ObjectParser`` class works fine with a `Customer` class from the legacy library, but when we try to parse an `Order` type object, the output is still not what we expect.

Your goal is to make the test case pass by only changing the implementation of `ObjectParser` class.