package com.example.first.simpleadapteruse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtendListView extends AppCompatActivity {

        // 제목으로 사용할 문자열 배열 생성
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_extend_list_view);

            // 제목으로 사용할 문자열 배열 생성
            String [] main = {
                    "BackEnd", "FrontEnd", "Database"
            };

            // 제목을 누르면 보여질 문자열 배열 생성
            String [][] sub = {
                    {"Java", "Swift", "Python", "C"},
                    {"HTML", "CSS", "JavaScript", "Android", "iOS"},
                    {"Oracle", "MySQL", "MongoDB"}
            };

            // 위의 데이터를 가지고 출력할 수 있는 데이터를 생성
            List<Map<String,String>>mainData = new ArrayList<>();
            List<List<Map<String, String>>>subData = new ArrayList<>();

            for(int i=0; i<main.length; i=i+1){
                Map<String, String>mainMap = new HashMap<String, String>();
                mainMap.put("main", main[i]);
                mainData.add(mainMap);

                List<Map<String, String>> subList = new ArrayList<>();

                for(int j=0; j<sub[i].length; j=j+1){
                    Map<String, String> subMap =
                            new HashMap<>();
                    subMap.put("sub", sub[i][j]);
                    subList.add(subMap);
                }
                subData.add(subList);
            }

            ExpandableListView listView = findViewById(R.id.listView);
            ExpandableListAdapter adapter = new SimpleExpandableListAdapter(this, mainData,
                            android.R.layout.simple_expandable_list_item_1,
                            new String[]{"main"},
                            new int[]{android.R.id.text1},
                            subData,
                            android.R.layout.simple_expandable_list_item_1,
                            new String[]{"sub"},
                            new int[]{android.R.id.text1}
                    );
            listView.setAdapter(adapter);
        }
}
