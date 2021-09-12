package com.learnpea.web

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import com.learnpea.MainActivity
import com.pixplicity.easyprefs.library.Prefs

class CustomWebView : WebView {
    inner class InlineJavaScriptInterface(var webView: CustomWebView?) {

        @JavascriptInterface
        fun log(tag:String,message:String) {
            Log.d(tag,message)
        }
        @JavascriptInterface
        fun shortToast(message:String) {
            if(webView!=null)
            {
                Toast.makeText(webView!!.context,message,Toast.LENGTH_LONG).show()
            }
        }
        @JavascriptInterface
        fun longToast(message:String) {
            if(webView!=null)
            {
                Toast.makeText(webView!!.context,message,Toast.LENGTH_SHORT).show()
            }
        }
        @JavascriptInterface
        fun savePref(key:String,value:String) {
            Prefs.putString(key, value)
        }

        @JavascriptInterface
        fun getPref(key: String, value: String): String {
            return Prefs.getString(key, value)
        }

        @JavascriptInterface
        fun composeEmail(address: String, subject: String, text: String) {
            val selectorIntent = Intent(Intent.ACTION_SENDTO)
            selectorIntent.data = Uri.parse("mailto:$address")
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, text)
            emailIntent.selector = selectorIntent
            (webView!!._context as MainActivity).startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }
    }
    private var _context: Context
    private var webClient: CustomWebClient? = null
    private fun eval(script: String) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                evaluateJavascript(script, null)
            } else {
                loadUrl("javascript:$script")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun commonConstructor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true)
        }
        clearCache(true)
        clearHistory()
        settings.setAppCacheEnabled(false)
        settings.javaScriptEnabled = true
        webClient = CustomWebClient()
        addJavascriptInterface(InlineJavaScriptInterface(this),"agent")
        webViewClient = webClient!!

        settings.javaScriptCanOpenWindowsAutomatically = true;
        try {
            settings.setPluginState(WebSettings.PluginState.ON);
        } catch (e: Exception) {
        }
        settings.mediaPlaybackRequiresUserGesture = false;

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
        }


        settings.cacheMode = WebSettings.LOAD_NO_CACHE;
        settings.domStorageEnabled = true
        try {
            settings.setAppCacheEnabled(false)
        } catch (e: Exception) {
        }

        webChromeClient = CustomWebViewChrome()
        setBackgroundColor(Color.TRANSPARENT)

        loadUrl("file:///android_asset/index.html")
    }



    constructor(context: Context) : super(context) {
        this._context = context
        commonConstructor()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this._context = context
        commonConstructor()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this._context = context
        commonConstructor()
    }


}