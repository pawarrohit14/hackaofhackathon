package info.androidhive.navigationdrawer.fragment;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import info.androidhive.navigationdrawer.adapters.BackoutListAdapter;
import info.androidhive.navigationdrawer.event.BackoutEvent;
import info.androidhive.navigationdrawer.utils.ApplicationController;

import static android.content.Context.MODE_PRIVATE;


public class BackoutListFragment extends Fragment implements BackoutListAdapter.UpdateList {
    RecyclerView recyclerView;
    BackoutListAdapter backoutListAdapter;
    private Cursor mCursor;
    private String userName;
    public static final String MY_PREFS_NAME = "eagle_eye_prefs";
    private ApplicationController applicationController;
    private BaseActivity baseActivity;


    public BackoutListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
        setHasOptionsMenu(true);
        baseActivity = new BaseActivity();
        applicationController = (ApplicationController) getContext().getApplicationContext();

        //  rtmProvider.handler = this;

    }

    private void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_backout, container, false);
        recyclerView = v.findViewById(R.id.list_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (prefs != null) {
            userName = prefs.getString("USER", "");//"No name defined" is the default value.

        }

        requestData();
        // waitForData();


/*        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Cursor cursor = (Cursor) backoutListAdapter.getItem(position);
                cursor.moveToPosition(position);

                // Extract properties from cursor
                String str_Name = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1)));
                String str_SubType = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3)));
                String str_Internal = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4)));

                //If interface subtype is 1 then only  perform action
                if (str_SubType.equals("1")) {

                    //Get current timestamp
                    long tsLong = System.currentTimeMillis() / 1000;

                    //Request
                    ActionMessage am = new ActionMessage();
                    am.setActionCode(3008 + "");
                    am.setActionVal(str_Internal + "," + str_Name);
                    am.setMsgCode(3008);
                    am.setSessionID(userName);
                    am.setTimeStamp(tsLong);
                    am.setSeverity(0);
                    am.setUserName(userName);

                    new AlertDialog.Builder(getActivity())
                            .setTitle("ACTION")
                            .setMessage("Do you want to release ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //  deleteSuggestions(position);
                                    ObjectMapper objectMapper = new ObjectMapper();

                                    try {
                                        //  String req = objectMapper.writeValueAsString(am);
                                        //  RTMProviderImpl.getInstance().send("ACTION", req);
                                        requestData();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }


        });*/
        return v;

    }

    private void requestData() {

        try {
            applicationController.showProgressDialog((BaseActivity) getActivity());
            RTMRequestMessage rtmRequestMessage = new RTMRequestMessage();
            rtmRequestMessage.setMsgCode(2005);
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
    }

    public void onEventMainThread(BackoutEvent event) {
        //  view.setText(view.getText() + "\n" + event.getData());
        Log.i("EVENT", "event captured in Interface fragment :: " + event.getData());
        //   mCursor = db.getAllInterfaces();
        backoutListAdapter = new BackoutListAdapter(getContext(), userName, event.getData());
        backoutListAdapter.updateList = this;
        recyclerView.setAdapter(backoutListAdapter);
    }


    @Override
    public void update() {
        requestData();
    }
}

