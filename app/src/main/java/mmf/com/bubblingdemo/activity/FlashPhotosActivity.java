package mmf.com.bubblingdemo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import mmf.com.bubblingdemo.R;
import mmf.com.bubblingdemo.utils.photoUtil;

/**
 * Created by MMF
 * date 2016/12/1
 * Description:闪照
 */
public class FlashPhotosActivity extends Activity implements View.OnTouchListener {
    @Bind(R.id.iv_long_click)
    ImageView ivLongClick;
    @Bind(R.id.tv_time)
    TextView tvTime;
    private static int DOWN_TIME = 3;
    private int recLen = DOWN_TIME;
    private boolean isUp = false;       //判断是否抬起
    private boolean isShow = false;     //判断是否显示照片
    private Timer timer;
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_photos);
        ButterKnife.bind(this);
        ivLongClick.setOnTouchListener(this);
        //targetSdkVersion>=23
//        ivLongClick.setForeground(getResources().getDrawable(R.mipmap.icon_1));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //如果未显示过照片，按住重新开始计时
                if (!isShow) {
                    if (task == null && timer == null) {
                        timer = new Timer();
                        startTask();
                        timer.schedule(task, 1000, 1000);
                    }
                    isUp = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isUp = true;
                break;
        }
        return false;
    }

    private void startTask() {
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recLen--;
                        if (!isShow) {
                            tvTime.setText("继续长按" + recLen + "秒");
                            if (recLen <= 0) {
                                //当按下时间足够且未抬起，显示照片，并结束计时，开始照片显示计时
                                if (!isUp) {
                                    reset();
                                    isShow = true;
                                    isUp = true;
                                    ivLongClick.setImageResource(R.mipmap.icon_photo);
//                                    ivLongClick.setForeground(null);
                                    tvTime.setText("已显示图片");
                                    //显示照片的计时
                                    recLen = DOWN_TIME;
                                    timer = new Timer();
                                    startTask();
                                    timer.schedule(task, 1000, 1000);
                                }
                            } else {
                                //当按下时间不够长且抬起不再继续计时
                                if (isUp) {
                                    reset();
                                    recLen = DOWN_TIME;
                                    tvTime.setText("继续长按" + recLen + "秒");
                                }
                            }
                        } else {
                            //当到达显示的时间后将照片打码
                            if (recLen <= 0)
                                closeImg();
                        }
                    }
                });
            }
        };
    }

    private void closeImg() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_photo);
        ivLongClick.setImageBitmap(photoUtil.BitmapMosaic(bmp, 80));
        reset();
    }

    private void reset() {
        timer.cancel();
        timer = null;
        task.cancel();
        task = null;
    }


}
