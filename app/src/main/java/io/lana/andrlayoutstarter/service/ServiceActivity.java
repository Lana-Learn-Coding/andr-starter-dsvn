package io.lana.andrlayoutstarter.service;

import android.os.Bundle;
import android.widget.ListView;

import io.lana.andrlayoutstarter.NavigableActivity;
import io.lana.andrlayoutstarter.R;

public class ServiceActivity extends NavigableActivity {
    private static final New loremNew = new New(
            "Tàu chất lượng cao vận tải Hà Nội",
            "Một thực tế đã có từ lâu rằng người đọc sẽ bị phân tâm bởi nội dung có thể đọc được của một trang khi nhìn vào bố cục của nó. Điểm đáng chú ý của việc sử dụng Lorem Ipsum là nó có sự phân bố các chữ cái bình thường ít nhiều, trái ngược với việc sử dụng 'Nội dung ở đây, nội dung ở đây', khiến nó trông giống như tiếng Anh có thể đọc được."
    );
    private static final New[] news = new New[]{loremNew, loremNew};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);


        ListView listNews = findViewById(R.id.list_news);
        listNews.setAdapter(new NewAdapter(this, news));
    }
}