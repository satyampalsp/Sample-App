// Generated code from Butter Knife. Do not modify!
package sample.daffodil.sample2;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import sample.daffodil.sample2.Fragments.ProfileFragment;

import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfileFragment_ViewBinding implements Unbinder {
  private ProfileFragment target;

  @UiThread
  public ProfileFragment_ViewBinding(ProfileFragment target, View source) {
    this.target = target;

    target.profile_pic = Utils.findRequiredViewAsType(source, R.id.id_profile_image_big, "field 'profile_pic'", ImageView.class);
    target.profileName = Utils.findRequiredViewAsType(source, R.id.id_profile_name, "field 'profileName'", TextView.class);
    target.profileEmail = Utils.findRequiredViewAsType(source, R.id.id_profile_email, "field 'profileEmail'", TextView.class);
    target.profilePhone = Utils.findRequiredViewAsType(source, R.id.id_profile_phone, "field 'profilePhone'", TextView.class);
    target.uploadPic = Utils.findRequiredViewAsType(source, R.id.id_upload_profile_pic, "field 'uploadPic'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfileFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.profile_pic = null;
    target.profileName = null;
    target.profileEmail = null;
    target.profilePhone = null;
    target.uploadPic = null;
  }
}
