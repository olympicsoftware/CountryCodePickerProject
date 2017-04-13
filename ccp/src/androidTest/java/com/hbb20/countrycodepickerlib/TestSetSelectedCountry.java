package com.hbb20.countrycodepickerlib;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hbb20.CountryCodePicker;
import com.hbb20.R;
import com.hbb20.TestActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(AndroidJUnit4.class)
public class TestSetSelectedCountry {

    @Rule
    public ActivityTestRule<TestActivity> activityTestRule = new ActivityTestRule<>(TestActivity.class);

    @Test
    public void invalid_country_name_selected() throws Exception {
        CountryCodePicker ccp = (CountryCodePicker)activityTestRule.getActivity().findViewById(R.id.picker);

        ccp.setSelectedCountry("garbage");
    }
}