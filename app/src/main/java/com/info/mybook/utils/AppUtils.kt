package com.info.mybook.utils

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.info.mybook.R

class AppUtils {

    companion object {
        fun errorShowSnackbar(context: Context, rootView: View, message: String) {
            val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
            // Customize the Snackbar
            snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.red))
            snackbar.setTextColor(ContextCompat.getColor(context, R.color.white))
            //snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.blue))
            snackbar.show()
        }

        fun successShowSnackbar(context: Context, rootView: View, message: String) {
            val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
            // Customize the Snackbar
            snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.green))
            snackbar.setTextColor(ContextCompat.getColor(context, R.color.white))
            //snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.blue))
            snackbar.show()
        }

        fun exitappShowSnackbar(context: Context, rootView: View, message: String) {
            val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
            // Customize the Snackbar
            snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.goodgrey))
            snackbar.setTextColor(ContextCompat.getColor(context, R.color.white))
            //snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.blue))
            snackbar.show()
        }

        private lateinit var pb : Dialog
        fun showProgressBar(context: Context)
        {
            pb = Dialog(context)
            pb.setContentView(R.layout.progress_bar)
            pb.setCancelable(false)
            pb.show()
        }

        fun hideProgressBar()
        {
            pb.dismiss()
        }
    }
}