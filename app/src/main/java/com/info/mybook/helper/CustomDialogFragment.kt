package com.info.mybook.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.info.mybook.R

class CustomDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.custom_dialog_layout, container, false)

        val btnClose: Button = view.findViewById(R.id.btn_yes)

        btnClose.setOnClickListener {
            // Close the dialog when the button is clicked
            dismiss()
        }

        return view
    }
}
