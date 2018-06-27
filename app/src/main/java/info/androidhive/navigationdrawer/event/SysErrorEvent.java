package info.androidhive.navigationdrawer.event;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.json.SystemErrorRec;

/**
 * Created by Rohit Pawar on 12-04-2018.
 */

public class SysErrorEvent {
    private List<SystemErrorRec> systemErrorRecList = new ArrayList<SystemErrorRec>();

    public SysErrorEvent(List<SystemErrorRec> systemErrorRecList) {
        this.systemErrorRecList = systemErrorRecList;
    }

    public List<SystemErrorRec> getData() {
        return systemErrorRecList;
    }
}

