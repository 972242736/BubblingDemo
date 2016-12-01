package mmf.com.bubblingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
