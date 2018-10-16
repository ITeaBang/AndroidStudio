package com.example.first.activityuse;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MasterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        // 데이터베이스에서 tb_data 테이블의 category 컬럼의 값을 중복없이 가져와서 List에 삽입
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // select 구문 실행 , sql 구문의 마지막에 세미콜론은 있어도 되고 없어도 된다.
        Cursor cursor = db.rawQuery("select distinct category from tb_data", null);

        // 데이터를 저장할 ArraysList 생성
        ArrayList<String> list = new ArrayList<>();

        // select 구문 순회
        while(cursor.moveToNext()){
            list.add(cursor.getString(0));
        }
        db.close();

        // List의 데이터를 ListView에 출력
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        ListView listView = (ListView)findViewById(R.id.mainList);
        listView.setAdapter(adapter);

        // listView에서 항목을 클릭했을 때 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 선택한 항목의 문자열 가져오기
                TextView textView = (TextView)view;
                String category = textView.getText().toString();

                Intent intent = new Intent(MasterActivity.this, DetailActivity.class);
                intent.putExtra("category", category);
                startActivityForResult(intent, 10);
            }
        });
    }
}
