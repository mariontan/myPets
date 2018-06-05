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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ViewPetDetails extends AppCompatActivity {
    private List<String> petVac = new ArrayList<String>();
    private List<Integer> monthDays = new ArrayList<Integer>(Arrays.asList(31,28,31,30,31,30,31,31,30,31,30,31));
    SavePetDetails savePetDetails = new SavePetDetails();

    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<String> details = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet_details);
        Intent intent = getIntent();
        final String petDetails = intent.getStringExtra(Intent.EXTRA_TEXT);
        String currentDay = new SimpleDateFormat("MMddyyyy").format(new Date());
        int curYear,curMonth,curDay,birthYear,birthMonth,birthDay,ageYear,ageMonth,ageDay,numDays = 0;

        details = Arrays.asList(petDetails.split("\\s*,\\s*"));

        TextView petType =(TextView) findViewById(R.id.petTypeIndividual);
        TextView petName = (TextView) findViewById(R.id.petNameIndividual);
        TextView petBirthday = (TextView) findViewById(R.id.petBirthdayIndividual);
        TextView petWeight = (TextView) findViewById(R.id.petWeightIndividual);
        TextView petSex = (TextView) findViewById(R.id.petSexIndividual);
        TextView petAge = (TextView) findViewById(R.id.petAgeIndividual);

        //getting pet age
        curYear = Integer.parseInt(currentDay.substring(4));
        birthYear = Integer.parseInt(details.get(2).substring(4));
        curMonth = Integer.parseInt(currentDay.substring(0, 2));
        birthMonth = Integer.parseInt(details.get(2).substring(0, 2));
        curDay = Integer.parseInt(currentDay.substring(2, 4));
        birthDay = Integer.parseInt(details.get(2).substring(2,4));

        //get age in years
        if(birthMonth > curMonth){
            if(curYear == birthYear ){
                ageYear = 0;
            }
            else {
                ageYear = curYear - birthYear - 1;
            }
        }
        else{
            ageYear = curYear - birthYear;
        }
        //get days
        if(birthMonth < curMonth){
            for(int i = birthMonth; i<curMonth-2;i++  ){//because lists index starts at 0
                numDays = numDays + monthDays.get((i));
            }
            ageDay = monthDays.get(birthMonth-1)-birthDay+numDays+curDay;
        }
        else{
            int j = 0;

            for(int i = birthMonth; i<curMonth+11; i++){//because lists index starts at 0
                Log.i("INFO",Integer.toString(i+j));
                numDays = numDays + monthDays.get(i+j);
                //Log.i("INFO", monthDays.get(i + j).toString());
                if(i==11){
                    j = -12;
                }
            }
            ageDay = monthDays.get(birthMonth - 1) - birthDay + numDays + curDay;
        }

        petType.setText("pet type: " + details.get(0));
        petName.setText("pet name: " + details.get(1));
        petBirthday.setText("Birthday: " + details.get(2));
        petWeight.setText("Weight: " + details.get(3));
        petSex.setText("Sex: "+ details.get(4));
        petAge.setText("Age: " + ageYear+"Years "+ ageDay + "Days" );

        final String vaccines = savePetDetails.getData(details.get(1)+"Vac");
        final String name = details.get(1);
        //Log.i("INFO",petName.getText().toString());
        //Log.i("INFO",vaccines);
        petVac = Arrays.asList(vaccines.split("\\s*,\\s*"));
        listAdapter = new  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, petVac);
        ListView listview = (ListView) findViewById(R.id.petVacList);
        listview.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        listview.setOnItemClickListener(//edit listview Item
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String vac = Integer.toString(position)+","+ name +","+vaccines ;
                        Intent editVac = new Intent(ViewPetDetails.this,EditVacRec.class).putExtra(Intent.EXTRA_TEXT,vac);
                        startActivity(editVac);
                        finish();
                    }
                }
        );

        Button edit =(Button) findViewById(R.id.editPetDetails);
        edit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(ViewPetDetails.this, EnterPetDetails.class).putExtra(Intent.EXTRA_TEXT, petDetails);
                        startActivity(intent);
                    }
                }
        );

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
