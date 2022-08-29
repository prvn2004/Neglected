package com.project.neglected.DataFiles

data class UserDetailsDataFile( val personName: String, val personEmail: String) {

    fun getName(): String {
        return personName
    }

    fun getEmail(): String{
        return personEmail
    }

}
