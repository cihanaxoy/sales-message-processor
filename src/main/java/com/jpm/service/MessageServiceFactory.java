package com.jpm.service;

import com.jpm.config.Configurations;
import com.jpm.dao.Memory;
import com.jpm.dao.Persistence;
import com.jpm.io.destination.ConsoleWriter;
import com.jpm.io.destination.ReportWriterListener;

import java.util.ArrayList;
import java.util.List;

public class MessageServiceFactory {

    public MessageServiceFactory(){

    }

    /**
     * This method is to create message service
     * Depending on the config, it provides persistence and report output options to the service
     * @return MessageService
     */
    public MessageService createMessageService(){

        Persistence persistence = null;
        List<ReportWriterListener> reportDestinationList = new ArrayList<>();

        // there is only in-memory persistence option for instance, but it's extendable
        if(Configurations.map.get("persistenceChannel") != null &&
                Configurations.map.getProperty("persistenceChannel").equals("memory")){

            persistence = new Memory();
        }

        String reportDestConfig = Configurations.map.getProperty("reportDestinationChannels");
        if(reportDestConfig != null){
            String[] reportDestArray = reportDestConfig.split(",");
            for (String destination : reportDestArray) {
                // there is only console output option for instance, but it's extendable
                if(destination.equals("console")){
                    ConsoleWriter consoleWriter = new ConsoleWriter();
                    reportDestinationList.add(consoleWriter);
                }
            }
        }

        if(persistence == null || reportDestinationList.size() == 0){
            return null;
        }

        return new MessageService(persistence, reportDestinationList);
    }
}
