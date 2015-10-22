package com.jenkov.nioserver.http;

import com.jenkov.nioserver.IMessageReader;
import com.jenkov.nioserver.IMessageReaderFactory;
import com.jenkov.nioserver.MessageBuffer;

/**
 * Created by jjenkov on 18-10-2015.
 */
public class HttpMessageReaderFactory implements IMessageReaderFactory {

    private MessageBuffer messageBuffer = null;

    public HttpMessageReaderFactory(MessageBuffer messageBuffer) {
        this.messageBuffer = messageBuffer;
    }

    @Override
    public IMessageReader createMessageReader() {
        return new HttpMessageReader(this.messageBuffer);
    }
}
