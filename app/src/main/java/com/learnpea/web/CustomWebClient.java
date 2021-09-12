package com.learnpea.web;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebClient extends WebViewClient {

    public interface WebEventListener
    {
        void onLoad();
        void onError();
        void onHttpError();
    }

    private WebEventListener listener = null;

    public void setListener(WebEventListener listener)
    {
        this.listener = listener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if(listener!=null)
        {
            listener.onLoad();
        }
    }

    @Override
    public void onReceivedError(WebView webview, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(webview, request, error);
        if(listener!=null)
        {
            listener.onError();
        }
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        if(listener!=null)
        {
            listener.onHttpError();
        }
    }
}