package com.example.first.simpleadapteruse;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    // Context 변수
    Context context;

    // 출력할 데이터의 List
    List<VO> data;

    // 하나의 셀을 만들 때, 레이아웃의 ID
    int layout;

    // layout을 하단에 출력하기 위해서 전개할 Inflater
    LayoutInflater inflater;

    // 생성자
    public MyAdapter(Context context, List<VO> data, int layout) {
        this.context = context;
        this.data = data;
        this.layout = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // 이 메소드가 리턴한 개수만큼 리스트 뷰는 항목을 출력
    @Override
    public int getCount() {
        return data.size();
    }

    // 항목의 이름을 설정하는 메소드
    @Override
    public Object getItem(int position) {
        return data.get(position).getName();
    }

    // 항목의 아이디를 설정하는 메소드
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 항목 뷰를 만드는 메소드
    // 첫번째 매개변수는 출력되는 뷰의 인덱스, 두번째 매개변수는 이전 셀의 주소, 세번째 매개변수는 출력되는 AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // position은 지역 변수이므로 anonymous class에서 사용이 불가능하므로 final로 변환
        final int pos = position;

        // 이전에 넘어온 뷰가 없으면 생성
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        // 짝수 행과 홀수 행의 배경색을 다르게 설정
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.CYAN);
        } else {
            convertView.setBackgroundColor(Color.GRAY);
        }

        // 이미지 출력
        ImageView imageView = convertView.findViewById(R.id.img);
        imageView.setImageResource(data.get(pos).getIcon());

        // 텍스트 출력
        TextView textView = convertView.findViewById(R.id.text);
        textView.setText(data.get(pos).getName());

        // 버튼을 클릭헀을 때, 수행할 내용 작성
        Button button = convertView.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = data.get(pos).getName() + "선택";
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
