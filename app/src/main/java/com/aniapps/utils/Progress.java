package com.aniapps.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.aniapps.ebook.R;

/**
 * Created by Admin on 9/19/2016.
 */
public class Progress extends Dialog {

    MyProgress cpb;
    public static TextView tv_loading_text;

    public Progress(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
        cpb = (MyProgress) findViewById(R.id.progress);
        tv_loading_text=(TextView)findViewById(R.id.tv_progress_text);
        cpb.startAnimation();
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
        cpb.dismiss();
    }

}
