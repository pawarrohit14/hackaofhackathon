package info.androidhive.navigationdrawer.event;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.json.BackoutRec;

/**
 * Created by Rohit Pawar on 12-04-2018.
 */

public class BackoutEvent {
    private List<BackoutRec> backoutRecList = new ArrayList<BackoutRec>();

    public BackoutEvent(List<BackoutRec> backoutRecList) {
        this.backoutRecList = backoutRecList;
    }

    public List<BackoutRec> getData() {
        return backoutRecList;
    }
}

