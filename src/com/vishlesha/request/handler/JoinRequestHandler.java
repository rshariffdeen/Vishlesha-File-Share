package com.vishlesha.request.handler;

import com.vishlesha.app.GlobalState;
import com.vishlesha.dataType.Node;
import com.vishlesha.log.AppLogger;
import com.vishlesha.network.Client;
import com.vishlesha.request.JoinRequest;
import com.vishlesha.response.JoinResponse;
import com.vishlesha.response.Response;

import java.util.logging.Logger;

/**
 * Created by ridwan on 1/2/16.
 */
class JoinRequestHandler {

    private static final int RESPOND_CODE_JOIN_SUCCESS = 0;
    private static final int RESPOND_CODE_JOIN_ERROR = 9999;

    private final Logger log = Logger.getLogger(AppLogger.APP_LOGGER_NAME);

    public void handle(JoinRequest request) {
        // add as neighbor
        Node neighbor = request.getInitialNode();
        try {
            GlobalState.addNeighbor(neighbor);
            log.info("New neighbour joined " + neighbor.toString());
            Response joinResponse = new JoinResponse(RESPOND_CODE_JOIN_SUCCESS);
            joinResponse.setRecipientNode(neighbor);
            sendResponse(joinResponse);
        } catch (IllegalStateException ex) {
            log.warning("Neighbour already exists " + neighbor.toString());
            Response joinResponse = new JoinResponse(RESPOND_CODE_JOIN_SUCCESS);
            joinResponse.setRecipientNode(neighbor);
            sendResponse(joinResponse);
        } catch (Exception ex) {
            log.severe("Could not add neighbour " + neighbor.toString());
            Response joinResponse = new JoinResponse(RESPOND_CODE_JOIN_ERROR);
            joinResponse.setRecipientNode(neighbor);
            sendResponse(joinResponse);
        }
    }

    private void sendResponse(Response response) {
        GlobalState.getClient().sendUDPResponse(response);
    }
}
