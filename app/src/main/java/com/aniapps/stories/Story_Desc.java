package com.aniapps.stories;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aniapps.ebook.R;
import com.aniapps.utils.JustifiedTextView;


/**
 * Created by NagRaj Pilla on 9/26/2016.
 */

public class Story_Desc extends Fragment {
    int fragVal;
    float textSize;
    String s_title, s_desc;
    public JustifiedTextView tv_desc;

    static Story_Desc init(int val, String story_title, String story_desc, float text_size) {
        Story_Desc stories = new Story_Desc();
        Bundle args = new Bundle();
        args.putInt("val", val);
        args.putString("story_title", story_title);
        args.putString("story_desc", story_desc);
        args.putFloat("text_size", text_size);

        stories.setArguments(args);
        return stories;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
        s_title = getArguments() != null ? getArguments().getString("story_title") : "";
        s_desc = getArguments() != null ? getArguments().getString("story_desc") : "";
        textSize = getArguments() != null ? getArguments().getFloat("text_size") : 0;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.story_desc, container,
                false);
       /* CardView card_title = (CardView) layoutView.findViewById(R.id.card_title);
        card_title.setCardBackgroundColor(Color.WHITE);*/
        View title = layoutView.findViewById(R.id.tv_story_title);
        ((TextView) title).setText(s_title);
        // tv_desc = new JustifiedTextView(getContext());
        tv_desc = (JustifiedTextView) layoutView.findViewById(R.id.tv_desc);
        s_desc.replaceAll("'", "''");
        tv_desc.setText(s_desc);
        tv_desc.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv_desc.setLineSpacing(8);
        tv_desc.setAlignment(Paint.Align.LEFT);
        tv_desc.setPadding(25, 10, 25, 10);
        tv_desc.setTypeFace(Typeface.createFromAsset(getResources().getAssets(), "fonts/Quivira.otf"));
      /*  LinearLayout place = (LinearLayout) layoutView.findViewById(R.id.text_layout);
        place.addView(tv_desc);*/
        return layoutView;
    }

}