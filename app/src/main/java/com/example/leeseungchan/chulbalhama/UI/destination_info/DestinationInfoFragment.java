/**
 * @todo implement drop down
 */
package com.example.leeseungchan.chulbalhama.UI.destination_info;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leeseungchan.chulbalhama.DestinationActivity;
import com.example.leeseungchan.chulbalhama.MainActivity;
import com.example.leeseungchan.chulbalhama.R;
import com.example.leeseungchan.chulbalhama.UI.map.MapAddFragment;
import com.example.leeseungchan.chulbalhama.UI.personal_info.PersonalInfoFragment;

public class DestinationInfoFragment extends Fragment implements View.OnClickListener {

    private View view;

    public static DestinationInfoFragment newInstance() {
        return new DestinationInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.fragment_destination_info, container, false);

        view = v;

        TextView destinationTimeBtn = v.findViewById(R.id.destination_time);
        TextView destinationDescription = v.findViewById(R.id.destination_desc);

        Button destinationSetBtn = v.findViewById(R.id.destination_setting);
        destinationSetBtn.setOnClickListener(this);

        Button destinationStoreBtn = v.findViewById(R.id.store_destination);
        destinationStoreBtn.setOnClickListener(this);

        EditText dest_name = v.findViewById(R.id.destination_name);

        return v;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment fg;
        if (view.getId() == R.id.destination_setting) {
            fg = new MapAddFragment();
            if (!fg.isAdded()) {
                transaction.replace(R.id.nav_host_fragment, fg)
                        .commitNowAllowingStateLoss();
            }
        } else if (view.getId() == R.id.store_destination) {
            //@todo implement insert operation to "destination" table.
            getActivity().finish();
        }
    }

    private void setNameListener(EditText edit){
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                ((DestinationActivity)getActivity()).setName(arg0.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });
    }

}