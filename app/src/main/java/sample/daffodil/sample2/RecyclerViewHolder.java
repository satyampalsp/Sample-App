package sample.daffodil.sample2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DAFFODIL-29 on 3/14/2018.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder  {
    TextView bookid;
    TextView ministry;
    TextView department;
    TextView state;
    TextView noOfPersons;
    TextView city;
    TextView conf_name;
    TextView date;
    TextView enddate;
    TextView start_date;
    TextView confid;

    public RecyclerViewHolder(View view){
        super(view);
        bookid=(TextView)view.findViewById(R.id.id_book_id);
        ministry=(TextView)view.findViewById(R.id.id_ministry);
        department=(TextView)view.findViewById(R.id.id_department);
        state=(TextView)view.findViewById(R.id.id_state);
        noOfPersons=(TextView)view.findViewById(R.id.id_no_of_persons);
        city=(TextView)view.findViewById(R.id.id_city);
        conf_name=(TextView)view.findViewById(R.id.id_conf_name);
        date=(TextView)view.findViewById(R.id.id_date);
        enddate=(TextView)view.findViewById(R.id.id_end_date);
        start_date=(TextView)view.findViewById(R.id.id_start_date);
        confid=(TextView)view.findViewById(R.id.id_conf_id);
    }

}
