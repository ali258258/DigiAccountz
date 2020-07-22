package com.digiaccounts.digiaccountz.Activities.busineses;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.digiaccounts.digiaccountz.Activities.customers.customerDetailsBean;
import com.digiaccounts.digiaccountz.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CustomAdapter_BusinessListing extends BaseAdapter {


    Context context;
    private ArrayList<customerDetailsBean> list;
    private static LayoutInflater inflater=null;

    public CustomAdapter_BusinessListing(Context c, ArrayList<customerDetailsBean> l) {
        context = c;
        list=l;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.customitemforreports_business, null);

        TextView status = (TextView)vi.findViewById(R.id.statusTv); // title
        TextView date = (TextView)vi.findViewById(R.id.dateTv); // duration
        TextView amount = (TextView)vi.findViewById(R.id.amountTv); // duration
        TextView type = (TextView)vi.findViewById(R.id.typeTvv); // duration
        TextView comment = (TextView)vi.findViewById(R.id.commentTvv); // duration


        // Setting all values in listview
        type.setText(list.get(position).getType());
        comment.setText(list.get(position).getComment());
        status.setText(list.get(position).getType());
        date.setText(list.get(position).getDate().substring(0,10)); // substring
        amount.setText(bigDecimalData(list.get(position).getAmount()));
        status.setTextColor(list.get(position).getColor());
        amount.setTextColor(list.get(position).getColor());
        type.setTextColor(list.get(position).getColor());


        //        if (list.get(position).started_at.isEmpty() || list.get(position).started_at.equalsIgnoreCase("")){
//            //  reported.setText("Reported on: N/A");
//        }
//        else {
//            reported.setText(list.get(position).started_at);
//        }

        return vi;
    }

    public static String bigDecimalData(String data) {
        if (!TextUtils.isEmpty(data)) {
            BigDecimal bd = new BigDecimal(Double.parseDouble(data));
            DecimalFormat df = new DecimalFormat("#,###,###");
            return df.format(bd);
        }
        return "";
    }
}
