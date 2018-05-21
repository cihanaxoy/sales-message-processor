package com.jpm;

import com.jpm.config.Configurations;
import com.jpm.io.source.MessageSource;
import com.jpm.io.source.MessageSourceFactory;
import com.jpm.service.MessageService;
import com.jpm.service.MessageServiceFactory;

import java.util.List;

public class Launcher {

    public static void main( String[] args )
    {

        /**
         *  Get the configurations ready to use
         */
        Configurations.load();

        /**
         *  Create the message service
         */
        MessageServiceFactory messageServiceFactory = new MessageServiceFactory();
        MessageService messageService = messageServiceFactory.createMessageService();
        if(messageService == null){
            System.out.println("Something went wrong during message service creation...");
            System.exit(1);
        }

        /**
         *  Create message source(s) for the message service
         */
        MessageSourceFactory messageSourceFactory = new MessageSourceFactory();
        List<MessageSource> messageSourcesList = messageSourceFactory.createMessageSources();
        if(messageSourcesList == null){
            System.out.println("Something went wrong during message source creation...");
            System.exit(1);
        }
        messageSourcesList.stream().forEach(messageSource -> {

            // register the service to the source
            messageSource.registerMessageListener(messageService);

            // add source reference to the service so that the service will be able to unregister
            messageService.addMessageSource(messageSource);

            // let the source emit the messages
            messageSource.startListening();
        });
    }
}
