package com.example.andorid.mypets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditVacRec extends AppCompatActivity {
    private List<String> vacData = new ArrayList<String>();

    SavePetDetails savePetDetails =new SavePetDetails();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vac_rec);
        Intent intent = getIntent();
        final EditText record = (EditText) findViewById(R.id.petEditVacIn);
        final String vacRecord = intent.getStringExtra(Intent.EXTRA_TEXT);
        vacData = Arrays.asList(vacRecord.split("\\s*,\\s*"));
        final Integer positonToChange = Integer.parseInt(vacData.get(0));
        final String name = vacData.get(1);
        Button update = (Button) findViewById(R.id.EditVac);
        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vacData.set(positonToChange+2,record.getText().toString());
                        String newVacRecord="";
                        for(int i =2; i<vacData.size();i++){
                            newVacRecord=newVacRecord+vacData.get(i)+",";
                        }
                        savePetDetails.saveData(name + "Vac", newVacRecord, false);// to overwirte old data

                        Intent intent = new Intent(EditVacRec.this,ViewPets.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_vac_rec, menu);
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
