package sample.daffodil.sample2.Model;

import android.content.Context;

/**
 * Created by DAFFODIL-29 on 4/6/2018.
 */

public interface SignUpBackground {
    interface OnFinished{
        void getStatus(boolean status);
    }
    void SignUp(String email, String FirstName, String LastName, String PhoneNo, String pass, OnFinished onFinished, Context context);
}
