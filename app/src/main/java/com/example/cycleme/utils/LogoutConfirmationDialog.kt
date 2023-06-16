package com.example.cycleme.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.cycleme.R

class LogoutConfirmationDialog : DialogFragment() {

    interface ConfirmationDialogListener {
        fun onConfirmLogout()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.logout_message_confirmation))
                .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                    val listener = activity as? ConfirmationDialogListener
                    listener?.onConfirmLogout()
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
