package com.example.leeseungchan.chulbalhama.UI.map;

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
import android.widget.FrameLayout;

import com.example.leeseungchan.chulbalhama.DestinationActivity;
import com.example.leeseungchan.chulbalhama.R;
import com.example.leeseungchan.chulbalhama.UI.destination_info.DestinationInfoFragment;

public class MapAddFragment extends Fragment implements View.OnClickListener{


    public static MapAddFragment newInstance(){
        return new MapAddFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saveInstanceState){
        View v = inflater.inflate(R.layout.fragment_map_select, container, false);

        Button registerLocation = v.findViewById(R.id.register_btn);
        registerLocation.setOnClickListener(this);

        EditText description = v.findViewById(R.id.editText);
        setDiscriptionListener(description);

        FrameLayout map = v.findViewById(R.id.linearLayoutMap);
        return v;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment fg;
        if(view.getId() == R.id.register_btn){
            fg = new DestinationInfoFragment();
            if (!fg.isAdded()) {
                transaction.replace(R.id.nav_host_fragment, fg)
                        .commitNowAllowingStateLoss();
            }
        }
    }
    private void setDiscriptionListener(EditText edit){
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                ((DestinationActivity)getActivity()).setDescription(arg0.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });
    }

    private void setMap(FrameLayout map){
        //@todo implement map
    }
}
