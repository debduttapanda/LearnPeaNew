package com.learnpea.web;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

public class CustomWebViewChrome extends WebChromeClient {
  @Override
  public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
    AlertDialog.Builder b = new AlertDialog.Builder(view.getContext())
    .setTitle("LearnPea")
    .setCancelable(false)
    .setMessage(message)
    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        result.confirm();
      }
    })
    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        result.cancel();
      }
    });

    b.show();

    // Indicate that we're handling this manually
    return true;
  }

  @Override
  public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
    AlertDialog.Builder b = new AlertDialog.Builder(view.getContext())
            .setTitle("LearnPea").setCancelable(false)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                result.confirm();
              }
            });

    b.show();

    // Indicate that we're handling this manually
    return true;
  }


  @Override
  public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
      final EditText input = new EditText(view.getContext());
      input.setInputType(InputType.TYPE_CLASS_TEXT);
      input.setText(defaultValue);
      new AlertDialog.Builder(view.getContext())
              .setTitle("LearnPea").setCancelable(false)
              .setView(input)
              .setMessage(message)
              .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                      result.confirm(input.getText().toString());
                  }
              })
              .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                      result.cancel();
                  }
              })
              .create()
              .show();
      return true;
  }


}