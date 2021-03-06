package com.vishlesha.response;

import com.vishlesha.app.GlobalConstant;
import com.vishlesha.app.GlobalState;
import com.vishlesha.dataType.Node;
import com.vishlesha.error.LeaveError;

/**
 * Created by ridwan on 1/1/16.
 */
public class LeaveResponse extends Response {

    private int responseCode;

    public LeaveResponse(String responseMessage, Node senderNode) {
        super(responseMessage, senderNode);

        String[] token = responseMessage.split(" ");
        responseCode = Integer.valueOf(token[2]);

        if (token[1].equals("LEAVEOK") && responseCode == 0) {
            setFail(false);
            if (!GlobalState.isTestMode())
                System.out.println(GlobalConstant.SUCCESS_MSG_LEAVE);
        } else {
            setFail(true);
            LeaveError leaveError = new LeaveError(responseMessage, senderNode);
            if (!GlobalState.isTestMode())
                System.out.println("Leave Error: " + leaveError.getErrorMessage());
        }

    }

    public LeaveResponse(int responseCode) {
        String responseMessage = " LEAVEOK " + responseCode;
        setResponseMessage(responseMessage);
        appendMsgLength();
    }
}
