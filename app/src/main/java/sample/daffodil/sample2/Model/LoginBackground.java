package sample.daffodil.sample2.Model;

import android.content.Context;

/**
 * Created by DAFFODIL-29 on 4/5/2018.
 */

public interface LoginBackground {
    interface OnFinished{
        void getLoginStatus(boolean status);
        void getSocialLoginStatus(boolean googleLoginStatus);
    }
    void LoginPerform(String username, String password, OnFinished onFinished, Context context);
    void SocialLogin(String email, String FirstName, String LastName, String PhoneNo, String pass, OnFinished onFinished, Context context);
}
