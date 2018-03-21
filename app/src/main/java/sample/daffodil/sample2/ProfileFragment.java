package sample.daffodil.sample2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akhgupta.easylocation.EasyLocationActivity;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.DISPLAY_SERVICE;

/**
 * Created by DAFFODIL-29 on 3/13/2018.
 */

public class ProfileFragment extends Fragment {
    View view;
    @BindView(R.id.id_profile_image_big) ImageView profile_pic;
    @BindView(R.id.id_profile_name)TextView profileName;
    @BindView(R.id.id_profile_email)TextView profileEmail;
    @BindView(R.id.id_profile_phone)TextView profilePhone;
    @BindView(R.id.id_upload_profile_pic)Button uploadPic;
    String url;
    private ProgressDialog dialog = null;

    private String upLoadServerUri = null;
    private String imagepath=null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.profile_fragment, container, false);
        ButterKnife.bind(this,view);
        SharedPreferences sp = getContext().getSharedPreferences("Mypref", 0);
        String email_id=sp.getString("email",null);
        UserDatabase userdb = Room.databaseBuilder(getContext(),
                UserDatabase.class, "userDatabase").allowMainThreadQueries().build();
        List<User> u = userdb.getUserDao().getUserDetails(email_id);

        profileName.setText(u.get(0).firstName+" "+u.get(0).lastName);
        profileEmail.setText(u.get(0).email);
        profilePhone.setText(u.get(0).mobile);
        url=sp.getString("picUrl",null);
        if(url!=null){
            Picasso.with(getContext()).load(url).fit().into(profile_pic);
        }
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertadd = new AlertDialog.Builder(getContext());
                LayoutInflater factory = LayoutInflater.from(getContext());
                final View view = factory.inflate(R.layout.dialog_layout, null);
                alertadd.setView(view);
                ImageView im=(ImageView)view.findViewById(R.id.dialog_imageview);
                Picasso.with(getContext()).load(url).fit().into(im);
                alertadd.setNeutralButton("OK!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {

                    }
                });

                alertadd.show();
            }
        });
        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...
                                Toast.makeText(getActivity(),r.getPath().toString(),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), ImageUploadService.class);
                                Bundle b = new Bundle();
                                b.putString("File", r.getPath());
                                intent.putExtras(b);
                                getActivity().startService(intent);

                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(getFragmentManager());
            }
        });
        return view;
    }
    public  void SetImage(String Url){
        Picasso.with(getContext()).load(Url).fit().into(profile_pic);
    }
}