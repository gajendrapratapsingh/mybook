package com.info.mybook.models

import kotlinx.serialization.Serializable


@Serializable
data class User(
    var users : ArrayList<Users> = arrayListOf(),
    var total : Int? = null,
    var skip  : Int? = null,
    var limit : Int? = null
)

@Serializable
data class Hair(
    var color : String? = null,
    var type  : String? = null
)

@Serializable
data class Coordinates(
    var lat : Double? = null,
    var lng : Double? = null
)

@Serializable
data class Address (
    var address : String? = null,
    var city : String? = null,
    var coordinates : Coordinates? = Coordinates(),
    var postalCode  : String? = null,
    var state: String? = null

)

@Serializable
data class Bank(
    var cardExpire : String? = null,
    var cardNumber : String? = null,
    var cardType  : String? = null,
    var currency : String? = null,
    var iban : String? = null
)

@Serializable
data class Company (
    var address    : Address? = Address(),
    var department : String?  = null,
    var name       : String?  = null,
    var title      : String?  = null
)

@Serializable
data class Crypto (
   var coin    : String? = null,
   var wallet  : String? = null,
   var network : String? = null
)

@Serializable
data class Users (
    var id         : Int?     = null,
    var firstName  : String?  = null,
    var lastName   : String?  = null,
    var maidenName : String?  = null,
    var age        : Int?     = null,
    var gender     : String?  = null,
    var email      : String?  = null,
    var phone      : String?  = null,
    var username   : String?  = null,
    var password   : String?  = null,
    var birthDate  : String?  = null,
    var image      : String?  = null,
    var bloodGroup : String?  = null,
    var height     : Int?     = null,
    var weight     : Double?  = null,
    var eyeColor   : String?  = null,
    var hair       : Hair?    = Hair(),
    var domain     : String?  = null,
    var ip         : String?  = null,
    var address    : Address? = Address(),
    var macAddress : String?  = null,
    var university : String?  = null,
    var bank       : Bank?    = Bank(),
    var company    : Company? = Company(),
    var ein        : String?  = null,
    var ssn        : String?  = null,
    var userAgent  : String?  = null,
    var crypto     : Crypto?  = Crypto()
)


