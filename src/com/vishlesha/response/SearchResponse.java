package com.vishlesha.response;

import com.vishlesha.app.GlobalConstant;
import com.vishlesha.app.GlobalState;
import com.vishlesha.dataType.Node;
import com.vishlesha.error.SearchError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ridwan on 1/1/16.
 */
public class SearchResponse extends Response {

    int responseCode;
    List<String > fileList = new ArrayList<>();

    // decoding response sent from another node
    public SearchResponse (String responseMessage, Node senderNode){
        setSenderNode(senderNode);
        setResponseMessage(responseMessage);
        String[] token = responseMessage.split(" ");
        responseCode = Integer.valueOf(token[2]);

        if (token[1].equals("SEROK") && responseCode == 0){

        }

        else if (token[1].equals("SEROK") && responseCode < 9000){
            for (int i=6; i< token.length;i++){
                fileList.add(token[i]);
            }
        }

        else{
            setFail(true);
            SearchError searchError = new SearchError(responseMessage,senderNode);

        }

    }

    public List<String> getFileList(){
        return fileList;
    }

    // responding with files on local node
    public SearchResponse(int responseCode, int noOfHops, List<String> fileList){
        String fileNameList ="";
        for (int i=0; i<fileList.size(); i++)
            fileNameList += " " + fileList.get(i);
        String responseMessage = " SEROK " + responseCode + " " + GlobalState.getLocalServerNode().getIpaddress() + " " + GlobalState.getLocalServerNode().getPortNumber() + " " + noOfHops + fileNameList;
        setResponseMessage(responseMessage);
        appendMsgLength();
    }

}
