package sanilk.com.customviews.views.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.ImageViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import sanilk.com.customviews.R;

public class Slider extends RelativeLayout {

    private int setColor;
    private int unsetColor;

    private int setRed = 244;
    private int setGreen = 67;
    private int setBlue = 54;

    private int unsetRed = 150;
    private int unsetGreen = 150;
    private int unsetBlue = 150;

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

        setColor = Color.argb(255, setRed, setGreen, setBlue);
        unsetColor = Color.argb(255, unsetRed, unsetGreen, unsetBlue);

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

                if(Build.VERSION.SDK_INT >= 21) {
                    leftBall.setImageTintList(ColorStateList.valueOf(setColor));
                    rightBall.setImageTintList(ColorStateList.valueOf(setColor));
                    centerRect.setImageTintList(ColorStateList.valueOf(setColor));
                }else {
                    ImageViewCompat.setImageTintList(leftBall, ColorStateList.valueOf(setColor));
                    ImageViewCompat.setImageTintList(rightBall, ColorStateList.valueOf(setColor));
                    ImageViewCompat.setImageTintList(centerRect, ColorStateList.valueOf(setColor));
                }


            }else{
                layoutParams.width = shrinkedSize;

                leftLayoutParams.width = shrinkedSize;
                rightLayoutParams.width = shrinkedSize;
                centerLayoutParams.width = 0;

                leftLayoutParams.leftMargin = 0;
                centerLayoutParams.leftMargin = shrinkedSize/2;
                rightLayoutParams.leftMargin = 0;

                leftMarginCounter += shrinkedSize;

                if(Build.VERSION.SDK_INT >= 21) {
                    leftBall.setImageTintList(ColorStateList.valueOf(unsetColor));
                    rightBall.setImageTintList(ColorStateList.valueOf(unsetColor));
                    centerRect.setImageTintList(ColorStateList.valueOf(unsetColor));
                }else {
                    ImageViewCompat.setImageTintList(leftBall, ColorStateList.valueOf(unsetColor));
                    ImageViewCompat.setImageTintList(rightBall, ColorStateList.valueOf(unsetColor));
                    ImageViewCompat.setImageTintList(centerRect, ColorStateList.valueOf(unsetColor));
                }
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
                // Update the color of the previously expanded view
                int tempColor = Color.argb(
                        255,
                        (int)((animation.getAnimatedFraction()) * (unsetRed - setRed) + setRed),
                        (int)((animation.getAnimatedFraction()) * (unsetGreen - setGreen) + setGreen),
                        (int)((animation.getAnimatedFraction()) * (unsetBlue - setBlue) + setBlue)
                );
                if(Build.VERSION.SDK_INT >= 21) {
                    ((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_right))).setImageTintList(ColorStateList.valueOf(tempColor));
                    ((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_left))).setImageTintList(ColorStateList.valueOf(tempColor));
                    ((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_rect))).setImageTintList(ColorStateList.valueOf(tempColor));
                }else {
                    ImageViewCompat.setImageTintList((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_right)), ColorStateList.valueOf(tempColor));
                    ImageViewCompat.setImageTintList((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_rect)), ColorStateList.valueOf(tempColor));
                    ImageViewCompat.setImageTintList((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_left)), ColorStateList.valueOf(tempColor));
                }

                int tempColor2 = Color.argb(
                        255,
                        (int)((animation.getAnimatedFraction()) * (setRed - unsetRed) + unsetRed),
                        (int)((animation.getAnimatedFraction()) * (setGreen - unsetGreen) + unsetGreen),
                        (int)((animation.getAnimatedFraction()) * (setBlue - unsetBlue) + unsetBlue)
                );
                if(Build.VERSION.SDK_INT >= 21) {
                    ((ImageView)(mViews[enabledId+1].findViewById(R.id.slider_ball_image_right))).setImageTintList(ColorStateList.valueOf(tempColor2));
                    ((ImageView)(mViews[enabledId+1].findViewById(R.id.slider_ball_image_left))).setImageTintList(ColorStateList.valueOf(tempColor2));
                    ((ImageView)(mViews[enabledId+1].findViewById(R.id.slider_ball_image_rect))).setImageTintList(ColorStateList.valueOf(tempColor2));
                }else {
                    ImageViewCompat.setImageTintList((ImageView)(mViews[enabledId+1].findViewById(R.id.slider_ball_image_right)), ColorStateList.valueOf(tempColor2));
                    ImageViewCompat.setImageTintList((ImageView)(mViews[enabledId+1].findViewById(R.id.slider_ball_image_rect)), ColorStateList.valueOf(tempColor2));
                    ImageViewCompat.setImageTintList((ImageView)(mViews[enabledId+1].findViewById(R.id.slider_ball_image_left)), ColorStateList.valueOf(tempColor2));
                }

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

                if(Build.VERSION.SDK_INT >= 21) {
                    ((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_right))).setImageTintList(ColorStateList.valueOf(unsetColor));
                    ((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_left))).setImageTintList(ColorStateList.valueOf(unsetColor));
                    ((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_rect))).setImageTintList(ColorStateList.valueOf(unsetColor));
                }else {
                    ImageViewCompat.setImageTintList((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_right)), ColorStateList.valueOf(unsetColor));
                    ImageViewCompat.setImageTintList((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_rect)), ColorStateList.valueOf(unsetColor));
                    ImageViewCompat.setImageTintList((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_left)), ColorStateList.valueOf(unsetColor));
                }

                enabledId = enabledId + 1;

                sliding = false;

                if(Build.VERSION.SDK_INT >= 21) {
                    ((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_right))).setImageTintList(ColorStateList.valueOf(setColor));
                    ((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_left))).setImageTintList(ColorStateList.valueOf(setColor));
                    ((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_rect))).setImageTintList(ColorStateList.valueOf(setColor));
                }else {
                    ImageViewCompat.setImageTintList((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_right)), ColorStateList.valueOf(setColor));
                    ImageViewCompat.setImageTintList((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_rect)), ColorStateList.valueOf(setColor));
                    ImageViewCompat.setImageTintList((ImageView)(mViews[enabledId].findViewById(R.id.slider_ball_image_left)), ColorStateList.valueOf(setColor));
                }
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
