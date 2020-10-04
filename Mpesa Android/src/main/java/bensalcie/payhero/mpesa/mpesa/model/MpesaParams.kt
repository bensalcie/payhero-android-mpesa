package bensalcie.payhero.mpesa.mpesa.model

data class MpesaParams (
    val accountReference: String,
    val amount: String,
    val businessShortCode: String,
    val callBackURL: String,
    val partyA: String,
    val partyB: String,
    val password: String,
    val phoneNumber: String,
    val transactionDesc: String,
    val transactionType: String

    )