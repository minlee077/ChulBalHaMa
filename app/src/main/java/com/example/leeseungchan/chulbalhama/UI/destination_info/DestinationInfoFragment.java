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
import android.widget.Toast;

import com.example.leeseungchan.chulbalhama.DestinationActivity;
import com.example.leeseungchan.chulbalhama.MainActivity;
import com.example.leeseungchan.chulbalhama.R;
import com.example.leeseungchan.chulbalhama.UI.map.MapAddFragment;
import com.example.leeseungchan.chulbalhama.UI.personal_info.PersonalInfoFragment;

public class DestinationInfoFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Bundle bundle;

    public static DestinationInfoFragment newInstance() {
        return new DestinationInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.fragment_destination_info, container, false);

        view = v;

        Button destinationSetBtn = v.findViewById(R.id.destination_setting);
        destinationSetBtn.setOnClickListener(this);

        Button destinationStoreBtn = v.findViewById(R.id.store_destination);
        destinationStoreBtn.setOnClickListener(this);

        Spinner hour = (Spinner)v.findViewById(R.id.hour);
        setAdapter(R.array.hour_array, hour);
        hour.setSelection(((DestinationActivity)getActivity()).getTimeHour());
        setSpinnerListener(hour,R.id.hour);

        Spinner minute = (Spinner)v.findViewById(R.id.min);
        setAdapter(R.array.min_array, minute);
        minute.setSelection(((DestinationActivity)getActivity()).getTimeMin());
        setSpinnerListener(minute, R.id.min);

        EditText dest_name = v.findViewById(R.id.destination_name);
        String name = ((DestinationActivity)getActivity()).getName();
        if(name != null) {
            dest_name.setText(((DestinationActivity) getActivity()).getName());
        }
        setNameListener(dest_name);

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

    private void setAdapter(int id, Spinner spinner) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                id, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
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

    private void setSpinnerListener(final Spinner spinner, final int spinnerId){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(spinnerId){
                    case R.id.hour:
                        String hour = spinner.getItemAtPosition(position).toString();
                        if(!hour.equals("0"))
                            ((DestinationActivity)getActivity()).setTimeHour(Integer.parseInt(hour));
                        break;
                    case R.id.min:
                        String min = spinner.getItemAtPosition(position).toString();
                        if(!min.equals("0"))
                            ((DestinationActivity)getActivity()).setTimeMin(Integer.parseInt(min));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}