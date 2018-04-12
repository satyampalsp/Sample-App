package sample.daffodil.sample2.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.daffodil.sample2.R;
import sample.daffodil.sample2.RecylerViewAdapter.ApiClient;
import sample.daffodil.sample2.RecylerViewAdapter.ApiService;
import sample.daffodil.sample2.RecylerViewAdapter.ConfHall;
import sample.daffodil.sample2.RecylerViewAdapter.RecycleViewAdapter;

/**
 * Created by DAFFODIL-29 on 3/13/2018.
 */

public class HomeFragment extends Fragment {
    View view;
    RecyclerView recyler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_fragment, container, false);
        recyler=(RecyclerView)view.findViewById(R.id.id_recycler_view);
        //Setting layout manager
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyler.setLayoutManager(staggeredGridLayoutManager);
        getList();
        return view;
    }
    void getList(){
        try {
            ApiService service = ApiClient.getRetrofit().create(ApiService.class);
            Call<List<ConfHall>> call = service.getUserData();
            final ProgressDialog progressDoalog;
            progressDoalog = new ProgressDialog(getContext());
            progressDoalog.setMax(100);
            progressDoalog.setMessage("Its loading....");
            progressDoalog.setTitle("ProgressDialog bar example");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // show it
            progressDoalog.show();
             call.enqueue(new Callback<List<ConfHall>>() {
                @Override
                public void onResponse(Call<List<ConfHall>> call, Response<List<ConfHall>> response) {
                    List<ConfHall> userList = response.body();
                    RecycleViewAdapter recyclerViewAdapter =
                            new RecycleViewAdapter(userList);
                    recyler.setAdapter(recyclerViewAdapter);
                    progressDoalog.dismiss();
                }

                @Override
                public void onFailure(Call<List<ConfHall>> call, Throwable t) {

                    progressDoalog.dismiss();
                }
            });
        }catch (Exception e) {
            Log.e("exception",e.toString());
        }
    }
}