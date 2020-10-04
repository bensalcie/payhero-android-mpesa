package bensalcie.payhero.mpesa.mpesa.model


import com.google.gson.annotations.SerializedName

data class STKResponse(
    @SerializedName("CheckoutRequestID")
    val checkoutRequestID: String,
    @SerializedName("CustomerMessage")
    val customerMessage: String,
    @SerializedName("MerchantRequestID")
    val merchantRequestID: String,
    @SerializedName("ResponseCode")
    val responseCode: String,
    @SerializedName("ResponseDescription")
    val responseDescription: String
)