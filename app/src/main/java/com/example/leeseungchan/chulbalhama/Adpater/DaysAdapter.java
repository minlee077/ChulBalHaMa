package com.example.leeseungchan.chulbalhama.Adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.leeseungchan.chulbalhama.R;

import java.util.ArrayList;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ListViewHolder> {

    private ArrayList<ArrayList<Boolean>> days;

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView item_name, description;
        Button change, delete;


        ListViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            item_name = itemView.findViewById(R.id.item_name) ;
            description = itemView.findViewById(R.id.item_description);
            change = itemView.findViewById(R.id.button_change);
            delete = itemView.findViewById(R.id.button_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteList(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public DaysAdapter(){}
    public DaysAdapter(ArrayList<ArrayList<Boolean>> days){
        this.days = days;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public DaysAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_list_change_delete, parent, false) ;
        DaysAdapter.ListViewHolder vh = new DaysAdapter.ListViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(DaysAdapter.ListViewHolder holder, int position) {
        ArrayList<Boolean> day = days.get(position);

        String[] dayName = {"월", "화","수", "목", "금", "토", "일"};

        StringBuffer name = new StringBuffer();

        for(int i = 0; i < day.size();i++){
            if(day.get(i)) {
                name.append(" / " + dayName[i]);
            }
        }

        holder.description.setText(name);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        if(days == null)
            return 0;
        return days.size() ;
    }

    public void addList(ArrayList<Boolean> day,String places){
        this.days.add(day);
    }

    public void deleteList(int position){
        days.remove(position);
    }
}
