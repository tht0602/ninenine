package tht.webgames.lastcode

import com.google.gson.Gson
import com.google.gson.JsonElement
import java.io.Serializable


open class BaseData() : Serializable {
    fun toJson(): JsonElement {
        return Gson().toJsonTree(this)
    }
}

data class Login(
    val user_name: String,
    var memberid: String? = null
) : BaseData()