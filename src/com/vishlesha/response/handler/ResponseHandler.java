package com.vishlesha.response.handler;

import com.vishlesha.dataType.Node;
import com.vishlesha.network.Client;
import com.vishlesha.response.*;

/**
 * Created by ridwan on 1/18/16.
 */
public class ResponseHandler {

    protected static final int KEY_MSG_LENGTH = 0;
    protected static final int KEY_RESPONSE_TYPE = 1;

    protected static final String RESPONSE_TYPE_JOIN = "JOINOK";
    protected static final String RESPONSE_TYPE_LEAVE = "LEAVEOK";
    protected static final String RESPONSE_TYPE_SEARCH = "SEROK";
    protected static final String RESPONSE_TYPE_FILE_SHARE = "FILES";

    public void handle(String responseMessage , Node sender) {

        String[] token = responseMessage.split(" ");
        int lengthOfMessage = Integer.valueOf(token[KEY_MSG_LENGTH]);

        if (lengthOfMessage != responseMessage.length()) {
            sendResponse(new ErrorResponse());
        } else {
            if (token[KEY_RESPONSE_TYPE].equals(RESPONSE_TYPE_JOIN)) {
                JoinResponse joinResponse = new JoinResponse(responseMessage,sender);
                JoinResponseHandler joinResponseHandler = new JoinResponseHandler();
                joinResponseHandler.handle(joinResponse);

            } else if (token[KEY_RESPONSE_TYPE].equals(RESPONSE_TYPE_LEAVE)) {
                LeaveResponse leaveResponse = new LeaveResponse(responseMessage,sender);
                LeaveResponseHandler leaveResponseHandler = new LeaveResponseHandler();
                leaveResponseHandler.handle(leaveResponse);

            } else if (token[KEY_RESPONSE_TYPE].equals(RESPONSE_TYPE_FILE_SHARE)) {
                FileListShareResponse fileListShareResponse = new FileListShareResponse(responseMessage);
                FileShareResponseHandler fileShareResponseHandler = new FileShareResponseHandler();
                fileShareResponseHandler.handle(fileListShareResponse);

            } else if (token[KEY_RESPONSE_TYPE].equals(RESPONSE_TYPE_SEARCH)) {

                SearchResponse searchResponse = new SearchResponse(responseMessage,sender);
                SearchResponseHandler searchResponseHandler = new SearchResponseHandler();
                searchResponseHandler.handle(searchResponse);

            } else {
                sendResponse(new ErrorResponse());
            }
        }
    }

    private void sendResponse(Response response){
        Client client = new Client();
        client.sendUDPResponse(response);
    }

}
