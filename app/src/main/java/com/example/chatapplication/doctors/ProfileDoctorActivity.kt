package com.example.chatapplication.doctors

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.chatapplication.LogIn
import com.example.chatapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class ProfileDoctorActivity : AppCompatActivity() {

    private lateinit var logOutButton:Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var userId: FirebaseUser
    private lateinit var deleteAccountButton:Button
    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_doctor)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        userId = FirebaseAuth.getInstance().currentUser!!


        logOutButton=findViewById(R.id.button_log_out)
        logOutButton.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this, LogIn::class.java)
            finish()
            startActivity(intent)
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right)

        }

      /*  deleteAccountButton=findViewById(R.id.button_delete_account)
        deleteAccountButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure?")
            builder.setMessage(
                "Deleting this account will result in completely removing your " +
                        "account from system and you won't be able to access the app."
            )
                .setCancelable(false)
                .setPositiveButton(
                    "Delete"
                ) { _, _ ->
                    this.finish()
                    val user = Firebase.auth.currentUser!!
                    user.delete()

                    database.child("user").child(userId).removeValue()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                Toast.makeText(
                                    this,
                                    "Account Deleted",
                                    Toast.LENGTH_LONG
                                ).show()
                                val intent = Intent(this, LogIn::class.java)
                                finish()
                                startActivity(intent)
                                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right)

                            }
                        }

                }
                .setNegativeButton(
                    "Dismiss"
                ) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }

       */
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
    }
}