package com.example.first.simpleadapteruse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SimpleAdapter를 이용해서 출력할 데이터 생성
        // List<Map<String, Object> 구조
        List<Map<String, String >> list = new ArrayList<>();
        Map<String, String > map = new HashMap<>();
        map.put("name", "조계현");
        map.put("alias", "싸움닭");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "최향남");
        map.put("alias", "관운장");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "이대진");
        map.put("alias", "Ace of Ace");
        list.add(map);

        map = new HashMap<>();
        map.put("name", "권혁");
        map.put("alias", "불꽃");
        list.add(map);

        // 데이터 출력을 위한 Adapter 생성
        // 첫번째는 Context, 두번째는 데이터, 세번째는 셀의 출력 모양
        // 네번째는 출력할 데이터의 키 배열, 다섯번째는 출력할 셀 안의 ID
        SimpleAdapter adapter =
                new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,
                        new String[]{"name", "alias"},
                        new int[]{android.R.id.text1, android.R.id.text2});

        // ListView에 adapter를 연결해서 출력
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
