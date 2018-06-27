package info.androidhive.navigationdrawer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import backend.gppmon.handler.ActionMessage;
import backend.gppmon.handler.RTMProviderImpl;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.json.BackoutRec;

/**
 * Created by Rohit Pawar on 18-01-2018.
 */

public class BackoutListAdapter extends RecyclerView.Adapter<BackoutListAdapter.MyViewHolder> {

    public BackoutListAdapter.UpdateList updateList;
    private Context context;
    private String username;
    private List<BackoutRec> backoutRecs;
    private LayoutInflater inflater;


    public BackoutListAdapter(Context context, String userName, List<BackoutRec> backoutRecs) {

        this.context = context;
        this.username = userName;
        this.backoutRecs = backoutRecs;
    }

    @Override
    public BackoutListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.backout_list_row, parent, false);

        return new BackoutListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BackoutListAdapter.MyViewHolder holder, final int i) {


        holder.tvName.setText(backoutRecs.get(i).getInterfaceName());
        holder.tvType.setText(backoutRecs.get(i).getInterfaceType());
        holder.tvSubType.setText(backoutRecs.get(i).getInterfaceSubType());
        holder.tvInternalId.setText(backoutRecs.get(i).getInternalID());

        holder.setOnItemClickListener(new ItemClickListner() {
            @Override
            public void onClick(View view) {

                //If interface subtype is 1 then only  perform action
                if (backoutRecs.get(i).getInterfaceSubType().equals("1")) {

                    //Get current timestamp
                    long tsLong = System.currentTimeMillis() / 1000;
                    final ActionMessage am = new ActionMessage();
                    //Request

                    am.setActionCode(3008 + "");
                    am.setActionVal(backoutRecs.get(i).getInternalID() + "," + backoutRecs.get(i).getInterfaceName());
                    am.setMsgCode(3008);
                    am.setSessionID(username);
                    am.setTimeStamp(tsLong);
                    am.setSeverity(0);
                    am.setUserName(username);

                    new AlertDialog.Builder(context)
                            .setTitle("ACTION")
                            .setMessage("Do you want to release this record ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    try {

                                         RTMProviderImpl.getInstance().requestAction(am);
                                         updateList.update();

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
        });

    }

    @Override
    public int getItemCount() {
        return backoutRecs.size();
    }


    public interface UpdateList {
        void update();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvName, tvType, tvSubType, tvInternalId;
        private ItemClickListner itemClickListner;

        public MyViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tv_name);
            tvType = view.findViewById(R.id.tv_type);
            tvSubType = view.findViewById(R.id.tv_subtype);
            tvInternalId = view.findViewById(R.id.tv_internalid);
            view.getRootView().setOnClickListener(this);
        }

        public void setOnItemClickListener (ItemClickListner itemClickListener) {
            this.itemClickListner = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListner.onClick(view);
        }
    }

    public interface ItemClickListner {
        void onClick (View view);
    }

}
