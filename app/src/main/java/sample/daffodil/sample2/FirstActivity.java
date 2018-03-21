package sample.daffodil.sample2;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.Permission;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends AppCompatActivity {

    @BindView(R.id.id_username) EditText userName;
    @BindView(R.id.id_password) EditText password;
    @BindView(R.id.id_login_button) Button login;
    @BindView(R.id.id_user_registration) TextView registerUser;
    @BindView(R.id.id_forgot_password) TextView forgotPassword;
    @BindView(R.id.id_facebook_connect) ImageView facebook;
    @BindView(R.id.id_twiiter_connect) ImageView twitter;
    @BindView(R.id.id_google_plus_connect) ImageView googlePlus;
    UserDatabase userdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);

        //Sample Test for asking permission


        //Action on clicking Login button

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUsername = userName.getText().toString().trim();
                String enteredPassword = password.getText().toString();
                //Vaidation for email and password
                if (enteredUsername.trim().length() == 0) {
                    userName.setError("Email is not entered!");
                    userName.requestFocus();
                }
                else if (enteredPassword.trim().length() == 0) {
                    password.setError("Password is not entered!");
                    password.requestFocus();
                }
                else if(enteredUsername.contains("@")==false){
                    userName.setError("Invalid Email!");
                    userName.requestFocus();
                }
                else if(enteredPassword.length()<8){
                    password.setError("Password can't be less than 8 characters!");
                    password.requestFocus();
                }
                else{
                    userdb = Room.databaseBuilder(getApplicationContext(),
                            UserDatabase.class, "userDatabase").allowMainThreadQueries().build();
                    List<User> u=userdb.getUserDao().getUserDetails(enteredUsername.trim());
                    //Checking for email exists
                    if(u.size()==0){
                        Toast.makeText(FirstActivity.this, "Your Email is not registered with us", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //password encryption for password verification
                        byte[] base64=enteredPassword.getBytes(StandardCharsets.UTF_8)   ;
                        String pass = Base64.encodeToString(base64, Base64.DEFAULT);
                        //Successfull Login
                        if (u.get(0).email.equals(enteredUsername) && u.get(0).password.equals(pass)) {
                            SharedPreferences sp=getApplicationContext().getSharedPreferences("Mypref",0);
                            SharedPreferences.Editor editor=sp.edit();
                            editor.putString("email",enteredUsername.trim());
                            editor.putString("pass",pass);
                            editor.putBoolean("flag",true);

                            editor.apply();
                            Toast.makeText(FirstActivity.this,sp.getString("pass",null),Toast.LENGTH_LONG).show();
                            Intent i = new Intent(FirstActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(FirstActivity.this, "Invalid Password!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Register now intent
                Intent i=new Intent(FirstActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }

}