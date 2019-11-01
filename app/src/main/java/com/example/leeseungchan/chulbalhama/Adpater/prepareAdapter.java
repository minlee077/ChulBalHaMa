package com.example.leeseungchan.chulbalhama.Adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.leeseungchan.chulbalhama.R;

import java.util.ArrayList;

public class prepareAdapter extends RecyclerView.Adapter<prepareAdapter.ListViewHolder> {

    private ArrayList<String> mData = null;

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView item_name ;
        Button change, delete;


        ListViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            item_name = itemView.findViewById(R.id.item_name) ;
            change = itemView.findViewById(R.id.button_change);
            change.setVisibility(View.INVISIBLE);
            delete = itemView.findViewById(R.id.button_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("\n"+mData.get(getAdapterPosition()));
                    deleteList(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public prepareAdapter(ArrayList<String> list) {
        mData = list ;
    }
    public prepareAdapter(){}

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public prepareAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_list_change_delete, parent, false) ;
        prepareAdapter.ListViewHolder vh = new prepareAdapter.ListViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(prepareAdapter.ListViewHolder holder, int position) {
        String text = mData.get(position) ;
        holder.item_name.setText(text) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        if(mData == null)
            return 0;
        return mData.size() ;
    }

    public void addList(String name){
        mData.add(name);
    }

    public void deleteList(int position){
        mData.remove(position);
    }
}
