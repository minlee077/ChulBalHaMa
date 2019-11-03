package com.example.leeseungchan.chulbalhama.UI.location_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leeseungchan.chulbalhama.LocationInfoActivity;
import com.example.leeseungchan.chulbalhama.R;
import com.example.leeseungchan.chulbalhama.UI.map.MapAddFragment;

public class StartingPointInfoFragment extends Fragment {
    Bundle bundle = new Bundle();

    public static StartingPointInfoFragment newInstance(Bundle bundle){
        StartingPointInfoFragment v = new StartingPointInfoFragment();
        v.bundle = bundle;
        return v;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.fragment_starting_info, container, false);

        bundle.putInt("type", 0);

        EditText start_name = v.findViewById(R.id.starting_name);
        String name = ((LocationInfoActivity)getActivity()).getName();

        if(name != null) {
            start_name.setText(((LocationInfoActivity) getActivity()).getName());
        }
        setNameListener(start_name);

        /* starting setting view */
        LinearLayout destinationCord = v.findViewById(R.id.starting_setting);

        // starting TextView guide text
        TextView destGuideText = destinationCord.findViewById(R.id.item_name);
        String address = bundle.getString("address");
        if(address == null)
            destGuideText.setText(R.string.guide_address);
        else
            destGuideText.setText(address);

        TextView startText = destinationCord.findViewById(R.id.item_description);
        startText.setVisibility(View.INVISIBLE);

        // set add button
        Button startSetButton = destinationCord.findViewById(R.id.button_change);
        startSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fg;
                fg = MapAddFragment.newInstance(bundle);
                if (!fg.isAdded()) {
                    transaction.replace(R.id.nav_host_fragment, fg)
                            .commitNowAllowingStateLoss();
                }
            }
        });
        startSetButton.setText(R.string.button_setting);

        Button startingPoint = destinationCord.findViewById(R.id.button_delete);
        destinationCord.removeView(startingPoint);


        /* starting store */
        Button destinationStoreBtn = v.findViewById(R.id.store_starting);
        destinationStoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }

    private void setNameListener(EditText edit){
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                ((LocationInfoActivity)getActivity()).setName(arg0.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });
    }
}