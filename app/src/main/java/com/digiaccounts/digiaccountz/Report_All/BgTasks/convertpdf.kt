package com.digiaccounts.digiaccountz.Report_All.BgTasks

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.AsyncTask
import android.os.Environment
import android.os.Handler
import android.widget.Toast
import com.digiaccounts.digiaccountz.Report_All.Activities.View_Report
import com.digiaccounts.digiaccountz.Report_All.Model.BgTaskModel
import com.digiaccounts.digiaccountz.Report_All.Others.Utils
import java.io.File
import java.io.FileOutputStream

public class convertpdf() : AsyncTask<BgTaskModel, Integer, BgTaskModel>() {
    public var context: Context? = null
    public var utilss: Utils? = null
    public var progressDialog: ProgressDialog? = null
    var pgnum = 0
    fun AssignContext(c: Context, pgnum: Int) {
        context = c;
        utilss = Utils(c)
        this.pgnum = pgnum
    }

    override fun doInBackground(vararg params: BgTaskModel?): BgTaskModel {
        var pdfdoc = PdfDocument()
        var nw = ""
        nw = params[0]!!.customername+" "+utilss!!.date+" "+utilss!!.time
        var saving = nw!!.replace(" ", "_")


        val pageInfo: PdfDocument.PageInfo = PdfDocument.PageInfo.Builder(
                params[0]!!.b.width,
                params[0]!!.b.height, 1
        ).create()


        val page: PdfDocument.Page = pdfdoc.startPage(pageInfo)
        val canvas: Canvas = page.canvas
        canvas.drawBitmap(params[0]!!.b, 0f, 0f, null)
        pdfdoc.finishPage(page)


        val dir = File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Cashify", saving + ".pdf")
        if (!dir.parentFile.parentFile.exists())
            dir.parentFile.parentFile.mkdir()
        if (!dir.parentFile.exists())
            dir.parentFile.mkdir()

        pdfdoc.writeTo(FileOutputStream(dir))
        pdfdoc.close()


        val handler = Handler(context!!.mainLooper)
        handler.post(Runnable {
            Toast.makeText(context, "saved to " + dir + " ", Toast.LENGTH_SHORT).show()
        })
        return params[0]!!
    }


    override fun onPostExecute(result: BgTaskModel?) {
        super.onPostExecute(result)
        progressDialog!!.dismiss()
        utilss!!.SwitchActivity(View_Report::class.java)
    }

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog = ProgressDialog(context)
        progressDialog!!.setMessage("Rendering Document ...")
        progressDialog!!.show()
    }

}

