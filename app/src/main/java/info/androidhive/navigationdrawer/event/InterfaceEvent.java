package info.androidhive.navigationdrawer.event;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.json.InterfaceRec;

/**
 * Created by Rohit Pawar on 12-04-2018.
 */

public class InterfaceEvent {
        private List<InterfaceRec> recList = new ArrayList<InterfaceRec>();

        public InterfaceEvent(List<InterfaceRec> recList){
            this.recList = recList;
        }
        public List<InterfaceRec> getData(){
            return recList;
        }
    }

