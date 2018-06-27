package info.androidhive.navigationdrawer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.roger.catloadinglibrary.CatLoadingView;

import info.androidhive.navigationdrawer.utils.ApplicationController;

/**
 * Created by Rohit Pawar on 05-04-2018.
 */

public class BaseActivity extends AppCompatActivity {

/*    private SimpleMessageHandler handler;
    private RTMProvider provider;
    private BaseStore db;
    private MessageHandler broadcastMessagehandler;*/

    // tags used to attach the fragments
    private static final String TAG_INTERFACE = "interface";
    private static final String TAG_BROADCAST = "broadcast";
    private static final String TAG_QUEUE_LIST = "queue_list";
    private static final String TAG_BACKOUT_LIST = "backout_list";
    private static final String TAG_TASK_LIST = "task_list";
    private static final String TAG_EVENT_STATUS = "event_status";
    private static final String TAG_ERROR_LOG = "error_log";

    public RefreshFragment refreshFragment;
    private CatLoadingView mView;
    private ApplicationController applicationController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



       /* StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

         applicationController = (ApplicationController) getApplicationContext();
        try{
            handler = new SimpleMessageHandler();
            provider = RTMProviderImpl.getInstance();
            provider.setLoginURL(AppConstants.LOGIN_URL);
            provider.setMessageHandler(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }


        db = new BaseStore(this);*/

    }




   /* public void LoginToRTMServer(String userName, String password) {

        try {
            try {

                provider.login(userName, password);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/

   /* public void setBroadcastMessagehandler(MessageHandler broadcastMessagehandler) {
        this.broadcastMessagehandler = broadcastMessagehandler;
    }
*/
   /* public class SimpleMessageHandler extends MessageHandlerImpl {
        @Override
        public void subscribed(String channel) {
            Log.i("SUBSCRIBED", channel);
            //Can be used by any application to know whether channel is subscribed or not
        }

        @Override
        public void handleAction(ActionMessage msg) {
            //Implement a logic for handling action message. To be  used by consumer

        }

        @Override
        public void handleEvent(BroadcastMessage msg, String ap) {
            // To be used by consumer application. This method is invoked when broadcast is received
            System.out.println(msg);
            broadcastMessagehandler.HandleMessage(msg);

        }

        @Override
        public void handleResponse(RTMResponseMessage msg, String app) {
            //To be used by consumer. This will contain response.

            //Interface list
            if (msg.getMsgCode() == 2500) {
                try {

                    if (db.getAllInterface() != null && db.getAllInterface().size() > 0) {
                        db.deleteAllInterfaces();
                    }
                    ArrayList list = (ArrayList) msg.getResult();

                    for (int i = 0; i < list.size(); i++) {
                        Map<String, String> r1 = (Map) list.get(i);
                        InterfaceRec interfaceRec = new InterfaceRec();
                        interfaceRec.setUid(r1.get("uid").substring(0, 3));
                        interfaceRec.setStatus(r1.get("status"));
                        interfaceRec.setName(r1.get("name"));
                        interfaceRec.setIntfType(r1.get("intfType"));
                        interfaceRec.setIntfSubType(r1.get("intfSubType"));
                        //add to database
                        db.addInterface(interfaceRec);


                    }

                    applicationController.dismissProgressDialog();
                    if(refreshFragment!=null)
                    refreshFragment.updateUI();
                    InterfaceFragment interfaceFragment = (InterfaceFragment)
                            getSupportFragmentManager().findFragmentByTag(TAG_INTERFACE);
                    if (interfaceFragment !=null) {
                        interfaceFragment.refreshList();
                    }


                } catch (Exception e) {

                    e.printStackTrace();
                }
                //Queue list
            } else if (msg.getMsgCode() == 2501) {

                try {
                    // queueRecs.clear();
                    ArrayList list = (ArrayList) msg.getResult();

                    for (int i = 0; i < list.size(); i++) {
                        Map<String, Object> r1 = (Map) list.get(i);
                        QueueRec queueRec = new QueueRec();
                        queueRec.setQueueName(r1.get("queueName").toString());
                        String countVal = r1.get("count").toString();
                        Long count = Long.parseLong(countVal);
                        queueRec.setCount(count);
                        queueRec.setOffice(r1.get("office").toString());
                        db.addQueue(queueRec);

                    }


                    applicationController.dismissProgressDialog();
                    if(refreshFragment!=null)
                        refreshFragment.updateUI();
                    QueueListFragment queueListFragment = (QueueListFragment)
                            getSupportFragmentManager().findFragmentByTag(TAG_QUEUE_LIST);
                    if (queueListFragment !=null) {
                        queueListFragment.refreshList();
                    }
                    //  queueListFragment.refreshList();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            } else if (msg.getMsgCode() == 2512) {

                try {

                    ArrayList list = (ArrayList) msg.getResult();

                    for (int i = 0; i < list.size(); i++) {
                        Map<String, Object> r1 = (Map) list.get(i);
                        SystemErrorRec systemErrorRec = new SystemErrorRec();

                        systemErrorRec.setUserID(r1.get("userID").toString());
                        systemErrorRec.setDateTime(r1.get("dateTime").toString());
                        systemErrorRec.setErrorCode(r1.get("errorCode").toString());
                        systemErrorRec.setErrorMessage(r1.get("errorMessage").toString());
                        systemErrorRec.setModule(r1.get("module").toString());
                        db.addError(systemErrorRec);


                    }
                    //  errorLogFragment.refreshList();


                    applicationController.dismissProgressDialog();
                    if(refreshFragment!=null)
                        refreshFragment.updateUI();
                    ErrorLogFragment errorLogFragment = (ErrorLogFragment)
                            getSupportFragmentManager().findFragmentByTag(TAG_ERROR_LOG);
                    if (errorLogFragment !=null) {
                        errorLogFragment.refreshList();
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }
            } else if (msg.getMsgCode() == 2505) {

                try {
                    //  queueRecs.clear();
                    ArrayList list = (ArrayList) msg.getResult();

                    for (int i = 0; i < list.size(); i++) {
                        Map<String, Object> r1 = (Map) list.get(i);
                        BackoutRec backoutRec = new BackoutRec();
                        backoutRec.setInterfaceName(r1.get("interfaceName").toString());
                        backoutRec.setInterfaceType(r1.get("interfaceType").toString());
                        backoutRec.setInterfaceSubType(r1.get("interfaceSubType").toString());
                        backoutRec.setInternalID(r1.get("internalID").toString());
                        db.addBackout(backoutRec);
                    }
                    //  backoutListFragment.refreshList();


                    applicationController.dismissProgressDialog();
                    if(refreshFragment!=null)
                        refreshFragment.updateUI();
                    BackoutListFragment backoutListFragment = (BackoutListFragment)
                            getSupportFragmentManager().findFragmentByTag(TAG_BACKOUT_LIST);
                    if (backoutListFragment !=null) {
                        backoutListFragment.refreshList();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }

        @Override
        public void connected() {
            super.connected();
            Log.i("ROHIT", "Connected to Server");
        }

    }*/

    public interface RefreshFragment {
        public void updateUI();
    }
}
