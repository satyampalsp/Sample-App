package sample.daffodil.sample2.Presenter;

import android.content.Context;

import sample.daffodil.sample2.Model.LoginBackground;
import sample.daffodil.sample2.View.LoginView;

/**
 * Created by DAFFODIL-29 on 4/5/2018.
 */

public class LoginPresenterImpl implements LoginPresenter,LoginBackground.OnFinished {
    LoginView loginView;

    @Override
    public void getSocialLoginStatus(boolean googleLoginStatus) {
        if(googleLoginStatus==true){
            loginView.LoginAction();
        }
    }

    @Override
    public void OnSocialLoginClick(String email, String FirstName, String LastName, String PhoneNo, String Pass, Context context) {
    loginBackground.SocialLogin(email,FirstName,LastName,PhoneNo,Pass,this,context);
    }

    LoginBackground loginBackground;

    public LoginPresenterImpl(LoginView loginView, LoginBackground loginBackground) {
        this.loginView = loginView;
        this.loginBackground = loginBackground;
    }

    @Override
    public void onLoginClick(String username, String password, Context context) {
        loginBackground.LoginPerform(username,password,this,context);
    }

    @Override
    public void getLoginStatus(boolean status) {
    if(status==true){
        loginView.LoginAction();
    }
    }
}
