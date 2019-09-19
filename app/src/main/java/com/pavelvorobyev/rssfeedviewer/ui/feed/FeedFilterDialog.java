package com.pavelvorobyev.rssfeedviewer.ui.feed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import com.pavelvorobyev.rssfeedviewer.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedFilterDialog extends DialogFragment {

    @BindView(R.id.keywordsInputView)
    EditText keywordsInputView;
    @BindView(R.id.searchBtnView)
    ImageButton searchBtnView;

    private Callback callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Light_Dialog_Alert);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_feed_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchBtnView.setOnClickListener(v -> {
            if (callback != null)
                callback.searchClick(keywordsInputView.getText().toString());

            dismiss();
        });
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void searchClick(String keywords);
    }
}
