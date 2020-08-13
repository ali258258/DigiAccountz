package com.digiaccounts.digiaccountz.Report_All.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digiaccounts.digiaccountz.*
import com.digiaccounts.digiaccountz.Report_All.Adaptors.DocsAdaptors
import com.digiaccounts.digiaccountz.Report_All.BgTasks.getallfiles
import com.digiaccounts.digiaccountz.Report_All.Interfaces.returnallfiles
import com.digiaccounts.digiaccountz.Report_All.Model.Customer_Model_FB
import com.digiaccounts.digiaccountz.Report_All.Model.MyTransactions
import com.digiaccounts.digiaccountz.Report_All.Others.InformationDialog
import com.digiaccounts.digiaccountz.Report_All.Others.Utils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import kotlin.jvm.Throws


class View_Report : AppCompatActivity(),
        DocsAdaptors.ItemClickListener,
        DocsAdaptors.LongClickListener,
        returnallfiles {

    lateinit var utills: Utils
    lateinit var docsAdaptors: DocsAdaptors

    lateinit var recyclerViewv: RecyclerView
    lateinit var layoutManagerv: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view__report)

        var builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        recyclerViewv = findViewById(R.id.recyclerview1)
        layoutManagerv = LinearLayoutManager(this)

        layoutManagerv.orientation = LinearLayoutManager.VERTICAL
        recyclerViewv.layoutManager = layoutManagerv

        utills =
                Utils(
                        this
                )


var       gaf= getallfiles()

        gaf.AssignContext(this@View_Report,this@View_Report)

        gaf.execute(""+Environment.getExternalStorageDirectory().getAbsolutePath()+"/Cashify")

    }

    private fun SetVideosAdaptorNew(
            newlist: List<File>,
            loadnew: Boolean
    ) {
        if (loadnew) {

Log.d("mysize",""+newlist!!.size)
            docsAdaptors =
                    DocsAdaptors(
                            this@View_Report, newlist!!
                    )
            docsAdaptors.setClickListener(this@View_Report)
            docsAdaptors.setLongClickListener(this@View_Report)

            recyclerViewv.adapter = docsAdaptors



        } else {
            docsAdaptors.updatelist(newlist)
        }


    }

    override fun onDetailsRequested(from: File?, posd: Int) {

        var kk = from

        if (kk!!.exists()) {

            var inf = InformationDialog(this, kk!!)
            inf.show()
        } else {
            Toast.makeText(this@View_Report, "File not exists", Toast.LENGTH_SHORT).show();
        }

    }

    override fun onItemShare(view: View?, position: Int, file: File?, realposition: Int) {
        var outputFile = file
        var uri = Uri.fromFile(outputFile)
        var share = Intent()
        share.setAction(Intent.ACTION_SEND)
        share.setType("application/pdf")
        share.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(share)
    }

    override fun onItemDelete(view: View?, position: Int, file: File?, realposition: Int) {

        if (file!!.exists()) {
            file!!.delete()

            var       gaf= getallfiles()

            gaf.AssignContext(this@View_Report,this@View_Report)

            gaf.execute(""+Environment.getExternalStorageDirectory().getAbsolutePath()+"/Cashify")

            utills.showToastFromResource(R.string.file_deleted)
        }
    }

    override fun onItemRename(view: View?, position: Int, file: File?, realposition: Int) {

    }


    @Throws(IOException::class)
    fun copyFile(sourceFile: File?, destFile: File) {
        if (!destFile.parentFile.exists()) destFile.parentFile.mkdirs()
        if (!destFile.exists()) {
            destFile.createNewFile()
        }
        var source: FileChannel? = null
        var destination: FileChannel? = null
        try {
            source = FileInputStream(sourceFile).getChannel()
            destination = FileOutputStream(destFile).getChannel()
            destination.transferFrom(source, 0, source.size())
        } finally {
            if (source != null) {
                source.close()
            }
            if (destination != null) {
                destination.close()
            }
        }
    }

    override fun onItemClick(view: View?, position: Int, file: File?, realposition: Int) {


        Utils.final_Selected_path = file!!.absolutePath
        startActivity(
                Intent(
                        this,
                        ViewPdf::class.java
                )
        )

    }

    override fun onItemLongClick(view: View?, item: File?, pos: Int) {
     //   Toast.makeText(this@View_Report, "onItemLongClick", Toast.LENGTH_SHORT).show();

    }


    fun backbtn(view: View?) {
        onBackPressed()
    }

    override fun returnfiles(listfiles: List<File>) {

        SetVideosAdaptorNew(listfiles,true)
    }

    override fun return_transactions(listfiles: ArrayList<MyTransactions>) {

    }

    override fun return_all_customers_for_full_business(listfiles: ArrayList<Customer_Model_FB>) {

    }


}
