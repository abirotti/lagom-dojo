- extend the given endpoint to take a second parameter, the surname
    ```/hello/:name/:surname -> Hello, :name :surname```
    - experiment with changing the path string first - what happens to the hello function? is this desirable?
    (https://www.lagomframework.com/documentation/1.4.x/scala/ServiceDescriptors.html)

- modify the greeting to your favorite one (look no further than in the Api definition)

- elect one of you to break the above in one of two ways: 
    - either you cannot specify a new greeting message, 
    - or the new message doesn't apply to any further request
    - then pair, and have the breaker provide hints
    - what did you learn? what are the advantages of this approach to persistency?
    - (https://www.lagomframework.com/documentation/1.4.x/scala/ES_CQRS.html)

- we now want to create a service to add a user: a user has an id, a name and a surname
    - Create endpoint
    - Read endpoint which returns JSON ```e.g. /users/:id```
    - n.b. JSON is the default serialization format 
    - hint: feel free to use REST identifiers (https://www.lagomframework.com/documentation/1.4.x/scala/ServiceDescriptors.html#REST-identifiers)
    
- read the list of available users
    - to do this, we need to implement a Read Side strategy 
    (https://www.lagomframework.com/documentation/1.4.x/scala/ReadSide.html#Persistent-Read-Side)