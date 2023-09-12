package com.example.chatapplication.patients

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.chatapplication.R
import com.example.chatapplication.doctors.DoctorsListActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION", "UNREACHABLE_CODE")
class SignUpPatients : AppCompatActivity() {

    private lateinit var edtName: TextInputEditText
    private lateinit var edtEmail: TextInputEditText
    private lateinit var edtPhone: TextInputEditText
    private lateinit var edtCountry: TextInputEditText
    private lateinit var edtCity: TextInputEditText
    private lateinit var edtPassword: TextInputEditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var edtNameLayout: TextInputLayout
    private lateinit var edtPasswordLayout: TextInputLayout
    private lateinit var edtPhoneLayout: TextInputLayout
    private lateinit var edtEmailLayout:TextInputLayout


    @SuppressLint("MissingInflatedId", "ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //emailFocusListener()
       // phoneFocusListener()
       // passwordFocusListener()

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtName = findViewById(R.id.edit_name)
        edtEmail = findViewById(R.id.edit_email)
        edtPhone = findViewById(R.id.edit_phone)
        edtCountry = findViewById(R.id.edit_country)
        edtCity = findViewById(R.id.edit_city)
        edtPassword = findViewById(R.id.edit_password)
        btnSignUp = findViewById(R.id.btnSignUp)
        edtEmailLayout=findViewById(R.id.edit_email_layout)
        edtNameLayout = findViewById(R.id.edit_name_layout)
        edtPasswordLayout = findViewById(R.id.edit_password_layout)
        edtPhoneLayout = findViewById(R.id.edit_phone_layout)
        
        edtName.doOnTextChanged { text, _, _, _ ->
            if (text!!.length>25){
                edtNameLayout.error = "No more!"
            }else if (text.length <=25)
                edtNameLayout.error = null
        }

        edtPhone.doOnTextChanged { text, _, _, _ ->
           if (text!!.length == 10){
               edtPhoneLayout.error = null
           }
            else if (text.length <  10) {
               edtPhoneLayout.error = "Please enter a valid phone number"
           }
           else if (text.length >  10) {
               edtPhoneLayout.error = "Must be 10 Digits"
           }
        }
        edtEmail.doOnTextChanged { text, _, _, _ ->
            if (!Patterns.EMAIL_ADDRESS.matcher(text!!).matches()) {
                edtEmailLayout.error = "Invalid Email Address"
            }else {
                edtEmailLayout.error = null
            }
        }
        edtPassword.doOnTextChanged { text, _, _, _ ->

            if (text != null) {
                if (!text.matches(".*[A-Z].*".toRegex())){
                    edtPasswordLayout.error =  "Must Contain 1 Upper-case Character"
                } else if (!text.matches(".*[a-z].*".toRegex())){
                    edtPasswordLayout.error =  "Must Contain 1 Lower-case Character"
                } else if (!text.matches(".*[?=.*@$#!%&].*".toRegex())){
                    edtPasswordLayout.error =  "Must Contain 1 Special Character "
                } else if (text.length < 8){
                    edtPasswordLayout.error =  "Minimum 8 Character Password"
                } else {
                    edtPasswordLayout.error  = null
                }
            }
        }



        btnSignUp.setOnClickListener {
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val phone = edtPhone.text.toString()
            val country = edtCountry.text.toString()
            val city = edtCity.text.toString()
            val password = edtPassword.text.toString()

            signUp(name, email, phone, country, city, password)
        }

        edtName.addTextChangedListener(signUpTextWatcher)
        edtEmail.addTextChangedListener(signUpTextWatcher)
        edtPhone.addTextChangedListener(signUpTextWatcher)
        edtCountry.addTextChangedListener(signUpTextWatcher)
        edtCity.addTextChangedListener(signUpTextWatcher)
        edtPassword.addTextChangedListener(signUpTextWatcher)
    }


/*
    private fun emailFocusListener() {
        edit_email.setOnFocusChangeListener { _, focused->
            if (!focused){
                edtEmailContainer.helperText = validEmail()

            }
        }
        return
    }
    private fun phoneFocusListener(){
        edit_phone.setOnFocusChangeListener { _, focused ->
            if (!focused){
                edtPhoneContainer.helperText = validPhone()
            }
        }

    }
    private fun passwordFocusListener() {
        edit_password.setOnFocusChangeListener { _, focused ->
            if (!focused){
                edtPasswordContainer.helperText = validPassword()
            }
        }

    }

    private fun validEmail(): String? {
        val emailText = edit_email.text.toString()
        return if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            "Invalid Email Address"
        }else{
            return null
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun validPassword(): String? {
        val passwordText = edit_password.text.toString()
        if (passwordText.length<8){
            return "Minimum 8 Character Password"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())){
            return "Must Contain 1 Upper-case Character"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())){
            return "Must Contain 1 Lower-case Character"
        }
        if (!passwordText.matches(".*[?=.*@$!%&].*".toRegex())){
            return "Must Contain 1 Special Character "
        }
        return null
    }
    private fun validPhone():String?{
        val phoneText = edit_phone.text.toString()
        if (!phoneText.matches(".*[0-9].*".toRegex())){
           return "Must be all Digits"
        }
        if(phoneText.length != 10){
          return  "Must be 10 Digits"
        }
        return null
    }

 */

    private val signUpTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val editName = edtName.text.toString().trim()
            val editEmail = edtEmail.text.toString().trim()
            val editPhone = edtPhone.text.toString().trim()
            val editCountry = edtCountry.text.toString().trim()
            val editCity = edtCity.text.toString().trim()
            val editPassword = edtPassword.text.toString().trim()

            btnSignUp.isEnabled =
                editName.isNotEmpty() && editEmail.isNotEmpty() &&
                        editPhone.isNotEmpty() && editCountry.isNotEmpty() &&
                        editCity.isNotEmpty() && editPassword.isNotEmpty()
                    //    validEmail().isNullOrBlank() && validPassword().isNullOrBlank() && validPhone().isNullOrBlank()

        }

        override fun afterTextChanged(s: Editable?) {

        }

    }

    private fun signUp(
        name: String,
        email: String,
        phone: String,
        country: String,
        city: String,
        password: String,
    ) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, phone, country, city, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUpPatients, DoctorsListActivity::class.java)
                    finish()
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left)
                } else {
                    Toast.makeText(this@SignUpPatients, "Some error occurred! Check your email", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addUserToDatabase(
        name: String,
        email: String,
        phone: String,
        country: String,
        city: String,
        uid: String
    ) {
        mDbRef = FirebaseDatabase.getInstance().reference

        mDbRef.child("user").child(uid).setValue(Patient(name, email, phone, country, city, uid, patientProfileImage = null))

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right)
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}