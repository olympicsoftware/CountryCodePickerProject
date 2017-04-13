package com.hbb20;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hbb20 on 11/1/16.
 */
public class CountryCodePicker extends RelativeLayout {

    int defaultCountryCode;
    Context context;
    View holderView;
    LayoutInflater mInflater;
    TextView textView_selectedCountry;
    RelativeLayout holder;
    ImageView imageViewArrow;
    ImageView imageViewFlag;
    LinearLayout linearFlagHolder;
    Country selectedCountry;
    CountryCodePicker codePicker;
    boolean hideNameCode = false;
    boolean showFullName = true;
    List<Country> preferredCountries;
    Language customLanguage = Language.ENGLISH;
    boolean keyboardAutoPopOnSearch = true;
    View.OnClickListener countryCodeHolderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestFocus();
            CountryCodeDialog.openCountryCodeDialog(codePicker);
        }
    };

    public String getName(){
        return selectedCountry.getName();
    }

    public Country getSelectedCountry(){
        return selectedCountry;
    }

    private OnCountryChangeListener onCountryChangeListener;

    public CountryCodePicker(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CountryCodePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CountryCodePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        setClickable(true);
        mInflater = LayoutInflater.from(context);
        holderView = mInflater.inflate(R.layout.layout_code_picker, this, true);
        textView_selectedCountry = (TextView) holderView.findViewById(R.id.textView_selectedCountry);
        holder = (RelativeLayout) holderView.findViewById(R.id.countryCodeHolder);
        imageViewArrow = (ImageView) holderView.findViewById(R.id.imageView_arrow);
        imageViewFlag = (ImageView) holderView.findViewById(R.id.image_flag);
        linearFlagHolder = (LinearLayout) holderView.findViewById(R.id.linear_flag_holder);
        codePicker = this;
        holder.setOnClickListener(countryCodeHolderClickListener);
        setSelectedCountry("New Zealand");
    }

    public void setSelectedCountry(String country) {
        Country countryForName = Country.getCountryForName(customLanguage, preferredCountries, country);
        if(countryForName != null) {
            selectedCountry = countryForName;
            setSelectedCountry(selectedCountry);
        }
    }

    void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
        // as soon as country is selected, textView should be updated
        if (selectedCountry == null) {
            selectedCountry = Country.getCountryForCode(customLanguage, preferredCountries, defaultCountryCode);
        }

        if (!hideNameCode) {
            if (showFullName) {
                textView_selectedCountry.setText(selectedCountry.getName());
            } else {
                textView_selectedCountry.setText("(" + selectedCountry.getNameCode().toUpperCase() + ")  +" + selectedCountry.getPhoneCode());
            }
        } else {
            textView_selectedCountry.setText("+" + selectedCountry.getPhoneCode());
        }

        if (onCountryChangeListener != null) {
            onCountryChangeListener.onCountrySelected();
        }

        imageViewFlag.setImageResource(selectedCountry.getFlagID());
    }


    boolean isKeyboardAutoPopOnSearch() {
        return keyboardAutoPopOnSearch;
    }

    String getDialogTitle() {
        return "Select country";
    }

    String getSearchHintText() {
        return "Search...";
    }

    String getNoResultFoundText() {
        return "No result found";
    }

    /**
     * Update every time new language is supported #languageSupport
     */
    //add an entry for your language in attrs.xml's <attr name="language" format="enum"> enum.
    //add getMasterListForLanguage() to Country.java

    //add here so that language can be set programmatically
    public enum Language {
        ARABIC, BENGALI, CHINESE, ENGLISH, FRENCH, GERMAN, GUJARATI, HINDI, JAPANESE, JAVANESE, PORTUGUESE, RUSSIAN, SPANISH
    }

    /*
    interface to set change listener
     */
    public interface OnCountryChangeListener {
        void onCountrySelected();
    }
}
