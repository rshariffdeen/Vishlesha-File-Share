package com.vishlesha.request.handler;

import com.vishlesha.app.GlobalState;
import com.vishlesha.dataType.Node;
import com.vishlesha.log.AppLogger;
import com.vishlesha.network.Client;
import com.vishlesha.request.FileShareRequest;
import com.vishlesha.response.FileShareResponse;

import java.util.logging.Logger;

/**
 * Created by ridwan on 1/21/16.
 */
class FileShareRequestHandler {

    private final Logger log = Logger.getLogger(AppLogger.APP_LOGGER_NAME);

    public void handle(FileShareRequest fileShareResponse) {
        Node neighbour = fileShareResponse.getRecipientNode();
        Client client = GlobalState.getClient();

        try {
            GlobalState.addNeighborFiles(neighbour, fileShareResponse.getFiles());
            log.info("added files from neighbor " + fileShareResponse.getSenderNode().toString());
            client.sendUDPResponse(new FileShareResponse(neighbour, GlobalState.getLocalFiles()));
        } catch (IllegalStateException ex) {
            log.warning(ex.getMessage() + " request sent by " + neighbour.toString());
        } catch (RuntimeException ex) {
            log.warning(ex.getMessage() + " request sent by " + neighbour.toString());
            client.sendUDPResponse(new FileShareResponse(neighbour, GlobalState.getLocalFiles()));
        } catch (Exception ex) {
            log.severe(ex.getMessage());
        }
    }

}

