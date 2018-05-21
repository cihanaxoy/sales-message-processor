package com.jpm.io.destination;

/**
 *  This interface is the way for message service to register and listen third party message sources
 */
public interface MessageReceivedListener {

    public void onMessageReceived(String message);
}
