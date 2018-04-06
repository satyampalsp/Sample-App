package sample.daffodil.sample2.Presenter;

import android.content.Context;

/**
 * Created by DAFFODIL-29 on 4/6/2018.
 */

public interface SignUpPresenter {
    void OnSignUpClick(String email, String FirstName, String LastName, String PhoneNo, String Pass, Context context);
}
