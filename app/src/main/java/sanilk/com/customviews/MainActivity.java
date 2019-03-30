package sanilk.com.customviews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sanilk.com.customviews.views.bubble_image_view.BubbleImageView;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        Button loadingBar1Button = findViewById(R.id.activity_main_loading_bar1);
        loadingBar1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LoadingBarActivity.class);
                startActivity(intent);
            }
        });

        Button materialSwitch = findViewById(R.id.activity_main_material_switch);
        materialSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, MaterialSwitchActivity.class);
                startActivity(intent);
            }
        });

        Button bubbleImageView = findViewById(R.id.activity_main_bubble_image_view);
        bubbleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, BubbleImageViewActivity.class);
                startActivity(intent);
            }
        });

        Button sliderButton = findViewById(R.id.activity_main_slider);
        sliderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SliderActivity.class);
                startActivity(intent);
            }
        });
    }
}
