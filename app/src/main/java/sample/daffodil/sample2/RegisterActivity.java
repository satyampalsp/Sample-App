package sample.daffodil.sample2;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    String firstName,lastName,email,phoneNo,password,cnfrmPassword;
    EditText inputFirstName,inputLastName,inputEmail,inputPhone,inputPassword,inputCnfrmPassword;
    Button registerButton;
    User user;
    UserDatabase userdb;
    UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFirstName=(EditText)findViewById(R.id.id_resgisterFirstName);
        inputLastName=(EditText)findViewById(R.id.id_resgisterLastName);
        inputEmail=(EditText)findViewById(R.id.id_resgisterEmail);
        inputPhone=(EditText)findViewById(R.id.id_resgisterPhoneNo);
        inputPassword=(EditText)findViewById(R.id.id_resgisterPassword);
        inputCnfrmPassword=(EditText)findViewById(R.id.id_resgisterCnfrmPassword);
        registerButton=(Button)findViewById(R.id.id_registerButton);

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
                    userdb = Room.databaseBuilder(getApplicationContext(),
                            UserDatabase.class, "userDatabase").allowMainThreadQueries().build();

                    //encryption for password
                    byte[] base64=password.getBytes(StandardCharsets.UTF_8)   ;
                    String pass = Base64.encodeToString(base64, Base64.DEFAULT);
                    user=new User(email,firstName,lastName,phoneNo,pass);
                    List<User> e=userdb.getUserDao().getUserDetails(email.trim());
                    List<User> m=userdb.getUserDao().getPhoneDetails(phoneNo.trim());
                    //verification for email doesn't exist
                    if(e.size()==1){
                        Toast.makeText(RegisterActivity.this,"Email id already exist",Toast.LENGTH_SHORT).show();
                    }
                    //verification for mobile no doesn't exist
                    else if(m.size()==1){
                        Toast.makeText(RegisterActivity.this,"Phone no already exist",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        userdb.getUserDao().insert(user);
                        List<User> verify_email=userdb.getUserDao().getUserDetails(email.trim());
                        //verification for successfull entry
                        if(verify_email.size()==1){
                            Toast.makeText(RegisterActivity.this,"Registration Successfull. Please Log in to continue!",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(RegisterActivity.this,FirstActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }
            }
        });
    }
}
