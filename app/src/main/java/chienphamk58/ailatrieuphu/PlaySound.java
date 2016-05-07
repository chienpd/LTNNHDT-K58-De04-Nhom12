package chienphamk58.ailatrieuphu;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by pdc on 08/05/2016.
 */
public class PlaySound extends Activity{
    MediaPlayer mediaPlayer;
    public void setSound(Context context,Integer id){
        mediaPlayer = new MediaPlayer().create(context,id);
        mediaPlayer.start();
    }
}
