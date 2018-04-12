package sample.daffodil.sample2.RecylerViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by DAFFODIL-29 on 3/14/2018.
 */

public interface ApiService {
    @GET("bookingdetails.php")
    Call<List<ConfHall>> getUserData();
}
