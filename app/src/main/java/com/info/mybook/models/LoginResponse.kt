package com.info.mybook.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    var id: Int?    = null,
    var username  : String? = null,
    var email     : String? = null,
    var firstName : String? = null,
    var lastName  : String? = null,
    var gender    : String? = null,
    var image     : String? = null,
    var token     : String? = null

)