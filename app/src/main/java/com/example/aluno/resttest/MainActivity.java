package com.example.aluno.resttest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView retorno;
    EditText cep;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cep = (EditText) findViewById(R.id.cep);
        retorno = (TextView) findViewById(R.id.cidade);
        button = (Button) findViewById(R.id.getCEP);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t_cep = cep.getText().toString();
                Task t = new Task();
                t.execute(t_cep);
            }
        });




    }

    private class Task extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            String t_cep = strings[0];
            String cidade = "Nao possui cidade";
            try {
                url = new URL("http://viacep.com.br/ws/"+ t_cep +"/json/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                BufferedReader reader;
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String data = null;
                String content = "";
                while ((data = reader.readLine()) != null) {
                    content += data + "\n";
                }
                JSONObject jsonObject=new JSONObject(content);
                return cidade =jsonObject.getString("localidade");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return cidade;
        }
        protected void onPostExecute(String Result){
            retorno.setText(Result);
        }
    }
}
