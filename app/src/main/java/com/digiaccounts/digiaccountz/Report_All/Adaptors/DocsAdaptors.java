package com.digiaccounts.digiaccountz.Report_All.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;


import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.Report_All.Others.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class DocsAdaptors
        extends RecyclerView.Adapter<DocsAdaptors.ViewHolder> {


    private List<File> mItems;



    private ItemClickListener mClickListener;
    private LongClickListener mLongClickListener;

    ViewHolder mholder;

    Context mContext;
    Utils utils;

    public DocsAdaptors(Context context, List<File> objects) {
        mContext = context;
        mItems = objects;
        utils = new Utils(context);
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        public TextView foldertitle, numvideos, tvfn;

        public LinearLayout sdfg;
        public View rootView;
        ImageView tvthumnail;

        TextView addbtn;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;

            foldertitle = itemView.findViewById(R.id.foldertitle);
            tvthumnail = itemView.findViewById(R.id.tvthumnail);
            sdfg=itemView.findViewById(R.id.asdfg);


            addbtn = itemView.findViewById(R.id.addbtn);


            tvfn = itemView.findViewById(R.id.foldername);


            numvideos = itemView.findViewById(R.id.numvideos);
            itemView.setOnClickListener(this);
            addbtn.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null) {

                if (view == addbtn) {

                    ShowMenuOptions(rootView,view, getAdapterPosition());
                } else {

                    mClickListener.onItemClick(view, getAdapterPosition(), mItems.get(getAdapterPosition()), 0);

                }
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

    private void ShowMenuOptions(final View rootView,final View viewById, final int pos) {
        PopupMenu popup = new PopupMenu(mContext, viewById.findViewById(R.id.addbtn));
        //inflating menu from xml resource
        popup.inflate(R.menu.doc_options);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        mClickListener.onItemDelete(rootView,pos,mItems.get(pos), pos);
                        return true;
                    case R.id.share:
                        mClickListener.onItemShare(rootView,pos,mItems.get(pos), pos);
                        return true;

/*                    case R.id.rename:
                        mClickListener.onItemRename(rootView,pos,mItems.get(pos), pos);
                        return true;

 */
                    case R.id.details:
                        mClickListener.onDetailsRequested(mItems.get(pos), pos);
                        return true;
                   /* case R.id.select:
                        mLongClickListener.onItemLongClick
                                (rootView,mItems.get(pos),pos);
                        return true;
                     */
                    default:

                        return false;
                }
            }
        });
        //displaying the popup
        popup.show();
    }


    @Override
    public int getItemCount() {

        return mItems.size();


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        File item = mItems.get(position);
        mholder = holder;
        File f=item;

        String mylastmodified;

        try {
            holder.tvthumnail.setImageResource(R.drawable.appplogo);

        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.foldertitle.setText("" + f.getName());
        holder.tvfn.setText("PDF");






     mylastmodified=""+new Date(f.lastModified()).toString();


        try {
            try {

                List<String> list = new ArrayList<String>(Arrays.asList(mylastmodified.split(" ")));

                list.remove(3);
                list.remove(3);


               mylastmodified=  ""+list.get(0)
                        + " " +list.get(1)+" "+list.get(2)+" "+list.get(3);

            } catch (Exception e) {
                e.printStackTrace();
                mylastmodified=mylastmodified+"";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        holder.numvideos.setText(mylastmodified.toString());

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_item, parent, false);
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
        void onItemClick(View view, int position, File file, int realposition);

        void onItemRename(View view, int position, File file, int realposition);

        void onItemShare(View view, int position, File file, int realposition);

        void onItemDelete(View view, int position, File file, int realposition);

        void onDetailsRequested(File from, int posd);

    }

    public interface LongClickListener {
        void onItemLongClick(View view, File item, int pos);
    }

    public void updatelist(List<File> objects) {
        this.mItems = objects;
        notifyDataSetChanged();
    }

}