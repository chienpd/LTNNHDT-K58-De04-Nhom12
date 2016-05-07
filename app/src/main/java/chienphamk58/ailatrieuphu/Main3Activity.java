package chienphamk58.ailatrieuphu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class Main3Activity extends AppCompatActivity {
    Intent intent;
    PlaySound playSound = new PlaySound();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main3);

            intent = new Intent(Main3Activity.this, PlaySongServiceLevel1.class);
        if(playSound.mutesound) {
            startService(intent);
            playSound.setSound(getApplicationContext(), R.raw.luatchoi);
        }

    }

    @Override
    protected void onStop(){
        super.onStop();
        if(playSound.mutesound)
            playSound.mediaPlayer.stop();
            stopService(intent);


    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(playSound.mutesound)
            playSound.mediaPlayer.stop();
            stopService(intent);

    }
}
