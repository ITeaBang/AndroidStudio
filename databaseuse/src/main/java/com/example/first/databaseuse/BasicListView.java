package com.example.first.databaseuse;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BasicListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_list_view);

        // 출력할 데이터 배열이나 List 생성
        String[] player = {
                "조계현", "최향남", "이대진", "권혁"
        };

        // 위의 데이터를 가지고 Adapter 생성
        // 첫번째 매개변수는 Context
        // 두번째는 출력할 셀의 모양 :  제공되는 모양 사용
        // 세번째는 출력할 데이터
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        player);

        // ListView에 위의 Adapter 연결
        ListView listView =
                findViewById(R.id.listView);

        ArrayAdapter adapter1 =
                ArrayAdapter.createFromResource(
                        this, R.array.players,
                        android.R.layout.simple_list_item_1
                );

        listView.setAdapter(adapter1);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.RED));
        listView.setDividerHeight(3);

        listView.setOnItemClickListener(
                new ListView.OnItemClickListener() {
                    @Override
                    // 첫번째 매개변수는 이벤트가 발생한 객체
                    // 두번째 매개변수는 항목 뷰 - 선택한 항목의 뷰
                    // 세번째 매개변수는 클릭한 항목 뷰의 인덱스
                    // 네번째 매개변수는 항목 뷰의 아이디
                    public void onItemClick(
                            AdapterView<?> parent, View view,
                            int position, long id) {
                        Toast.makeText(BasicListView.this,
                                position + "번째 선택",
                                Toast.LENGTH_LONG).show();
                    }
                });

    }
}

