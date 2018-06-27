package info.androidhive.navigationdrawer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.json.QueueRec;

/**
 * Created by Rohit Pawar on 18-01-2018.
 */

public class QueueListViewAdapter extends RecyclerView.Adapter<QueueListViewAdapter.MyViewHolder> {

    public QueueListViewAdapter.UpdateList updateList;
    private Context context;
    private String username;
    private List<QueueRec> queueRecs;
    private LayoutInflater inflater;


    public QueueListViewAdapter(Context context, String userName, List<QueueRec> queueRecs) {

        this.context = context;
        this.username = userName;
        this.queueRecs = queueRecs;
    }

    @Override
    public QueueListViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.queue_list_row, parent, false);

        return new QueueListViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QueueListViewAdapter.MyViewHolder holder, final int position) {

        holder.tvOfficeId.setText(queueRecs.get(position).getOffice());
        holder.tvName.setText(queueRecs.get(position).getQueueName());
        holder.tvCount.setText(queueRecs.get(position).getCount() + "");

    }

    @Override
    public int getItemCount() {
        return queueRecs.size();
    }


    public interface UpdateList {
        void update();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOfficeId, tvName, tvCount;

        public MyViewHolder(View view) {
            super(view);

            tvOfficeId = view.findViewById(R.id.tv_officeid);
            tvName = view.findViewById(R.id.tv_name);
            tvCount = view.findViewById(R.id.tv_count);
        }
    }

}
