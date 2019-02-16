package sanilk.com.customviews.views.loading_bar1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.ImageViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import sanilk.com.customviews.R;

public class LoadingBar extends LinearLayout {

    private Context mContext;
    private View mInflatedView;

    private ImageView mBallImageView;
    private ImageView mBackgroundImageView;

    private boolean mLoading;

    private ObjectAnimator mObjectAnimator;
    private int mDuration = 600;

    private boolean mGoingRight;

    public boolean isLoading(){
        return mLoading;
    }

    public void startLoading(){
        if(!mLoading) {
            mLoading = true;
            mObjectAnimator.start();
        }
    }

    public void stopLoading(){
        if(mLoading) {
            mLoading = false;
            mObjectAnimator.cancel();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        mObjectAnimator = ObjectAnimator.ofFloat(mBallImageView, "translationX",
                0,
                        mBackgroundImageView.getWidth() - mBackgroundImageView.getLeft() - 2*getResources().getDimension(R.dimen.loadingBar1BallMarginLeft) - mBallImageView.getWidth()
                );
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(mGoingRight){
                    mObjectAnimator.setFloatValues(
                            mBackgroundImageView.getWidth() - mBackgroundImageView.getLeft() - 2*getResources().getDimension(R.dimen.loadingBar1BallMarginLeft) - mBallImageView.getWidth(),
                            0
                    );
                    mGoingRight = false;
                }else{
                    mObjectAnimator.setFloatValues(
                            0,
                            mBackgroundImageView.getWidth() - mBackgroundImageView.getLeft() - 2*getResources().getDimension(R.dimen.loadingBar1BallMarginLeft) - mBallImageView.getWidth()
                    );
                    mGoingRight = true;
                }
                if(mLoading) {
                    mObjectAnimator.start();
                }
            }
        });
        mObjectAnimator.setDuration(mDuration);
    }

    public LoadingBar(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        this.mContext = context;

        mInflatedView = inflate(context, R.layout.loading_bar, null);
        addView(mInflatedView);

        mBallImageView = mInflatedView.findViewById(R.id.loading_bar1_ball);
        mBackgroundImageView = mInflatedView.findViewById(R.id.loading_bar1_back);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.LoadingBar,
                0, 0
        );

        int ballColor = attributes.getColor(R.styleable.LoadingBar_mainColor, Color.argb(255, 0, 0,0));

        if(Build.VERSION.SDK_INT >= 21) {
            mBallImageView.setImageTintList(ColorStateList.valueOf(ballColor));
        }else {
            ImageViewCompat.setImageTintList(mBallImageView, ColorStateList.valueOf(ballColor));
        }

        mGoingRight = true;

        mLoading = false;
    }

}
