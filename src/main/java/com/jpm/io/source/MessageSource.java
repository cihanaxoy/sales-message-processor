package com.jpm.io.source;

import com.jpm.io.destination.MessageReceivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *  This class is a bridge between message sources and message service
 */
public abstract class MessageSource {

    private List<MessageReceivedListener> messageReceivedListeners = new CopyOnWriteArrayList<>();

    protected void notifyReceiverListeners(String message){
        for (MessageReceivedListener messageReceivedListener : messageReceivedListeners ) {
            messageReceivedListener.onMessageReceived(message);
        }
    }

    public void registerMessageListener(MessageReceivedListener messageReceivedListener){
        messageReceivedListeners.add(messageReceivedListener);
    }

    public void unregisterMessageListener(MessageReceivedListener messageReceivedListener){
        messageReceivedListeners.remove(messageReceivedListener);
    }

    public abstract void startListening();
}
