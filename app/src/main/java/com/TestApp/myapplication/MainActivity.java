package com.TestApp.myapplication;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static com.TestApp.myapplication.Methods.Unit.*;

public class MainActivity extends AppCompatActivity {
    private EditText searchfild;
    private Button searchButton;
    private TextView result;
    private TextView erroeMessage;
    private ProgressBar progressBar;
    private void ShowResult(){
        result.setVisibility(View.VISIBLE);
        erroeMessage.setVisibility(View.INVISIBLE);
    }
    private void showError(){
        result.setVisibility(View.INVISIBLE);
        erroeMessage.setVisibility(View.VISIBLE);
    }

    class VKQuerryTask extends AsyncTask<URL,Void,String>{

        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String response){
            String firstName = null;
            String lastName = null;
            String readyInfo = "";
            if(response !=null && !response.equals("")){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");

                    for(int i = 0; i < jsonArray.length();i++){
                        JSONObject usersInfo = jsonArray.getJSONObject(i);

                        firstName = usersInfo.getString("first_name");
                        lastName = usersInfo.getString("last_name");
                        readyInfo += "Name: " + firstName +"\n" + "Surname: " + lastName +"\n\n";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                result.setText(readyInfo);
                ShowResult();
            } else{
                showError();
            }

            progressBar.setVisibility(View.INVISIBLE);

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.testApp.myapplication.R.layout.activity_main);
        searchfild = findViewById(com.testApp.myapplication.R.id.et_search_field);
        searchButton = findViewById(com.testApp.myapplication.R.id.b_search_vk);
        result = findViewById(com.testApp.myapplication.R.id.tv_result);
        erroeMessage = findViewById(com.testApp.myapplication.R.id.error);
        progressBar = findViewById(com.testApp.myapplication.R.id.pb_loading);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL link = generateURL(searchfild.getText().toString());
                new VKQuerryTask().execute(link);
            }
        };
        searchButton.setOnClickListener(onClickListener);
    }
}
