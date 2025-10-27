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

