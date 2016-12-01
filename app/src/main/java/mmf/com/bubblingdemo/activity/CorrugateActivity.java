package mmf.com.bubblingdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import mmf.com.bubblingdemo.R;

/**
 * Created by MMF
 * date 2016/12/1
 * Description:
 */
public class CorrugateActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrugate);
        ButterKnife.bind(this);
    }
}
