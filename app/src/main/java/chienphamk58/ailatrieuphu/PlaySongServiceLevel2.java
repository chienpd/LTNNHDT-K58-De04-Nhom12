package chienphamk58.ailatrieuphu;

/**
 * Created by pdc on 07/05/2016.
 */
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class PlaySongServiceLevel2 extends Service {

    private MediaPlayer mediaPlayer;

    public PlaySongServiceLevel2() {
    }


    @Override
    public IBinder onBind(Intent intent){
        // Service này là loại không giàng buộc (Un bounded)
        // Vì vậy method này ko bao giờ được gọi.
        return null;
    }


    @Override
    public void onCreate(){
        super.onCreate();
        // Tạo đối tượng MediaPlayer, chơi file nhạc của bạn.
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.moc2);
        mediaPlayer.setLooping(true);
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        // Chơi nhạc.
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();

        return START_STICKY;
    }

    // Hủy bỏ dịch vụ.
    @Override
    public void onDestroy() {
        // Giải phóng nguồn dữ nguồn phát nhạc.
        super.onDestroy();
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
    }
}