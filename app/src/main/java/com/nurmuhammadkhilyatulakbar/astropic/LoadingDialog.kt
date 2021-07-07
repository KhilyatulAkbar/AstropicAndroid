package com.nurmuhammadkhilyatulakbar.astropic

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.progress_dialog.view.*

class LoadingDialog {
    private lateinit var dialog: Dialog

    fun progressDialog(context: Activity, text: String): Dialog {
        dialog = Dialog(context)
        val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(false)
        inflate.tvText.text = text
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        return dialog
    }

    fun showProgressDialog(context: Activity, text: String) {
        progressDialog(context, text).show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }
}