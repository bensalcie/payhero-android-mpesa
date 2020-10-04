package bensalcie.payhero.mpesa.mpesa.services

object Environment {
    const val PRODUCTION = "https://api.safaricom.co.ke/"
    const val SANDBOX = "https://sandbox.safaricom.co.ke/"

    object TransactionType {
        const val CustomerPayBillOnline = "CustomerPayBillOnline"
    }
}