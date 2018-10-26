package com.example.first.datashare;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Button
    Button contactBtn, galleryBtn;

    // Layout
    LinearLayout mainContent;

    // 이미지 출력 크기를 위한 변수
    int reqWidth, reqHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 디자인한 위젯 찾아오기
        contactBtn = (Button) findViewById(R.id.lab2_contacts);
        galleryBtn = (Button) findViewById(R.id.lab2_gallery);
        mainContent = (LinearLayout) findViewById(R.id.lab2_content);

        // Button의 클릭 이벤트를 처리할 객체 설정
        contactBtn.setOnClickListener(this);
        galleryBtn.setOnClickListener(this);

        // 디바이스의 너비와 높이를 가져와서 변수에 대입
        DisplayMetrics dm = getResources().getDisplayMetrics();
        reqHeight = dm.widthPixels;
        reqWidth = dm.widthPixels;

        // OS가 6.0 이상일 때 동적인 권한 요청 - 연락처, 외부 저장 장치
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
    }

    // Button의 클릭 이벤트 처리
    @Override
    public void onClick(View v) {
        // 연락처 클릭
        if (v.getId() == R.id.lab2_contacts) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(Uri.parse("content://com.android.contacts/data/phones"));

            // Activity를 화면에 출력하고 종료되면 10이라는 값과 onActivityResult라는 메소드를 호출
            startActivityForResult(intent, 10);
        }
        // Gallery App 실행하기
        else if (v == galleryBtn) {
            // Gallery App 호출하기
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 20);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 연락처 App에서 확인을 눌렀을 경우
        if (requestCode == 10 && resultCode == RESULT_OK) {

            // 선택한 데이터 가져오기
            String id = Uri.parse(data.getDataString()).getLastPathSegment();
            Cursor cursor = getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    new String[]{ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER},
                    ContactsContract.Contacts._ID + "=" + id, null, null);

            // 처음 행으로 이동
            cursor.moveToNext();
            String name = cursor.getString(0);
            String number = cursor.getString(1);

            // 동적으로 TextView를 만들어서 화면에 추가
            TextView textView = new TextView(this);
            textView.setText(name + ":" + number);
            textView.setTextSize(28);

            // 크기 만들기
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mainContent.addView(textView, params);

        } else if (requestCode == 20 && resultCode == RESULT_OK) {

            // Gallery App의 URI
            Uri uri = data.getData();

            // 선택한 이미지 파일의 파일 경로 생성
            String filepath = getFilePathFromDocumentUrl(this, uri);

            // ImageView에 추가
            insertImageView(filepath);

        }
    }

    private void insertImageView(String filepath) {

        // 파일 경로 존재시
        if (filepath.equals("") == false) {

            // 파일 객체 생성
            File file = new File(filepath);

            // 이미지를 확대, 축소하기위한 객체 생성
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;

            try {
                // 파일의 내용을 읽어서 Bitmap으로 생성
                InputStream in = new FileInputStream(file);
                BitmapFactory.decodeStream(in, null, options);
                in.close();

                // 메모리 정리
                in = null;
            } catch (Exception e) {
                Log.e("이미지 파일 읽기 실패", e.getMessage());
            }

            int width = options.outWidth;
            int sampleSize = 1;

            // 이미지의 크기가 화면 크기보다 크다면 이미지를 축소하기 위해서 나누기를 하고 그 비율을 sampleSize에 대입
            if (width > reqWidth) {
                sampleSize = Math.round((float) width / (float) reqWidth);
            }

            // 확대, 축소 배율을 적용해서 이미지를 다시 읽기
            BitmapFactory.Options imgOptions = new BitmapFactory.Options();
            imgOptions.inSampleSize = sampleSize;
            Bitmap bitmap = BitmapFactory.decodeFile(filepath, imgOptions);

            // Bitmap을 ImageView를 이용해서 출력
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(bitmap);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mainContent.addView(imageView, params);
        }
    }

    // 이미지 파일의 경로를 가져오는 메소드, 이미지 파일은 image : 파일명의 형식
    private String getFilePathFromDocumentUrl(Context context, Uri uri) {
        String docId = DocumentsContract.getDocumentId(uri);
        String[] split = docId.split(":");
        String type = split[0];
        Uri contentUri = null;
        // NullPointerException을 방지하기 위해서 상수를 가지고 비교
        if ("image".equals(type)) {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        // 이미지 파일의 ID 가져오기
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArg = new String[]{split[1]};

        String column = "_data";
        String[] projection = {column};

        // ContentProvider에서 데이터 찾아오기
        Cursor cursor = context.getContentResolver().query(contentUri, projection, selection, selectionArg, null);

        // Cursor의 데이터 읽기
        String filepath = null;
        if (cursor != null && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(column);
            filepath = cursor.getString(column_index);
        }
        cursor.close();
        return filepath;
    }
}
