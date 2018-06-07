package com.example.andorid.mypets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ViewPets extends AppCompatActivity {
    private List<String> petData = new ArrayList<String>();
    private List<String> petName = new ArrayList<String>();

    private ArrayAdapter<String> listAdpater;

    SavePetDetails savePetDetails = new SavePetDetails();

    @Override
    protected void onStart(){
        super.onStart();
        listAdpater.notifyDataSetChanged();
    }

    @Override// @Override is for making it clear that methods are actually overridden
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pets);

        //to display only pet name in the list
        List<String> fileName = new ArrayList<String>();
        String files= savePetDetails.getData("files");// store filenames to files
        fileName = Arrays.asList(files.split("\\s*,\\s*"));
        List<String> listWithoutDuplicates = new ArrayList<>(new HashSet<>(fileName));

        Collections.sort(listWithoutDuplicates);
        for(int i = 0; i<listWithoutDuplicates.size(); i++){
            Log.i("INFO", listWithoutDuplicates.get(i));
            petName.add(listWithoutDuplicates.get(i));
            petData.add(savePetDetails.getData(listWithoutDuplicates.get(i)));
        }
        /*Collections.sort(fileName);
        for(int i = 0; i<fileName.size(); i++){
            Log.i("INFO", fileName.get(i));
            petName.add(fileName.get(i));
            petData.add(savePetDetails.getData(fileName.get(i)));
        }*/

        listAdpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, petName);
        ListView listview = (ListView) findViewById(R.id.petListView);
        listview.setAdapter(listAdpater);
        listAdpater.notifyDataSetChanged();
        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       String pet = petData.get(position);
                        Intent listDisplay = new Intent(ViewPets.this,ViewPetDetails.class).putExtra(Intent.EXTRA_TEXT,pet);
                        startActivity(listDisplay);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pets, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
