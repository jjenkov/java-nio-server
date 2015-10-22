package com.jenkov.nioserver.http;

import com.jenkov.nioserver.IMessageWriter;
import com.jenkov.nioserver.Message;
import com.jenkov.nioserver.SocketSwitch;
import com.jenkov.nioserver.Socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjenkov on 21-10-2015.
 */
public class HttpMessageWriter implements IMessageWriter {

    private List<Message> writeQueue   = new ArrayList<>();
    private Message  messageInProgress = null;
    private int      bytesWritten      =    0;

    public HttpMessageWriter() {
    }

    @Override
    public void enqueue(Message message) {
        if(this.messageInProgress == null){
            this.messageInProgress = message;
            System.out.println("Message set as message in progress.");
        } else {
            this.writeQueue.add(message);
            System.out.println("Message enqueued.");
        }

        //todo register socket for write interest

    }


    @Override
    public void write(Socket socket, ByteBuffer byteBuffer) throws IOException {
        System.out.println("Writing message to socket");

        byteBuffer.put(this.messageInProgress.sharedArray, this.messageInProgress.offset + this.bytesWritten, this.messageInProgress.length - this.bytesWritten);
        byteBuffer.flip();

        this.bytesWritten += socket.write(byteBuffer);
        byteBuffer.clear();

        if(bytesWritten >= this.messageInProgress.length){
            if(this.writeQueue.size() > 0){
                this.messageInProgress = this.writeQueue.remove(0);
            } else {
                this.messageInProgress = null;
                //todo unregister from selector
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.writeQueue.isEmpty() && this.messageInProgress == null;
    }
}
