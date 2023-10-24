package email.phone.phoneverification.data

//
// Created by Dev on 25-09-2023.
//
data class OtpApiRequest(
    val apiKey: String,
    val fromCountryCode: String, val fromPhoneNo: String,
    val toCountrycode: String, val toPhoneNo: String,
    val subject: String, val messageBody: String,
    val tinyFlag: Boolean
)