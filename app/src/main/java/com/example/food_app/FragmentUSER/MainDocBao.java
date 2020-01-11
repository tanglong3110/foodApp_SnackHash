package com.example.food_app.FragmentUSER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.food_app.Model.DocBao;
import com.example.food_app.MyAdapter.AdapterThongBao_Fragment;
import com.example.food_app.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainDocBao extends AppCompatActivity {
    AdapterThongBao_Fragment adapter;
    ArrayList<DocBao> listDB;
    ListView lv;
    String linklist ;

    String hinhanh = "";
    String title= "";
    String link= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doc_bao);
        setTitle("Đọc báo ");
        lv = findViewById(R.id.lv);
        listDB = new ArrayList<DocBao>();
        linklist = getIntent().getExtras().getString("link");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Readdata().execute(linklist);
            }
        });

    }
    class Readdata extends AsyncTask<String ,Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListdescription  = document.getElementsByTagName("description");

            for(int i = 0; i<nodeList.getLength(); i++){
                String cdata = nodeListdescription.item(i+1).getTextContent();
                // Cach doc hinh
                Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher =pattern.matcher(cdata);
                if(matcher.find()){
                    hinhanh = matcher.group(1);
                }

                Element element  = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                link = parser.getValue(element, "link");
                listDB.add(new DocBao(title, link, hinhanh));
            }
            adapter = new AdapterThongBao_Fragment(MainDocBao.this,android.R.layout.simple_list_item_1, listDB);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainDocBao.this, MainDocBaoWEB.class);
                    intent.putExtra("link",link);
                    startActivity(intent);
                }
            });

        }
    }
    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }
}
