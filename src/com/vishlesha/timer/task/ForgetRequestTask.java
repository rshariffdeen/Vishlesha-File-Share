package com.vishlesha.timer.task;

import com.vishlesha.app.GlobalState;
import com.vishlesha.log.AppLogger;
import com.vishlesha.request.SearchRequest;

import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Created by ridwan on 1/22/16.
 */
public class ForgetRequestTask extends TimerTask {

    private final Logger log = Logger.getLogger(AppLogger.APP_LOGGER_NAME);
    private final SearchRequest request;

    public ForgetRequestTask(SearchRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        try {
            GlobalState.forgetRequest(request);
        } catch (Exception ex) {
            log.severe("could not remove search task from remember list");
        }
    }
}
