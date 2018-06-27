package info.androidhive.navigationdrawer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.json.SystemErrorRec;

/**
 * Created by Rohit Pawar on 18-01-2018.
 */

public class ErrorListAdapter extends RecyclerView.Adapter<ErrorListAdapter.MyViewHolder> {


    private Context mContext;
    private List<SystemErrorRec> systemErrorRecs;
    private LayoutInflater inflater;
    private String userName;
    public ErrorListAdapter.UpdateList updateList;

    public interface ItemClickListener {
        void onClick(View view);
    }

    public ErrorListAdapter(Context context, List<SystemErrorRec> systemErrorRecs) {
        this.mContext = context;
        this.systemErrorRecs = systemErrorRecs;

    }

    public ErrorListAdapter(Context context, String userName, List<SystemErrorRec> errorRecs) {

        this.mContext = context;
        this.userName = userName;
        this.systemErrorRecs = errorRecs;
    }

    @Override
    public ErrorListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.error_list_row, parent, false);

        return new ErrorListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ErrorListAdapter.MyViewHolder holder, final int position) {

        holder.tvErrorCode.setText(systemErrorRecs.get(position).getErrorCode());
        holder.tvDateTime.setText(systemErrorRecs.get(position).getErrorCode());
        holder.tvErrorMsg.setText(systemErrorRecs.get(position).getErrorMessage());
        holder.tvUserId.setText(systemErrorRecs.get(position).getUserID());
        holder.tvModule.setText(systemErrorRecs.get(position).getModule());

    }

    @Override
    public int getItemCount() {
        return systemErrorRecs.size();
    }


    public interface UpdateList {
        void update();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvErrorCode, tvDateTime, tvErrorMsg, tvUserId, tvModule;

        public MyViewHolder(View view) {
            super(view);

            tvErrorCode = view.findViewById(R.id.tv_errorcode);
            tvDateTime = view.findViewById(R.id.tv_datetime);
            tvErrorMsg = view.findViewById(R.id.tv_errormsg);
            tvUserId = view.findViewById(R.id.tv_userid);
            tvModule = view.findViewById(R.id.tv_module);
        }
    }


}
