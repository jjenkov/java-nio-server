package com.jenkov.nioserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;

/**
 * Created by jjenkov on 16-10-2015.
 */
public interface IMessageWriter {

    /**
     * Called by IMessageProcessor - so no access to write Selector from here.
     *
     * @param message
     */
    public void enqueue(Message message);

    public void write(Socket socket, ByteBuffer byteBuffer) throws IOException;

    public boolean isEmpty();

}
