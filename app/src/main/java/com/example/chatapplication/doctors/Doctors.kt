package com.example.chatapplication.doctors

class Doctors{
    var name: String? = null
    var email: String? = null
    var phone: String? = null
    var country: String?=null
    var city: String?=null
    var hospital:String?=null
    var department:String?=null
    var uid: String? = null

    constructor()

    constructor(name:String?,email:String?,phone: String?,country:String?,city: String?,hospital:String?,department:String?,uid:String){
        this.name = name
        this.email = email
        this.country = country
        this.city = city
        this.phone = phone
        this.hospital=hospital
        this.department=department
        this.uid = uid
    }

}