package bs.payhero.app.mpesa.services

object Constants {
    const val CONNECT_TIMEOUT = 60 * 1000

    const val READ_TIMEOUT = 60 * 1000

    const val WRITE_TIMEOUT = 60 * 1000

    //    const val BASE_URL = "https://sandbox.safaricom.co.ke/"
//    const val PRODUCTION_BASE_URL = "https://api.safaricom.co.ke/"
//mpesa/stkpush/v1/processrequest
//    https://api.safaricom.co.ke/mpesa/stkpush/v1/processrequest
    const val BUSINESS_SHORT_CODE = "4060195"

    //    const val BUSINESS_SHORT_CODE = "174379"
    val PASSKEY = "6ef546b207f07d6582f52ae916b0711b76064e6e7a47a2c439c2a2cb5dd9fad3"
    const val TRANSACTION_TYPE = "CustomerPayBillOnline"

    //    const val PARTYB = "174379" //same as business shortcode above
    const val PARTYB = "4060195" //same as business shortcode above

    const val CALLBACKURL = "https://payherokenya.com/phk/c2bcallback.php"

}