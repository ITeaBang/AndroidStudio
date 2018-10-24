package com.example.first.jsonparsing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;

    // ListView 출력 관련 변수 선언
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> nameList;

    // 상세 보기를 위해 id를 저장할 List
    ArrayList<String> idList;

    // 데이터를 다운로드 받는 동안 보여질 대화상자
    ProgressDialog progressDialog;

    // ListView의 데이터를 다시 출력하는 Handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    class ThreadEx extends Thread {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();

            // 다운로드 받는 코드
            try {
                // 다운로드 받을 주소 생성
                URL url = new URL("http://192.168.0.229:8015/androidphone/listitem");
                // Connection 생성
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setUseCaches(false);
                con.setConnectTimeout(30000);
                // 문자열을 다운로드 받을 스트림 만들기
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                // 문자열을 다운로드 받아서 sb에 추가하기
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    {
                        sb.append(line + "\n");
                    }
                }
                br.close();
                con.disconnect();
            } catch (Exception e) {
                Log.e("다운로드 실패", e.getMessage());
            }

            // Parsing 코드
            try {
                // 전체 문자열을 배열로 변경
                JSONArray ar = new JSONArray(sb.toString());
                // 사용 전에 반드시 clear
                nameList.clear();
                idList.clear();
                // 배열 순회
                for (int i = 0; i < ar.length(); i = i + 1) {
                    JSONObject object = ar.getJSONObject(i);
                    // 객체에서 itemname의 값을 가져와서 nameList에 추가
                    nameList.add(object.getString("itemname"));
                    idList.add(object.getString("itemid"));
                }
                // Handler 호출 - 재출력
                handler.sendEmptyMessage(0);
            } catch (Exception e) {
                Log.e("Parsing Error", e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ThreadEx().start();
            }
        });

        nameList = new ArrayList<>();
        idList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList);

        listView = (ListView) findViewById(R.id.itemlist);
        listView.setAdapter(adapter);

        // ListView에서 항목을 클릭했을 때 수행할 내용
        listView.setOnItemClickListener(
                new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("itemid", idList.get(position));
                        startActivity(intent);
                    }
                });
    }

    // Activity가 실행될 때 호출되는 메소드 ( 빠른 Update 가능 )
    @Override
    protected void onResume() {
        super.onResume();
        progressDialog = ProgressDialog.show(this, "", "다운로드 중");
        new ThreadEx().start();
    }
}
