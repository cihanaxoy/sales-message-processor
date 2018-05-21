package com.jpm.io.source;

import com.jpm.config.Configurations;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileSource extends MessageSource {

    public FileSource(){

    }

    /**
     * This method is to provide message data and notify the service
     */
    @Override
    public void startListening(){

        String fileName = Configurations.map.getProperty("messageSourceFile");

        try (Scanner scanner = new Scanner(new File(fileName))) {

            while (scanner.hasNext()){
                notifyReceiverListeners(scanner.nextLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
