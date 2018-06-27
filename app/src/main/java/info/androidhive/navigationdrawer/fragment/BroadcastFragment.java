package info.androidhive.navigationdrawer.fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import backend.gppmon.handler.BroadcastMessage;
import de.greenrobot.event.EventBus;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.adapters.BroadcastListAdapter;
import info.androidhive.navigationdrawer.event.BroadcastEvent;


public class BroadcastFragment extends Fragment {

    RecyclerView recyclerView;
    private static ArrayList<BroadcastMessage> broadcastmessages = new ArrayList<BroadcastMessage>();
    BroadcastListAdapter broadcastListAdapter;
    private boolean isVisible = false;

    public BroadcastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
        NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(12);
        }


    }

    private void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_broadcast, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        broadcastListAdapter = new BroadcastListAdapter(getActivity(), broadcastmessages);
        broadcastListAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(broadcastListAdapter);
        return v;

    }



    @Override
    public void onResume() {
        super.onResume();
        if (broadcastmessages != null) {

        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
    }

    public void onEventMainThread(BroadcastEvent event) {
        //  view.setText(view.getText() + "\n" + event.getData());
        Log.i("EVENT", "event captured in Interface fragment :: " + event.getData());
        //   mCursor = db.getAllInterfaces();
        handleBroadcast(event.getData());
        // interfaceListAdapter.updateList = this;
        //  recyclerView.setAdapter(interfaceListAdapter);
    }

    public void handleBroadcast(BroadcastMessage msg) {

        try {
            // mSwipeRefreshLayout.setRefreshing(false);
            BroadcastMessage broadcastMessage = new BroadcastMessage();
            if (msg != null) {
                broadcastMessage.setJvmName(msg.getJvmName());
                broadcastMessage.setMessage(msg.getMessage());
                broadcastMessage.setSeverity(msg.getSeverity());
                broadcastMessage.setTimeStamp(msg.getTimeStamp());

                //add to list
                broadcastmessages.add(broadcastMessage);
                broadcastListAdapter.swap(broadcastmessages);
              //  playAlertTone();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
