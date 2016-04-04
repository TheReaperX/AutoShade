package com.cnu.rufflez.AutoShade;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rufflez on 6/20/15.
 */
public class homeFragment extends Fragment{

    TextView response;
    TextView response5;
    TextView response4;
    TextView mode;
    TextView openState;
    ToggleButton toggle;
    Button auto;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.home, container, false);

        toggle = (ToggleButton) rootView.findViewById(R.id.toggleButton);
        auto = (Button) rootView.findViewById(R.id.goAuto);
        mode = (TextView) rootView.findViewById(R.id.mode);
        response = (TextView) rootView.findViewById(R.id.response);
        response5 = (TextView) rootView.findViewById(R.id.response5);
        response4 = (TextView) rootView.findViewById(R.id.response4);
        openState = (TextView) rootView.findViewById(R.id.openState);
        dialog = new ProgressDialog(getActivity());
        new RestTask(mode).execute("digital/5/");
        new RestTask(openState).execute("digital/4/");
        if(openState.getText().equals("Open")){
            toggle.setChecked(true);
        }else{
            toggle.setChecked(false);
        }
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                new RestTask(mode).execute("digital/5/1");
                if (isChecked) {
                    // toggled to open
                    //TextView mode = (TextView) rootView.findViewById(R.id.mode);
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "Blinds are opening";
                    int duration = Toast.LENGTH_SHORT;
                    //new RestTask(response5).execute("digital/5/1");
                    openState.setText("Open");
                    new RestTask(openState,dialog).execute("digital/4/1");
                    Toast toast = Toast.makeText(context, text, duration);
                    //toggle.setTextOn("Close");
                    toast.show();


                } else {
                   // TextView mode = (TextView) rootView.findViewById(R.id.mode);
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = "Blinds are closing"; 
                    int duration = Toast.LENGTH_SHORT;
                    //new RestTask(response5).execute("digital/5/1");
                    openState.setText("Closed");
                    new RestTask(openState,dialog).execute("digital/4/0");
                    Toast toast = Toast.makeText(context, text, duration);
                    //toggle.setTextOff("Open");
                    toast.show();

                }
            }
        });
        auto.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new RestTask(mode).execute("digital/5/0");
            }
        });
        return rootView;
    }
}
