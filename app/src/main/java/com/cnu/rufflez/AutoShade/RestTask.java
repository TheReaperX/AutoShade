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
public class RestTask extends AsyncTask<String,String, Integer> {

        public static final String BaseUrl = "https://cloud.arest.io/001/";
        private TextView t;

        RestTask(){}
        RestTask(TextView t) {
            this.t = t;
        }
        /*RestTask(TextView t, ProgressDialog d){
            this.t = t;
            this.d = d;
        }*/

        @Override
        protected Integer doInBackground(String... params)
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
                int  return_value= -1;
                try {

                    json = new JSONObject(finalRes);
                    if(json.has("return_value")) {
                        return_value = json.getInt("return_value");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return return_value;
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
    }

    @Override
        protected void onPostExecute(Integer result)
        {
                    String tvString= "";
                    super.onPostExecute(result);
                    int temp = t.getId();
                    System.out.println(temp);
                    if (temp == 2131558516) {
                        if (result == 1) {
                            tvString = "Manual";
                        }
                        else if(result == 0){
                            tvString ="Automatic";
                        }else{
                            tvString= "Error";
                        }
                    }
                    if (temp == 2131558517) {
                        if (result == 1) {
                            tvString = "Open";
                        }
                        else if(result == 0){
                            tvString ="Closed";
                        }else{
                            tvString= "Error";
                        }
                    }
                    t.setText(tvString);


        }


}
