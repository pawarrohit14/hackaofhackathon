package info.androidhive.navigationdrawer.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import backend.gppmon.handler.BroadcastMessage;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.databinding.BroadcastListRowBinding;

/**
 * Created by Rohit Pawar on 18-01-2018.
 */

public class BroadcastListAdapter extends RecyclerView.Adapter<BroadcastListAdapter.MyViewHolder> {


    private Context mContext;
    private List<BroadcastMessage> broadcastmessages;
    private BroadcastListRowBinding binding;

    public interface ItemClickListener {
        void onClick(View view);
    }

    public BroadcastListAdapter(Context context, List<BroadcastMessage> broadcastmessages) {
        this.mContext = context;
        this.broadcastmessages = broadcastmessages;

    }


    public void swap(ArrayList<BroadcastMessage> mBroadcastmessages) {
        broadcastmessages = mBroadcastmessages;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.broadcast_list_row, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(broadcastmessages.get(position));

    }

    @Override
    public int getItemCount() {
        return broadcastmessages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final BroadcastListRowBinding binding;
        private ItemClickListener itemClickListener;

        public MyViewHolder(BroadcastListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        public void bind(BroadcastMessage broadcastMessage) {

            binding.tvJvmname.setText(broadcastMessage.getJvmName());
            binding.tvMessage.setText(broadcastMessage.getMessage());
            binding.tvSeverity.setText(broadcastMessage.getSeverity() + "");

            //set severity color codes

            switch (broadcastMessage.getSeverity()) {
                case 1:
                    binding.tvSevColor.setBackground(ContextCompat.getDrawable(mContext, R.drawable.notification_circle_inactive));
                    break;
                case 2:
                    binding.tvSevColor.setBackground(ContextCompat.getDrawable(mContext, R.drawable.notification_circle_orange));
                    break;

                case 3:
                    binding.tvSevColor.setBackground(ContextCompat.getDrawable(mContext, R.drawable.notification_circle_yellow));
                    break;

                case 4:
                    binding.tvSevColor.setBackground(ContextCompat.getDrawable(mContext, R.drawable.notification_circle_active));
                    break;
            }

            //convert unix epoch timestamp (seconds) to milliseconds
            String dateString = new SimpleDateFormat("dd/MM/yyyy \n HH:mm:ss ").format(new Date(broadcastMessage.getTimeStamp()));
            binding.tvTimestamp.setText(dateString);
            binding.executePendingBindings();

        }

        public void setOnItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            //  itemClickListener.onClick(v);
        }
    }

    private String getDate(long timeStamp) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }

}
