package com.digiaccounts.digiaccountz.Report_All.BgTasks

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.digiaccounts.digiaccountz.Report_All.Interfaces.returnallfiles
import com.digiaccounts.digiaccountz.Report_All.Others.Utils
import java.io.File


public class getallfiles() : AsyncTask<String, Integer, List<File>>() {
    public var context: Context? = null
    public var utilss: Utils? = null

  lateinit var rf: returnallfiles
    lateinit var pd:ProgressDialog

    fun AssignContext(c: Context,rf: returnallfiles) {
        context = c;
        utilss =
                Utils(
                        c
                )
        this.rf=rf
        pd= ProgressDialog(c)
        pd.setMessage("Wait for a moment .... ")

    }




    override fun doInBackground(vararg params: String?): List<File> {





        var listfiles:List<File>

        listfiles=ArrayList<File>()
        Log.d("Files", "Path: ${params[0]!!}")
        val directory = File(params[0]!!)
        val files = directory.listFiles()
        Log.d("Files", "Size: " + files.size)
        for (i in files.indices) {
            Log.d("FilesNames", "FileName:" + files[i].name)

            listfiles.add(files[i])


        }

        return listfiles
    }


    override fun onPostExecute(result: List<File>?) {
        super.onPostExecute(result)

        pd.dismiss()
        rf.returnfiles(result!!)

    }

    override fun onPreExecute() {
        super.onPreExecute()

        pd.show()

    }
}

