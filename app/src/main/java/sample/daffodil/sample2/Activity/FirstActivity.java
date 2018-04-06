package sample.daffodil.sample2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import sample.daffodil.sample2.Activity.MainActivity;
import sample.daffodil.sample2.Activity.RegisterActivity;
import sample.daffodil.sample2.Database.UserDatabase;
import sample.daffodil.sample2.Model.LoginBackgroundImpl;
import sample.daffodil.sample2.Presenter.LoginPresenter;
import sample.daffodil.sample2.Presenter.LoginPresenterImpl;
import sample.daffodil.sample2.View.LoginView;

public class FirstActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.id_username) EditText userName;
    @BindView(R.id.id_password) EditText password;
    @BindView(R.id.id_login_button) Button login;
    @BindView(R.id.id_user_registration) TextView registerUser;
    @BindView(R.id.id_forgot_password) TextView forgotPassword;
    @BindView(R.id.id_facebook_connect) ImageView facebook;
    @BindView(R.id.id_twiiter_connect) ImageView twitter;
    @BindView(R.id.id_google_plus_connect) ImageView googlePlus;
    UserDatabase userdb;
    FirebaseAuth auth;
    LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        auth=FirebaseAuth.getInstance();
        loginPresenter=new LoginPresenterImpl(this,new LoginBackgroundImpl());
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
                else {
                    loginPresenter.onLoginClick(enteredUsername.trim(),enteredPassword,getApplicationContext());
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

    @Override
    public void LoginAction() {
        Intent i=new Intent(FirstActivity.this,MainActivity.class);
        startActivity(i);
    }
}