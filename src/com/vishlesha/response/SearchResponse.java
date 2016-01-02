package com.vishlesha.response;

import com.vishlesha.app.GlobalConstant;
import com.vishlesha.app.GlobalState;
import com.vishlesha.dataType.Node;
import com.vishlesha.error.SearchError;

/**
 * Created by ridwan on 1/1/16.
 */
public class SearchResponse extends Response {

    int responseCode;

    public SearchResponse (String responseMessage, Node respondNode){
        super(respondNode);
        String[] token = responseMessage.split(" ");
        responseCode = Integer.valueOf(token[2]);

        if (token[1].equals("SEROK") && responseCode == 0){
            setFail(false);
            if (!GlobalState.isTestMode())
                System.out.println(GlobalConstant.MSG_SEARCH_NORESULT);
        }

        else if (token[1].equals("SEROK") && responseCode < 9000){
            setFail(false);
            if (!GlobalState.isTestMode())
                System.out.println(GlobalConstant.SUCCESS_MSG_SEARCH);
        }

        else{
            setFail(true);
            SearchError searchError = new SearchError(responseMessage,respondNode);
            if (!GlobalState.isTestMode())
                System.out.println("Search Error: " + searchError.getErrorMessage());
        }

    }
}
