package com.loginext.quidel.helpers

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import com.loginext.quidel.BuildConfig
import com.loginext.quidel.models.AutoResolver
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.reflect.KClass


fun Context.quickStartActivity(className: Class<*>, intentReceiver: (Intent.() -> Unit) = {}) {
    startActivity(Intent(this, className).apply(intentReceiver))
}
fun Context.scaledDrawableResources(@DrawableRes id: Int, @DimenRes width: Int, @DimenRes height: Int): Drawable {
    val w = resources.getDimension(width).toInt()
    val h = resources.getDimension(height).toInt()
    return scaledDrawable(id, w, h)
}


val Number.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

val Number.px: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()


fun Context.scaledDrawable(@DrawableRes id: Int, width: Int, height: Int): Drawable {
    val bmp = BitmapFactory.decodeResource(resources, id)
    val bmpScaled = Bitmap.createScaledBitmap(bmp, width, height, false)
    return BitmapDrawable(resources, bmpScaled)
}

@Suppress("UPPER_BOUND_VIOLATED")
val <T>  KClass<T>.asLiveData: MutableLiveData<T>
    @JvmName("getLiveData")
    get() = MutableLiveData<T>()

fun logit(msg: Any? = "...") {
    if (BuildConfig.DEBUG) {
        val trace: StackTraceElement? = Thread.currentThread().stackTrace[3]
        val lineNumber = trace?.lineNumber
        val methodName = trace?.methodName
        val className = trace?.fileName?.replaceAfter(".", "")?.replace(".", "")
        Log.d("Line $lineNumber", "$className::$methodName() -> $msg")
    }
}

fun Number.toDp() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics
)

fun Number.toPx() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_PX,
    this.toFloat(),
    Resources.getSystem().displayMetrics
)

infix fun Context.showToast(msg: Any?) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show()
    } else {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    imm.hideSoftInputFromWindow(windowToken, 0)
}



suspend fun <T, V> Iterable<T>.asyncAll(coroutine: suspend (T) -> V): Iterable<V> = coroutineScope {
    this@asyncAll.map { async { coroutine(it) } }.awaitAll()
}

fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER
    val product = Build.PRODUCT
    return if (product.startsWith(manufacturer)) {
        product
    } else {
        manufacturer + "_" + product
    }
}

fun Context.startAppSettings(launcher: ActivityResultLauncher<Intent>){
    val intentAppSettings = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", this.packageName, null)
    intentAppSettings.data = uri
    launcher.launch(intentAppSettings)
}

fun getDeviceId(context: Context): String {
    return Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    ) ?: "n/a"
}

inline fun <T> AutoResolver<T>.successResource(
    success: (T) -> Unit,
): AutoResolver<T> = apply {
    if (status is AutoResolver.Companion.Success) {
        success.invoke(data!!)
    }
}

inline fun <T> AutoResolver<T>.loadingResource(block: (Boolean) -> Unit) = apply {
    block(status is AutoResolver.Companion.Loading)
}
inline fun <T> AutoResolver<T>.failResource(
    fail: (String) -> Unit,
): AutoResolver<T> = apply {
    if (status is AutoResolver.Companion.Failure) {
        fail(message!!)
    }
}
