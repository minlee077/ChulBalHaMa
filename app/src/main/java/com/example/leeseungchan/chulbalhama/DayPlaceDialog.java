package com.example.leeseungchan.chulbalhama;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.leeseungchan.chulbalhama.Adpater.DatePlaceAdapter;
import com.example.leeseungchan.chulbalhama.UI.components.CustomDayCheckBox;

import java.util.ArrayList;


public class DayPlaceDialog {
    private Context context;
    private DatePlaceAdapter dayPlaceAdapter;

    public DayPlaceDialog(Context context) {
        this.context = context;
    }

    public void callFunction(final ArrayList<ArrayList<Boolean>> result,
                             final ArrayList<String> selected,
                             final RecyclerView.Adapter dayPlaceAdapter) {

        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dlg.setContentView(R.layout.day_place_dialog);

        dlg.show();

        final CustomDayCheckBox dayCheckBox =
                new CustomDayCheckBox(dlg.findViewById(R.id.custom_days_checkbox));
        final Spinner placeSelector = (Spinner)dlg.findViewById(R.id.spinner);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                dlg.getContext(),
                R.array.day_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSelector.setAdapter(adapter);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.add(dayCheckBox.getResult());
                selected.add(placeSelector.getSelectedItem().toString());
                dayPlaceAdapter.notifyDataSetChanged();
                dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();
                dlg.dismiss();
            }
        });
    }
}
