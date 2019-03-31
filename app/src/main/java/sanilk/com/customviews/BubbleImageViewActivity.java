package sanilk.com.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import sanilk.com.customviews.views.bubble_image_view.BubbleImageView;

public class BubbleImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_image_view);

        RelativeLayout root = findViewById(R.id.activity_bubble_image_view_root);
//        root.setClipChildren(false);

        final BubbleImageView bubbleImageView = findViewById(R.id.activity_bubble_image_view_bubble);

        Button startButton = findViewById(R.id.activity_bubble_image_view_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bubbleImageView.startAnimation();
            }
        });

        SeekBar durationSeekBar = findViewById(R.id.activity_bubble_image_view_duration_seek);

        durationSeekBar.setProgress((int)BubbleImageView.DEFAULT_DURATION);

        if(Build.VERSION.SDK_INT > 25) {
            durationSeekBar.setMin(100);
            durationSeekBar.setMax(2000);
        }

        final Context context = this;

        durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                bubbleImageView.setCurrentDuration(progress);
                Toast.makeText(context, -progress+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
