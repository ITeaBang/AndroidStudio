package com.example.first.xmlparsing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    // 기사 제목을 저장할 리스트
    ArrayList<String> titleList;
    ArrayAdapter<String> adapter;
    ListView listView;

    // 링크를 저장할 리스트
    ArrayList<String> linkList;

    // 대화상자
    ProgressDialog progressDialog;

    // 업데이트를 위한 레이아웃
    SwipeRefreshLayout swipeRefreshLayout;

    // UI 갱신을 위한 Handler
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

        }
    };

    // 데이터를 다운로드 받을 Thread
    class ThreadEx extends Thread{
        @Override
        public void run() {

            // 다운로드 받은 문자열을 저장할 객체 생성
            StringBuilder sb = new StringBuilder();

            // 데이터 다운로드 받기
            try{

                URL url = new URL("http://www.hani.co.kr/rss/science/");

                // 연결
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                // 옵션 설정
                con.setUseCaches(false);
                con.setConnectTimeout(30000);

                // 데이터 읽기
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while (true){
                    String line = br.readLine();
                    if(line == null) break;
                    sb.append(line + "\n");
                }
                br.close();
                con.disconnect();
                // Log.e("다운로드 받은 문자열", sb.toString());
            }catch (Exception e){
                Log.e("다운로드 실패", e.getMessage());
            }
            try{
                // SAX Parser를 이용한 Parsing 요청
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                XMLReader reader = parser.getXMLReader();

                // Parsing을 수행할 객체 생성
                SaxHandler saxHandler = new SaxHandler();

                // XML Parsing을 위임
                reader.setContentHandler(saxHandler);

                // 데이터 전달
                InputStream inputStream = new ByteArrayInputStream(sb.toString().getBytes("utf-8"));

                // Parsing 시작
                reader.parse(new InputSource(inputStream));

                // Handler에게 메시지 전달
                handler.sendEmptyMessage(0);

            }catch (Exception e){
                Log.e("Parsing Error", e.getMessage());
            }
        }
    };

    // XML Parsing을 수행해 줄 클래스
    class SaxHandler extends DefaultHandler{
        String content = null;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            // Log.e("태그", "문서 읽기 시작");
            titleList.clear();
            linkList.clear();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            // Log.e("태그", "문서 읽기 종료");
            // Log.e("제목", titleList.toString());
            // Log.e("링크", linkList.toString());
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            // Log.e("시작 태그", qName);
            content = null;

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            // Log.e("종료 태그", qName);
            if(qName.equals("title")){
                titleList.add(content);
            }else if(qName.equals("link")){
                linkList.add(content);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            // Log.e("태그 안의 내용", new String(ch));
            content = new String(ch, start, length);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ListView 초기화
        titleList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titleList);
        listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);

        // 다른 변수 초기화
        linkList = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        progressDialog = ProgressDialog.show(this, "한겨레 과학", "다운로드 중");

        // Thread 시작
        ThreadEx th = new ThreadEx();
        th.start();

        // 하단으로 Drag시 수행할 이벤트 Handler
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                progressDialog = ProgressDialog.show(MainActivity.this, "한겨레 과학", "업데이트 중");
                Thread th = new ThreadEx();
                th.start();
            }
        });

        // ListView의 항목을 클릭시 호출되는 이벤트 Handler
        listView.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = linkList.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent);
            }
        });
    }
}
