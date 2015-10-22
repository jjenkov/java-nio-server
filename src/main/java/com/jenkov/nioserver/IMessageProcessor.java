package com.jenkov.nioserver;

/**
 * Created by jjenkov on 16-10-2015.
 */
public interface IMessageProcessor {

    public void process(Message message, WriteProxy writeProxy);

}
