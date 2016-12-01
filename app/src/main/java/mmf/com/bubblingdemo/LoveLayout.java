package mmf.com.bubblingdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by MMF on 2016/9/18.
 */
public class LoveLayout extends RelativeLayout {
    Drawable[] drawable = new Drawable[3];
    private Random random = new Random();
    private int dHeight;
    private int dWidth;
    private RelativeLayout.LayoutParams params;
    private int mWidth;
    private int mHeight;

    public LoveLayout(Context context) {
        super(context);
    }

    public LoveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        drawable[0] = getResources().getDrawable(R.mipmap.icon_1);
        drawable[1] = getResources().getDrawable(R.mipmap.icon_2);
        drawable[2] = getResources().getDrawable(R.mipmap.icon_3);

        dHeight = DipUtil.dip2px(getContext(), 50);
        dWidth = DipUtil.dip2px(getContext(), 50);
        params = new LayoutParams(dWidth, dHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    public void addLove() {
        final ImageView iv = new ImageView(getContext());
        iv.setImageDrawable(drawable[random.nextInt(3)]);
        //显示在容器底部居中
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(ALIGN_PARENT_BOTTOM);
        addView(iv, params);
        //平移、渐变、缩放
        AnimatorSet set = getAnimator(iv);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(iv);
            }
        });
        //开启动画
        set.start();
    }

    private AnimatorSet getAnimator(ImageView iv) {
        //缩放
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv, "scaleX", 0.4f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv, "scaleY", 0.4f, 1f);
        //透明度
        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv, "alpha", 0.4f, 1f);
        AnimatorSet enterSet = new AnimatorSet();
        enterSet.setDuration(500);
        enterSet.playTogether(scaleX, scaleY, alpha);
        //贝塞尔动画
        ValueAnimator bezierAnimator = getBezierAnimator(iv);
        //所有动画的集合
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(enterSet, bezierAnimator);
        set.setTarget(iv);
        return set;
    }

    private ValueAnimator getBezierAnimator(final ImageView iv) {
        //贝塞尔曲线
        PointF pointF0 = new PointF((mWidth - dWidth) / 2, mHeight - dHeight);
        PointF pointF1 = getTogglePointF(1);
        PointF pointF2 = getTogglePointF(2);
        PointF pointF3 = new PointF(random.nextInt(mWidth), 0);
        BezierEvaluator evaluator = new BezierEvaluator(pointF1,pointF2);
        ValueAnimator animator = ValueAnimator.ofObject(evaluator,pointF0,pointF3);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                iv.setX(pointF.x);
                iv.setY(pointF.y);
                iv.setAlpha(1-animation.getAnimatedFraction());
            }
        });
        return animator;
    }

    private PointF getTogglePointF(int i) {
        PointF pointF = new PointF();
        pointF.x = random.nextInt(mWidth);
        if (i == 1) {//屏幕下面一半的点
            pointF.y = random.nextInt(mHeight / 2) + mHeight/2;
        } else {
            //屏幕上面一半的点
            pointF.y = random.nextInt(mHeight / 2);
        }
        return pointF;
    }
}
