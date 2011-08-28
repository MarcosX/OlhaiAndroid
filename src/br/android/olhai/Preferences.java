package br.android.olhai;

import br.android.olhai.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
 
public class Preferences extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);       
	    
    }
	
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	    Preference pref = findPreference(key);

	    if (pref instanceof ListPreference) {
	        //ListPreference listPref = (ListPreference) pref;
	        //pref.setSummary(listPref.getEntry());
	    }
	}

}

