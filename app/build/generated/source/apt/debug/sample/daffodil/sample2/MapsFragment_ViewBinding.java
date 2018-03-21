// Generated code from Butter Knife. Do not modify!
package sample.daffodil.sample2;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MapsFragment_ViewBinding implements Unbinder {
  private MapsFragment target;

  private View view2131624114;

  @UiThread
  public MapsFragment_ViewBinding(final MapsFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.place_autocomplete_fragment, "method 'searchArea'");
    view2131624114 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.searchArea();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131624114.setOnClickListener(null);
    view2131624114 = null;
  }
}
