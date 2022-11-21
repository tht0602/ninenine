package tht.webgames.lastcode

import com.google.gson.JsonElement
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

object WebGameClient {

    private var client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    private var lifecycleScope: CoroutineScope? = null
    private const val WEB_SITE = "www.smile888.tw"
    private const val BASE_PATH = "webgame"

    fun init(lifecycleScope: CoroutineScope) {
        this.lifecycleScope = lifecycleScope
    }

    fun sendMsg(query: BaseData, path: String, successCallback: ((response: Response) -> Unit)? = null) {
        lifecycleScope?.launch {
            val json = query.toJson().asJsonObject
            val entries: Set<Map.Entry<String, JsonElement>> = json.entrySet()

            val httpUrl = HttpUrl.Builder()
                .scheme("http")
                .host(WEB_SITE)
                .addPathSegment(BASE_PATH)
                .addPathSegment(path).apply {
                    entries.forEach {
                        this.addQueryParameter(it.key, it.value.asString)
                    }
                }
                .build()

            val request = Request.Builder()
                .url(httpUrl)
                .build()

            val call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Logger.d(e.message.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    successCallback?.invoke(response)
                    response.close()
                }
            })
        }
    }

    fun getMsg(path: String) {
        lifecycleScope?.launch {
            val httpUrl = HttpUrl.Builder()
                .scheme("http")
                .host(WEB_SITE)
                .addPathSegment(BASE_PATH)
                .addPathSegment(path)
                .build()

            val request = Request.Builder()
                .url(httpUrl)
                .build()

            val call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Logger.d(e.message.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    Logger.d(response.body.toString())
                    response.close()
                }
            })
        }
    }
}