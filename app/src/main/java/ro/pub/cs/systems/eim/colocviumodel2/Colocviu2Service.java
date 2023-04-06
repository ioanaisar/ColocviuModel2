package ro.pub.cs.systems.eim.colocviumodel2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Colocviu2Service extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String textCoord;
        textCoord = intent.getStringExtra(Constants.COORD);
        System.out.println("Service primeste : " + textCoord);
        // creez thread si start
        ProcessingThread processingThread = new ProcessingThread(this, textCoord);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }
}
