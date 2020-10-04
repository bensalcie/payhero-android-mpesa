package bensalcie.payhero.mpesa.mpesa.model


import com.google.gson.annotations.SerializedName
data class STKPush(
    @SerializedName("AccountReference")
    val accountReference: String,
    @SerializedName("Amount")
    val amount: String,
    @SerializedName("BusinessShortCode")
    val businessShortCode: String,
    @SerializedName("CallBackURL")
    val callBackURL: String,
    @SerializedName("PartyA")
    val partyA: String,
    @SerializedName("PartyB")
    val partyB: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("PhoneNumber")
    val phoneNumber: String,
    @SerializedName("Timestamp")
    val timestamp: String,
    @SerializedName("TransactionDesc")
    val transactionDesc: String,
    @SerializedName("TransactionType")
    val transactionType: String
)