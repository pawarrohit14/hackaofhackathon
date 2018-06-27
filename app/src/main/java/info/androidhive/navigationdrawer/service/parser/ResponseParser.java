package info.androidhive.navigationdrawer.service.parser;

import java.util.ArrayList;
import java.util.Map;

import backend.gppmon.handler.RTMResponseMessage;
import de.greenrobot.event.EventBus;
import info.androidhive.navigationdrawer.event.BackoutEvent;
import info.androidhive.navigationdrawer.event.InterfaceEvent;
import info.androidhive.navigationdrawer.event.QueueEvent;
import info.androidhive.navigationdrawer.event.SysErrorEvent;
import info.androidhive.navigationdrawer.json.BackoutRec;
import info.androidhive.navigationdrawer.json.InterfaceRec;
import info.androidhive.navigationdrawer.json.QueueRec;
import info.androidhive.navigationdrawer.json.SystemErrorRec;

public class ResponseParser {

    public void handleInterfaceResponse(RTMResponseMessage msg) {
        try {

            ArrayList list = (ArrayList) msg.getResult();
            ArrayList<InterfaceRec> recArrayList = new ArrayList<InterfaceRec>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> r1 = (Map) list.get(i);
                InterfaceRec interfaceRec = new InterfaceRec();
                interfaceRec.setUid(r1.get("uid").substring(0, 3));
                interfaceRec.setStatus(r1.get("status"));
                interfaceRec.setName(r1.get("name"));
                interfaceRec.setIntfType(r1.get("intfType"));
                interfaceRec.setIntfSubType(r1.get("intfSubType"));

                recArrayList.add(interfaceRec);

            }
            InterfaceEvent interfaceEvent = new InterfaceEvent(recArrayList);
            EventBus.getDefault().post(interfaceEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleQueueResponse(RTMResponseMessage msg) {
        try {
            // queueRecs.clear();
            ArrayList list = (ArrayList) msg.getResult();
            ArrayList<QueueRec> queueRecs = new ArrayList<QueueRec>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> r1 = (Map) list.get(i);
                QueueRec queueRec = new QueueRec();
                queueRec.setQueueName(r1.get("queueName").toString());
                String countVal = r1.get("count").toString();
                Long count = Long.parseLong(countVal);
                queueRec.setCount(count);
                queueRec.setOffice(r1.get("office").toString());
                queueRecs.add(queueRec);
                //  db.addQueue(queueRec);
            }

            QueueEvent queueEvent = new QueueEvent(queueRecs);
            EventBus.getDefault().post(queueEvent);
            //  queueListFragment.refreshList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSysErrorResponse(RTMResponseMessage msg) {
        try {
            ArrayList list = (ArrayList) msg.getResult();
            ArrayList<SystemErrorRec> systemErrorRecs = new ArrayList<SystemErrorRec>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> r1 = (Map) list.get(i);
                SystemErrorRec systemErrorRec = new SystemErrorRec();
                systemErrorRec.setUserID(r1.get("userID").toString());
                systemErrorRec.setDateTime(r1.get("dateTime").toString());
                systemErrorRec.setErrorCode(r1.get("errorCode").toString());
                systemErrorRec.setErrorMessage(r1.get("errorMessage").toString());
                systemErrorRec.setModule(r1.get("module").toString());
                systemErrorRecs.add(systemErrorRec);
            }
            SysErrorEvent sysErrorEvent = new SysErrorEvent(systemErrorRecs);
            EventBus.getDefault().post(sysErrorEvent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleBackoutResponse(RTMResponseMessage msg) {
        try {

            ArrayList list = (ArrayList) msg.getResult();
            ArrayList<BackoutRec> backoutRecs = new ArrayList<BackoutRec>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> r1 = (Map) list.get(i);
                BackoutRec backoutRec = new BackoutRec();
                backoutRec.setInterfaceName(r1.get("interfaceName").toString());
                backoutRec.setInterfaceType(r1.get("interfaceType").toString());
                backoutRec.setInterfaceSubType(r1.get("interfaceSubType").toString());
                backoutRec.setInternalID(r1.get("internalID").toString());

                backoutRecs.add(backoutRec);
            }
            BackoutEvent backoutEvent = new BackoutEvent(backoutRecs);
            EventBus.getDefault().post(backoutEvent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
