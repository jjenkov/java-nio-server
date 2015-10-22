package com.jenkov.nioserver.example;

import com.jenkov.nioserver.*;
import com.jenkov.nioserver.http.HttpMessageReaderFactory;
import com.jenkov.nioserver.http.HttpMessageWriterFactory;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by jjenkov on 19-10-2015.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        BlockingQueue inboundSocketQueue = new ArrayBlockingQueue(1024);

        Server server = new Server(9999, inboundSocketQueue);

        Thread serverThread = new Thread(server);
        serverThread.start();


        MessageBuffer readMessageBuffer  = new MessageBuffer();
        MessageBuffer writeMessageBuffer = new MessageBuffer();
        IMessageReaderFactory messageReaderFactory = new HttpMessageReaderFactory(readMessageBuffer);
        IMessageWriterFactory messageWriterFactory = new HttpMessageWriterFactory();

        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: 38\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Hello World!</body></html>";

        byte[] httpResponseBytes = httpResponse.getBytes("UTF-8");

        IMessageProcessor messageProcessor = (request, writeProxy) -> {
            System.out.println("Message Received from socket: " + request.socketId);

            Message response = writeProxy.getMessage();
            response.socketId = request.socketId;
            response.writeToMessage(httpResponseBytes);

            writeProxy.enqueue(response);
        };

        ServerCore serverCore       = new ServerCore(inboundSocketQueue, readMessageBuffer, writeMessageBuffer, messageReaderFactory, messageWriterFactory, messageProcessor);
        Thread     serverCoreThread = new Thread(serverCore);
        serverCoreThread.start();
    }


}
