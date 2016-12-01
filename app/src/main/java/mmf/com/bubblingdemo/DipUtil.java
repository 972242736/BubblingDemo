package mmf.com.bubblingdemo;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DipUtil {


  /**
   * @Description: 根据手机的分辨率从 dp 的单位 转成为 px(像素)
   */
  public static int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  /**
   * @Title: dip2px
   * @Description: 根据手机的分辨率从px 的单位 转成为 dp
   */
  public static int px2dip(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  /**
   * @Description: 获得屏幕高度
   */
  public static int getHeight(Activity activity) {
    DisplayMetrics dm = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    return dm.heightPixels;
  }

  /**
   * @Description: 获得屏幕宽度
   */
  public static int getWidth(Activity activity) {
    DisplayMetrics dm = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    return dm.widthPixels;
  }

  /**
   * @Description: 获得屏幕宽度
   */
  public static int getWidth(Context context) {
    DisplayMetrics dm = new DisplayMetrics();
    ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
        .getMetrics(dm);
    return dm.widthPixels;
  }

}
