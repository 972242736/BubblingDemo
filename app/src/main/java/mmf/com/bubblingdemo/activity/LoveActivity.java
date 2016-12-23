package mmf.com.bubblingdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import mmf.com.bubblingdemo.LoveLayout;
import mmf.com.bubblingdemo.R;
/**
 * Created by MMF
 * Description:点击出现爱心
 */
public class LoveActivity extends Activity {

    private LoveLayout loveLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        loveLayout = (LoveLayout)findViewById(R.id.loveLayout);
    }
    public void start(View view){
        loveLayout.addLove();
    }
}
