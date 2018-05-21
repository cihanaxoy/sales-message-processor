package com.jpm.io.source;

import com.jpm.config.Configurations;

import java.util.ArrayList;
import java.util.List;

public class MessageSourceFactory {


    public MessageSourceFactory(){

    }

    /**
     * This method is to create message sources
     * Depending on the config, it provides message source channels for the message service
     * @return List<MessageSource>
     */
    public List<MessageSource> createMessageSources(){

        List<MessageSource> messageSourceList = new ArrayList<>();

        String messageSourceConfig = Configurations.map.getProperty("messageSourceChannels");
        String[] sourceConfigArray = messageSourceConfig.split(",");

        for(String sourceConfig : sourceConfigArray) {
            MessageSource messageSource = null;

            // there is only file message source option for instance, but it's extendable
            if(sourceConfig.equals("file")){
                messageSource = new FileSource();
            }

            if(messageSource != null){
                messageSourceList.add(messageSource);
            }
        }

        if(messageSourceList.size() == 0){
            return null;
        }

        return messageSourceList;
    }
}
