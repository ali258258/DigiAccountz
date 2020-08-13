package com.digiaccounts.digiaccountz.Report_All.Adaptors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.Report_All.Model.Customer_Model_FB;
import com.digiaccounts.digiaccountz.Report_All.Others.Utils;

import java.io.File;
import java.util.List;


public class Customer_Adaptor_FB
        extends RecyclerView.Adapter<Customer_Adaptor_FB.ViewHolder> {


    private List<Customer_Model_FB> mItems;


    private ItemClickListener mClickListener;
    private LongClickListener mLongClickListener;

    ViewHolder mholder;

    Context mContext;
    Utils utils;

    public Customer_Adaptor_FB(Context context, List<Customer_Model_FB> objects) {
        mContext = context;
        mItems = objects;
        utils = new Utils(context);
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        public TextView customer_name,netblnc,youwillgivtxt;
        public RecyclerView recyclerView_fb;

        public View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;

            customer_name = itemView.findViewById(R.id.tv_customer_name_full_business);
            recyclerView_fb = itemView.findViewById(R.id.recyclerview_transactions_fb);
            netblnc=itemView.findViewById(R.id.youwillgiveval2);
            youwillgivtxt=itemView.findViewById(R.id.youwillgivetxt2);

            LinearLayoutManager layoutManagerv=new LinearLayoutManager(mContext);

            layoutManagerv.setOrientation(RecyclerView.VERTICAL);
            recyclerView_fb.setLayoutManager(layoutManagerv);




            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null) {

                mClickListener.onItemClick(view, getAdapterPosition(), mItems.get(getAdapterPosition()), 0);

            }

        }


        @Override
        public boolean onLongClick(View v) {


            if (mLongClickListener != null)
                mLongClickListener.onItemLongClick
                        (v, mItems.get(getAdapterPosition()), getAdapterPosition());

            return true;
        }
    }

    @Override
    public int getItemCount() {

        Log.d("transactionssize3", mItems.size() + "");

        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Customer_Model_FB item = mItems.get(position);
        mholder = holder;


        mholder.customer_name.setText(item.customer_name);
        mholder.netblnc.setText(item.net_balance);



        String sent=item.total_sent.replaceAll(",","");
        String rec=item.total_recive.replaceAll(",","");

            if(Integer.parseInt(sent)>Integer.parseInt(rec)) {
                mholder.youwillgivtxt.setText("You will Recieve");
                mholder.netblnc.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
            }
            else {
                mholder.youwillgivtxt.setText("You will Give");
                mholder.netblnc.setTextColor(ContextCompat.getColor(mContext,R.color.appTextColorPurple));
            }


            mholder.netblnc.setText(utils.afterTextChanged(mholder.netblnc.getText().toString()));

        mholder.recyclerView_fb.setAdapter(new TransactionsAdaptor(mContext,item.getMyTransactions()));

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_transactions_report, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void setLongClickListener(LongClickListener itemClickListener) {
        this.mLongClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position, Customer_Model_FB file, int realposition);

        void onItemRename(View view, int position, File file, int realposition);

        void onItemShare(View view, int position, Customer_Model_FB file, int realposition);

        void onItemDelete(View view, int position, Customer_Model_FB file, int realposition);

        void onDetailsRequested(Customer_Model_FB from, int posd);

    }

    public interface LongClickListener {
        void onItemLongClick(View view, Customer_Model_FB item, int pos);
    }

    public void updatelist(List<Customer_Model_FB> objects) {
        this.mItems = objects;
        notifyDataSetChanged();
    }


}