/**
 * @todo implement drop down
 */

package com.example.leeseungchan.chulbalhama;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.leeseungchan.chulbalhama.UI.destination_info.DestinationInfoFragment;
import com.example.leeseungchan.chulbalhama.UI.habit_list.HabitListFragment;
import com.example.leeseungchan.chulbalhama.UI.map.MapAddFragment;
import com.example.leeseungchan.chulbalhama.UI.personal_info.PersonalInfoFragment;

import org.w3c.dom.Text;

public class DestinationActivity extends AppCompatActivity{

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private DestinationInfoFragment destinationInfoFragment;

    private int timeHour;
    private int timeMin;
    private double latitude;
    private double longitude;
    private String name;
    private String description;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        name = null;
        latitude = 0;
        longitude = 0;
        description = null;
        timeHour = 0;
        timeMin = 0;

        // set up toolbar on top
        Toolbar toolbarMain = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //set up destination Info
        destinationInfoFragment = new DestinationInfoFragment();

        fragmentManager = getSupportFragmentManager();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, destinationInfoFragment)
                .commitAllowingStateLoss();

        setTitle(R.string.title_destination);

    }

    @Override
    public void setTitle(int id){
        TextView title = (TextView)findViewById(R.id.toolbar_title);
        title.setText(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
        Log.d(null,Integer.toString(timeHour));
    }

    public int getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(int timeMin) {
        this.timeMin = timeMin;
    }
}
