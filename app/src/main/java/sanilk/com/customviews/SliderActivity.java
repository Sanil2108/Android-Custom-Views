package sanilk.com.customviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sanilk.com.customviews.views.slider.Slider;

public class SliderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        final Slider slider = findViewById(R.id.slider);

        Button rightSlide = findViewById(R.id.activity_slider_right_slide);
        rightSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slider.expandRight();
            }
        });

        Button leftSlide = findViewById(R.id.activity_slider_left_slide);
        leftSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slider.expandLeft();
            }
        });
    }
}
