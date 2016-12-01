package mmf.com.bubblingdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MMF on 2016/11/29.
 * 波纹
 */
public class CorrugateView extends View {
    private int imgSize;        //移动的图标的大小
    private int waveHeight;     //波浪的高度
    private int rollTime;       //移动一次所需的时间
    private int rollDistance;   //移动一次的距离
    private int mWidth;         //控件的总宽度
    private int mHeight;        //控件的总高度
    private Path mWavePathBottom;
    private Paint mPaintBottom;
    private Path mWavePath;
    private Paint mPaint;
    private int length;
    private MyTimerTask mTask;
    private Timer timer;
    private List<Point> mPointsList;
    private List<Point> mPointsListBottom;
    private int allLength = 0;      //移动距离的总值，最大为总宽，用于重置波浪使用
    private int allHeight = 0;      //移动距离的总值，最大为总宽的一半，用于改变图标向上还是向下使用
    int i = 0;
    private boolean ismHeight = false;

    public CorrugateView(Context context) {
        super(context);
        init(context, null);
    }

    public CorrugateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CorrugateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = waveHeight+imgSize;
//        mHeight = getMeasuredHeight();
//        imgSize20 = mHeight - imgSize;
//        allHeight = imgSize20 / 2 + imgSize;
        initPoint();
        invalidate();
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray attr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CorrugateView, 0, 0);
        try {
            imgSize = (int) attr.getDimension(R.styleable.CorrugateView_imgSize, getResources().getDimensionPixelSize(
                    R.dimen.top_distance));
            waveHeight = (int) attr.getDimension(R.styleable.CorrugateView_waveHeight, getResources().getDimensionPixelSize(
                    R.dimen.top_distance_20));
            rollTime = attr.getInteger(R.styleable.CorrugateView_rollTime, 30);
            rollDistance = attr.getInteger(R.styleable.CorrugateView_rollDistance, 5);
        } finally {
            attr.recycle();
        }
        length = rollDistance;
        mPointsList = new ArrayList<Point>();
        mPointsListBottom = new ArrayList<Point>();
        mWavePath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.withe));
        mWavePathBottom = new Path();
        mPaintBottom = new Paint();
        mPaintBottom.setAntiAlias(true);
        mPaintBottom.setStyle(Paint.Style.FILL);
        mPaintBottom.setColor(getResources().getColor(R.color.top_withe));
    }

    private void initPoint() {
        mPointsList = new ArrayList<Point>();
        mPointsListBottom = new ArrayList<Point>();
        for (int i = 0; i < 9; i++) {
            int x = i * mWidth / 4 - mWidth;
            int y = 0;
            switch (i % 4) {
                case 0:
                case 2:
                    // 定义波浪的中间为起点
                    y = waveHeight / 2 + imgSize;
                    break;
                case 1:
                    // 往下波动的控制点
                    y = imgSize;
                    break;
                case 3:
                    // 往上波动的控制点
                    y = waveHeight + imgSize;
                    break;
            }
            mPointsList.add(new Point(x, y));
        }
        for (int i = 0; i < 11; i++) {
            int x = i * mWidth / 4 - mWidth - mWidth / 4;
            int y = 0;
            switch (i % 4) {
                case 0:
                case 2:
                    // 定义波浪的中间为起点
                    y = waveHeight/ 2 + imgSize;
                    break;
                case 1:
                    // 往下波动的控制点
                    y = imgSize;
                    break;
                case 3:
                    // 往上波动的控制点
                    y = waveHeight + imgSize;
                    break;
            }
            mPointsListBottom.add(new Point(x, y));
        }
        invalidate();
        if (timer == null)
            start();
    }

    private void start() {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        mTask = new MyTimerTask(updateHandler);
        timer = new Timer();
        timer.schedule(mTask, 0, rollTime);
    }

    class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.sendMessage(handler.obtainMessage());
        }

    }

    Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            allLength += length;
            allHeight += length;
            if (allLength >= mWidth) {
                length = length - (allLength - mWidth);
                ismHeight = false;
                allHeight = 0;
                i = 0;
            } else {
                length = rollDistance;
            }
            if (allHeight >= mWidth / 2 && !ismHeight) {
                ismHeight = true;
                i = 0;
            }
            for (Point point : mPointsList) {
                point.x = point.x + length;
            }
            for (Point point : mPointsListBottom) {
                point.x = point.x + length;
            }
            invalidate();
        }

    };

    private void resetPoints() {
        for (int i = 0; i < mPointsList.size(); i++) {
            mPointsList.get(i).x = mPointsList.get(i).x - mWidth;

        }
        for (Point point : mPointsListBottom) {
            point.x = point.x - mWidth;
        }
    }

    private float getHeigthIcon() {
        i++;
        float t = 20.0F * i / mWidth;
        float y;
        if (ismHeight) {
            y = 2 * imgSize + waveHeight - (mPointsList.get(0).y * (1 - t) * (1 - t)
                    + 2 * mPointsList.get(1).y * t * (1 - t)
                    + mPointsList.get(2).y * t * t);
        } else {
            y = mPointsList.get(0).y * (1 - t) * (1 - t)
                    + 2 * mPointsList.get(1).y * t * (1 - t)
                    + mPointsList.get(2).y * t * t;
        }
        return y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mWavePath.reset();
        mWavePathBottom.reset();
        mWavePathBottom.moveTo(mPointsListBottom.get(0).x, mPointsListBottom.get(0).y);
        mWavePathBottom.quadTo(mPointsListBottom.get(1).x, mPointsListBottom.get(1).y, mPointsListBottom.get(2).x, mPointsListBottom.get(2).y);
        mWavePathBottom.quadTo(mPointsListBottom.get(3).x, mPointsListBottom.get(3).y, mPointsListBottom.get(4).x, mPointsListBottom.get(4).y);
        mWavePathBottom.quadTo(mPointsListBottom.get(5).x, mPointsListBottom.get(5).y, mPointsListBottom.get(6).x, mPointsListBottom.get(6).y);
        mWavePathBottom.quadTo(mPointsListBottom.get(7).x, mPointsListBottom.get(7).y, mPointsListBottom.get(8).x, mPointsListBottom.get(8).y);
        mWavePathBottom.quadTo(mPointsListBottom.get(9).x, mPointsListBottom.get(9).y, mPointsListBottom.get(10).x, mPointsListBottom.get(10).y);
        mWavePathBottom.lineTo(mPointsListBottom.get(10).x, mHeight);
        mWavePathBottom.lineTo(mPointsListBottom.get(0).x, mHeight);
        mWavePathBottom.lineTo(mPointsListBottom.get(0).x, mPointsListBottom.get(0).y);
        mWavePathBottom.close();
        canvas.drawPath(mWavePathBottom, mPaintBottom);
        mWavePath.moveTo(mPointsList.get(0).x, mPointsList.get(0).y);
        mWavePath.quadTo(mPointsList.get(1).x, mPointsList.get(1).y, mPointsList.get(2).x, mPointsList.get(2).y);
        mWavePath.quadTo(mPointsList.get(3).x, mPointsList.get(3).y, mPointsList.get(4).x, mPointsList.get(4).y);
        mWavePath.quadTo(mPointsList.get(5).x, mPointsList.get(5).y, mPointsList.get(6).x, mPointsList.get(6).y);
        mWavePath.quadTo(mPointsList.get(7).x, mPointsList.get(7).y, mPointsList.get(8).x, mPointsList.get(8).y);
        mWavePath.lineTo(mPointsList.get(8).x, mHeight);
        mWavePath.lineTo(mPointsList.get(0).x, mHeight);
        mWavePath.lineTo(mPointsList.get(0).x, mPointsList.get(0).y);
        mWavePath.close();
        canvas.drawPath(mWavePath, mPaint);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getContext()
                .getResources(), R.mipmap.icon_2017);
//        i++;
//        if (i % 2 == 0)
        drawImage(canvas, bitmap, (mWidth - imgSize) / 2, (int) getHeigthIcon() - imgSize,
                imgSize, imgSize, 0, 0, mPaint);

        if (allLength >= mWidth) {
            resetPoints();
            allLength = 0;
        }
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

    private PointF getTogglePointF(int i, int length) {
        PointF pointF = new PointF();
        if (i == 2) {
            //下面的点
            pointF.x = mWidth / 4 + mWidth / 2;
            pointF.y = mHeight - length;
        } else {
            //上面的点
            pointF.x = mWidth / 2;
            pointF.y = 0 + length;
        }
        return pointF;
    }
}
