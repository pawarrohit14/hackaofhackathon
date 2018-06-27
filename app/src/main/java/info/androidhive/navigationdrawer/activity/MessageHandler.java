package info.androidhive.navigationdrawer.activity;

import backend.gppmon.handler.BroadcastMessage;

/**
 * Created by Rohit Pawar on 17-01-2018.
 */

public interface MessageHandler {

   public void HandleMessage (BroadcastMessage rtmMessage);
    // public void HandleMessage (ResponseMessage responseMessage , int msgCode);
}
