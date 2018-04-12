package sample.daffodil.sample2.RecylerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sample.daffodil.sample2.R;

/**
 * Created by DAFFODIL-29 on 3/14/2018.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<ConfHall> conf;
    public RecycleViewAdapter(List<ConfHall> c){
        this.conf=c;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.bookid.setText(conf.get(position).getBook_id());
            holder.ministry.setText(conf.get(position).getMinistry());
            holder.department.setText(conf.get(position).getDepartment());
        holder.state.setText(conf.get(position).getState());
        holder.noOfPersons.setText(conf.get(position).getNo_of_persons());
        holder.city.setText(conf.get(position).getCity());
        holder.conf_name.setText(conf.get(position).getConf_Name());
        holder.date.setText(conf.get(position).getDate());
        holder.start_date.setText(conf.get(position).getStart_time());
        holder.enddate.setText(conf.get(position).getEnd_time());
        holder.confid.setText(conf.get(position).Conf_id);

    }

    @Override
    public int getItemCount() {
        return conf.size();
    }
}
