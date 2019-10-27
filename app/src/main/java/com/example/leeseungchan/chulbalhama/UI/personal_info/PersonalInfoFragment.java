package com.example.leeseungchan.chulbalhama.UI.personal_info;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.leeseungchan.chulbalhama.R;
import com.example.leeseungchan.chulbalhama.DestinationActivity;

public class PersonalInfoFragment extends Fragment implements View.OnClickListener{

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saveInstanceState){
        View v = inflater.inflate(R.layout.fragment_personal_info, container, false);

        Button startPointChangeBtn = v.findViewById(R.id.info_setting_start);
        startPointChangeBtn.setOnClickListener(this);
        Button endPointAddBtn = v.findViewById(R.id.info_add_destination);
        endPointAddBtn.setOnClickListener(this);
        Button store = v.findViewById(R.id.store_personal);
        store.setOnClickListener(this);

        fragmentManager = getActivity().getSupportFragmentManager();

        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
        case R.id.info_setting_start:
            break;
        case R.id.info_add_destination:
            startActivity(DestinationActivity.class);
            break;
        case R.id.store_personal:
            //@todo implement update on "Person" table
        }
    }

    private void startActivity(Class activity){

        Intent intent = new Intent(getContext(), activity);
        startActivity(intent);
    }
}
