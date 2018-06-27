package info.androidhive.navigationdrawer.event;

import java.util.ArrayList;
import java.util.List;

import backend.gppmon.handler.BroadcastMessage;
import info.androidhive.navigationdrawer.json.InterfaceRec;

/**
 * Created by Rohit Pawar on 12-04-2018.
 */

public class BroadcastEvent {

public BroadcastMessage broadcastMessage;
        public BroadcastEvent(BroadcastMessage broadcastMessage){
            this.broadcastMessage = broadcastMessage;
        }
        public BroadcastMessage getData(){
            return broadcastMessage;
        }
    }

