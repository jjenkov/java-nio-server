# Java NIO Server
A Java NIO Server using non-blocking IO all the way through.

Note:
This is NOT intended for reuse "as is". This is an example of how you could design a Java NIO server yourself.
The design is explained in this tutorial:

[Java NIO Non-blocking Server](http://tutorials.jenkov.com/java-nio/non-blocking-server.html)

Because this is an example app - this project will NOT accept feature requests. If there are any obvious bugs in the code, I can fix those, but apart from that, the code has to stay as it is.

## Bugs and Improvements:
This server implementation has bugs - and room for improvement.

For instance, memory is never returned to the memory buffer from which they are borrowed (allocated).
You can add that yourself, easily. 

Also, the memory buffer's design could be improved to just use a single, contiguous memory array. I have that working elsewhere, but just not implemented here.

## Virtual Threads
After the introduction of virtual threads in Java, a server design using single-threaded non-blocking IO is not nearly as attractive anymore.
It might still have it uses in router-type servers, where messages come in on one connection, and has to be sent out again on another. 
But traditional servers where the response is sent back to the same connection as the request came from, are probably easier to implement using
virtual threads.
