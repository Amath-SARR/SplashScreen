package com.example.splashscreen

data class Client(var fullName: String ?= null, var login: String ?= null, var tel: String ?= null,var uid: String ?= null,var solde: String ?= null){

    @JvmName("getFullName1")
    fun getFullName(): String? {
        return fullName
    }
    @JvmName("getLogin1")
    fun getLogin(): String? {
        return login
    }
    @JvmName("getTel1")
    fun getTel(): String? {

        return tel
    }
    @JvmName("getUid1")
    fun getUid(): String? {

        return uid
    }
    @JvmName("getSolde1")
    fun getSolde(): String? {

        return solde
    }
}
