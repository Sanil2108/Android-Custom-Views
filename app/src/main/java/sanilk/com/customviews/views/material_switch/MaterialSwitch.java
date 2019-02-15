package sanilk.com.customviews.views.material_switch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.widget.ImageViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import sanilk.com.customviews.R;

public class MaterialSwitch extends LinearLayout {
    View mInflatedView;

    ImageView mBall;
    ImageView mOutline;
    ImageView mBackground;

    ObjectAnimator mObjectAnimator;

    boolean mSet = false;

    int mDuration = 200;

    MaterialSwitchListener mMaterialSwitchListener;

    int setColor;
    int unsetColor;
    int mRedIncrease;
    int mBlueIncrease;
    int mGreenIncrease;

    ValueAnimator mValueAnimator;

    private boolean mSwitching;

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        mObjectAnimator = ObjectAnimator.ofFloat(mBall, "translationX", 0,
                mOutline.getWidth() - mBall.getWidth() - (mOutline.getWidth() - mBackground.getWidth()) - 2 * getResources().getDimension(R.dimen.materialSwitchBallMarginLeft));
        mValueAnimator = ValueAnimator.ofInt(0, 100);
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                int newColor;
                if(progress<100) {
                    if (!mSet) {
                        newColor = Color.argb(
                                255,
                                (int) (Color.red(unsetColor) + mRedIncrease * ((float) progress / 100)),
                                (int) (Color.green(unsetColor) + mGreenIncrease * ((float) progress / 100)),
                                (int) (Color.blue(unsetColor) + mBlueIncrease * ((float) progress / 100))
                        );
                    } else {
                        newColor = Color.argb(
                                255,
                                (int) (Color.red(setColor) - mRedIncrease * ((float) progress / 100)),
                                (int) (Color.green(setColor) - mGreenIncrease * ((float) progress / 100)),
                                (int) (Color.blue(setColor) - mBlueIncrease * ((float) progress / 100))
                        );
                    }

                    if (Build.VERSION.SDK_INT >= 21) {
                        mOutline.setImageTintList(ColorStateList.valueOf(newColor));
                        mBall.setImageTintList(ColorStateList.valueOf(newColor));
                    } else {
                        ImageViewCompat.setImageTintList(mOutline, ColorStateList.valueOf(newColor));
                        ImageViewCompat.setImageTintList(mBall, ColorStateList.valueOf(newColor));
                    }
                }else{
                    mSet = !mSet;
                    if(mMaterialSwitchListener != null){
                        mMaterialSwitchListener.switchChanged(mSet);
                    }

                    if(mSet){
                        mObjectAnimator.setFloatValues((mOutline.getWidth() - mBall.getWidth() - (mOutline.getWidth() - mBackground.getWidth()) - 2 * getResources().getDimension(R.dimen.materialSwitchBallMarginLeft)), 0);
                    }else{
                        mObjectAnimator.setFloatValues(0, (mOutline.getWidth() - mBall.getWidth() - (mOutline.getWidth() - mBackground.getWidth()) - 2 * getResources().getDimension(R.dimen.materialSwitchBallMarginLeft)));
                    }

                    mSwitching = false;
                }
            }
        });
        mObjectAnimator.setDuration(mDuration);
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

    }

    public void addMaterialSwitchListener(MaterialSwitchListener materialSwitchListener){
        this.mMaterialSwitchListener = materialSwitchListener;
    }

    public MaterialSwitch(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.MaterialSwitch,
                0, 0
        );

        mInflatedView = inflate(context, R.layout.material_switch, null);
        addView(mInflatedView);

        mBall = mInflatedView.findViewById(R.id.material_switch_ball);
        mOutline = mInflatedView.findViewById(R.id.material_switch_outline);
        mBackground = mInflatedView.findViewById(R.id.material_switch_back);

        unsetColor = typedArray.getColor(R.styleable.MaterialSwitch_startColor, Color.argb(255, 170, 170, 170));
        setColor = typedArray.getColor(R.styleable.MaterialSwitch_endColor, Color.argb(255, 170, 170, 170));

        mRedIncrease = Color.red(setColor) - Color.red(unsetColor);
        mBlueIncrease = Color.blue(setColor) - Color.blue(unsetColor);
        mGreenIncrease = Color.green(setColor) - Color.green(unsetColor);

        if(Build.VERSION.SDK_INT >= 21) {
            mOutline.setImageTintList(ColorStateList.valueOf(unsetColor));
            mBall.setImageTintList(ColorStateList.valueOf(unsetColor));
        }else {
            ImageViewCompat.setImageTintList(mOutline, ColorStateList.valueOf(unsetColor));
            ImageViewCompat.setImageTintList(mBall, ColorStateList.valueOf(unsetColor));
        }

        mSwitching = false;

        mInflatedView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mSwitching) {
                    mSwitching = true;
                    mObjectAnimator.start();
                    mValueAnimator.start();
                }
            }
        });

    }

    public interface MaterialSwitchListener{
        void switchChanged(boolean set);
    }
}
