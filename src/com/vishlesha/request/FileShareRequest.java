package com.vishlesha.request;

import com.vishlesha.app.GlobalState;
import com.vishlesha.dataType.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ridwan on 1/21/16.
 */
public class FileShareRequest extends Request {

    private List<String> files;

    public List<String> getFiles() {
        return files;
    }

    void setFiles(List<String> files) {
        this.files = files;
    }

    // response sent out by this node
    public FileShareRequest(Node node, List<String> files) {
        setRecipientNode(node);
        setFiles(files);
        StringBuilder builder = new StringBuilder(" FILES ");
        builder.append(GlobalState.getLocalServerNode().getIpaddress())
                .append(" ")
                .append(GlobalState.getLocalServerNode().getPortNumber())
                .append(" ");
        for (String file : files) {
            builder.append(file).append(" ");   // FIXME assuming no spaces in file names
        }
        setRequestMessage(builder.substring(0, builder.length() - 1));    // ignore last ' '
        appendMsgLength();
    }

    // request from a different node
    public FileShareRequest(String requestMessage) {
        String[] tokens = requestMessage.split(" ");
        Node node = new Node();
        node.setIpaddress(tokens[Request.KEY_IP_ADDRESS]);
        node.setPortNumber(Integer.valueOf(tokens[Request.KEY_PORT_NUM]));
        setRecipientNode(node);

        List<String> files = new ArrayList<>();
        files.addAll(Arrays.asList(tokens).subList(Request.KEY_PORT_NUM + 1, tokens.length));
        setFiles(files);
    }

    public String getHashCode() {
        return "FILES-" + getRecipientNode().getIpaddress();
    }
}
