package ro.pub.cs.systems.eim.practicaltest01var03;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class PracticalTest01Var03Service extends Service {
    public PracticalTest01Var03Service() {
    }

    final private IBinder binder = new SomeBinder();

    public class SomeBinder extends Binder {
        PracticalTest01Var03Service getService() {
            return PracticalTest01Var03Service.this;
        }
    }

    private String name;
    private String group;

    @Override
    public IBinder onBind(Intent intent) {


        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getStringExtra("NAME_INPUT") != null ){
            name = intent.getStringExtra("NAME_INPUT");
        }
        if(intent.getStringExtra("GROUP_INPUT") != null){
            group = intent.getStringExtra("GROUP_INPUT");
        }

        ProcessingThread pThread = new ProcessingThread(this, name, group);
        pThread.run();

        return super.onStartCommand(intent, flags, startId);
    }
}
