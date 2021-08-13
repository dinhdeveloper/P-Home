/*
 * (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved
 * This source code and any compilation or derivative thereof is the sole
 * property of STYL Solutions Pte. Ltd. and is provided pursuant to a
 * Software License Agreement.  This code is the proprietary information of
 * STYL Solutions Pte. Ltd. and is confidential in nature. Its use and
 * dissemination by any party other than STYL Solutions Pte. Ltd. is strictly
 * limited by the confidential information provisions of the Agreement
 * referenced above.
 */

package com.styl.phome.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Environment
import android.util.Base64
import android.view.View
import android.view.animation.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.styl.phome.BuildConfig
import java.math.BigInteger
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.spec.MGF1ParameterSpec
import java.security.spec.RSAPublicKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource

class GeneralUtils {

    companion object {
        const val PAGE_SIZE = 20
        const val IMAGE_PAGE_SIZE = 10

        /*---------Sensor max values: %4 == 0 -----------------*/
        const val MAX_TEMPERATURE = 80f
        const val MAX_CARBON = 8000f
        const val MAX_HUMIDITY = 120f
        const val MAX_PH = 12f
        const val MAX_EC = 20000f
        const val MAX_SENSOR_VALUE_DEFAULT = 120f
        /*--------------------------*/

        private const val CAMERA_FOLDER = "camera"
        private const val ANIM_DEFAULT_DURATION = 250L//millisecond

        fun getMaxValueBySensorUnit(unit: String?): Float {
            if (unit == null || unit.isEmpty()) {
                return MAX_SENSOR_VALUE_DEFAULT
            }
            return when {
                unit.contains("Temp") -> {
                    MAX_TEMPERATURE
                }
                unit.contains("Hum") -> {
                    MAX_HUMIDITY
                }
                unit.contains("CO") -> {
                    MAX_CARBON
                }
                unit.contains("EC") -> {
                    MAX_EC
                }
                unit.contains("pH") -> {
                    MAX_PH
                }
                else -> {
                    MAX_SENSOR_VALUE_DEFAULT
                }
            }
        }

        fun formatDecimalValue(value: Float?): String {
            try {
                if (value != null) {
                    val valueAsString = value.toString()
                    val arrValue = valueAsString.split(".")
                    return if (arrValue[1].toInt() == 0) {
                        arrValue[0]
                    } else {
                        value.toString()
                    }
                }
            } catch (e: Exception) {
                return ""
            }
            return ""
        }

        fun getPluralChar(endChar: String): String {
            return when (endChar) {
                "0", "s", "ch", "x", "sh", "z" -> "es"
                else -> "s"
            }
        }

        fun isNetworkAvailable(context: Context?): Boolean {
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
            return if (connectivityManager is ConnectivityManager) {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                networkInfo?.isConnected ?: false
            } else {
                false
            }
        }

        fun encrypt(mod: String?, exp: String?, clr: CharArray?): String {
            val encrypted: ByteArray
            val pubMod = BigInteger(mod, 16)
            val pubExp = BigInteger(exp, 16)
            val rsaPublicKeySpec = RSAPublicKeySpec(pubMod, pubExp)
            val keyFactory = KeyFactory.getInstance("RSA")
            val publicKey = keyFactory.generatePublic(rsaPublicKeySpec)
            val cipher = Cipher.getInstance("RSA/ECB/OAEPPadding")
            cipher.init(
                Cipher.ENCRYPT_MODE,
                publicKey,
                OAEPParameterSpec("MD5", "MGF1", MGF1ParameterSpec.SHA1, PSource.PSpecified.DEFAULT)
            )
            val bytes = toBytes(clr)
            encrypted = cipher.doFinal(bytes)
            Arrays.fill(bytes, 0)
            return String(Base64.encode(encrypted, Base64.NO_WRAP))
        }

        private fun toBytes(chars: CharArray?): ByteArray {
            val charBuffer = CharBuffer.wrap(chars)
            val byteBuffer = Charset.forName("UTF-8").encode(charBuffer)
            val bytes =
                Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit())

            Arrays.fill(byteBuffer.array(), 0)
            return bytes
        }

        fun getDeviceId(context: Context?): String? {
            var deviceId: String? = MySharedPref(context).deviceId
            if (deviceId.isNullOrEmpty()) {
                deviceId = UUID.randomUUID().toString()
                MySharedPref(context).deviceId = deviceId
            }
            return deviceId
        }

        fun getOs(): String {
            return Build.VERSION.RELEASE
        }

        fun getDeviceName(): String {
            return Build.MANUFACTURER + Build.PRODUCT
        }

        fun getDeviceModel(): String {
            return Build.MODEL
        }

        fun getAppVersion(): String {
            return BuildConfig.VERSION_NAME
        }

        fun getSaveImagePath(ctx: Context): String {
            return ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath + "/$CAMERA_FOLDER"
        }

        fun expandView(v: View, duration: Long?) {
            val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                (v.parent as View).width,
                View.MeasureSpec.EXACTLY
            )
            val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            )
            v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
            val targetHeight = v.measuredHeight
            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            v.layoutParams.height = 1
            v.visibility = View.VISIBLE
            val a: Animation = object : Animation() {
                override fun applyTransformation(
                    interpolatedTime: Float,
                    t: Transformation?
                ) {
                    v.layoutParams.height =
                        if (interpolatedTime == 1f) LinearLayout.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }
            // Expansion speed of 1dp/ms
//            a.duration = ((targetHeight / v.context.resources.displayMetrics.density).toLong())
            a.duration = duration ?: ANIM_DEFAULT_DURATION
            v.startAnimation(a)
        }

        fun collapseView(v: View, duration: Long?) {
            val initialHeight = v.measuredHeight

            val a: Animation = object : Animation() {
                override fun applyTransformation(
                    interpolatedTime: Float,
                    t: Transformation
                ) {
                    if (interpolatedTime == 1f) {
                        v.visibility = View.GONE
                    } else {
                        v.layoutParams.height =
                            initialHeight - (initialHeight * interpolatedTime).toInt()
                        v.requestLayout()
                    }
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }
            // Collapse speed of 1dp/ms
//            a.duration = ((initialHeight / v.context.resources.displayMetrics.density).toLong())
            a.duration = duration ?: ANIM_DEFAULT_DURATION
            v.startAnimation(a)
        }

        fun rotateView(v: View, from: Float, to: Float, duration: Long?) {
            val animSet = AnimationSet(true)
            animSet.interpolator = DecelerateInterpolator()
            animSet.fillAfter = true
            animSet.isFillEnabled = true
            val animRotate = RotateAnimation(
                from, to,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
            )
            animRotate.duration = duration ?: ANIM_DEFAULT_DURATION
            animRotate.fillAfter = true
            animSet.addAnimation(animRotate)
            v.startAnimation(animSet)
        }
    }
}