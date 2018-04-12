// Generated code from Butter Knife. Do not modify!
package sample.daffodil.sample2;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import sample.daffodil.sample2.Activities.FirstActivity;

import java.lang.IllegalStateException;
import java.lang.Override;

public class FirstActivity_ViewBinding implements Unbinder {
  private FirstActivity target;

  @UiThread
  public FirstActivity_ViewBinding(FirstActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FirstActivity_ViewBinding(FirstActivity target, View source) {
    this.target = target;

    target.userName = Utils.findRequiredViewAsType(source, R.id.id_username, "field 'userName'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.id_password, "field 'password'", EditText.class);
    target.login = Utils.findRequiredViewAsType(source, R.id.id_login_button, "field 'login'", Button.class);
    target.registerUser = Utils.findRequiredViewAsType(source, R.id.id_user_registration, "field 'registerUser'", TextView.class);
    target.forgotPassword = Utils.findRequiredViewAsType(source, R.id.id_forgot_password, "field 'forgotPassword'", TextView.class);
    target.facebook = Utils.findRequiredViewAsType(source, R.id.id_facebook_connect, "field 'facebook'", ImageView.class);
    target.twitter = Utils.findRequiredViewAsType(source, R.id.id_twiiter_connect, "field 'twitter'", ImageView.class);
    target.googlePlus = Utils.findRequiredViewAsType(source, R.id.id_google_plus_connect, "field 'googlePlus'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FirstActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userName = null;
    target.password = null;
    target.login = null;
    target.registerUser = null;
    target.forgotPassword = null;
    target.facebook = null;
    target.twitter = null;
    target.googlePlus = null;
  }
}
