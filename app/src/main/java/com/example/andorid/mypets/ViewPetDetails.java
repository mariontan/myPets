package com.example.andorid.mypets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPetDetails extends AppCompatActivity {
    private List<String> petVac = new ArrayList<String>();
    SavePetDetails savePetDetails = new SavePetDetails();

    private ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<String> details = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet_details);
        Intent intent = getIntent();
        final String petDetails = intent.getStringExtra(Intent.EXTRA_TEXT);


        details = Arrays.asList(petDetails.split("\\s*,\\s*"));

        TextView petType =(TextView) findViewById(R.id.petTypeIndividual);
        TextView petName = (TextView) findViewById(R.id.petNameIndividual);
        TextView petBirthday = (TextView) findViewById(R.id.petBirthdayIndividual);
        TextView petWeight = (TextView) findViewById(R.id.petWeightIndividual);
        TextView petSex = (TextView) findViewById(R.id.petSexIndividual);

        petType.setText("pet type: " + details.get(0));
        petName.setText("pet name: " + details.get(1));
        petBirthday.setText("Birthday: " + details.get(2));
        petWeight.setText("Weight: " + details.get(3));
        petSex.setText("Sex: "+ details.get(4));

        String vaccines = savePetDetails.getData(details.get(1)+"Vac");
        //Log.i("INFO",petName.getText().toString());
        //Log.i("INFO",vaccines);
        petVac = Arrays.asList(vaccines.split("\\s*,\\s*"));
        listAdapter = new  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, petVac);
        ListView listview = (ListView) findViewById(R.id.petVacList);
        listview.setAdapter(listAdapter);
        Button edit =(Button) findViewById(R.id.editPetDetails);
        edit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(ViewPetDetails.this,EnterPetDetails.class).putExtra(Intent.EXTRA_TEXT,petDetails);
                        startActivity(intent);
                    }
                }
        );
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pet_details, menu);
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
