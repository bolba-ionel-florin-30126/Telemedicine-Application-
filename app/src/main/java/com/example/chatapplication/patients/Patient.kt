package com.example.chatapplication.patients

class Patient {
    var name: String? = null
    var email: String? = null
    var phone: String? = null
    var country: String?=null
    var city: String?=null
    var uid: String? = null
    var patientProfileImage: String? = null

    constructor()

    constructor(name:String?,email:String?,phone: String?,country:String?,city: String?,uid:String,patientProfileImage: String?){
        this.name = name
        this.email = email
        this.country = country
        this.city = city
        this.phone = phone
        this.uid = uid
        this.patientProfileImage = null

    }

}