package info.androidhive.navigationdrawer.event;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.json.InterfaceRec;
import info.androidhive.navigationdrawer.json.QueueRec;

/**
 * Created by Rohit Pawar on 12-04-2018.
 */

public class QueueEvent {
        private List<QueueRec> queueList = new ArrayList<QueueRec>();

        public QueueEvent(List<QueueRec> queueList){
            this.queueList = queueList;
        }
        public List<QueueRec> getData(){
            return queueList;
        }
    }

