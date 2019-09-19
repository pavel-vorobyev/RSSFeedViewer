package com.pavelvorobyev.rssfeedviewer.ui.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.webkit.WebView;
import android.widget.ImageButton;
import com.pavelvorobyev.rssfeedviewer.R;
import com.pavelvorobyev.rssfeedviewer.ui.base.BaseMviActivity;
import butterknife.BindView;

public class DetailsActivity extends BaseMviActivity<DetailsView, DetailsPresenter,
        DetailsViewState> implements DetailsView {

    public static void start(Context context, String link) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("link", link);
        context.startActivity(intent);
    }

    @BindView(R.id.backBtnView)
    ImageButton backBtnView;
    @BindView(R.id.webView)
    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backBtnView.setOnClickListener(v -> {
            finish();
        });

        String link = getIntent().getStringExtra("link");
        webView.loadUrl(link);
    }

    @NonNull
    @Override
    public DetailsPresenter createPresenter() {
        DetailsPresenter presenter = new DetailsPresenter();
        presenter.init(this, getIntent());
        return presenter;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_details;
    }
}
