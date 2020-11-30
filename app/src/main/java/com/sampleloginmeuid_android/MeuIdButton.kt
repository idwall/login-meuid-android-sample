package com.sampleloginmeuid_android

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat

class MeuIdButton(context: Context, attrs: AttributeSet? = null) : AppCompatButton(context, attrs) {

    companion object {
        // MeuID applicationId settings
        const val meuIdApplicationId = "[YOUR_APP_ID_HERE]"
    }

    init {
        // MeuID button settings
        // Options: "button_dark" / "button_light" / "button_light_outline"
        val meuIdButtonStyle = "button_light_outline"
        // Options: "default" / "small"
        val meuIdButtonSize = "default"

        setBtnAsEnable()
        setBtnLayoutGravity()
        setBtnStyle(meuIdButtonStyle)
        setBtnSize(meuIdButtonSize)
        setBtnClickListener()
    }

    private fun setBtnAsEnable() {
        isEnabled = true
    }

    private fun setBtnLayoutGravity() {
        gravity = Gravity.CENTER
    }

    private fun setBtnStyle(style: String) {
        setBtnBackground(style)
        setBtnContent(style)
    }

    private fun setBtnBackground(style: String) {
        setBackgroundResource(
            when (style) {
                "button_light" -> R.drawable.bg_meuid_btn_light
                "button_light_outline" -> R.drawable.bg_meuid_btn_light_outline
                "button_dark" -> R.drawable.bg_meuid_btn_dark
                else -> R.drawable.bg_meuid_btn_dark
            }
        )
    }

    private fun setBtnContent(style: String) {
        val icons: Pair<Drawable?, Drawable?> = when (style) {
            "button_light", "button_light_outline" -> Pair(
                getDrawable(R.drawable.ic_meuid_logo_light),
                getDrawable(R.drawable.ic_meuid_text_light)
            )
            "button_dark" -> Pair(
                getDrawable(R.drawable.ic_meuid_logo_dark),
                getDrawable(R.drawable.ic_meuid_text_dark)
            )
            else -> Pair(
                getDrawable(R.drawable.ic_meuid_logo_dark),
                getDrawable(R.drawable.ic_meuid_text_dark)
            )
        }
        val logoIcon = icons.first
        val textIcon = icons.second
        setCompoundDrawablesWithIntrinsicBounds(logoIcon, null, textIcon, null)
    }

    private fun getDrawable(resource: Int) = ResourcesCompat.getDrawable(resources, resource, null)

    private fun setBtnSize(size: String) {
        minimumHeight = convertToDp(40)
        minHeight = convertToDp(40)

        compoundDrawablePadding = convertToDp(6)

        var hrzPadding = convertToDp(48)
        var vrtPadding = convertToDp(12)
        if (size == "small") {
            hrzPadding = convertToDp(24)
            vrtPadding = convertToDp(8)
        }
        setPadding(hrzPadding, vrtPadding, hrzPadding, vrtPadding)
    }

    private fun convertToDp(px: Int) = px * this.context.resources.displayMetrics.density.toInt()

    private fun setBtnClickListener() {
        setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("meuid://meuid?action=MEUID_AUTHENTICATION&applicationId=$meuIdApplicationId&parameters=eyJvcmlnaW4iOiAiTU9CSUxFIiwgInNlc3Npb24iOiAidGVzdCJ9")
            if (isSafeToCall(intent).not()) intent.data =
                Uri.parse("https://play.google.com/store/apps/details?id=com.meuid")
            context.startActivity(intent)
        }
    }

    private fun isSafeToCall(intent: Intent): Boolean {
        return context.packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        ).isNotEmpty()
    }
}