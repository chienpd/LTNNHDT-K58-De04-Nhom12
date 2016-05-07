package chienphamk58.ailatrieuphu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


public class Main5Activity extends AppCompatActivity {
    Intent intent;
    PlaySound playSound = new PlaySound();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main5);
        intent = new Intent(Main5Activity.this, PlaySongServiceLevel1.class);
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
