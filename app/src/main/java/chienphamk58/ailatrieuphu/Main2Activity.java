package chienphamk58.ailatrieuphu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity {
    Intent intent;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        intent = new Intent(Main2Activity.this, PlaySongServiceLevel1.class);
        startService(intent);
        final TextView myCounter = (TextView)findViewById(R.id.textView19);
        countDownTimer = new CountDownTimer(600000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                myCounter.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                myCounter.setText("TimeUp!");
            }
        }.start();

    }

    @Override
    protected void onStop(){
        super.onStop();
        stopService(intent);
        countDownTimer.onFinish();
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        stopService(intent);
        countDownTimer.onFinish();
    }
}
