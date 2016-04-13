package chienphamk58.ailatrieuphu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class Main3Activity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main3);
        intent = new Intent(Main3Activity.this, PlaySongServiceLevel1.class);
        startService(intent);
        mediaPlayer = new MediaPlayer().create(this, R.raw.luatchoi);
        mediaPlayer.start();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mediaPlayer.stop();
        stopService(intent);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
        stopService(intent);
    }
}
