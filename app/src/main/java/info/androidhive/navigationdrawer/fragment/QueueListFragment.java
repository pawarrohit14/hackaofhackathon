package info.androidhive.navigationdrawer.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import backend.gppmon.handler.RTMProviderImpl;
import backend.gppmon.handler.RTMRequestMessage;
import de.greenrobot.event.EventBus;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.BaseActivity;
import info.androidhive.navigationdrawer.adapters.QueueListViewAdapter;
import info.androidhive.navigationdrawer.event.QueueEvent;
import info.androidhive.navigationdrawer.utils.ApplicationController;

import static android.content.Context.MODE_PRIVATE;


public class QueueListFragment extends android.support.v4.app.Fragment implements QueueListViewAdapter.UpdateList {

    public static final String MY_PREFS_NAME = "eagle_eye_prefs";
    private RecyclerView recyclerView;
    private QueueListViewAdapter queueListViewAdapter;
    private String userName;
    private BaseActivity baseActivity;
    private ApplicationController applicationController;


    public QueueListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
        setHasOptionsMenu(true);
        baseActivity = new BaseActivity();
        applicationController = (ApplicationController) getContext().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_queue, container, false);
        recyclerView = v.findViewById(R.id.list_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //get stored username from Prefs
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (prefs != null) {
            userName = prefs.getString("USER", "");//"No name defined" is the default value.

        }
        requestData();
        return v;

    }

    private void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void requestData() {
        try {
            applicationController.showProgressDialog((BaseActivity) getActivity());
            RTMRequestMessage rtmRequestMessage = new RTMRequestMessage();
            rtmRequestMessage.setMsgCode(2001);
            rtmRequestMessage.setSessionID(userName);
            rtmRequestMessage.setSeverity(1);
            rtmRequestMessage.setTimeStamp(new Date().getTime());
            rtmRequestMessage.setUserName(userName);
            rtmRequestMessage.setRequestFilter("uid=*");
            //  rtmRequestMessage.setRe(2000 + "");
            RTMProviderImpl.getInstance().requestData(rtmRequestMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.refresh, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.ic_refresh:
                requestData();
                return false;

            default:
                break;
        }

        return false;
    }


    public void onEventMainThread(QueueEvent event) {
        //  view.setText(view.getText() + "\n" + event.getData());
        Log.i("EVENT", "event captured in Interface fragment :: " + event.getData());
        //   mCursor = db.getAllInterfaces();
        queueListViewAdapter = new QueueListViewAdapter(getContext(), userName, event.getData());
        queueListViewAdapter.updateList = this;
        recyclerView.setAdapter(queueListViewAdapter);
    }

    @Override
    public void update() {
        requestData();
    }

  /*  QueueListViewAdapter queueListViewAdapter;
    private BaseStore db;
    private Cursor mCursor;
    private ListView recyclerView;
    public static final String MY_PREFS_NAME = "eagle_eye_prefs";
    private String userName;
    private CatLoadingView mView;


    public QueueListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   RTMProviderImpl rtmProvider = new RTMProviderImpl();
        db = new BaseStore(getActivity());
        setHasOptionsMenu(true);
        //  rtmProvider.handler = this;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_queue, container, false);
        recyclerView = (ListView) v.findViewById(R.id.list_recycler_view);

        //get stored username from Prefs
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (prefs != null) {
            userName = prefs.getString("USER", "");//"No name defined" is the default value.

        }

        requestData();
        return v;

    }

    private void requestData() {

        try {

          *//*  mView = new CatLoadingView();
            mView.setCancelable(false);
            mView.show(getActivity().getSupportFragmentManager(), "");

*//*
            ApplicationController applicationController = (ApplicationController) getContext().getApplicationContext();
            applicationController.showProgressDialog((BaseActivity)getActivity());

            RTMRequestMessage req = new RTMRequestMessage();
            req.setMsgCode(2001);
            req.setSessionID(userName);
            req.setSeverity(1);
            req.setTimeStamp(new Date().getTime());
            req.setUserName(userName);
            req.setRequestFilter("uid=*");
           // req.setRequestCode(2001 + "");
           // ObjectMapper objectMapper = new ObjectMapper();
           // String request = objectMapper.writeValueAsString(req);
              RTMProviderImpl.getInstance().requestData(req);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void refreshList() {
       // mView.dismiss();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCursor = db.getAllQueues();
                queueListViewAdapter = new QueueListViewAdapter(getActivity(), mCursor);
                recyclerView.setAdapter(queueListViewAdapter);

            }

                   *//* private void playAlertTone() {

                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getActivity(), notification);
                        r.play();
                    }*//*
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.refresh, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.ic_refresh:
                requestData();
                // Not implemented here
                return false;

            default:
                break;
        }

        return false;
    }*/
}
