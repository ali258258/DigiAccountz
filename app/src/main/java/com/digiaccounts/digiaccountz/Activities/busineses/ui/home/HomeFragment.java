package com.digiaccounts.digiaccountz.Activities.busineses.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.digiaccounts.digiaccountz.Activities.LoginActivity;
import com.digiaccounts.digiaccountz.Activities.MainActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.BusinessListActivity;
import com.digiaccounts.digiaccountz.Activities.busineses.HomeActivityWithDrawer;
import com.digiaccounts.digiaccountz.Activities.busineses.NewBusineses_Activity;
import com.digiaccounts.digiaccountz.Activities.busineses.TransactionAdd_RealActivity;
import com.digiaccounts.digiaccountz.Activities.callbacks.CreateCustomerCallbacks;
import com.digiaccounts.digiaccountz.Activities.callbacks.CreateTransactionCallback;
import com.digiaccounts.digiaccountz.Activities.callbacks.ReCreateTransactionsCallbacks;
import com.digiaccounts.digiaccountz.Activities.customers.Contacts.ContactsLoad;
import com.digiaccounts.digiaccountz.Activities.customers.CreateCustomerActivity;
import com.digiaccounts.digiaccountz.Activities.customers.CustomAdapter_customerListing;
import com.digiaccounts.digiaccountz.Activities.customers.CustomerListActivity;
import com.digiaccounts.digiaccountz.Activities.signups.AddCustomers_SupplierActivity;
import com.digiaccounts.digiaccountz.Activities.signups.SingupWithEmail_Activity;
import com.digiaccounts.digiaccountz.R;
import com.digiaccounts.digiaccountz.roomdatabase.tables.SigninWithemailTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.business.BusinessTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.customer.CustomerTable;
import com.digiaccounts.digiaccountz.roomdatabase.tables.loginmanage.LoginManageTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class HomeFragment extends Fragment implements CreateCustomerCallbacks, CreateTransactionCallback, ReCreateTransactionsCallbacks {

    private HomeViewModel homeViewModel;
    ListView lv;
    CustomAdapterforcustomerlist adap;
    Spinner businessnameSpin;
    CustomAdapterfor_bussinessSpin adapterbusiness;

    Context context;

    public static long currentBusinessID = 0;
    public static String currentBusinessCurrency = "";
    public static String currentBusinessName = "";

    ArrayList<CustomerListBean> Clist = new ArrayList<>();
    ArrayList<BusinessTableBean> Blist = new ArrayList<>();

    ImageView addMv;
    EditText searchEt_home;

    LinearLayout reportsBtn;
    RelativeLayout rootlayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();
        lv = root.findViewById(R.id.listmain);
        businessnameSpin = root.findViewById(R.id.businessnameSpin);
        rootlayout = root.findViewById(R.id.rootlay);
        searchEt_home = root.findViewById(R.id.searchEt_home);
        CreateCustomerActivity.setListenerCallback(this);
        TransactionAdd_RealActivity.setListenerCallbackforhome(this);
        CustomAdapter_customerListing.setListenerCallback2(this);

        final BusinessTable[] lisst = MainActivity.database.businessManageTable().loadAllBusiness();
        if (lisst != null && lisst.length > 0) {
            currentBusinessID = lisst[0].getId();
            currentBusinessCurrency = lisst[0].getBusinesscurrency();
            currentBusinessName = lisst[0].getBusinessname();
            for (int o = 0; o < lisst.length; o++) {
                Blist.add(new BusinessTableBean(lisst[o].getId(), lisst[o].getUserid(), lisst[o].getBusinessname(),
                        lisst[o].getTotalamount(), lisst[o].getTotalgiven(), lisst[o].getTotalrecieved(), lisst[o].getBusinesscurrency()));
            }
        }
        BusinessTableBean bvb = new BusinessTableBean(-1, 0, "Create New Business", "0", "0", "0", "");
        Blist.add(bvb);

        adapterbusiness = new CustomAdapterfor_bussinessSpin(getActivity(), Blist);
        businessnameSpin.setAdapter(adapterbusiness);
        if (HomeActivityWithDrawer.condition ==1) {
            businessnameSpin.setSelection((Blist.size() - 2));
        }

        CustomerTable customerList[] = MainActivity.database.customerManageTable().loadAllCustomersByBusinessID(currentBusinessID);
        for (int i = 0; i < customerList.length; i++) {
            Log.i("sdsfrrrgv", ":" + customerList[i].getId());
            CustomerListBean ob = new CustomerListBean(customerList[i].getFullname() + " (" + customerList[i].getCatagory() + ")", "Last Updated: " + customerList[i].getDatetime(), customerList[i].getCustomer_balance(), Color.parseColor("#ae09a5"), String.valueOf(customerList[i].getId()), String.valueOf(customerList[i].getBusinessid()), customerList[i].getCatagory());
            ob.setBalance(customerList[i].getCustomer_balance());
            ob.setYouwillget(customerList[i].getYouwillget_amount());
            ob.setYouwillgive(customerList[i].getYouwillgive_amount());
            if (Integer.parseInt(customerList[i].getYouwillget_amount()) >= Integer.parseInt(customerList[i].getYouwillgive_amount())) {
                ob.setColor(Color.parseColor("#ae09a5"));
            } else if (Integer.parseInt((customerList[i].getYouwillget_amount())) < Integer.parseInt(customerList[i].getYouwillgive_amount())) {
                ob.setColor(Color.parseColor("#0066ff"));
            }
            Clist.add(ob);

        }

        reportsBtn = root.findViewById(R.id.reportsBtn);
        addMv = root.findViewById(R.id.addBtn);
        adap = new CustomAdapterforcustomerlist(getActivity(), Clist);
        lv.setAdapter(adap);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                deletecustomerDailog(Long.parseLong(Clist.get(position).getCustomerid()),Long.parseLong(Clist.get(position).getBusinessid()));
                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CustomerListBean object = (CustomerListBean) adap.getItem(position);
                Log.i("rfvbgtyh", Clist.get(position).getBalance() + " : " + Clist.get(position).getBusinessid());
                Intent intent = new Intent(getActivity(), CustomerListActivity.class);
                intent.putExtra("customeridd", object.getCustomerid());
                intent.putExtra("customername", object.getNametitle());
                intent.putExtra("businessidd", object.getBusinessid());
                intent.putExtra("busnesscurrency", "" + currentBusinessCurrency);
                intent.putExtra("catagory", object.getCatagoty());
                intent.putExtra("cbalance", object.getBalance());
                intent.putExtra("cyouwillget", object.getYouwillget());
                intent.putExtra("cyouwillgive", object.getYouwillgive());
                startActivity(intent);

            }
        });


        businessnameSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (Blist.get(position).getId() == -1) {
                    startActivity(new Intent(getActivity(), NewBusineses_Activity.class));
                    getActivity().finish();
                } else {
                    currentBusinessID = Blist.get(position).getId();
                    currentBusinessCurrency = Blist.get(position).getBusinesscurrency();
                    currentBusinessName = Blist.get(position).getBusinessname();
                    Clist.clear();
                    CustomerTable customerList[] = MainActivity.database.customerManageTable().loadAllCustomersByBusinessID(Blist.get(position).getId());
                    for (int i = 0; i < customerList.length; i++) {
                        Log.i("sdsfrrrgv", ":" + customerList[i].getId());
                        CustomerListBean ob = new CustomerListBean(customerList[i].getFullname() + " (" + customerList[i].getCatagory() + ")", "Last Updated: " + customerList[i].getDatetime(), customerList[i].getCustomer_balance(), Color.parseColor("#ae09a5"), String.valueOf(customerList[i].getId()), String.valueOf(customerList[i].getBusinessid()), customerList[i].getCatagory());
                        ob.setBalance(customerList[i].getCustomer_balance());
                        ob.setYouwillget(customerList[i].getYouwillget_amount());
                        ob.setYouwillgive(customerList[i].getYouwillgive_amount());
                        if (Integer.parseInt(customerList[i].getYouwillget_amount()) >= Integer.parseInt(customerList[i].getYouwillgive_amount())) {
                            ob.setColor(Color.parseColor("#ae09a5"));
                        } else if (Integer.parseInt((customerList[i].getYouwillget_amount())) < Integer.parseInt(customerList[i].getYouwillgive_amount())) {
                            ob.setColor(Color.parseColor("#0066ff"));
                        }
                        Clist.add(ob);
                    }

                    adap.notifyDataSetChanged();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        showhintFirst();

        addMv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), CreateCustomerActivity.class));


            }
        });

        reportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessListActivity.ff=0;
                // Toast.makeText(getActivity(), "dd"+currentBusinessID, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BusinessListActivity.class);
                //   Toast.makeText(getActivity(), ""+currentBusinessID, Toast.LENGTH_SHORT).show();
                intent.putExtra("businessidd", "" + currentBusinessID);
                intent.putExtra("businessname", "" + currentBusinessName);
                startActivity(intent);

            }
        });


        searchEt_home.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adap.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void Callon(CustomerTable customerTable) {
        //  Toast.makeText(getActivity(), "call", Toast.LENGTH_SHORT).show();

        Clist.clear();
        CustomerTable customerList[] = MainActivity.database.customerManageTable().loadAllCustomersByBusinessID(currentBusinessID);
        for (int i = 0; i < customerList.length; i++) {
            Log.i("ikmlopouy", ":" + customerList[i].getId());
            //    Clist.add(new CustomerListBean(customerList[i].getFullname(),"Last Updated: "+customerList[i].getDatetime(),customerList[i].getCustomer_balance(),Color.parseColor("#ae09a5"),String.valueOf(customerList[i].getId()),String.valueOf(customerList[i].getBusinessid())));
            CustomerListBean ob = new CustomerListBean(customerList[i].getFullname() + " (" + customerList[i].getCatagory() + ")", "Last Updated: " + customerList[i].getDatetime(), customerList[i].getCustomer_balance(), Color.parseColor("#ae09a5"), String.valueOf(customerList[i].getId()), String.valueOf(customerList[i].getBusinessid()), customerList[i].getCatagory());
            ob.setBalance(customerList[i].getCustomer_balance());
            ob.setYouwillget(customerList[i].getYouwillget_amount());
            ob.setYouwillgive(customerList[i].getYouwillgive_amount());
            if (Integer.parseInt(customerList[i].getYouwillget_amount()) >= Integer.parseInt(customerList[i].getYouwillgive_amount())) {
                ob.setColor(Color.parseColor("#ae09a5"));
            } else if (Integer.parseInt((customerList[i].getYouwillget_amount())) < Integer.parseInt(customerList[i].getYouwillgive_amount())) {
                ob.setColor(Color.parseColor("#0066ff"));
            }
            Clist.add(ob);
        }

        // Clist.add(new CustomerListBean(customerTable.getFullname(),"Last Updated: "+customerTable.getDatetime(),customerTable.getCustomer_balance(),Color.parseColor("#ae09a5"),String.valueOf(customerTable.getId()),String.valueOf(customerTable.getBusinessid())));
        adap.notifyDataSetChanged();
    }

    @Override
    public void Callon(long balance, long youwillgive, long youwillget) {

        Clist.clear();
        CustomerTable customerList[] = MainActivity.database.customerManageTable().loadAllCustomersByBusinessID(currentBusinessID);
        for (int i = 0; i < customerList.length; i++) {
            Log.i("12yygy6", ":" + customerList[i].getId());
//           Clist.add(new CustomerListBean(customerList[i].getFullname(),"Last Updated: "+customerList[i].getDatetime(),customerList[i].getCustomer_balance(),Color.parseColor("#ae09a5"),String.valueOf(customerList[i].getId()),String.valueOf(customerList[i].getBusinessid())));
            CustomerListBean ob = new CustomerListBean(customerList[i].getFullname() + " (" + customerList[i].getCatagory() + ")", "Last Updated: " + customerList[i].getDatetime(), customerList[i].getCustomer_balance(), Color.parseColor("#ae09a5"), String.valueOf(customerList[i].getId()), String.valueOf(customerList[i].getBusinessid()), customerList[i].getCatagory());
            ob.setBalance(customerList[i].getCustomer_balance());
            ob.setYouwillget(customerList[i].getYouwillget_amount());
            ob.setYouwillgive(customerList[i].getYouwillgive_amount());
            if (Integer.parseInt(customerList[i].getYouwillget_amount()) >= Integer.parseInt(customerList[i].getYouwillgive_amount())) {
                ob.setColor(Color.parseColor("#ae09a5"));
            } else if (Integer.parseInt((customerList[i].getYouwillget_amount())) < Integer.parseInt(customerList[i].getYouwillgive_amount())) {
                ob.setColor(Color.parseColor("#0066ff"));
            }
            Clist.add(ob);
        }
        // Clist.add(new CustomerListBean(customerTable.getFullname(),"Last Updated: "+customerTable.getDatetime(),customerTable.getCustomer_balance(),Color.parseColor("#ae09a5"),String.valueOf(customerTable.getId()),String.valueOf(customerTable.getBusinessid())));
        adap.notifyDataSetChanged();

    }


    public void showhintFirst() {

        final SigninWithemailTable list[] = MainActivity.database.signinDetails().loadAllUsers();

        if (SingupWithEmail_Activity.checkfirst == 1) {
            new SimpleTooltip.Builder(getActivity())
                    .anchorView(addMv)
                    .text("Add customer or supplier")
                    .gravity(Gravity.TOP)
                    .textColor(Color.parseColor("#ffffff"))
                    .backgroundColor(getResources().getColor(R.color.screenbackgroundsBlue))
                    .animated(true)
                    .transparentOverlay(false)
                    .build()
                    .show();
        }
    }


    @Override
    public void Changes(String recieve, String get, String balance) {

        Clist.clear();
        CustomerTable customerList[] = MainActivity.database.customerManageTable().loadAllCustomersByBusinessID(currentBusinessID);
        for (int i = 0; i < customerList.length; i++) {
            Log.i("12yygy6", ":" + customerList[i].getId());
//           Clist.add(new CustomerListBean(customerList[i].getFullname(),"Last Updated: "+customerList[i].getDatetime(),customerList[i].getCustomer_balance(),Color.parseColor("#ae09a5"),String.valueOf(customerList[i].getId()),String.valueOf(customerList[i].getBusinessid())));
            CustomerListBean ob = new CustomerListBean(customerList[i].getFullname() + " (" + customerList[i].getCatagory() + ")", "Last Updated: " + customerList[i].getDatetime(), customerList[i].getCustomer_balance(), Color.parseColor("#ae09a5"), String.valueOf(customerList[i].getId()), String.valueOf(customerList[i].getBusinessid()), customerList[i].getCatagory());
            ob.setBalance(customerList[i].getCustomer_balance());
            ob.setYouwillget(customerList[i].getYouwillget_amount());
            ob.setYouwillgive(customerList[i].getYouwillgive_amount());
            if (Integer.parseInt(customerList[i].getYouwillget_amount()) >= Integer.parseInt(customerList[i].getYouwillgive_amount())) {
                ob.setColor(Color.parseColor("#ae09a5"));
            } else if (Integer.parseInt((customerList[i].getYouwillget_amount())) < Integer.parseInt(customerList[i].getYouwillgive_amount())) {
                ob.setColor(Color.parseColor("#0066ff"));
            }
            Clist.add(ob);
        }
        // Clist.add(new CustomerListBean(customerTable.getFullname(),"Last Updated: "+customerTable.getDatetime(),customerTable.getCustomer_balance(),Color.parseColor("#ae09a5"),String.valueOf(customerTable.getId()),String.valueOf(customerTable.getBusinessid())));
        adap.notifyDataSetChanged();

    }

    public void deletecustomerDailog(final long customerid , final long businessid){

        new AlertDialog.Builder(context)
                .setTitle("Confirmation")
                .setMessage("Do you want to delete this customer?")
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                customerdeletion(customerid,businessid);
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    public void customerdeletion(long customerid, long businessid){

        CustomerTable t =MainActivity.database.customerManageTable().loadCustomerusingID(customerid);
        long c_balance = Long.parseLong(t.getCustomer_balance());
        long c_youwillget = Long.parseLong(t.getYouwillget_amount()); //recieved
        long c_youwillgive = Long.parseLong(t.getYouwillgive_amount()); // sent

        BusinessTable b =MainActivity.database.businessManageTable().loadWithID(businessid);
        long b_balance = Long.parseLong(b.getTotalamount());
        long b_youwillget = Long.parseLong(b.getTotalrecieved()); //recieved
        long b_youwillgive = Long.parseLong(b.getTotalgiven()); // sent

        b_balance = b_balance-c_balance;
        b_youwillget = b_youwillget-c_youwillget;
        b_youwillgive = b_youwillgive-c_youwillgive;


        MainActivity.database.businessManageTable().UpdateAmountValuesBusiness(businessid,Long.toString(b_balance),Long.toString(b_youwillget),Long.toString(b_youwillgive));
        MainActivity.database.customerManageTable().deleteCustomerById(customerid);
        MainActivity.database.transactionManageTable().deleteTransactionBycustomerId(customerid);
        MainActivity.database.RemiderManageTable().deleteRemindersbycustomerid(customerid);


        NormalList();

    }


    public void NormalList(){
        Clist.clear();
        CustomerTable customerList[] = MainActivity.database.customerManageTable().loadAllCustomersByBusinessID(currentBusinessID);
        for (int i = 0; i < customerList.length; i++) {
            Log.i("12yygy6", ":" + customerList[i].getId());
//           Clist.add(new CustomerListBean(customerList[i].getFullname(),"Last Updated: "+customerList[i].getDatetime(),customerList[i].getCustomer_balance(),Color.parseColor("#ae09a5"),String.valueOf(customerList[i].getId()),String.valueOf(customerList[i].getBusinessid())));
            CustomerListBean ob = new CustomerListBean(customerList[i].getFullname() + " (" + customerList[i].getCatagory() + ")", "Last Updated: " + customerList[i].getDatetime(), customerList[i].getCustomer_balance(), Color.parseColor("#ae09a5"), String.valueOf(customerList[i].getId()), String.valueOf(customerList[i].getBusinessid()), customerList[i].getCatagory());
            ob.setBalance(customerList[i].getCustomer_balance());
            ob.setYouwillget(customerList[i].getYouwillget_amount());
            ob.setYouwillgive(customerList[i].getYouwillgive_amount());
            if (Integer.parseInt(customerList[i].getYouwillget_amount()) >= Integer.parseInt(customerList[i].getYouwillgive_amount())) {
                ob.setColor(Color.parseColor("#ae09a5"));
            } else if (Integer.parseInt((customerList[i].getYouwillget_amount())) < Integer.parseInt(customerList[i].getYouwillgive_amount())) {
                ob.setColor(Color.parseColor("#0066ff"));
            }
            Clist.add(ob);
        }
        // Clist.add(new CustomerListBean(customerTable.getFullname(),"Last Updated: "+customerTable.getDatetime(),customerTable.getCustomer_balance(),Color.parseColor("#ae09a5"),String.valueOf(customerTable.getId()),String.valueOf(customerTable.getBusinessid())));
        adap.notifyDataSetChanged();

    }

}
