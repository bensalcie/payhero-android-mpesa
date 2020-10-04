package bs.payhero.app.mpesa.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AccessToken (
    @SerializedName("access_token")
    @Expose
    val accessToken:String ?=null,
    @SerializedName("expires_in")
    @Expose
    val expiresIn:String?=null
)