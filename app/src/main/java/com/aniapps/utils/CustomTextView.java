package com.aniapps.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

	private Context c;

	public CustomTextView(Context c) {
		super(c);
		this.c = c;
		if (isInEditMode()) {
			return;
		}
		Typeface tfs = Typeface.createFromAsset(c.getAssets(),
                "font/quivira.otf");
		// Typeface bold = Typeface.create(tfs, Typeface.BOLD);
		setTypeface(tfs);

	}

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.c = context;

		if (isInEditMode()) {
			return;
		}
		Typeface tfs = Typeface.createFromAsset(c.getAssets(),
                "font/quivira.otf");
		// Typeface bold = Typeface.create(tfs, Typeface.BOLD);
		setTypeface(tfs);

	}

	public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.c = context;
		if (isInEditMode()) {
			return;
		}
		Typeface tfs = Typeface.createFromAsset(c.getAssets(),
                "font/quivira.otf");
		// Typeface bold = Typeface.create(tfs, Typeface.BOLD);
		setTypeface(tfs);
	}

}
