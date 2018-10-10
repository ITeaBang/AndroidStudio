package com.example.first.simpleadapteruse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ComboBox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_box);

        // Spinner 가져오기
        Spinner spinner = findViewById(R.id.spinner);

        // Spinner에 출력할 데이터 어댑터 생성
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.team, android.R.layout.simple_spinner_item);

        // Spinner 모양 선택
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Spinner에 데이터 출력
        spinner.setAdapter(adapter);

        // Spinner에서 선택이 변경된 경우 처리
        spinner.setOnItemSelectedListener(
                new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String[] team =
                                getResources().getStringArray(R.array.team);
                        Toast.makeText(ComboBox.this,
                                team[position] + "을(를) 선택",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
    }
}