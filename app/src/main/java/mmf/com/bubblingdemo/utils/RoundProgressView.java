package mmf.com.bubblingdemo.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mmf.com.bubblingdemo.R;

/**
 * Created by MMF on 2016/11/29.
 * 波纹
 */
public class RoundProgressView extends View {
    private int mWidth;         //控件的总宽度
    private int mHeight;        //控件的总高度
    private Path mPath;
    private Paint mPaint;
    private Path mTextPath;
    private Paint mTextPaint;
    private int bottomBackgroundResId = 0;      //底下的图片的路径
    private int textPaintSize;      //画百分比的字体大小

    public RoundProgressView(Context context) {
        super(context);
        init(context, null);
    }

    public RoundProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray attr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CorrugateView, 0, 0);
        try {
            bottomBackgroundResId = (int) attr.getResourceId(R.styleable.RoundProgressView_bottomImage, bottomBackgroundResId);
            mHeight = (int) attr.getDimension(R.styleable.RoundProgressView_heightSize, getResources().getDimensionPixelSize(
                    R.dimen.top_distance_100));
            this.mWidth = mHeight;
            textPaintSize = (int) attr.getDimension(R.styleable.RoundProgressView_textPaintSize, getResources().getDimensionPixelSize(
                    R.dimen.text_paint_15));
        } finally {
            attr.recycle();
        }
        //画上面曲线的画笔和线
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.white));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mWidth = getMeasuredWidth();
        //控件高度=图片的高度加上波浪的高度
//        mHeight = waveHeight + imgSize;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bottomBackgroundResId != 0) {
            //画头像
            Bitmap bitmap = BitmapFactory.decodeResource(this.getContext()
                    .getResources(), R.mipmap.icon_2017);
            drawImage(canvas, bitmap, 0, 0,
                    mWidth, mWidth, 0, 0, mPaint);
        } else {
            //画圆
        }

//        //取两层交集显示在上层
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        //画两条曲线
//        mPath.reset();
//        //曲线路径
//        mPath.moveTo(0, mWidth);
//        mPath.cubicTo(
//                mBezierDiffX, mWaveY - (mWaveLowestY - mWaveY),
//                mBezierDiffX + mOriginalBitmapWidth / 2, mWaveLowestY,
//                mOriginalBitmapWidth, mWaveY);
//        //竖直线
//        mPath.lineTo(mOriginalBitmapWidth, mOriginalBitmapHeight);
//        //横直线
//        mPath.lineTo(0, mOriginalBitmapHeight);
//        mPath.close();
//        canvas.drawPath(mPath, mPaint);
    }

    public void drawImage(Canvas canvas, Bitmap blt, int x, int y, int w,
                          int h, int bx, int by, Paint paint) { // x,y表示绘画的起点，
        Rect src = new Rect();// 图片
        Rect dst = new Rect();// 屏幕位置及尺寸
        // src 这个是表示绘画图片的大小
        src.left = bx; // 0,0
        src.top = by;
        src.right = bx + w;// mBitDestTop.getWidth();,这个是桌面图的宽度，
        src.bottom = by + h;// mBitDestTop.getHeight()/2;// 这个是桌面图的高度的一半
        // 下面的 dst 是表示 绘画这个图片的位置
        dst.left = x; // miDTX,//这个是可以改变的，也就是绘图的起点X位置
        dst.top = y; // mBitQQ.getHeight();//这个是QQ图片的高度。 也就相当于 桌面图片绘画起点的Y坐标
        dst.right = x + w; // miDTX + mBitDestTop.getWidth();// 表示需绘画的图片的右上角
        dst.bottom = y + h; // mBitQQ.getHeight() +
        // mBitDestTop.getHeight();//表示需绘画的图片的右下角
        canvas.drawBitmap(blt, null, dst, null);// 这个方法 第一个参数是图片原来的大小，第二个参数是
        // 绘画该图片需显示多少。也就是说你想绘画该图片的某一些地方，而不是全部图片，第三个参数表示该图片绘画的位置

        src = null;
        dst = null;
    }

}
