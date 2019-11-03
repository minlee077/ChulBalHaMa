/**
 * @todo implement drop down
 */
package com.example.leeseungchan.chulbalhama.UI.destination_info;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leeseungchan.chulbalhama.DestinationActivity;
import com.example.leeseungchan.chulbalhama.R;
import com.example.leeseungchan.chulbalhama.UI.map.MapAddFragment;

public class DestinationInfoFragment extends Fragment implements View.OnClickListener {

    private Bundle bundle;

    public static DestinationInfoFragment newInstance() {
        return new DestinationInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.fragment_destination_info, container, false);


        /* destination setting view */
        LinearLayout destinationCord = v.findViewById(R.id.destination_setting);

        // destination TextView guide text
        TextView destGuideText = destinationCord.findViewById(R.id.item_name);
        destGuideText.setText(R.string.guide_address);

        TextView destText = destinationCord.findViewById(R.id.item_description);
        destText.setVisibility(View.INVISIBLE);

        // set add button
        Button destSetButton = destinationCord.findViewById(R.id.button_change);
        destSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fg;
                fg = new MapAddFragment();
                if (!fg.isAdded()) {
                    transaction.replace(R.id.nav_host_fragment, fg)
                            .commitNowAllowingStateLoss();
                }
            }
        });
        destSetButton.setText(R.string.button_setting);

        Button destDelButton = destinationCord.findViewById(R.id.button_delete);
        destinationCord.removeView(destDelButton);


        /* time setting view */
        LinearLayout timeCord = v.findViewById(R.id.destination_time_duration);

        // time TextView guide text
        TextView timeGuideText = timeCord.findViewById(R.id.item_name);
        timeGuideText.setText(R.string.guide_when_time);

        TextView timeText = timeCord.findViewById(R.id.item_description);
        timeText.setVisibility(View.INVISIBLE);

        // set add button
        Button timeButton = timeCord.findViewById(R.id.button_change);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // @todo time-day dialog is needed.
                Toast.makeText(getContext(), "button clicked", Toast.LENGTH_SHORT).show();
            }
        });
        timeButton.setText(R.string.button_setting);

        Button timeDelButton = timeCord.findViewById(R.id.button_delete);
        timeCord.removeView(timeDelButton);


        /* destination */
        LinearLayout destination = v.findViewById(R.id.destination_time_start);

        TextView endPointName = destination.findViewById(R.id.guide_for_selection);
        endPointName.setText(R.string.map_location_go_time);

        EditText endPointDesc = destination.findViewById(R.id.input_for_selection);
        endPointDesc.setVisibility(View.INVISIBLE);
        destination.removeView(endPointDesc);

        Button endPointChangeBtn = destination.findViewById(R.id.button_for_selection);
        endPointChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        RecyclerView destinationRecycler = destination.findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        destinationRecycler.setLayoutManager(layoutManager);


        Button destinationStoreBtn = v.findViewById(R.id.store_destination);
        destinationStoreBtn.setOnClickListener(this);

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
        if (view.getId() == R.id.store_destination) {
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

}