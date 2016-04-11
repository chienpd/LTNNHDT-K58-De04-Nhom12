package chienphamk58.ailatrieuphu;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class Main3Activity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main3);
        mediaPlayer = new MediaPlayer().create(this, R.raw.luatchoi);
        mediaPlayer.start();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mediaPlayer.stop();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
    }
}
