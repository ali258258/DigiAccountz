package com.digiaccounts.digiaccountz.Report_All.Adaptors;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.Report_All.Model.MyTransactions;
import com.digiaccounts.digiaccountz.Report_All.Others.Utils;

import java.io.File;
import java.util.List;


public class TransactionsAdaptor
        extends RecyclerView.Adapter<TransactionsAdaptor.ViewHolder> {


    private List<MyTransactions> mItems;



    private ItemClickListener mClickListener;
    private LongClickListener mLongClickListener;

    ViewHolder mholder;

    Context mContext;
    Utils utils;

    public TransactionsAdaptor(Context context, List<MyTransactions> objects) {

        Log.d("transactionssize2",objects.size()+"");

        mContext = context;
        mItems = objects;
        utils = new Utils(context);
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        public TextView id, type, amount,description;

        public View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;

            id = itemView.findViewById(R.id.tv_my_id);
            amount = itemView.findViewById(R.id.tv_my_transaction_amount);
            type = itemView.findViewById(R.id.tv_my_transaction_type);
            description = itemView.findViewById(R.id.tv_my_transaction_description);




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

        Log.d("transactionssize3",mItems.size()+"");

        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MyTransactions item = mItems.get(position);
        mholder = holder;

        Log.d("mynametr",position+"");
        mholder.type.setText(item.transaction_type);
        mholder.description.setText(item.transaction_description);
        mholder.amount.setText(item.transaction_amount);
        mholder.id.setText(item.transaction_date+"");


        String cooma_amount=utils.afterTextChanged(mholder.amount.getText().toString());

        mholder.amount.setText(cooma_amount.toString());

        Log.d("afteramount",cooma_amount);




    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_report, parent, false);
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
        void onItemClick(View view, int position, MyTransactions file, int realposition);

        void onItemRename(View view, int position, File file, int realposition);

        void onItemShare(View view, int position, MyTransactions file, int realposition);

        void onItemDelete(View view, int position, MyTransactions file, int realposition);

        void onDetailsRequested(MyTransactions from, int posd);

    }

    public interface LongClickListener {
        void onItemLongClick(View view, MyTransactions item, int pos);
    }

    public void updatelist(List<MyTransactions> objects) {
        this.mItems = objects;
        notifyDataSetChanged();
    }




}