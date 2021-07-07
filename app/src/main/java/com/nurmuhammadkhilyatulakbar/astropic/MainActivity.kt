package com.nurmuhammadkhilyatulakbar.astropic

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var builder: AlertDialog.Builder? = null
    var apod : ApodModel? = null
    var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        builder = AlertDialog.Builder(this)
        builder!!.setView(R.layout.progress_dialog)
        dialog = builder!!.create()
        dialog?.setCancelable(false)

        setDialog(true)

        cvToday.setOnClickListener {
            intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra("apod", apod)
            startActivity(intent)
        }

        val myCalendar = Calendar.getInstance()
        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "yyyy-MM-dd"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                etDate.setText(sdf.format(myCalendar.getTime()))
            }

        etDate.setOnClickListener {
            val dpd = DatePickerDialog(
                    this@MainActivity, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dpd.datePicker.maxDate = System.currentTimeMillis()
            dpd.show()
        }

        NetworkConfig().getService()
            .getApod(true)
            .enqueue(object : Callback<ApodModel> {
                override fun onFailure(call: Call<ApodModel>, t: Throwable) {
                    setDialog(false)
                    Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<ApodModel>,
                    response: Response<ApodModel>
                ) {
                    setDialog(false)
                    apod = response.body()
                    tvTitle.text = apod?.title
                    if(apod?.mediaType.equals("image")){
                        ivImage.load(apod?.url)
                    }
                    else if(apod?.mediaType.equals("video")){
                        ivImage.load(apod?.thumbnailUrl)
                    }
                }

            })

        btnSearchD.setOnClickListener {
            setDialog(true)
            NetworkConfig().getService()
                .getApodByDate(etDate.text.toString(), true)
                .enqueue(object : Callback<ApodModel> {
                    override fun onFailure(call: Call<ApodModel>, t: Throwable) {
                        setDialog(false)
                        Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<ApodModel>,
                        response: Response<ApodModel>
                    ) {
                        setDialog(false)
                        intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra("apod", response.body())
                        startActivity(intent)
                    }

                })
        }

        btnSearchK.setOnClickListener {
            intent = Intent(this, ListActivity::class.java)
            intent.putExtra("keyword", etKeyword.text.toString())
            intent.putExtra("type", "keyword")
            startActivity(intent)
        }

        cvLatest.setOnClickListener {
            intent = Intent(this, ListActivity::class.java)
            intent.putExtra("type", "latest")
            startActivity(intent)
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