package com.example.banglapharmacy.Model

import java.io.Serializable

class Drug(val name: String, val description: String,  val howToUse: String,
           val sideEffects: String, val precaution: String, val notes: String, val storage: String) : Serializable