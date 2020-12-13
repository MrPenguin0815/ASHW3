package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity{

   private List<Data> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager manager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new LinearLayoutManager(this);

        sendRequestWithHttpURLConnection();

    }




    /**
     * 以下使用HttpURLConnection发送请求
     */
    private void sendRequestWithHttpURLConnection() {
        //开启线程发送网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    //想要获取到HttpURLConnection的实例，一般只要new一个URL对象，传入目标网络地址，然后调用openConnection方法
                    URL url = new URL("https://www.wanandroid.com/article/list/0/json");
                    connection = (HttpURLConnection) url.openConnection();

                    //设置HTTP请求所使用的方法，其中，GET表示希望从服务器那里获取数据
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    //再调用getInputStream()方法就可以获取到服务器返回的输入流了
                    InputStream in = connection.getInputStream();

                    //对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));

                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    parseJSONWithJSONObject(response.toString());
                    showResponse();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (connection != null) {
                        connection.disconnect();
                    }
                }


            }
        }).start();
    }






    /**
     * JSONObject方式解析JSON格式数据
     */
    private void parseJSONWithJSONObject(String jsonData) {
        try {

            JSONObject object = new JSONObject(jsonData);
            JSONObject dataObject = object.getJSONObject("data");

            JSONArray jsonArray = dataObject.getJSONArray("datas");

            for (int i = 0; i < jsonArray.length(); i++) {

                //遍历JSONArray，依次从每一个JSONObject对象里用getString()方法来取出要用的数据
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String shareUser = jsonObject.getString("shareUser");
                String link = jsonObject.getString("link");
                int chapterId = jsonObject.getInt("chapterId");

                if("".equals(shareUser)){
                    shareUser = "某雷锋";
                }

                dataList.add(new Data(chapterId,shareUser,title,link));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }






    /**
     * 数据解析完毕后切换到主线程
     */
    private void showResponse() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(manager);
                DataAdapter adapter = new DataAdapter(dataList);
                adapter.setOnItemClickListener(new DataAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                        intent.putExtra("address",dataList.get(position).getLink());
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);

            }
        });
    }


}



//
