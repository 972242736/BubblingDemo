package mmf.com.bubblingdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mmf.com.bubblingdemo.LoveLayout;
import mmf.com.bubblingdemo.R;
/**
 * Created by MMF
 * Description:点击出现爱心
 */
public class LoveActivity extends AppCompatActivity {

    private LoveLayout loveLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loveLayout = (LoveLayout)findViewById(R.id.loveLayout);
    }
    public void start(View view){
        loveLayout.addLove();
    }
}