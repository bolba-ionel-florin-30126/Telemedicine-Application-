package com.example.chatapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

/*  val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
           logOutButton=findViewById(R.id.log_out)
           logOutButton.setOnClickListener {
               drawerLayout.closeDrawer(GravityCompat.END)
               mAuth.signOut()
               val intent = Intent(this, LogIn::class.java)
               finish()
               startActivity(intent)
               overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right)

           }
           deleteAccountButton=findViewById(R.id.delete_account)
           deleteAccountButton.setOnClickListener {
               drawerLayout.closeDrawer(GravityCompat.START)
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

