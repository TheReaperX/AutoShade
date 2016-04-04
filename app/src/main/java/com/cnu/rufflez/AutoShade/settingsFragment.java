package com.cnu.rufflez.AutoShade;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by rufflez on 6/20/15.
 */
public class settingsFragment extends PreferenceFragment {

    /*public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.floating_action_button, container, false);
        FloatingActionButton button = (FloatingActionButton)rootView.findViewById(R.id.fab2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This is a FAB", Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }*/
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager prefMan = getPreferenceManager();
        if(prefMan.getSharedPreferences().getBoolean("Automation",true)){
           // new RestTask().execute("digital/5/0");
            System.out.println("automatic task is finished running");
        }else{
            //new RestTask().execute("digital/5/1");
            System.out.println("manual task is finished running");
        }
    }
}
