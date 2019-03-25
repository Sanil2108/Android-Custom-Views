package sanilk.com.customviews.views.bubble_image_view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import sanilk.com.customviews.R;

public class BubbleImageView extends LinearLayout {

    private Context mContext;

    private ObjectAnimator mIncreaseScaleAnimator;
    private ObjectAnimator mDecreaseScaleAnimator;

    private final static int SCALE_INCREASE_SPEED = 500;
    private final static int SCALE_DECREASE_SPEED = 500;

    private ImageView mImageView;

    private final static float SCALE_INCREASE_DEFAULT = 1.5f;
    private final static float SCALE_DECREASE_DEFAULT = 0.8f;

    public BubbleImageView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        this.mContext =context;

        mImageView = (ImageView) inflate(context, R.layout.bubble_image_view, null);
        this.addView(mImageView);

        int rotationFirst = (int)((SCALE_INCREASE_SPEED/((float)SCALE_INCREASE_SPEED + SCALE_DECREASE_SPEED)) * 360);
        int rotationSecond = 360-rotationFirst;

        PropertyValuesHolder scaleXProperty = PropertyValuesHolder.ofFloat("scaleX", 1, SCALE_INCREASE_DEFAULT);
        PropertyValuesHolder scaleYProperty = PropertyValuesHolder.ofFloat("scaleY", 1, SCALE_INCREASE_DEFAULT);
        PropertyValuesHolder rotationProperty = PropertyValuesHolder.ofFloat("rotation", 0, rotationFirst);


        PropertyValuesHolder scaleDownXProperty = PropertyValuesHolder.ofFloat("scaleX", SCALE_INCREASE_DEFAULT, SCALE_DECREASE_DEFAULT);
        PropertyValuesHolder scaleDownYProperty = PropertyValuesHolder.ofFloat("scaleY", SCALE_INCREASE_DEFAULT, SCALE_DECREASE_DEFAULT);
        PropertyValuesHolder rotationDownProperty = PropertyValuesHolder.ofFloat("rotation", rotationFirst, 360);

        mIncreaseScaleAnimator = ObjectAnimator.ofPropertyValuesHolder(mImageView, scaleXProperty, scaleYProperty, rotationProperty).setDuration(SCALE_INCREASE_SPEED);

    }

    public void startAnimation(){
        mIncreaseScaleAnimator.start();
        mIncreaseScaleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mDecreaseScaleAnimator.start();
            }
        });
    }

}
