package sample.daffodil.sample2.Activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import sample.daffodil.sample2.Database.User;
import sample.daffodil.sample2.Database.UserDao;
import sample.daffodil.sample2.Database.UserDatabase;
import sample.daffodil.sample2.Model.SignUpBackgroundImpl;
import sample.daffodil.sample2.Presenter.SignUpPresenter;
import sample.daffodil.sample2.Presenter.SignUpPresenterImpl;
import sample.daffodil.sample2.R;
import sample.daffodil.sample2.View.SignUpView;

public class RegisterActivity extends AppCompatActivity implements SignUpView{
    String firstName,lastName,email,phoneNo,password,cnfrmPassword;
    EditText inputFirstName,inputLastName,inputEmail,inputPhone,inputPassword,inputCnfrmPassword;
    Button registerButton;
    User user;
    UserDatabase userdb;
    UserDao userDao;
    FirebaseAuth auth;
    SignUpPresenter signUpPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signUpPresenter=new SignUpPresenterImpl(this,new SignUpBackgroundImpl());
        inputFirstName=(EditText)findViewById(R.id.id_resgisterFirstName);
        inputLastName=(EditText)findViewById(R.id.id_resgisterLastName);
        inputEmail=(EditText)findViewById(R.id.id_resgisterEmail);
        inputPhone=(EditText)findViewById(R.id.id_resgisterPhoneNo);
        inputPassword=(EditText)findViewById(R.id.id_resgisterPassword);
        inputCnfrmPassword=(EditText)findViewById(R.id.id_resgisterCnfrmPassword);
        registerButton=(Button)findViewById(R.id.id_registerButton);
        auth=FirebaseAuth.getInstance();
        //Action on clicking Register button

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName=inputFirstName.getText().toString().trim();
                lastName=inputLastName.getText().toString().trim();
                email=inputEmail.getText().toString().trim();
                phoneNo=inputPhone.getText().toString().trim();
                password=inputPassword.getText().toString();
                cnfrmPassword=inputCnfrmPassword.getText().toString();
                //Validation for input fields
                if(firstName.equals(" ")){
                    inputFirstName.setError("First Name cant be Empty");
                }
                if(lastName.equals(" ")){
                    inputLastName.setError("Last Name cant be Empty");
                }
                if(email.equals(" ")){
                    inputEmail.setError("Please enter email id");
                }
                if(phoneNo.equals(" ")){
                    inputPhone.setError("Enter Phone no");
                }
                if(password.equals(" ")){
                    inputPassword.setError("Password cannot be empty");
                }
                if(cnfrmPassword.equals(" ")){
                    inputCnfrmPassword.setError("Please enter confirm password");
                }
                else if(email.contains("@")==false){
                    inputEmail.setError("Invalid Email!");
                    inputEmail.requestFocus();
                }
                else if(phoneNo.length()!=10){
                    inputPhone.setError("Enter correct mobile no");
                }
                else if(password.length()<8){
                    inputPassword.setError("Password should not be less than 8 characters");
                    inputPassword.requestFocus();
                }
                else if(password.equals(cnfrmPassword)==false){
                    inputPassword.setError("Password not match");
                    inputCnfrmPassword.setError("Password not match");
                    inputPassword.requestFocus();
                }
                else{
                   signUpPresenter.OnSignUpClick(email.trim(),firstName.trim(),lastName.trim(),phoneNo.trim(),password,getApplicationContext());
                }
            }
        });
    }

    @Override
    public void SignUpAction() {
        Intent i=new Intent(this, FirstActivity.class);
        startActivity(i);
        finish();
    }
}
