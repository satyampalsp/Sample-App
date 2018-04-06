package sample.daffodil.sample2.Presenter;

import android.content.Context;

import sample.daffodil.sample2.Model.LoginBackground;
import sample.daffodil.sample2.View.LoginView;

/**
 * Created by DAFFODIL-29 on 4/5/2018.
 */

public class LoginPresenterImpl implements LoginPresenter,LoginBackground.OnFinished {
    LoginView loginView;
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
