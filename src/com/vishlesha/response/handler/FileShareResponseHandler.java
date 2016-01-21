package com.vishlesha.response.handler;

import com.vishlesha.app.GlobalConstant;
import com.vishlesha.app.GlobalState;
import com.vishlesha.dataType.Node;
import com.vishlesha.log.AppLogger;
import com.vishlesha.network.Client;
import com.vishlesha.response.FileShareResponse;

import java.util.logging.Logger;

/**
 * Created by ridwan on 1/18/16.
 */
public class FileShareResponseHandler {

    Logger log = Logger.getLogger(AppLogger.APP_LOGGER_NAME);

    public void handle(FileShareResponse fileShareResponse){
        Node neighbour = fileShareResponse.getRecipientNode();

        try {
            GlobalState.addNeighborFiles(neighbour, fileShareResponse.getFiles());
            log.info("added files from neighbor " + fileShareResponse.getSenderNode().toString());
        }catch (IllegalStateException ex) {
            log.warning(ex.getMessage() + " sent by " + neighbour.toString());
        }catch (RuntimeException ex){
            log.warning(ex.getMessage() + " sent by " + neighbour.toString());

        }catch(Exception ex){
            log.severe(ex.getMessage());
        }

    }

}
