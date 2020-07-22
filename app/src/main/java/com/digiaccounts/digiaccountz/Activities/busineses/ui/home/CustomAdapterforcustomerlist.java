package com.digiaccounts.digiaccountz.Activities.busineses.ui.home;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.digiaccounts.digiaccountz.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CustomAdapterforcustomerlist extends BaseAdapter implements Filterable {


    Context context;
    private ArrayList<CustomerListBean> list;
    private ArrayList<CustomerListBean> itemsModelListFiltered;
    private static LayoutInflater inflater=null;

    public CustomAdapterforcustomerlist(Context c, ArrayList<CustomerListBean> l) {
        context = c;
        list=l;
        itemsModelListFiltered = l;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return itemsModelListFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.customlay_customersshow, null);

        TextView memo = (TextView)vi.findViewById(R.id.nametitleTv); // title
        TextView date = (TextView)vi.findViewById(R.id.lastupdatedTv); // artist name
        TextView price = (TextView)vi.findViewById(R.id.amountTv); // duration


        // Setting all values in listview
        memo.setText(itemsModelListFiltered.get(position).getNametitle());
    //    price.setText(list.get(position).getPrice());
      //  Log.i("cdfdfdeer4","->"+Math.abs(Integer.parseInt(list.get(position).getPrice())));
        price.setText(bigDecimalData(""+Math.abs(Integer.parseInt(itemsModelListFiltered.get(position).getPrice()))));
        date.setText(itemsModelListFiltered.get(position).getDate());
        price.setTextColor(itemsModelListFiltered.get(position).getColor());
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

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = list.size();
                    filterResults.values = list;

                }else {
                    ArrayList<CustomerListBean> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (CustomerListBean itemsModel : list) {
                        if (itemsModel.getNametitle().toLowerCase().contains(searchStr)) {
                            resultsModel.add(itemsModel);


                        }
                    }
                    filterResults.count = resultsModel.size();
                    filterResults.values = resultsModel;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                itemsModelListFiltered = (ArrayList<CustomerListBean>) results.values;
                notifyDataSetChanged();

            }
        };

        return filter;
    }
}
