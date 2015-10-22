package com.jenkov.nioserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

/**
 * Created by jjenkov on 18-10-2015.
 */
public class MessageBufferTest {

    @Test
    public void testGetMessage() {

        MessageBuffer messageBuffer = new MessageBuffer();

        Message message = messageBuffer.getMessage();

        assertNotNull(message);
        assertEquals(0       , message.offset);
        assertEquals(0       , message.length);
        assertEquals(4 * 1024, message.capacity);

        Message message2 = messageBuffer.getMessage();

        assertNotNull(message2);
        assertEquals(4096    , message2.offset);
        assertEquals(0       , message2.length);
        assertEquals(4 * 1024, message2.capacity);

        //todo test what happens if the small buffer space is depleted of messages.

    }


    @Test
    public void testExpandMessage(){
        MessageBuffer messageBuffer = new MessageBuffer();

        Message message = messageBuffer.getMessage();

        byte[] smallSharedArray = message.sharedArray;

        assertNotNull(message);
        assertEquals(0       , message.offset);
        assertEquals(0       , message.length);
        assertEquals(4 * 1024, message.capacity);

        messageBuffer.expandMessage(message);
        assertEquals(0         , message.offset);
        assertEquals(0         , message.length);
        assertEquals(128 * 1024, message.capacity);

        byte[] mediumSharedArray = message.sharedArray;
        assertNotSame(smallSharedArray, mediumSharedArray);

        messageBuffer.expandMessage(message);
        assertEquals(0          , message.offset);
        assertEquals(0          , message.length);
        assertEquals(1024 * 1024, message.capacity);

        byte[] largeSharedArray = message.sharedArray;
        assertNotSame(smallSharedArray, largeSharedArray);
        assertNotSame(mediumSharedArray, largeSharedArray);

        //next expansion should not be possible.
        assertFalse(messageBuffer.expandMessage(message));
        assertEquals(0          , message.offset);
        assertEquals(0          , message.length);
        assertEquals(1024 * 1024, message.capacity);
        assertSame(message.sharedArray, largeSharedArray);



    }
}
