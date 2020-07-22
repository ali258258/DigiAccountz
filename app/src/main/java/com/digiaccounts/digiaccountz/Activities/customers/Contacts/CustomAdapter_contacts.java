package com.digiaccounts.digiaccountz.Activities.customers.Contacts;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.digiaccounts.digiaccountz.Activities.busineses.ui.home.CustomerListBean;
import com.digiaccounts.digiaccountz.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CustomAdapter_contacts extends BaseAdapter implements Filterable {

    Context context;
    private ArrayList<ContactsBean> list;
    private ArrayList<ContactsBean> itemsModelListFiltered;
    private static LayoutInflater inflater=null;

    public CustomAdapter_contacts(Context c, ArrayList<ContactsBean> l) {
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
            vi = inflater.inflate(R.layout.contacts_customlist, null);

        TextView name = (TextView)vi.findViewById(R.id.contactnameTv); // title
        TextView number = (TextView)vi.findViewById(R.id.contactnumberTv); // artist name


        name.setText(itemsModelListFiltered.get(position).getName());
        number.setText(itemsModelListFiltered.get(position).getNumber());


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
                    ArrayList<ContactsBean> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (ContactsBean itemsModel : list) {
                        if (itemsModel.getName().toLowerCase().contains(searchStr)) {
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

                itemsModelListFiltered = (ArrayList<ContactsBean>) results.values;
                notifyDataSetChanged();

            }
        };

        return filter;
    }
}
