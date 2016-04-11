package chienphamk58.ailatrieuphu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class Main2Activity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        intent = new Intent(Main2Activity.this, PlaySongServiceLevel1.class);
        startService(intent);
    }

    @Override
    protected void onStop(){
        super.onStop();
        stopService(intent);
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        stopService(intent);
    }
}
