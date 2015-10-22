package com.jenkov.nioserver.http;

import com.jenkov.nioserver.IMessageWriter;
import com.jenkov.nioserver.IMessageWriterFactory;

import java.nio.channels.Selector;

/**
 * Created by jjenkov on 19-10-2015.
 */
public class HttpMessageWriterFactory implements IMessageWriterFactory {

    @Override
    public IMessageWriter createMessageWriter() {
        return new HttpMessageWriter();
        //return null;
    }
}
