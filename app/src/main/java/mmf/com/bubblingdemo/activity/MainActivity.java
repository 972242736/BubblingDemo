package mmf.com.bubblingdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import mmf.com.bubblingdemo.R;

/**
 * Created by MMF
 * date 2016/12/1
 * Description:主页
 */
public class MainActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                startActivity(new Intent(this, LoveActivity.class));
                break;
            case R.id.tv2:
                startActivity(new Intent(this, CorrugateActivity.class));
                break;
            case R.id.tv3:
                startActivity(new Intent(this, FlashPhotosActivity.class));
                break;
        }
    }
}
