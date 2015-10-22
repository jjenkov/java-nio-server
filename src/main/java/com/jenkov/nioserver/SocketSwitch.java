package com.jenkov.nioserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

/**
 *
 * Todo - rename class to SocketWriter ?
 *
 * Created by jjenkov on 20-10-2015.
 */
public class SocketSwitch {

    private Map<Long, Socket>         socketMap        = new HashMap<>();
    private Map<Long, IMessageWriter> messageWriterMap = new HashMap<>();

    private Set<Socket> socketsToCancel   = new HashSet<>();
    private Set<Socket> socketsToRegister = new HashSet<>();


    public Set<Socket> getSocketsToCancel() {
        return this.socketsToCancel;
    }

    public Set<Socket> getSocketsToRegister() {
        return this.socketsToRegister;
    }

    //todo change putMessageWriter to registerSocket();
    public void registerSocket(Socket socket){
        System.out.println("Registering socket: " + socket.socketId );
        this.socketMap.put(socket.socketId, socket);
        this.messageWriterMap.put(socket.socketId, socket.messageWriter);
    }

    public void enqueueMessage(long socketId, Message message){
        IMessageWriter messageWriter = this.messageWriterMap.get(socketId);



        if(messageWriter != null){
            boolean wasEmpty = messageWriter.isEmpty();
            messageWriter.enqueue(message);

            if(wasEmpty){
                Socket socket = this.socketMap.get(socketId);

                if(this.socketsToCancel.contains(socket)){
                    //Socket already registered with Selector. Remove cancellation request but
                    // do not re-register socket with Selector
                    this.socketsToCancel.remove(socket);
                } else {
                    //Socket not currently registered with Selector. Request registration.
                    this.socketsToRegister.add(socket);
                }
            }
        }
    }

    public void writeToSocket(Socket socket, ByteBuffer writeByteBuffer) throws IOException {
        IMessageWriter messageWriter = messageWriterMap.get(socket.socketId);
        if(messageWriter != null){
            messageWriter.write(socket, writeByteBuffer);

            if(messageWriter.isEmpty()) {
                this.socketsToCancel.add(socket);
            }
        }

    }

    public void unregisterSocket(long socketId){
        System.out.println("Unregistering socket: " + socketId);
        this.socketMap.remove(socketId);

        //todo in case the message writer should survive a "missing connection" - don't remove it.
        this.messageWriterMap.remove(socketId);
    }

    public void removeMessageWriter(long id){
        this.socketMap.remove(id);
    }



}
