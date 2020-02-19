package com.jenkov.nioserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.nio.ByteBuffer;


/**
 * Created by jjenkov on 18-10-2015.
 */
public class MessageTest {


    @Test
    public void testWriteToMessage() {
        MessageBuffer messageBuffer = new MessageBuffer();

        Message    message    = messageBuffer.getMessage();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);

        fill(byteBuffer, 4096);

        int written = message.writeToMessage(byteBuffer);
        assertEquals(4096, written);
        assertEquals(4096, message.length);
        assertSame(messageBuffer.smallMessageBuffer, message.sharedArray);

        fill(byteBuffer, 124 * 1024);
        written = message.writeToMessage(byteBuffer);
        assertEquals(124 * 1024, written);
        assertEquals(128 * 1024, message.length);
        assertSame(messageBuffer.mediumMessageBuffer, message.sharedArray);

        fill(byteBuffer, (1024-128) * 1024);
        written = message.writeToMessage(byteBuffer);
        assertEquals(896  * 1024, written);
        assertEquals(1024 * 1024, message.length);
        assertSame(messageBuffer.largeMessageBuffer, message.sharedArray);

        fill(byteBuffer, 1);
        written = message.writeToMessage(byteBuffer);
        assertEquals(-1, written);

    }

    private void fill(ByteBuffer byteBuffer, int length){
        byteBuffer.clear();
        for(int i=0; i<length; i++){
            byteBuffer.put((byte) (i%128));
        }
        byteBuffer.flip();
    }
}
