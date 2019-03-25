package sanilk.com.customviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import sanilk.com.customviews.views.bubble_image_view.BubbleImageView;

public class BubbleImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_image_view);

        RelativeLayout root = findViewById(R.id.activity_bubble_image_view_root);
        root.setClipChildren(false);

        final BubbleImageView bubbleImageView = findViewById(R.id.activity_bubble_image_view_bubble);

        Button startButton = findViewById(R.id.activity_bubble_image_view_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bubbleImageView.startAnimation();
            }
        });
    }
}
