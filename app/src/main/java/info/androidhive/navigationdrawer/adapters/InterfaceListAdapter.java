package info.androidhive.navigationdrawer.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.databinding.InterfaceListRowBinding;
import info.androidhive.navigationdrawer.json.InterfaceRec;


/**
 * Created by Rohit Pawar on 18-01-2018.
 */

public class InterfaceListAdapter extends RecyclerView.Adapter<InterfaceListAdapter.MyViewHolder> {


    private Context mContext;
    private List<InterfaceRec> interfaceRecs;
    private InterfaceListRowBinding binding;

    public interface ItemClickListener {
        void onClick(View view);
    }

    public InterfaceListAdapter(Context context, List<InterfaceRec> interfaceRecs) {
        this.mContext = context;
        this.interfaceRecs = interfaceRecs;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.interface_list_row, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(interfaceRecs.get(position));

    }

    @Override
    public int getItemCount() {
        return interfaceRecs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final InterfaceListRowBinding binding;
        private ItemClickListener itemClickListener;

        public MyViewHolder(InterfaceListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        public void bind(InterfaceRec interfaceRec) {
            binding.tvOfficeid.setText(interfaceRec.getUid());


            if (interfaceRec.getStatus().equals("ACTIVE")) {
              //  binding.tvStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.notification_circle_active));
            } else {
              //  binding.tvStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.notification_circle_inactive));
            }

            binding.tvName.setText(interfaceRec.getName());
            binding.tvInterfaceType.setText(interfaceRec.getIntfType());
            binding.tvInterfaceSubtype.setText(interfaceRec.getIntfSubType());

            //binding.setAccountSummaryModel(accountViewModel);
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

}
