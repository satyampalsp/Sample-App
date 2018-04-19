package sample.daffodil.sample2.Presenter;

import android.content.Context;

/**
 * Created by DAFFODIL-29 on 4/5/2018.
 */

public interface LoginPresenter {
    void onLoginClick(String username, String password, Context context);
    void OnSocialLoginClick(String email, String FirstName, String LastName, String PhoneNo, String Pass, Context context);
}
