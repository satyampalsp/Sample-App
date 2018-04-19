package sample.daffodil.sample2.Presenter;

import android.content.Context;

import sample.daffodil.sample2.Model.SignUpBackground;
import sample.daffodil.sample2.View.SignUpView;

/**
 * Created by DAFFODIL-29 on 4/6/2018.
 */

public class SignUpPresenterImpl implements SignUpPresenter,SignUpBackground.OnFinished {
    SignUpView signUpView;
    SignUpBackground signUpBackground;

    public SignUpPresenterImpl(SignUpView signUpView, SignUpBackground signUpBackground) {
        this.signUpView = signUpView;
        this.signUpBackground = signUpBackground;
    }

    @Override
    public void OnSignUpClick(String email, String FirstName, String LastName, String PhoneNo, String Pass, Context context) {
        signUpBackground.SignUp(email,FirstName,LastName,PhoneNo,Pass,this,context);

    }

    @Override
    public void getStatus(boolean status) {
        signUpView.SignUpAction();
    }
}
