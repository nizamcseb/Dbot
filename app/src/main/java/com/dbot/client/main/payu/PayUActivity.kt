package com.dbot.client.main.payu

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.dbot.client.R
import com.dbot.client.common.CommonFunctions
import com.dbot.client.common.Key
import com.dbot.client.common.SessionManager
import com.dbot.client.common.Tags
import com.dbot.client.databinding.ActivityPayuBinding
import com.dbot.client.main.MainActivity
import com.dbot.client.main.newrequest.model.BookSlot
import com.dbot.client.retrofit.ApiClient
import com.google.android.material.snackbar.Snackbar
import com.payu.base.models.BaseApiLayerConstants
import com.payu.base.models.ErrorResponse
import com.payu.base.models.PayUPaymentParams
import com.payu.checkoutpro.PayUCheckoutPro
import com.payu.checkoutpro.utils.PayUCheckoutProConstants
import com.payu.checkoutpro.utils.PayUCheckoutProConstants.CP_HASH_NAME
import com.payu.checkoutpro.utils.PayUCheckoutProConstants.CP_HASH_STRING
import com.payu.ui.model.listeners.PayUCheckoutProListener
import com.payu.ui.model.listeners.PayUHashGenerationListener


class PayUActivity : AppCompatActivity() {

    /* private var userId = ""
     private var learLangId = ""
     private var learLangName = ""
     private var userName = ""
     private var phoneNumber = ""
     private var emailId = ""
     private var txnId = ""

     private var pdPackageId = ""
     private var pdPackageName = ""
     private var pdChaptersUnlocked = ""
     private var pdTotalDuration = ""
     private var pdPrice = ""
     private var pdTax = ""
     private var pdTotalAmount = ""
     private var pdSurl = ""
     private var pdFurl = ""*/
    private var pdPrice = ""
    private var txnId = ""
    private var sessionManager: SessionManager? = null
    private var bookSlot: BookSlot? = null
    private var key = Key.payULantoonKey
    private var salt = Key.payULantoonSalt

    private lateinit var binding: ActivityPayuBinding

