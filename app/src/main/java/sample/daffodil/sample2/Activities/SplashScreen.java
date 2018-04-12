package sample.daffodil.sample2.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.crashlytics.android.ndk.CrashlyticsNdk;
import java.nio.charset.StandardCharsets;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;

import io.fabric.sdk.android.Fabric;
import sample.daffodil.sample2.Database.User;
import sample.daffodil.sample2.Database.UserDatabase;
import sample.daffodil.sample2.R;

import java.util.List;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        setContentView(R.layout.activity_splash_screen);
        auth=FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getApplicationContext().getSharedPreferences("Mypref", 0);
                boolean flag = sp.getBoolean("flag", false);
                auth.getCurrentUser();
//                if(auth.getCurrentUser()!=null){
//                    Intent i=new Intent(SplashScreen.this,MainActivity.class);
//                    startActivity(i);
//                }
                if (flag == true) {
                    Log.e("isLoggedIn", "yes");
                    String email_id = sp.getString("email", "");
                    String pass = sp.getString("pass", "");
                    pass = pass.replaceAll(" ", "");
                    byte[] base64 = pass.getBytes(StandardCharsets.UTF_8);
                    String passwrd = Base64.encodeToString(base64, Base64.DEFAULT);

                    UserDatabase userdb = Room.databaseBuilder(getApplicationContext(),
                            UserDatabase.class, "userDatabase").allowMainThreadQueries().build();
                    List<User> u = userdb.getUserDao().getUserDetails(email_id);
                    if (u.get(0).email.equals(email_id) && u.get(0).password.equals(pass)) {
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.remove("email");
                        editor.remove("pass");
                        editor.putBoolean("flag", false);
                        editor.apply();
                        Intent i = new Intent(SplashScreen.this, FirstActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
                else {
                    Intent i = new Intent(SplashScreen.this, FirstActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 3000);

    }
}