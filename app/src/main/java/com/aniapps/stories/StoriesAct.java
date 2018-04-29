package com.aniapps.stories;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aniapps.ebook.BaseActivity;
import com.aniapps.ebook.R;
import com.aniapps.utils.HoloCircleSeekBar;
import com.aniapps.utils.MaterialRippleLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


//http://www.truiton.com/2013/05/android-fragmentstatepageradapter-example/
public class StoriesAct extends BaseActivity {
    public static int ITEMS = 0;
    public static int pos = 0;
    MyAdapter mAdapter;
    ViewPager mPager;
    public static HashMap<String, String> story_title_desc, my_desc;
    public static List<String> my_titles, my_title;
    List<Story_Desc> lst_Story_Desc = new ArrayList<>();
    private LinearLayout attachmentLayout;
    private boolean isHidden = true;
    private FrameLayout lay_settings;
    public HoloCircleSeekBar text_size_picker, pitch_picker, speed_picker;
    MaterialRippleLayout lay_language, lay_default, lay_set;
    Story_Desc story_desc;
    public float f0 = 20f, f1 = 50f, f2 = 50f, pitch, speed;
    TextToSpeech tts;
    boolean audio_flag = true;
    int result;

    boolean doubleBackToExitPressedOnce = false;
    Intent intentShareFile;
    String filePath = "";
    String text_size = "textsize";
    String pitch_value = "pitchvalue";
    String speed_value = "speedvalue";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View contentView = inflater.inflate(R.layout.fragment_pager, null, false);
        drawer_layout.addView(contentView, 0);
        Intent in = getIntent();
        ITEMS = in.getIntExtra("length", 0);
        pos = in.getIntExtra("pos", 0);
        my_title = getTitles();
        my_desc = getAllItemList();

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(pos);
        initView();

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_pager;
    }


    public class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fragmentManager2) {
            super(fragmentManager2);
        }

        @Override
        public int getCount() {
            return ITEMS;
        }

        @Override
        public Fragment getItem(int position) {

            return Story_Desc.init(position, my_title.get(position).toString(),
                    (String) my_desc.get(my_title.get(position).toString()), f0);
        }
    }

    private void initView() {
        attachmentLayout = (LinearLayout) findViewById(R.id.menu_attachments);
        lay_settings = (FrameLayout) findViewById(R.id.lay_settings);
        text_size_picker = (HoloCircleSeekBar) findViewById(R.id.text_size_picker);
        pitch_picker = (HoloCircleSeekBar) findViewById(R.id.pitch_picker);
        speed_picker = (HoloCircleSeekBar) findViewById(R.id.speed_picker);
        lay_language = (MaterialRippleLayout) findViewById(R.id.lay_language);
        lay_default = (MaterialRippleLayout) findViewById(R.id.lay_default);
        lay_set = (MaterialRippleLayout) findViewById(R.id.lay_set);
        lay_audio.setVisibility(View.VISIBLE);
        lay_fav.setVisibility(View.VISIBLE);
        lay_share.setVisibility(View.VISIBLE);
        lay_settingss.setVisibility(View.VISIBLE);


        if (getText_size() == 0) {
            f0 = 20f;
        } else {
            f0 = getText_size();
        }
        if (getPitch_value() == 0) {
            f1 = 50f;
        } else {
            f1 = getPitch_value();
        }
        if (getSpeed_value() == 0) {
            f2 = 50f;
        } else {
            f2 = getSpeed_value();
        }
        text_size_picker.setValue(f0);
        pitch_picker.setValue(f1);
        pitch = 2.0F * (f1 / 100.0F);
        speed_picker.setValue(f2);
        speed = 2.0F * (f2 / 100.0F);


        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = tts.setLanguage(Locale.getDefault());
                    tts.setPitch(pitch);
                    tts.setSpeechRate(speed);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                        Toast.makeText(getApplicationContext(), "Please Install The Following " +
                                "Language Files And Get Back To The Application!", Toast.LENGTH_SHORT).show();
                        Intent localIntent = new Intent();
                        localIntent.setAction("android.speech.tts.engine.INSTALL_TTS_DATA");
                        startActivity(localIntent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Could Not Initialise Engine", Toast.LENGTH_SHORT).show();
                }
            }
        });


        text_size_picker.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(HoloCircleSeekBar seekBar) {
                // Nothing to do
            }

            @Override
            public void onStopTrackingTouch(HoloCircleSeekBar seekBar) {
                // Nothing to do
            }

            @Override
            public void onProgressChanged(HoloCircleSeekBar seekBar, int progress, boolean fromUser) {
                f0 = progress;
                FragmentStatePagerAdapter a = (FragmentStatePagerAdapter) mPager.getAdapter();
                story_desc = (Story_Desc) a.instantiateItem(mPager, mPager.getCurrentItem());
                if (story_desc.tv_desc != null)
                    story_desc.tv_desc.setTextSize(TypedValue.COMPLEX_UNIT_SP, f0);
            }

        });

        pitch_picker.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(HoloCircleSeekBar seekBar) {
                // Nothing to do
            }

            @Override
            public void onStopTrackingTouch(HoloCircleSeekBar seekBar) {
                // Nothing to do
            }

            @Override
            public void onProgressChanged(HoloCircleSeekBar seekBar, int progress, boolean fromUser) {
                f1 = progress;
                pitch = 2.0F * (f1 / 100.0F);

            }

        });


        speed_picker.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(HoloCircleSeekBar seekBar) {
                // Nothing to do
            }

            @Override
            public void onStopTrackingTouch(HoloCircleSeekBar seekBar) {
                // Nothing to do
            }

            @Override
            public void onProgressChanged(HoloCircleSeekBar seekBar, int progress, boolean fromUser) {

                f2 = progress;
                speed = 2.0F * (f2 / 100.0F);

            }

        });

        lay_audio.setVisibility(View.VISIBLE);
        lay_fav.setVisibility(View.VISIBLE);
        lay_share.setVisibility(View.VISIBLE);
        lay_settingss.setVisibility(View.VISIBLE);
      //  myDbHelper = new Assets_DatabaseHandler(_context);
       /* final String name = myDbHelper.getFav(my_title.get(mPager.getCurrentItem()));
        if (name.equalsIgnoreCase("Y")) {
            img_fav.setVisibility(View.GONE);
            img_fav_done.setVisibility(View.VISIBLE);
        } else {
            img_fav.setVisibility(View.VISIBLE);
            img_fav_done.setVisibility(View.GONE);
        }*/

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*final String name = myDbHelper.getFav(my_title.get(position));
                if (name.equalsIgnoreCase("Y")) {
                    img_fav.setVisibility(View.GONE);
                    img_fav_done.setVisibility(View.VISIBLE);
                } else {
                    img_fav.setVisibility(View.VISIBLE);
                    img_fav_done.setVisibility(View.GONE);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        lay_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIntent_Stories("fav");
            }
        });
        lay_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIntent_Stories("share");
            }
        });
        lay_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIntent_Stories("audio");
            }
        });
        lay_settingss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIntent_Stories("settingss");
            }
        });
        lay_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIntent_Stories("language");
            }
        });
        lay_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIntent_Stories("default");
            }
        });
        lay_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIntent_Stories("set");
            }
        });

    }

    public float getText_size() {
        float t_size;
        t_size = settings.getFloat(text_size, 0);
        return t_size;
    }

    public float getPitch_value() {
        float pitch;
        pitch = settings.getFloat(pitch_value, 0);
        return pitch;
    }

    public float getSpeed_value() {
        float speed;
        speed = settings.getFloat(speed_value, 0);
        return speed;
    }

    private HashMap<String, String> getAllItemList() {
        story_title_desc = new HashMap<>();
      /*  if (Stories_List.catergory_level.equalsIgnoreCase("Favorite")) {
            Cursor c = myDbHelper.readData_favorite("Y");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                String title = c.getString(c.getColumnIndex("KEY_TITLE"));
                String desc = c.getString(c.getColumnIndex("KEY_DESC"));
                story_title_desc.put(title, desc);
                c.moveToNext();
            }
        } else {
            Cursor cc = myDbHelper.readData();
            cc.moveToFirst();
            while (!cc.isAfterLast()) {
                String title = cc.getString(cc.getColumnIndex("KEY_TITLE"));
                String desc = cc.getString(cc.getColumnIndex("KEY_DESC"));
                story_title_desc.put(title, desc);
                cc.moveToNext();
            }
        }*/
        return story_title_desc;
    }

    private List<String> getTitles() {
        my_titles = new ArrayList<>();
       /* if (Stories_List.catergory_level.equalsIgnoreCase("Favorite")) {
            Cursor c = myDbHelper.readData_favorite("Y");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndex("KEY_TITLE"));
                my_titles.add(name);
                c.moveToNext();
            }
        } else {
            Cursor cc = myDbHelper.readData();
            cc.moveToFirst();
            while (!cc.isAfterLast()) {
                String title = cc.getString(cc.getColumnIndex("KEY_TITLE"));
                my_titles.add(title);
                cc.moveToNext();
            }
        }*/
        return my_titles;
    }

    public void Save_Settings(float a, float b, float c) {
        editor.putFloat(text_size, a);
        editor.putFloat(pitch_value, b);
        editor.putFloat(speed_value, c);
        editor.commit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void showMenu() {
        int cx = (attachmentLayout.getLeft() + attachmentLayout.getRight());
        int cy = attachmentLayout.getTop();
        int radius = Math.max(attachmentLayout.getWidth(), attachmentLayout.getHeight());

        if (isHidden) {
            Animator anim = android.view.ViewAnimationUtils.createCircularReveal(attachmentLayout, cx, cy, 0, radius);
            lay_settings.setVisibility(View.VISIBLE);
            attachmentLayout.setVisibility(View.VISIBLE);
            anim.start();
            isHidden = false;
        } else {
            Animator anim = android.view.ViewAnimationUtils.createCircularReveal(attachmentLayout, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    lay_settings.setVisibility(View.INVISIBLE);
                    attachmentLayout.setVisibility(View.INVISIBLE);
                    isHidden = true;
                }
            });
            anim.start();
        }
    }


    public void speakOut(String my_story) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(my_story);
        } else {
            ttsUnder20(my_story);
        }
    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId = this.hashCode() + "";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    public void onClickIntent_Stories(String from) {
        switch (from) {

            case "fav":
               /* if (img_fav.getVisibility() == View.VISIBLE) {
                    img_fav.setVisibility(View.GONE);
                    img_fav_done.setVisibility(View.VISIBLE);
                    Animation animFadeIn = AnimationUtils.loadAnimation(_context,
                            R.anim.fav_done);
                    img_fav.startAnimation(animFadeIn);
                    myDbHelper.update_fav(my_title.get(mPager.getCurrentItem()).toString(), "Y");
                    Toast.makeText(_context, "Added into favorites list", Toast.LENGTH_SHORT).show();
                    // MyApplication.getInstance().trackEvent("add favorite", "Favorite|Add", "Add Favorite");
                } else {
                    img_fav_done.setVisibility(View.GONE);
                    img_fav.setVisibility(View.VISIBLE);
                    myDbHelper = new Assets_DatabaseHandler(_context);
                    myDbHelper.update_fav(my_title.get(mPager.getCurrentItem()).toString(), "N");
                    Toast.makeText(_context, "Removed from the favorites list", Toast.LENGTH_SHORT).show();
                    //  MyApplication.getInstance().trackEvent("remove favorite", "Favorite|Remove", "Remove Favorite");
                }*/


                break;
            case "share":

                /*if (Build.VERSION.SDK_INT >= 23)
                    if (!PermissionUtils.checkAndRequestPermission(Stories.this, REQUEST_CODE_ASK_PERMISSIONS, "You need to grant access to Write Storage", permission[0]))
                        return;
                FragmentStatePagerAdapter share = (FragmentStatePagerAdapter) mPager.getAdapter();
                story_desc = (Story_Desc) share.instantiateItem(mPager, mPager.getCurrentItem());
                if (story_desc.tv_desc != null)
                    filePath = createPdf(my_title.get(mPager.getCurrentItem()).toString(), story_desc.tv_desc.getText().toString());
                intentShareFile = new Intent(Intent.ACTION_SEND);
                intentShareFile.setType("application/pdf");
                intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
                intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                        "Sharing File...");
                intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
                startActivity(Intent.createChooser(intentShareFile, "Share File"));

                break;*/
            case "audio":
              /*  if (audio_flag) {
                    audio_flag = false;
                    img_speaker.setImageResource(R.drawable.audio_off);
                    FragmentStatePagerAdapter a = (FragmentStatePagerAdapter) mPager.getAdapter();
                    story_desc = (Story_Desc) a.instantiateItem(mPager, mPager.getCurrentItem());
                    if (story_desc.tv_desc != null)
                        speakOut(story_desc.tv_desc.getText().toString());
                } else {
                    img_speaker.setImageResource(R.drawable.audio_on);
                    tts.stop();
                    audio_flag = true;
                }*/
                break;
            case "settingss":
                if (!audio_flag) {
                  //  img_speaker.setImageResource(R.drawable.audio_on);
                    tts.stop();
                    audio_flag = true;
                }
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                  //  showMenuBelowLollipop();
                } else {
                    showMenu();
                }
                break;
            case "language":
                builder.setTitle("Languages");
                builder.setItems(getResources().getStringArray(R.array.list_local_language),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int selection = which;
                                switch (which) {
                                    case 0:
                                        result = tts.setLanguage(Locale.US);
                                        break;
                                    case 1:
                                        result = tts.setLanguage(Locale.UK);
                                        break;
                                    case 2:
                                        result = tts.setLanguage(Locale.CANADA);
                                        break;
                                    case 3:
                                        result = tts.setLanguage(Locale.CANADA_FRENCH);
                                        break;
                                    case 4:
                                        result = tts.setLanguage(Locale.CHINA);
                                        break;
                                    case 5:
                                        result = tts.setLanguage(Locale.CHINESE);
                                        break;
                                    case 6:
                                        result = tts.setLanguage(Locale.FRANCE);
                                        break;
                                    case 7:
                                        result = tts.setLanguage(Locale.FRENCH);
                                        break;
                                    case 8:
                                        result = tts.setLanguage(Locale.GERMAN);
                                        break;
                                    case 9:
                                        result = tts.setLanguage(Locale.GERMANY);
                                        break;
                                    case 10:
                                        result = tts.setLanguage(Locale.ITALIAN);
                                        break;
                                    case 11:
                                        result = tts.setLanguage(Locale.ITALY);
                                        break;
                                    case 12:
                                        result = tts.setLanguage(Locale.JAPAN);
                                        break;
                                    case 13:
                                        result = tts.setLanguage(Locale.JAPANESE);
                                        break;
                                    case 14:
                                        result = tts.setLanguage(Locale.KOREA);
                                        break;
                                    case 15:
                                        result = tts.setLanguage(Locale.KOREAN);
                                        break;
                                    case 16:
                                        result = tts.setLanguage(Locale.TAIWAN);
                                        break;
                                    default:
                                        result = tts.setLanguage(Locale.getDefault());
                                        break;
                                }

                                dialog.cancel();
                            }
                        });
                builder.show();
                break;
            case "default":
                Save_Settings(0, 0, 0);
                f0 = 20;
                f1 = 50;
                f2 = 50;
                text_size_picker.setValue(f0);
                pitch_picker.setValue(f1);
                speed_picker.setValue(f2);
                pitch = 2.0F * (f1 / 100.0F);
                speed = 2.0F * (f2 / 100.0F);
                FragmentStatePagerAdapter a = (FragmentStatePagerAdapter) mPager.getAdapter();
                story_desc = (Story_Desc) a.instantiateItem(mPager, mPager.getCurrentItem());
                if (story_desc.tv_desc != null)
                    story_desc.tv_desc.setTextSize(TypedValue.COMPLEX_UNIT_SP, f0);
                tts.setPitch(pitch);
                tts.setSpeechRate(speed);
               /* if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    showMenuBelowLollipop();
                } else {*/
                    showMenu();
                /*}*/

                if (audio_flag) {
                    audio_flag = false;
                   // img_speaker.setImageResource(R.drawable.audio_off);
                    if (story_desc.tv_desc != null)
                        speakOut(story_desc.tv_desc.getText().toString());

                } else {
                    //img_speaker.setImageResource(R.drawable.audio_on);
                    tts.stop();
                    audio_flag = true;
                }
                break;
            case "set":
                Save_Settings(f0, f1, f2);
                tts.setPitch(pitch);
                tts.setSpeechRate(speed);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                  //  showMenuBelowLollipop();
                } else {
                    showMenu();
                }
                FragmentStatePagerAdapter a2 = (FragmentStatePagerAdapter) mPager.getAdapter();
                story_desc = (Story_Desc) a2.instantiateItem(mPager, mPager.getCurrentItem());
                if (story_desc.tv_desc != null)
                    story_desc.tv_desc.setTextSize(TypedValue.COMPLEX_UNIT_SP, f0);
                if (audio_flag) {
                    audio_flag = false;
                 //   img_speaker.setImageResource(R.drawable.audio_off);
                    if (story_desc.tv_desc != null)
                        speakOut(story_desc.tv_desc.getText().toString());

                } else {
                  //  img_speaker.setImageResource(R.drawable.audio_on);
                    tts.stop();
                    audio_flag = true;
                }
                break;
        }

    }

    @Override
    protected void onStop() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onStop();
    }

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {

        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        if (filePath != "") {
            try {
            //    deleteImage(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
        System.gc();
    }



    @Override
    public void onBackPressed() {
        if (tts.isSpeaking()) {
            tts.stop();
            tts.shutdown();
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}