    // variable to track event time
    private var mLastClickTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payu)
        initDatas()
        //initListeners()
        initUI()
    }

    private fun initDatas() {


    }

    private fun initUI() {
        txnId = SystemClock.elapsedRealtime().toString();
        bookSlot = intent.getSerializableExtra(Tags.TAG_BOOK_SLOT_DATA) as? BookSlot
        if (bookSlot != null) {
            pdPrice = bookSlot!!.amountPaid.toString();
            //binding.pdTotalAmount.setText(bookSlot!!.amountPaid.toString())
        }

        if (ApiClient.isTest) {
            pdPrice = "1.0"
            //binding.pdCardViewEnv.isVisible = true
        }
        startPayment()
    }


  /*  private fun initListeners() {
        binding.radioGrpEnv.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            when (i) {
                R.id.radioBtnTest -> updateTestEnvDetails()
                R.id.radioBtnProduction -> updateProdEnvDetails()
                else -> updateTestEnvDetails()
            }
        }


    }
*/

    private fun updateTestEnvDetails() {
        //For testing
        key = Key.payUtestKey
        salt = Key.payUtestSalt
    }

    private fun updateProdEnvDetails() {
        //For Production
        key = Key.payULantoonKey
        salt = Key.payULantoonSalt
    }

    fun startPayment() {
        // Preventing multiple clicks, using threshold of 1 second
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()

        val paymentParams = preparePayUBizParams()
        initUiSdk(paymentParams)
    }

    fun preparePayUBizParams(): PayUPaymentParams {

        return PayUPaymentParams.Builder().setAmount(pdPrice.toDouble().toString())
                //.setAmount(pdPrice.toDouble().toString())
                //.setIsProduction(binding.radioBtnProduction.isChecked)
                .setIsProduction(true)
                .setKey(key)
                .setProductInfo(bookSlot!!.projectName)
                .setPhone(bookSlot!!.contactPersonPhone)
                .setTransactionId(txnId)
                .setFirstName(bookSlot!!.contactPersonName)
                .setEmail(sessionManager!!.clientEmailId)
                .setSurl("https://payuresponse.firebaseapp.com/success")
                .setFurl("https://payuresponse.firebaseapp.com/failure")
                .build()
    }

    private fun initUiSdk(payUPaymentParams: PayUPaymentParams) {
        PayUCheckoutPro.open(
                this,
                payUPaymentParams,
                object : PayUCheckoutProListener {

                    override fun onPaymentSuccess(response: Any) {
                        processSuccessResponse(response)
                    }

                    override fun onPaymentFailure(response: Any) {
                        processFailureResponse(response)
                    }

                    override fun onPaymentCancel(isTxnInitiated: Boolean) {
                        showSnackBar(resources.getString(R.string.transaction_cancelled_by_user))
                    }

                    override fun onError(errorResponse: ErrorResponse) {

                        val errorMessage: String
                        if (errorResponse != null && errorResponse.errorMessage != null && errorResponse.errorMessage!!.isNotEmpty())
                            errorMessage = errorResponse.errorMessage!!
                        else
                            errorMessage = resources.getString(R.string.some_error_occurred)
                        showSnackBar(errorMessage)
                    }

                    override fun generateHash(
                            map: HashMap<String, String?>,
                            hashGenerationListener: PayUHashGenerationListener
                    ) {
                        if (map.containsKey(CP_HASH_STRING)
                                && map.containsKey(CP_HASH_STRING) != null
                                && map.containsKey(CP_HASH_NAME)
                                && map.containsKey(CP_HASH_NAME) != null
                        ) {

                            val hashData = map[CP_HASH_STRING]
                            val hashName = map[CP_HASH_NAME]

                            var hash: String? = null


                            //calculate SDH-512 hash using hashData and salt
                            hash = HashGenerationUtils.generateHashFromSDK(
                                    hashData!!,
                                    salt
                            )
                            Log.d("hashData", hashData)
                            if (hash != null) {
                                Log.d("hash", hash)
                            }
                            if (hashName != null) {
                                Log.d("hashname", hashName)
                            }


                            if (!TextUtils.isEmpty(hash)) {
                                val hashMap: HashMap<String, String?> = HashMap()
                                hashMap[hashName!!] = hash!!
                                hashGenerationListener.onHashGenerated(hashMap)
                            }
                        }
                    }

                    override fun setWebViewProperties(webView: WebView?, bank: Any?) {
                    }
                })
    }


    private fun showSnackBar(message: String) {
        Snackbar.make(binding.clMain, message, Snackbar.LENGTH_LONG).show()
    }

    private fun processSuccessResponse(response: Any) {
        response as HashMap<*, *>
        Log.d(
                BaseApiLayerConstants.SDK_TAG,
                "payuResponse ; > " + response[PayUCheckoutProConstants.CP_PAYU_RESPONSE]
                        + ", merchantResponse : > " + response[PayUCheckoutProConstants.CP_MERCHANT_RESPONSE]
        )
        CommonFunctions.postPaymentPurchaseDetails(bookSlot, this, this,
                "Payment Successfull",
                "",
                "success", txnId)

        //alertbox("Payment Successfull", "You have unlocked the new chapters")

    }

    private fun processFailureResponse(response: Any) {
        // 5434021016824014	12	29	123
        response as HashMap<*, *>
        Log.d(
                BaseApiLayerConstants.SDK_TAG,
                "payuResponse ; > " + response[PayUCheckoutProConstants.CP_PAYU_RESPONSE]
                        + ", merchantResponse : > " + response[PayUCheckoutProConstants.CP_MERCHANT_RESPONSE]
        )
        CommonFunctions.postPaymentPurchaseDetails(bookSlot, this, this,
                "Payment Failure",
                "Try after sometime",
                "failure", txnId)
        //alertbox("Payment Failure", "Try after sometime")

    }

    private fun alertbox(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            startHomeActivity()
            dialog.dismiss()
        }

        builder.show()
    }


    fun startHomeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
