package bensalcie.payhero.mpesa.mpesa.services

object Constants {
    const val CONNECT_TIMEOUT = 60 * 1000

    const val READ_TIMEOUT = 60 * 1000

    const val WRITE_TIMEOUT = 60 * 1000

    const val BASE_URL = "https://sandbox.safaricom.co.ke/"

    const val BUSINESS_SHORT_CODE = "174379"
    val PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"
    const val TRANSACTION_TYPE = "CustomerPayBillOnline"
    const val PARTYB = "174379" //same as business shortcode above

    const val CALLBACKURL = "http://mpesa-requestbin.herokuapp.com/1ajipzt1"

}