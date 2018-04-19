package sample.daffodil.sample2.Services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.callback.UploadResult;
import com.cloudinary.android.callback.UploadStatus;
import com.squareup.picasso.Picasso;

import java.util.Map;

import sample.daffodil.sample2.Activities.MainActivity;
import sample.daffodil.sample2.Fragments.ProfileFragment;
import sample.daffodil.sample2.R;

public class ImageUploadService extends Service {
    public ImageUploadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    NotificationCompat.Builder uploadNotification;
    NotificationManager notification;
    @Override
    public void onCreate(){
        MediaManager.init(getApplicationContext());
        uploadNotification= (NotificationCompat.Builder) new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_logo);
        uploadNotification.setContentTitle("Uploading File");
        uploadNotification.setContentText("0% completed.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String file="";
        if(intent != null){
            Bundle bundle = intent.getExtras();
            file = bundle.getString("File");
        }

        String requestId = MediaManager.get().upload(file).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                // your code here
                notification=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notification.notify(0,uploadNotification.build());

            }
            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                // example code starts here
                Double progress = (double) bytes/totalBytes;
                // post progress to app UI (e.g. progress bar, notification)
                // example code ends here
                int per= (int) (progress*100);
                uploadNotification.setContentText(""+per+"% completed.");
                notification.notify(0,uploadNotification.build());
            }
            @Override
            public void onSuccess(String requestId, Map resultData) {
                // your code here
                Toast.makeText(getApplicationContext(), "Image Uploaded Successfully!!!", Toast.LENGTH_SHORT).show();
                Map<String,String> f=resultData;
                String url=f.get("url");
                SharedPreferences sp=getApplicationContext().getSharedPreferences("Mypref",0);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("picUrl",url);
                editor.apply();
                uploadNotification.setContentText("Uploaded Successfully");
                notification.notify(0,uploadNotification.build());
                Intent i =new Intent(getApplicationContext(),MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                ImageUploadService.this.stopSelf();
            }
            @Override
            public void onError(String requestId, ErrorInfo error) {
                // your code here
                Toast.makeText(ImageUploadService.this, "Error occurred! Please try again later...", Toast.LENGTH_SHORT).show();
                uploadNotification.setContentText("Error Occurred. Please Try again later");
                notification.notify(0,uploadNotification.build());

            }
            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                // your code here
            }}).dispatch();

        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "service Destroyed.", Toast.LENGTH_SHORT).show();
        super.onDestroy();

    }
}
