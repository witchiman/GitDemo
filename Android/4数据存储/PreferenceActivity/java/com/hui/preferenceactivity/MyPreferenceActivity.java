package com.hui.preferenceactivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

/**
 * Created by HUI on 2016/1/10.
 */
public class MyPreferenceActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new PrefFrament() ).commit();

    }

    public static  class PrefFrament extends PreferenceFragment {
        private PreferenceManager manager;
        private CheckBoxPreference checkBoxPreference;
        private ListPreference listPreference;
        private EditTextPreference editTextPreference;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.mypreference);
            manager = getPreferenceManager();

            checkBoxPreference = (CheckBoxPreference)manager.findPreference("checkbox");
            Toast.makeText(getActivity(), "当前的状态：" + checkBoxPreference.isChecked(), Toast.LENGTH_SHORT).show();

            listPreference = (ListPreference) manager.findPreference("list");
            Toast.makeText(getActivity(), listPreference.getEntry() + "的编辑环境是： " + listPreference.getValue(),
                    Toast.LENGTH_SHORT).show();

            editTextPreference = (EditTextPreference)manager.findPreference("edit");
            Toast.makeText(getActivity(), "输入的内容是： " + editTextPreference.getText() , Toast.LENGTH_SHORT).show();

        }
    }

}
