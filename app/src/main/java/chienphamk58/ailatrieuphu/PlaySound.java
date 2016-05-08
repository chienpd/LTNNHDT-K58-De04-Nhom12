package chienphamk58.ailatrieuphu;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by pdc on 08/05/2016.
 */
public class PlaySound{
    static MediaPlayer mediaPlayer;
    static boolean mutesound = true;
    public void setSound(Context context,Integer id){
        mediaPlayer = new MediaPlayer().create(context,id);
        mediaPlayer.start();
    }
}
