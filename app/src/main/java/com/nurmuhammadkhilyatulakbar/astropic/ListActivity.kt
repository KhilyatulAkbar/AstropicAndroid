package com.nurmuhammadkhilyatulakbar.astropic

import android.app.Dialog
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ListActivity : AppCompatActivity() {

    var builder: AlertDialog.Builder? = null
    var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        builder = AlertDialog.Builder(this)
        builder!!.setView(R.layout.progress_dialog)
        dialog = builder!!.create()
        dialog?.setCancelable(false)

        ivBack.setOnClickListener { finish() }

        val type:String = intent.getStringExtra("type")!!

        setDialog(true)

        if(type == "keyword"){
            NetworkConfig().getService()
                .getByKeyword(intent.getStringExtra("keyword")!!, 30, 1, true)
                .enqueue(object : Callback<List<ApodModel>> {
                    override fun onFailure(call: Call<List<ApodModel>>, t: Throwable) {
                        setDialog(false)
                        Toast.makeText(this@ListActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<List<ApodModel>>,
                        response: Response<List<ApodModel>>
                    ) {
                        setDialog(false)
                        rvApod.adapter = ApodAdapter(response.body(),this@ListActivity)
                    }

                })
        }
        else{
            val endDateStr: String =
                SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)

            val today = Date()
            val cal: Calendar = GregorianCalendar()
            cal.time = today
            cal.add(Calendar.DAY_OF_MONTH, -30)

            val startDateStr: String =
                SimpleDateFormat("yyyy-MM-dd").format(cal.time)

            NetworkConfig().getService()
                .getThisWeekApod(startDateStr, endDateStr, true)
                .enqueue(object : Callback<List<ApodModel>> {
                    override fun onFailure(call: Call<List<ApodModel>>, t: Throwable) {
                        Toast.makeText(this@ListActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<List<ApodModel>>,
                        response: Response<List<ApodModel>>
                    ) {
                        setDialog(false)
                        rvApod.adapter = ApodAdapter(response.body(), this@ListActivity)
                    }

                })
        }


    }

    private fun setDialog(show: Boolean) {
        if (show) {
            dialog?.show()
            dialog?.window?.setLayout(600, RelativeLayout.LayoutParams.WRAP_CONTENT)
        }
        else {
            dialog?.dismiss()
        }
    }
}