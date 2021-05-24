package com.example.studentsector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AddActivity extends AppCompatActivity {
private EditText edtStuId;
    private EditText  edtStuName;
    private EditText edtphonenumber;
    private EditText edtStuclass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setUpViews();
    }

    private void setUpViews() {
        edtStuId =  findViewById(R.id.edtStuId);
        edtStuName = findViewById(R.id.edtStuName);
        edtphonenumber = findViewById(R.id.edtphoneNumber);
        edtStuclass = findViewById(R.id.edtStuClass);


    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String ID = edtStuId.getText().toString();
        String name = edtStuName.getText().toString();
        String phone = edtphonenumber.getText().toString();
        String Class=edtStuclass.getText().toString();
        String data = URLEncoder.encode("ID", "UTF-8")
                + "=" + URLEncoder.encode(ID, "UTF-8");

        data += "&" + URLEncoder.encode("name", "UTF-8") + "="
                + URLEncoder.encode(name, "UTF-8");

        data += "&" + URLEncoder.encode("phone", "UTF-8")
                + "=" + URLEncoder.encode(phone, "UTF-8");
        data += "&" + URLEncoder.encode("Class", "UTF-8")
                + "=" + URLEncoder.encode(Class, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(restUrl);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        // Show response on activity
        return text;



    }

    private class SendPostRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return processRequest(urls[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(AddActivity.this, "Add", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAddOnClick(View view) {
        String restUrl = "http://192.168.1.105/androidproject/add.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            SendPostRequest runner = new SendPostRequest();
            runner.execute(restUrl);
        }
    }

}