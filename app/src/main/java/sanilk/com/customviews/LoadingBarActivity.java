package sanilk.com.customviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import sanilk.com.customviews.views.loading_bar1.LoadingBar;

public class LoadingBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_bar1);

//        RelativeLayout loadingBar1Container = findViewById(R.id.loading_bar_container1);
//        final LoadingBar loadingBar1 = findViewById(R.id.loading_bar1);
//        loadingBar1Container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(loadingBar1.isLoading()){
//                    loadingBar1.stopLoading();
//                }else {
//                    loadingBar1.startLoading();
//                }
//            }
//        });
//
//        RelativeLayout loadingBar2Container = findViewById(R.id.loading_bar2_container);
//        final LoadingBar loadingBar2 = findViewById(R.id.loading_bar2);
//        loadingBar2Container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(loadingBar2.isLoading()){
//                    loadingBar2.stopLoading();
//                }else {
//                    loadingBar2.startLoading();
//                }
//            }
//        });

        RelativeLayout loadingBar3Container = findViewById(R.id.loading_bar3_container);
        final LoadingBar loadingBar3 = findViewById(R.id.loading_bar3);
        loadingBar3Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loadingBar3.isLoading()){
                    loadingBar3.stopLoading();
                }else {
                    loadingBar3.startLoading();
                }
            }
        });
    }
}
