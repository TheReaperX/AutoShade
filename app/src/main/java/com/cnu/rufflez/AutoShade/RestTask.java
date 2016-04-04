package com.cnu.rufflez.AutoShade;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nana on 3/14/2016.
 */
public class RestTask extends AsyncTask<String,String, String> {

        public static final String BaseUrl = "https://cloud.arest.io/001/";
        private TextView t;
        private ProgressDialog d;

        RestTask(){}
        RestTask(TextView t) {
            this.t = t;
        }
        RestTask(TextView t, ProgressDialog d){
            this.t = t;
            this.d = d;
        }

        @Override
        protected String doInBackground(String... params)
        {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try{
                URL url = new URL(BaseUrl + params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                String line ="";
                StringBuffer buffer = new StringBuffer();
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                //return buffer.toString();
                String finalRes = buffer.toString();
                JSONObject json = null;
                String message = null;
                int  return_value= -1;
                try {
                    System.out.println(finalRes);
                    json = new JSONObject(finalRes);
                    if(json.has("message")) {
                        message = json.getString("message");
                    }
                    if(json.has("return_value")) {
                        return_value = json.getInt("return_value");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(message == null) {
                    String ret_val = String.valueOf(return_value);
                    return ret_val;
                }
                return message;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection != null)
                    connection.disconnect();
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    @Override
    protected void onPreExecute() {
        if(d != null) {
            d.setTitle("Blinds are operating");
            d.setMessage("Please wait.");
            d.setIndeterminate(true);
            d.setCancelable(false);
            d.show();
        }
    }

    @Override
        protected void onPostExecute(String result)
        {

                    super.onPostExecute(result);
                    int temp = t.getId();
                    System.out.println(temp);
                    if (temp == 2131558516) {
                        if (result.equals("Pin D5 set to 1")) {
                            result = "Manual";
                        }

                        if(result.equals("Pin D5 set to 0")){
                            result = "Automatic";
                        }

                    }
                    if (temp == 2131558517) {
                        if (result.equals("1")) {
                            result = "Open";
                        } else {
                            result = "Closed";
                        }
                    }
                    t.setText(result);
                    if (d != null) {
                        long delayMillis = 43000;
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                d.dismiss();
                            }
                        }, delayMillis);
                    }

        }


}
