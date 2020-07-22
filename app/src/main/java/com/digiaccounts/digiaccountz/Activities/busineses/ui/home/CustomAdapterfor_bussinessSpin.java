package com.digiaccounts.digiaccountz.Activities.busineses.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessTable;

import java.util.ArrayList;

public class CustomAdapterfor_bussinessSpin extends BaseAdapter {


    Context context;
    private ArrayList<BusinessTableBean> list;
    private static LayoutInflater inflater=null;

    public CustomAdapterfor_bussinessSpin(Context c, ArrayList<BusinessTableBean> l) {
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
            vi = inflater.inflate(R.layout.customspinnerlayout_home, null);

        TextView nameeTv = (TextView)vi.findViewById(R.id.businessnameTv_inCspin); // title
        ImageView dropmv = (ImageView) vi.findViewById(R.id.mv_drop); // artist name


        nameeTv.setText(list.get(position).getBusinessname());


        return vi;
    }
}
