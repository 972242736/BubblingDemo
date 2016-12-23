package mmf.com.bubblingdemo.activity;

import android.app.Activity;
import android.os.Bundle;

import butterknife.Bind;
import butterknife.ButterKnife;
import mmf.com.bubblingdemo.CorrugateView;
import mmf.com.bubblingdemo.R;

/**
 * Created by MMF
 * date 2016/12/1
 * Description:波浪的头像
 */
public class RoundProgressActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_progress);
        ButterKnife.bind(this);
    }

}
