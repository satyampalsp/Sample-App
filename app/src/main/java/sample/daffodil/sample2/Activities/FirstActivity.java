package sample.daffodil.sample2.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import sample.daffodil.sample2.Database.UserDatabase;
import sample.daffodil.sample2.Model.LoginBackgroundImpl;
import sample.daffodil.sample2.Model.SignUpBackground;
import sample.daffodil.sample2.Model.SignUpBackgroundImpl;
import sample.daffodil.sample2.Presenter.LoginPresenter;
import sample.daffodil.sample2.Presenter.LoginPresenterImpl;
import sample.daffodil.sample2.Presenter.SignUpPresenter;
import sample.daffodil.sample2.Presenter.SignUpPresenterImpl;
import sample.daffodil.sample2.R;
import sample.daffodil.sample2.View.LoginView;

public class FirstActivity extends AppCompatActivity implements LoginView {
    private static final int RC_SIGN_IN=9001;
    @BindView(R.id.id_username) EditText userName;
    @BindView(R.id.id_password) EditText password;
    @BindView(R.id.id_login_button) Button login;
    @BindView(R.id.id_user_registration) TextView registerUser;
    @BindView(R.id.id_forgot_password) TextView forgotPassword;
    @BindView(R.id.id_facebook_connect) LoginButton facebook;
    @BindView(R.id.id_google_plus_connect) SignInButton googlePlus;
    UserDatabase userdb;
    CallbackManager callbackManager;
    LoginPresenter loginPresenter;
    SignUpBackgroundImpl signUpBackgroundImpl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        signUpBackgroundImpl=new SignUpBackgroundImpl();
        googlePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestProfile().requestEmail().build();
                GoogleSignInClient signInClient= GoogleSignIn.getClient(FirstActivity.this,gso);
                GoogleSignInAccount currentSigninAccount=GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                    if(currentSigninAccount!=null){
                        Toast.makeText(FirstActivity.this, "Already sign in "+currentSigninAccount.getFamilyName(), Toast.LENGTH_SHORT).show();
                    }else {
                        Intent googleSigninIntent = signInClient.getSignInIntent();
                        startActivityForResult(googleSigninIntent, RC_SIGN_IN);
                    }
            }});
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code
                                Profile user=Profile.getCurrentProfile();
                                facebook.setReadPermissions(Arrays.asList("public_profile","email"));
                                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            Log.i("RESULTS : ", object.getString("email")+"  "+object);
                                            loginPresenter.OnSocialLoginClick(object.getString("email"),object.getString("first_name"),object.getString("last_name"),"","",getApplicationContext());
                                            SharedPreferences sp=getApplicationContext().getSharedPreferences("Mypref",0);
                                            SharedPreferences.Editor editor=sp.edit();
                                            String facebookUrl="https://graph.facebook.com/"+object.getString("id").toString()+"/picture?type=large";
                                            editor.putString("email",object.getString("email"));
                                            editor.putString("picUrl",facebookUrl);
                                            editor.apply();
                                        }catch (Exception e){

                                        }
                                    }
                                });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "email,first_name,last_name");
                                request.setParameters(parameters);
                                request.executeAsync();
                                Log.i("RESULTS :",""+parameters.getString("first_name"));
                             }
                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                            }
                        });

            }
        });
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
                finish();
            }
        });
    }

    @Override
    public void LoginAction() {
        Intent i=new Intent(FirstActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
            Log.i("RESULTS",""+data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.i("Google account",""+account.getDisplayName());
            Log.i("Google account",""+account.getEmail());
            loginPresenter.OnSocialLoginClick(account.getEmail(),account.getGivenName(),account.getFamilyName(),"","",getApplicationContext());
                Uri u=account.getPhotoUrl();
                if(u!=null) {
                    String googlePicUrl = u.toString();
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("Mypref", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("picUrl", googlePicUrl);
                    editor.apply();
                }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.i("Google Sign in", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}