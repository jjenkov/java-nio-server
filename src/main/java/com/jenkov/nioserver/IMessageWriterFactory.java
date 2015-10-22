package com.jenkov.nioserver;

import java.nio.channels.Selector;

/**
 * Created by jjenkov on 16-10-2015.
 */
public interface IMessageWriterFactory {

    public IMessageWriter createMessageWriter();

}
