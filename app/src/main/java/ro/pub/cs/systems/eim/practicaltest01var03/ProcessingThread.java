package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Random;

public class ProcessingThread extends Thread {

    private Context context;
    private String name;
    private String group;

    public ProcessingThread(Context context, String name, String group) {
        this.context = context;
        this.name = name;
        this.group = group;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while(true){
            sendMessage(rnd.nextInt());
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void sendMessage(int action) {
        Intent intent = new Intent();
                intent.setAction("5555");
                intent.putExtra(name, group);

        Log.d("Message", name + " " + group + " " + "Thread.run() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());
        context.sendBroadcast(intent);
        }
}