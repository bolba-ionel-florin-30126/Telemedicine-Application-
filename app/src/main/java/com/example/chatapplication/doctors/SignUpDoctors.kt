package com.example.chatapplication.doctors

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.chatapplication.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
class SignUpDoctors : AppCompatActivity() {

    private lateinit var edtNameDoctors: TextInputEditText
    private lateinit var edtEmailDoctors: TextInputEditText
    private lateinit var edtPhoneDoctors: TextInputEditText
    private lateinit var edtCountryDoctors: TextInputEditText
    private lateinit var edtCityDoctors: TextInputEditText
    private lateinit var edtHospitalDoctors: TextInputEditText
    private lateinit var edtDepartmentDoctors: TextInputEditText
    private lateinit var edtPasswordDoctors: TextInputEditText
    private lateinit var btnSignUpDoctors: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var edtNameDoctorLayout: TextInputLayout
    private lateinit var edtEmailDoctorLayout: TextInputLayout
    private lateinit var edtPhoneDoctorLayout: TextInputLayout
    private lateinit var edtPasswordDoctorLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_doctors)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtNameDoctors = findViewById(R.id.edit_name_doctors)
        edtEmailDoctors = findViewById(R.id.edit_email_doctors)
        edtPhoneDoctors = findViewById(R.id.edit_phone_doctors)
        edtCountryDoctors = findViewById(R.id.edit_country_doctors)
        edtCityDoctors = findViewById(R.id.edit_city_doctors)
        edtHospitalDoctors = findViewById(R.id.edit_hospital_doctors)
        edtDepartmentDoctors = findViewById(R.id.edit_department_doctors)
        edtPasswordDoctors = findViewById(R.id.edit_password_doctors)
        btnSignUpDoctors = findViewById(R.id.btnSignUpDoctors)
        edtNameDoctorLayout = findViewById(R.id.edit_name_doctor_layout)
        edtEmailDoctorLayout = findViewById(R.id.edit_email_doctor_layout)
        edtPhoneDoctorLayout = findViewById(R.id.edit_phone_doctor_layout)
        edtPasswordDoctorLayout = findViewById(R.id.edit_password_doctor_layout)

        edtNameDoctors.doOnTextChanged{ text, _, _, _ ->
            if (text!!.length>25){
                edtNameDoctorLayout.error = "No more!"
            }else if (text.length <=25)
                edtNameDoctorLayout.error = null
        }

        edtEmailDoctors.doOnTextChanged { text, _, _, _ ->
            if (!Patterns.EMAIL_ADDRESS.matcher(text!!).matches()) {
                edtEmailDoctorLayout.error = "Invalid Email Address"
            }else {
                edtEmailDoctorLayout.error = null
            }
        }

        edtPhoneDoctors.doOnTextChanged { text, _, _, _ ->
            if (text!!.length == 10){
                edtPhoneDoctorLayout.error = null
            }
            else if (text.length <  10) {
                edtPhoneDoctorLayout.error = "Please enter a valid phone number"
            }
            else if (text.length >  10) {
                edtPhoneDoctorLayout.error = "Must be 10 Digits"
            }
        }

        edtPasswordDoctors.doOnTextChanged { text, _, _, _ ->

            if (text != null) {
                if (!text.matches(".*[A-Z].*".toRegex())){
                    edtPasswordDoctorLayout.error =  "Must Contain 1 Upper-case Character"
                } else if (!text.matches(".*[a-z].*".toRegex())){
                    edtPasswordDoctorLayout.error =  "Must Contain 1 Lower-case Character"
                } else if (!text.matches(".*[?=.*@$#!%&].*".toRegex())){
                    edtPasswordDoctorLayout.error =  "Must Contain 1 Special Character "
                } else if (text.length < 8){
                    edtPasswordDoctorLayout.error =  "Minimum 8 Character Password"
                } else {
                    edtPasswordDoctorLayout.error  = null
                }
            }
        }


        btnSignUpDoctors.setOnClickListener {
            val name= edtNameDoctors.text.toString()
            val email= edtEmailDoctors.text.toString()
            val phone= edtPhoneDoctors.text.toString()
            val country=edtCountryDoctors.text.toString()
            val city=edtCityDoctors.text.toString()
            val hospital=edtHospitalDoctors.text.toString()
            val department=edtDepartmentDoctors.text.toString()
            val password = edtPasswordDoctors.text.toString()

            signUpDoctors(name,email,phone,country,city,hospital,department,password)
        }

        edtNameDoctors.addTextChangedListener(signUpTextWatcher)
        edtEmailDoctors.addTextChangedListener(signUpTextWatcher)
        edtPhoneDoctors.addTextChangedListener(signUpTextWatcher)
        edtCountryDoctors.addTextChangedListener(signUpTextWatcher)
        edtCityDoctors.addTextChangedListener(signUpTextWatcher)
        edtHospitalDoctors.addTextChangedListener(signUpTextWatcher)
        edtDepartmentDoctors.addTextChangedListener(signUpTextWatcher)
        edtPasswordDoctors.addTextChangedListener(signUpTextWatcher)
    }
    private val signUpTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val editName= edtNameDoctors.text.toString().trim()
            val editEmail = edtEmailDoctors.text.toString().trim()
            val editPhone = edtPhoneDoctors.text.toString().trim()
            val editCountry = edtCountryDoctors.text.toString().trim()
            val editCity = edtCityDoctors.text.toString().trim()
            val editHospital = edtHospitalDoctors.text.toString().trim()
            val editDepartment = edtDepartmentDoctors.text.toString().trim()
            val editPassword = edtPasswordDoctors.text.toString().trim()

            btnSignUpDoctors.isEnabled = editName.isNotEmpty() && editEmail.isNotEmpty() && editPhone.isNotEmpty() && editCountry.isNotEmpty() && editCity.isNotEmpty() && editHospital.isNotEmpty() && editDepartment.isNotEmpty() && editPassword.isNotEmpty()

        }

        override fun afterTextChanged(s: Editable?) {

        }

    }

    private fun signUpDoctors(
        name: String,
        email: String,
        phone: String,
        country: String,
        city: String,
        hospital: String,
        department: String,
        password: String,
    ) {

        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){

                    mAuth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
                        Toast.makeText(this@SignUpDoctors,"Check your Email", Toast.LENGTH_LONG).show()
                    }
                    addDoctorsToDatabase(name,email,phone, country,city,hospital,department,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUpDoctors, VerificationEmailActivity::class.java)
                    finish()
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left)

                }else{
                    Toast.makeText(this@SignUpDoctors,"Some error occurred", Toast.LENGTH_LONG).show()
                }
            }


    }

    private fun addDoctorsToDatabase(
        name: String,
        email: String,
        phone: String,
        country: String,
        city: String,
        hospital: String,
        department: String,
        uid: String
    ) {
        mDbRef = FirebaseDatabase.getInstance().reference

        mDbRef.child("doctors").child(uid).setValue(Doctors(name,email,phone,country,city,hospital,department,uid))
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