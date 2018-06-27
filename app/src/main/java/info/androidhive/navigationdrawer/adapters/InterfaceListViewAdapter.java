package info.androidhive.navigationdrawer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import backend.gppmon.handler.ActionMessage;
import backend.gppmon.handler.RTMProviderImpl;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.json.InterfaceRec;

/**
 * Created by Rohit Pawar on 18-01-2018.
 */

public class InterfaceListViewAdapter extends RecyclerView.Adapter<InterfaceListViewAdapter.MyViewHolder> {

    public UpdateList updateList;
    private Context context;
    private String username;
    private List<InterfaceRec> interfaceRecs;
    private LayoutInflater inflater;


    public InterfaceListViewAdapter(Context context, String userName, List<InterfaceRec> interfaceRecs) {

        this.context = context;
        this.username = userName;
        this.interfaceRecs = interfaceRecs;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.interface_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvOfficeId.setText(interfaceRecs.get(position).getUid());
        holder.tvName.setText(interfaceRecs.get(position).getName());
        holder.tvIntType.setText(interfaceRecs.get(position).getIntfType());
        holder.tvIntSubType.setText(interfaceRecs.get(position).getIntfSubType());

        holder.btn_toggle.setOnCheckedChangeListener(null);

        if (interfaceRecs.get(position).getStatus().equals("ACTIVE")) {
            holder.btn_toggle.setChecked(true);
            // tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.notification_circle_active));
        } else {
            holder.btn_toggle.setChecked(false);
            //tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.notification_circle_inactive));
        }

        holder.btn_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {

                    changeStatus(interfaceRecs.get(position), "ACTIVE");
                } else {
                    changeStatus(interfaceRecs.get(position), "NOT_ACTIVE");
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return interfaceRecs.size();
    }

    public void changeStatus(InterfaceRec interfaceRec, String status) {
        //Get current timestamp
        long tsLong = System.currentTimeMillis() / 1000;

        //Request
        final ActionMessage am = new ActionMessage();
        am.setActionCode(3001 + "");
        am.setActionVal(interfaceRec.getUid() + "," + interfaceRec.getName() + "," + status);
        am.setMsgCode(3001);
        am.setSessionID(username);
        am.setTimeStamp(tsLong);
        am.setSeverity(0);
        am.setUserName(username);


        new AlertDialog.Builder(context)
                .setTitle("ACTION")
                .setMessage("Change status to " + status + " ?")
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


    public interface UpdateList {
        void update();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOfficeId, tvName, tvIntType, tvIntSubType;
        SwitchCompat btn_toggle;

        public MyViewHolder(View view) {
            super(view);

            tvOfficeId =  view.findViewById(R.id.tv_officeid);
            tvName =  view.findViewById(R.id.tv_name);
            tvIntType =  view.findViewById(R.id.tv_interface_type);
            tvIntSubType =  view.findViewById(R.id.tv_interface_subtype);
            btn_toggle =  view.findViewById(R.id.btn_toggle);
        }
    }
}
