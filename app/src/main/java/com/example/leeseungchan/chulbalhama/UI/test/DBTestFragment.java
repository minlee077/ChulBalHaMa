package com.example.leeseungchan.chulbalhama.UI.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.leeseungchan.chulbalhama.R;

public class DBTestFragment extends Fragment implements View.OnClickListener {


    public static DBTestFragment newInstance(){
        return new DBTestFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saveInstanceState){
        View v = inflater.inflate(R.layout.test_fragment_db, container, false);
        Button backBtn = v.findViewById(R.id.back);
        backBtn.setOnClickListener(this);

        return v;
    }

    public void onClick(View view) {

        Fragment fg;
        switch (view.getId()) {
            case R.id.back:
                fg = TestFragment.newInstance();
                if(!fg.isAdded()) {
                    replaceFragment(fg);
                }
                break;
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment)
                .commitNowAllowingStateLoss();
    }

}
