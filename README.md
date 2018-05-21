# Sales Message Processor Application

It's a simple message processing application for sales messages.

It was developed with Java 8, tested with JUnit and built with Maven.

Main purpose of this application is to receive messages from some sources, process, persist and deliver them to some destinations.

For the sake of simplicity, the application has only one source (File input) and one destination (Console output) for instance. All messages are kept in-memory.

But the application has an extensible design which allows easily to receive messages from multiple sources and deliver them multiple destinations.  

There are 3 types of messages, they're parsed using regex statements. There're test cases relevant to those messages.

Messages are found in input.txt file and configurations are in config.properties under resource directory.

Persistence and service layers have their own test cases as well.

To build the application, just type `mvn clean verify` at the pom directory and give a try the resulting artifact by `java -jar target\SalesMessageProcessor-1.0-SNAPSHOT` at the same directory.
Note that config.properties is your friend in case of any path related error.  

Happy message processing!!