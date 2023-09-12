@file:Suppress("DEPRECATION")

package com.example.chatapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_profile.*

@Suppress("DEPRECATION", "UNREACHABLE_CODE")
class ProfileFragment : Fragment() {

    var auth:FirebaseAuth? = null
    var database:FirebaseDatabase? = null
    var storage:FirebaseStorage? = null

    val userInformation = Firebase.auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        log_out.setOnClickListener {
            logOut()
        }
        button_delete_account.setOnClickListener {
            deleteAccount()
        }

        userInformation?.let {
                val email = it.email
            val isEmailVerified = it.isEmailVerified
                email_current_user.text = email
                verified_current_user.text = isEmailVerified.toString()
        }


    }

    private fun deleteAccount() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are you sure?")
        builder.setMessage(
            "Deleting this account will result in completely removing your " +
                    "account from system and you won't be able to access the app."
        )
            .setCancelable(false)
            .setNegativeButton("Cancel")
            { dialog, _ ->
                // Handle the Cancel button click
                dialog.dismiss() // Close the dialog
            }
            .setPositiveButton(
                "Delete"
            ) { _, _ ->
                this.requireActivity().finish()
                val user = Firebase.auth.currentUser!!
                user.delete()
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                val database = FirebaseDatabase.getInstance().reference
                val userRef = userId?.let {
                    database.child("user").child(it)
                    database.child("doctors").child(it)
                }

                userRef?.removeValue()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(
                            context,
                            "Account Deleted",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent (context, LogIn::class.java)
                        activity?.finish()
                        startActivity(intent)
                        activity?.overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right)

                    }
                }

            }
        builder.show()
    }

    private fun logOut() {
        activity?.let{
            val intent = Intent (it, LogIn::class.java)
            it.startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right)
        }
    }

}
