package com.example.chatapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapplication.doctors.DoctorsListActivity
import com.example.chatapplication.patients.PatientsListActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class LogIn : AppCompatActivity() {

    private lateinit var edtEmail: TextInputEditText
    private lateinit var edtPassword: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var forgotPassword: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtEmail = findViewById(R.id.edit_email)
        edtPassword = findViewById(R.id.edit_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        forgotPassword = findViewById(R.id.forgot_password)


        edtEmail.addTextChangedListener(loginTextWatcher)
        edtPassword.addTextChangedListener(loginTextWatcher)

        btnSignUp.setOnClickListener {
            val intent = Intent(this, ChooseWhatYouAreActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left)
        }

        btnLogin.setOnClickListener {

            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email, password)
        }
        forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left)
        }
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val editEmail = edtEmail.text.toString().trim()
            val editPassword = edtPassword.text.toString().trim()

            btnLogin.isEnabled = editEmail.isNotEmpty() && editPassword.isNotEmpty()

        }

        override fun afterTextChanged(s: Editable?) {

        }

    }

    private fun login(email: String, password: String ) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && mAuth.currentUser!!.isEmailVerified){

                    val intent = Intent(this@LogIn, PatientsListActivity::class.java)
                    finish()
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left)
                }else if(task.isSuccessful){
                    val intent = Intent(this@LogIn, DoctorsListActivity::class.java)
                    finish()
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left)
                }
                else {
                    Toast.makeText(
                        this@LogIn,
                        "The username or password you entered is incorrect.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
