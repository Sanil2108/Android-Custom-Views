package sanilk.com.customviews.views.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import sanilk.com.customviews.R;

public class Slider extends RelativeLayout {

    private Context mContext;

    private boolean sliding = false;

    private int count = 4;

    View[] mViews;

    private int shrinkedSize = 100;
    private int expandedSize = 350;

    private int defaultExpanded = 0;

    private ValueAnimator mRightValueAnimator;
    private ValueAnimator mLeftValueAnimator;

    private int enabledId;

    private ImageView toEnableRect;
    private ImageView toEnableRightBall;
    private View toEnable;
    private float toEnableViewCurrentX;
    private float toEnableViewNewX;
    private float enabledRectCurrentX;
    private float enabledRectNewX;
    private float toEnableRightBallNewX;
    private float toEnableRightBallCurrentX;

    public Slider(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        this.mContext = context;

//        this.setOrientation(LinearLayout.HORIZONTAL);

        mViews = new View[count];

        enabledId = defaultExpanded;

        int leftMarginCounter = 0;

        for (int i=0;i<count;i++) {
            leftMarginCounter += 20;

            View view = inflate(context, R.layout.slider_ball_image, null);
            this.addView(view);

            ImageView leftBall = view.findViewById(R.id.slider_ball_image_left);
            ImageView rightBall = view.findViewById(R.id.slider_ball_image_right);
            ImageView centerRect = view.findViewById(R.id.slider_ball_image_rect);

            mViews[i] = view;

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();

            layoutParams.leftMargin = leftMarginCounter;

            RelativeLayout.LayoutParams leftLayoutParams = (RelativeLayout.LayoutParams) leftBall.getLayoutParams();
            RelativeLayout.LayoutParams centerLayoutParams = (RelativeLayout.LayoutParams) centerRect.getLayoutParams();
            RelativeLayout.LayoutParams rightLayoutParams = (RelativeLayout.LayoutParams) rightBall.getLayoutParams();

            leftLayoutParams.height = shrinkedSize;
            rightLayoutParams.height = shrinkedSize;
            centerLayoutParams.height = shrinkedSize;

            // TODO: This is the default margin
            layoutParams.rightMargin = 10;

            if(defaultExpanded == i) {
                // If enabled
                layoutParams.width = expandedSize;

                leftLayoutParams.width = shrinkedSize;
                rightLayoutParams.width = shrinkedSize;
                centerLayoutParams.width = expandedSize - shrinkedSize;

                leftLayoutParams.leftMargin = 0;
                centerLayoutParams.leftMargin = shrinkedSize / 2;
                rightLayoutParams.leftMargin = expandedSize - shrinkedSize;

                leftMarginCounter += expandedSize;

            }else{
                layoutParams.width = shrinkedSize;

                leftLayoutParams.width = shrinkedSize;
                rightLayoutParams.width = shrinkedSize;
                centerLayoutParams.width = 0;

                leftLayoutParams.leftMargin = 0;
                centerLayoutParams.leftMargin = shrinkedSize/2;
                rightLayoutParams.leftMargin = 0;

                leftMarginCounter += shrinkedSize;
            }

            leftBall.setLayoutParams(leftLayoutParams);
            centerRect.setLayoutParams(centerLayoutParams);
            rightBall.setLayoutParams(rightLayoutParams);

            view.setLayoutParams(layoutParams);

        }

        mRightValueAnimator = ValueAnimator.ofFloat(0, 100);
        mRightValueAnimator.setDuration(400);
        mRightValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Update the one to be enabled
                RelativeLayout.LayoutParams toEnableRectLayoutParams = (RelativeLayout.LayoutParams) toEnableRect.getLayoutParams();
                toEnableRectLayoutParams.width = (int) ( ( ((float)animation.getAnimatedValue()/100) * (expandedSize - shrinkedSize)) + 0);
                toEnableRect.setLayoutParams(toEnableRectLayoutParams);

                // Set the X of the entire view
                toEnable.setX((animation.getAnimatedFraction()) * (toEnableViewNewX - toEnableViewCurrentX) + toEnableViewCurrentX);

                // Set the x of the right circle of the new enabled view
                RelativeLayout.LayoutParams toEnableRightBallLayoutParams = (RelativeLayout.LayoutParams) toEnableRightBall.getLayoutParams();
//                toEnableRightBall.setX(( ( ((float)animation.getAnimatedValue()/100) * (toEnableRightBallNewX - toEnableRightBallCurrentX)) + toEnableRightBallCurrentX));
                toEnableRightBallLayoutParams.leftMargin = (int) ( ( ((float)animation.getAnimatedValue()/100) * (expandedSize - shrinkedSize)) + 0);
                toEnableRightBall.setLayoutParams(toEnableRightBallLayoutParams);

                // Increase the width of the entire view
                ViewGroup.LayoutParams toEnableLayoutParams = toEnable.getLayoutParams();
                toEnableLayoutParams.width = (int) ( ( ((float)animation.getAnimatedValue()/100) * (expandedSize - shrinkedSize)) + shrinkedSize);
                toEnable.setLayoutParams(toEnableLayoutParams);

                // Update the one to be disabled
                ImageView enabledRectImageView = mViews[enabledId].findViewById(R.id.slider_ball_image_rect);
                RelativeLayout.LayoutParams enabledRectImageViewLayoutParams = (RelativeLayout.LayoutParams) enabledRectImageView.getLayoutParams();
                enabledRectImageViewLayoutParams.width = (int) ( ( (1 - (float)animation.getAnimatedValue()/100) * (expandedSize - shrinkedSize)));

                ImageView enabledRightCircle = mViews[enabledId].findViewById(R.id.slider_ball_image_right);
                RelativeLayout.LayoutParams enabledRightCircleLayoutParams = (RelativeLayout.LayoutParams)enabledRightCircle.getLayoutParams();
                enabledRightCircleLayoutParams.leftMargin = (int) ( ( (1 - (float)animation.getAnimatedValue()/100) * (expandedSize - shrinkedSize)));
                enabledRightCircle.setLayoutParams(enabledRightCircleLayoutParams);
            }
        });

        mRightValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                enabledId = enabledId + 1;

                sliding = false;
            }
        });


        mLeftValueAnimator = ValueAnimator.ofFloat(0, 100);
        mLeftValueAnimator.setDuration(400);
        mLeftValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Update the one to be enabled
                RelativeLayout.LayoutParams toEnableRectLayoutParams = (RelativeLayout.LayoutParams) toEnableRect.getLayoutParams();
                toEnableRectLayoutParams.width = (int) ( ( ((float)animation.getAnimatedValue()/100) * (expandedSize - shrinkedSize)) + 0);
                toEnableRect.setLayoutParams(toEnableRectLayoutParams);

                // Set the right circle of the view to be enabled
                RelativeLayout.LayoutParams toEnableRightCircleLayoutParams = (RelativeLayout.LayoutParams) toEnableRightBall.getLayoutParams();
                toEnableRightCircleLayoutParams.leftMargin = (int) ( ( ((float)animation.getAnimatedValue()/100) * (expandedSize - shrinkedSize)) + 0);
                toEnableRightBall.setLayoutParams(toEnableRightCircleLayoutParams);

                // Increase the width of the entire view
                ViewGroup.LayoutParams toEnableLayoutParams = toEnable.getLayoutParams();
                toEnableLayoutParams.width = (int) ( ( ((float)animation.getAnimatedValue()/100) * (expandedSize - shrinkedSize)) + shrinkedSize);
                toEnable.setLayoutParams(toEnableLayoutParams);

                // Update the one to be disabled
                ImageView enabledRectImageView = mViews[enabledId].findViewById(R.id.slider_ball_image_rect);
                RelativeLayout.LayoutParams enabledRectImageViewLayoutParams = (RelativeLayout.LayoutParams) enabledRectImageView.getLayoutParams();
                enabledRectImageViewLayoutParams.width = (int) ( ( (1 - (float)animation.getAnimatedValue()/100) * (expandedSize - shrinkedSize)));
                enabledRectImageView.setX( ( ( ((float)animation.getAnimatedValue()/100) * (enabledRectNewX - enabledRectCurrentX)) + enabledRectCurrentX));
                enabledRectImageView.setLayoutParams(enabledRectImageViewLayoutParams);

                RelativeLayout.LayoutParams enabledLeftBallLayoutParams = (RelativeLayout.LayoutParams) mViews[enabledId].findViewById(R.id.slider_ball_image_left).getLayoutParams();
                enabledLeftBallLayoutParams.leftMargin = (int) ( ( ((float)animation.getAnimatedValue()/100) * (expandedSize - shrinkedSize)) + 0);
                mViews[enabledId].findViewById(R.id.slider_ball_image_left).setLayoutParams(enabledLeftBallLayoutParams);
            }
        });

        mLeftValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                enabledId = enabledId - 1;

                sliding = false;
            }
        });
    }

    public void expandLeft(){
        if(!sliding) {
            if (enabledId > 0) {
                sliding = true;
                toEnable = mViews[enabledId - 1];
                toEnableRect = mViews[enabledId - 1].findViewById(R.id.slider_ball_image_rect);
                toEnableRightBall = mViews[enabledId - 1].findViewById(R.id.slider_ball_image_right);
                enabledRectCurrentX = mViews[enabledId].findViewById(R.id.slider_ball_image_rect).getX();
                enabledRectNewX = enabledRectCurrentX + (expandedSize - shrinkedSize);

                mLeftValueAnimator.start();
            }
        }
    }

    public void expandRight(){
        if(!sliding) {
            if (enabledId < mViews.length - 1) {
                sliding = true;
                toEnable = mViews[enabledId + 1];
                toEnableRect = mViews[enabledId + 1].findViewById(R.id.slider_ball_image_rect);
                toEnableRightBall = mViews[enabledId + 1].findViewById(R.id.slider_ball_image_right);
                toEnableViewCurrentX = mViews[enabledId + 1].getX();
                toEnableViewNewX = toEnableViewCurrentX - expandedSize + shrinkedSize;

                mRightValueAnimator.start();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
