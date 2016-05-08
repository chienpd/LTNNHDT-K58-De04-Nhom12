package chienphamk58.ailatrieuphu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import chienphamk58.ailatrieuphu.Sound_Music.PlaySongServiceLevel1;
import chienphamk58.ailatrieuphu.Sound_Music.PlaySound;


public class About extends AppCompatActivity {
    Intent intent;
    PlaySound playSound = new PlaySound();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main5);
        intent = new Intent(About.this, PlaySongServiceLevel1.class);
        if(playSound.mutesound)
            startService(intent);
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(playSound.mutesound)
        stopService(intent);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(playSound.mutesound)
        stopService(intent);
    }
}
