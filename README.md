# Java NIO Server
A Java NIO Server using non-blocking IO all the way through.

Note:
This is NOT intended for reuse "as is". This is an example of how you could design a Java NIO server yourself.
The design is explained in this tutorial:

[Java NIO Non-blocking Server](http://tutorials.jenkov.com/java-nio/non-blocking-server.html)


Because this is an example app - this project will NOT accept feature requests. If there are any obvious bugs in the code, I can fix those, but apart from that, the code has to stay as it is.

By the way, I am working a real, usable non-blocking client-server API called "Nanosai Net Ops" - based on the designs of this project.
That project contains both a client and a server, and you can use both client and server using both blocking and non-blocking methods,
and switch between the two modes as you see fit. You can find Net Ops here:

[https://github.com/nanosai/net-ops-java](https://github.com/nanosai/net-ops-java)

I have been able to "echo" around 200.000 messages per second with Net Ops (in early versions), coming from 3 clients running on the same machine, against a single-threaded server - on a Quad core CPU.

Net Ops has several smaller improvements in the design and functionality over the server you see in this project, so if you really want
to study a more robust non-blocking IO client / server design, look at Net Ops too.
