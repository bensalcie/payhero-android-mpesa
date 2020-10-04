package bs.payhero.app

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import bs.payhero.app.mpesa.model.AccessToken
import bs.payhero.app.mpesa.model.Environment
import bs.payhero.app.mpesa.model.STKPush
import bs.payhero.app.mpesa.model.STKResponse
import bs.payhero.app.mpesa.services.Constants.BUSINESS_SHORT_CODE
import bs.payhero.app.mpesa.services.Constants.CALLBACKURL
import bs.payhero.app.mpesa.services.Constants.PARTYB
import bs.payhero.app.mpesa.services.Constants.PASSKEY
import bs.payhero.app.mpesa.services.DarajaApiClient
import bs.payhero.app.mpesa.services.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class FirstFragment : Fragment(),View.OnClickListener {
    lateinit var  ctx:Context
    lateinit var root:View


    private var mApiClient: DarajaApiClient? = null
    private var mProgressDialog: ProgressDialog? = null

    var mAmount: EditText? = null

    var mPhone: EditText? = null


    var mPay: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        ctx =container!!.context
        root = inflater.inflate(R.layout.fragment_first, container, false)
        mAmount= root.findViewById(R.id.etAmount)
        mPhone = root.findViewById(R.id.etPhone)
        mPay = root.findViewById(R.id.btnPay)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        mProgressDialog = ProgressDialog(ctx)
        mApiClient = DarajaApiClient(
            "YVz1AXOdFdJD0YJdFVba0JRkduEVzj8b",
            "Q13lhnP6hUUTPOIB",
            Environment.PRODUCTION
        )
        mApiClient!!.setIsDebug(true) //Set True to enable logging, false to disable.
        mPay!!.setOnClickListener(this)
        getAccessToken()
    }

    override fun onClick(v: View?) {
        if (v === mPay) {
            val phoneNumber = mPhone!!.text.toString()
            val amount = mAmount!!.text.toString()


            performSTKPush(phoneNumber, amount)
        }
    }
    private fun getAccessToken() {
        mApiClient!!.setGetAccessToken(true)
        mApiClient!!.mpesaService()!!.getAccessToken().enqueue(object : Callback<AccessToken?> {
            override fun onResponse(call: Call<AccessToken?>, response: Response<AccessToken?>) {
                if (response.isSuccessful) {
                    mApiClient!!.setAuthToken(response.body()?.accessToken)
                }
            }

            override fun onFailure(call: Call<AccessToken?>, t: Throwable) {}
        })
    }

    private fun performSTKPush(phone_number: String?, amount: String) {
        mProgressDialog!!.setMessage("Processing your request")
        mProgressDialog!!.setTitle("Please Wait...")
        mProgressDialog!!.isIndeterminate = true
        mProgressDialog!!.show()
        val timestamp = Utils.getTimestamp()
        val stkPush = STKPush(
            "DICE MONEY",
            amount,
            BUSINESS_SHORT_CODE,
            CALLBACKURL,
            Utils.sanitizePhoneNumber(phone_number!!)!!,
            PARTYB,
            Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp!!)!!,
            Utils.sanitizePhoneNumber(phone_number)!!,
            timestamp,
            "DICE MONEY PAYMENT",
            Environment.TransactionType.CustomerPayBillOnline
        )
        mApiClient!!.setGetAccessToken(false)

        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient!!.mpesaService()!!.sendPush(stkPush).enqueue(object : Callback<STKResponse> {
            override fun onResponse(call: Call<STKResponse>, response: Response<STKResponse>) {
                mProgressDialog!!.dismiss()
                try {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            ctx,
                            "Response : ${response.body().toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Timber.d("post submitted to API. %s", response.body())
                    } else {
                        Timber.e("Response %s", response.errorBody()!!.string())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<STKResponse>, t: Throwable) {
                mProgressDialog!!.dismiss()
                Timber.e(t)
            }
        })
    }
}