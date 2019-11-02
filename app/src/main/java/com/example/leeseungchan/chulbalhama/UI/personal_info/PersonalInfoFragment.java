package com.example.leeseungchan.chulbalhama.UI.personal_info;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leeseungchan.chulbalhama.R;
import com.example.leeseungchan.chulbalhama.DestinationActivity;

import org.w3c.dom.Text;

public class PersonalInfoFragment extends Fragment{

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saveInstanceState){
        View v = inflater.inflate(R.layout.fragment_personal_info, container, false);

        /* name */
        LinearLayout name = v.findViewById(R.id.info_name);
        Button nameChangeBtn = name.findViewById(R.id.button_change);
        nameChangeBtn.setText(R.string.button_change);
        nameChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Button nameDeleteBtn = name.findViewById(R.id.button_delete);
        nameDeleteBtn.setVisibility(View.GONE);
        name.removeView(nameDeleteBtn);

        // @todo need to get start point name data from db
        TextView textName = name.findViewById(R.id.item_name);
        textName.setText("Example");

        // @todo need to get start point description data from db
        TextView nameDesc = name.findViewById(R.id.item_description);
        nameDesc.setVisibility(View.INVISIBLE);


        /* start point */
        LinearLayout startPoint = v.findViewById(R.id.info_start);
        Button startPointChangeBtn = startPoint.findViewById(R.id.button_change);
        startPointChangeBtn.setText(R.string.button_change);
        startPointChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Button startPointDeleteBtn = startPoint.findViewById(R.id.button_delete);
        startPointDeleteBtn.setVisibility(View.GONE);
        startPoint.removeView(startPointDeleteBtn);

        // @todo need to get start point name data from db
        TextView startPointName = startPoint.findViewById(R.id.item_name);
        startPointName.setText("Example");

        // @todo need to get start point description data from db
        TextView startPointDesc = startPoint.findViewById(R.id.item_description);
        startPointDesc.setEnabled(false);
        startPointDesc.setText("Description");


        /* destination */
        LinearLayout destination = v.findViewById(R.id.info_destination);
        Button endPointChangeBtn = destination.findViewById(R.id.button_for_selection);
        endPointChangeBtn.setText(R.string.button_change);
        endPointChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DestinationActivity.class);
                startActivity(intent);
            }
        });

        // @todo need to get start point name data from db
        TextView endPointName = destination.findViewById(R.id.guide_for_selection);
        endPointName.setText(R.string.setting_destination);

        // @todo need to get start point description data from db
        EditText endPointDesc = destination.findViewById(R.id.input_for_selection);
        endPointDesc.setVisibility(View.INVISIBLE);
        destination.removeView(endPointDesc);

        RecyclerView descRecycler = destination.findViewById(R.id.list);
        descRecycler.setVisibility(View.GONE);

        fragmentManager = getActivity().getSupportFragmentManager();

        return v;
    }

}
