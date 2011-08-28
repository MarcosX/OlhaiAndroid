package br.android.olhai;

import br.android.olhai.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
 
public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);       
	    
	    for(int i=0;i<getPreferenceScreen().getPreferenceCount();i++){
	    	initSummary(getPreferenceScreen().getPreference(i));
	    }

    }
	
	 @Override 
     protected void onResume(){
         super.onResume();
         // Set up a listener whenever a key changes             
         getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
     }

	
	 public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) { 
         updatePrefSummary(findPreference(key));
     }

     private void initSummary(Preference p){
        if (p instanceof PreferenceCategory){
             PreferenceCategory pCat = (PreferenceCategory)p;
             for(int i=0;i<pCat.getPreferenceCount();i++){
                 initSummary(pCat.getPreference(i));
             }
         }else{
             updatePrefSummary(p);
         }

     }

     private void updatePrefSummary(Preference p){
         if (p instanceof ListPreference) {
             ListPreference listPref = (ListPreference) p; 
             p.setSummary(listPref.getEntry()); 
         }        

     }


}

