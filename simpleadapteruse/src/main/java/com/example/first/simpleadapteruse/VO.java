package com.example.first.simpleadapteruse;

// 아이폰이나 안드로이드 같은 모바일 프로그래밍에서는 데이터를 나타내는 클래스를 만들 때, 구조체 형태를 많이 사용
// 객체 지향의 디자인 패턴 적용시 부피가 커지고 복잡해질 수 있기 때문이다.
public class VO {

    public int icon;
    public String name;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
