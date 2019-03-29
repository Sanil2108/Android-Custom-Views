package sanilk.com.customviews.views.bubble_image_view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import sanilk.com.customviews.R;

public class BubbleImageView extends LinearLayout {

    public static final float DEFAULT_FREQUENCY = -7;
    public static final float DEFAULT_AMPLITUDE = -0.3f;
    public static final long DEFAULT_DURATION = 600;

    private float currentFrequency;
    private float currentAmplitude;
    private long currentDuration;

    private Context mContext;

    private ImageView mImageView;

    private Animation animation;
    private MyBounceInterpolator myBounceInterpolator;

    public void setCurrentDuration(long currentDuration) {
        this.currentDuration = currentDuration;
        animation.setDuration(currentDuration);
    }

//    public void setCurrentAmplitude(float currentAmplitude) {
//        this.currentAmplitude = currentAmplitude;
//        myBounceInterpolator.setAmplitude(currentAmplitude);
//    }
//
//    public void setCurrentFrequency(float currentFrequency) {
//        this.currentFrequency = currentFrequency;
//        myBounceInterpolator.setFrequency(currentFrequency);
//    }

    public BubbleImageView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);


        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.BubbleImageView,
                0, 0
        );
        Drawable drawable = getResources().getDrawable(typedArray.getResourceId(R.styleable.BubbleImageView_src, -1));

        // Setting default variables
        currentAmplitude = DEFAULT_AMPLITUDE;
        currentDuration = DEFAULT_DURATION;
        currentFrequency = DEFAULT_FREQUENCY;

        this.mContext =context;

        mImageView = (ImageView) inflate(context, R.layout.bubble_image_view, null);

        this.addView(mImageView);
        mImageView.setImageDrawable(drawable);

        myBounceInterpolator = new MyBounceInterpolator(currentFrequency, currentAmplitude);

        animation = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
        animation.setInterpolator(new MyBounceInterpolator(currentFrequency, currentAmplitude));
        animation.setDuration(currentDuration);
    }

    public void startAnimation(){
        mImageView.startAnimation(animation);
    }


    private static class MyBounceInterpolator implements Interpolator{

        private float frequency;
        private float amplitude;

        public void setFrequency(float frequency) {
            this.frequency = frequency;
        }

        public void setAmplitude(float amplitude) {
            this.amplitude = amplitude;
        }

        public MyBounceInterpolator(float frequency, float amplitude) {
            this.frequency = frequency;
            this.amplitude = amplitude;
        }

        @Override
        public float getInterpolation(float input) {
            return (float)(-(Math.exp(input/amplitude)*Math.cos(frequency*input))+1);
        }
    }
}
