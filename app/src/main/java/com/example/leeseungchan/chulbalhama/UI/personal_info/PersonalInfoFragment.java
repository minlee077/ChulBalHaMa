package com.example.leeseungchan.chulbalhama.UI.personal_info;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leeseungchan.chulbalhama.Adpater.DestinationAdapter;
import com.example.leeseungchan.chulbalhama.DBHelper;
import com.example.leeseungchan.chulbalhama.R;
import com.example.leeseungchan.chulbalhama.Activities.LocationInfoActivity;
import com.example.leeseungchan.chulbalhama.VO.DestinationsVO;

import java.util.ArrayList;

public class PersonalInfoFragment extends Fragment{

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private ArrayList<DestinationsVO> destinations = new ArrayList<>();
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal_info, container, false);
        dbHelper = new DBHelper(getContext());

        dbHelper = new DBHelper(getContext());
        
        /* name */
        LinearLayout name = v.findViewById(R.id.info_name);
        
        // @todo need to get name data from db
        final TextView textName = name.findViewById(R.id.item_name);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select name from user order by _id", null);
        cursor.moveToNext();
        textName.setText(cursor.getString(0));
        db.close();

        TextView nameDesc = name.findViewById(R.id.item_description);
        nameDesc.setVisibility(View.INVISIBLE);

        Button nameChangeBtn = name.findViewById(R.id.button_change);
        nameChangeBtn.setText(R.string.button_change);
        nameChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNameDialog();
            }
        });

        Button nameDeleteBtn = name.findViewById(R.id.button_delete);
        nameDeleteBtn.setVisibility(View.GONE);
        name.removeView(nameDeleteBtn);


        /* start point */
        LinearLayout startPoint = v.findViewById(R.id.info_start);

        SQLiteDatabase db2 = dbHelper.getReadableDatabase();
        String sql = "select starting_name, starting_coordinate from user order by _id";
        Cursor cursor2 =  db2.rawQuery(sql, null);
        cursor2.moveToNext();

        TextView startPointName = startPoint.findViewById(R.id.item_name);
        startPointName.setText(cursor2.getString(0));

        TextView startPointDesc = startPoint.findViewById(R.id.item_description);
        startPointDesc.setEnabled(false);
        startPointDesc.setText(cursor2.getString(1));

        db2.close();

        Button startPointChangeBtn = startPoint.findViewById(R.id.button_change);
        startPointChangeBtn.setText(R.string.button_change);
        startPointChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LocationInfoActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });

        Button startPointDeleteBtn = startPoint.findViewById(R.id.button_delete);
        startPointDeleteBtn.setVisibility(View.GONE);
        startPoint.removeView(startPointDeleteBtn);


        /* destination */
        LinearLayout destination = v.findViewById(R.id.info_destination);
        Button endPointChangeBtn = destination.findViewById(R.id.button_for_selection);
        endPointChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LocationInfoActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });

        TextView endPointName = destination.findViewById(R.id.guide_for_selection);
        endPointName.setText(R.string.setting_destination);

        EditText endPointDesc = destination.findViewById(R.id.input_for_selection);
        endPointDesc.setVisibility(View.INVISIBLE);
        destination.removeView(endPointDesc);

        RecyclerView destinationRecycler = destination.findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        destinationRecycler.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter;
        mAdapter = new DestinationAdapter(destinations);
        destinationRecycler.setAdapter(mAdapter);

        retrieve();

        fragmentManager = getActivity().getSupportFragmentManager();
        return v;
    }

    public void retrieve(){
        destinations.clear();
        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select destination_name from destinations";
        Cursor c = db.rawQuery(sql, null);
        while(c.moveToNext()){
            String name = c.getString(0);

            DestinationsVO h = new DestinationsVO();
            h.setDestinationName(name);

            destinations.add(h);
        }
        Log.e("destination size ", destinations.size() + "개");
    }

    public void setNameDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Title");
        View viewInflated =
                LayoutInflater.from(getContext())
                        .inflate(R.layout.dialog_edit_text, (ViewGroup) getView(), false);

        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String newName = input.getText().toString();
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                db.execSQL("update user set name=\""+ newName + "\" where _id=1");
                db.close();
                Log.e("name input", "onClick:" + newName);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}