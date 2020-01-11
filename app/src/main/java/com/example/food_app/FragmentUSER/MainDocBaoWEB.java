package com.example.food_app.FragmentUSER;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.food_app.R;

public class MainDocBaoWEB extends AppCompatActivity {
    String link;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doc_bao_web);
        setTitle("WEB");
        webView = findViewById(R.id.wv);

        link = getIntent().getExtras().getString("link");

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webView.loadUrl(link);
                return super.shouldOverrideUrlLoading(view, request);
            }

        });
        webView.loadUrl(link);

    }
}
