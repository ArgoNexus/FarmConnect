package com.example.farmconnect

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.farmconnect.databinding.ProgressDialogBinding
import com.google.firebase.auth.FirebaseAuth

object Utils {
    private var dialog: AlertDialog? = null

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showDialog(context: Context, message: String) {
        val progress = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        progress.Message.text = message
        dialog = AlertDialog.Builder(context)
            .setView(progress.root)
            .setCancelable(false)
            .create()
        dialog?.show()
    }

    fun hideDialog() {
        dialog?.dismiss()
    }

    private var fireBaseAuthInstance: FirebaseAuth? = null
    fun getFirebaseAuthInstance(): FirebaseAuth {
        if (fireBaseAuthInstance == null) {
            fireBaseAuthInstance = FirebaseAuth.getInstance()
        }
        return fireBaseAuthInstance!!

    }
    fun getCurrentUserId(): String{
        return getFirebaseAuthInstance().currentUser!!.uid
    }
}