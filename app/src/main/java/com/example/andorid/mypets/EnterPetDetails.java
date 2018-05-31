package com.example.andorid.mypets;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnterPetDetails extends AppCompatActivity {
    final int REQUEST_TAKE_PHOTO = 1;
    Context context = this;// get the context of present activity

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                //Log.i("INFO","createImageFile");
                photoFile = FileIO.createImageFile(context);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("ERROR", "IOException occurred" + ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SavePetDetails savePetDetails = new SavePetDetails();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);
        List<String> details = new ArrayList<String>();

        final EditText petType = (EditText) findViewById(R.id.petTypeIn);
        final EditText petName = (EditText) findViewById(R.id.petNameIn);
        final EditText petBirthday = (EditText) findViewById(R.id.petBirthdayIn);
        final EditText petWeight = (EditText) findViewById(R.id.petWeightIn);
        final EditText petSex = (EditText) findViewById(R.id.petSexIn);
        final EditText petVaccine=(EditText) findViewById(R.id.petVaccineIn);

        //editing of pet details
        try{
            Intent intent = getIntent();
            String petDetails = intent.getStringExtra(intent.EXTRA_TEXT);
            details = Arrays.asList(petDetails.split("\\s*,\\s*"));
            petType.setText(details.get(0));
            petName.setText(details.get(1));
            petBirthday.setText(details.get(2));
            petWeight.setText(details.get(3));
            petSex.setText(details.get(4));

            Button saveButton = (Button) findViewById(R.id.save);
            saveButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String typNmeWgt = petType.getText().toString() + ","
                                    + petName.getText().toString() + ","
                                    + petBirthday.getText().toString() + ","
                                    + petWeight.getText().toString() + ","
                                    + petSex.getText().toString();
                            String nme = petName.getText().toString() + ",";
                            savePetDetails.saveData(nme.substring(0, nme.length() - 1), typNmeWgt);//overwrites if same filename
                            savePetDetails.clearForm((ViewGroup) findViewById(R.id.newPet));
                            Intent intent = new Intent(EnterPetDetails.this, ViewPetDetails.class).putExtra(Intent.EXTRA_TEXT,typNmeWgt);
                            startActivity(intent);
                        }
                    }
            );
        //entering new pet
        }catch (NullPointerException npe){
            Button saveButton = (Button) findViewById(R.id.save);
            saveButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String typNmeWgt = petType.getText().toString()+","
                                    +petName.getText().toString()+","
                                    +petBirthday.getText().toString()+","
                                    +petWeight.getText().toString()+","
                                    +petSex.getText().toString();
                            String nme = petName.getText().toString()+",";
                            savePetDetails.saveData("files",nme);
                            savePetDetails.saveData(nme.substring(0,nme.length()-1),typNmeWgt);//overwrites if same filename
                            savePetDetails.clearForm((ViewGroup) findViewById(R.id.newPet));
                        }
                    }
            );

            Button enterButton =(Button) findViewById(R.id.EnterVac);
            enterButton.setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            String fileName = petName.getText().toString()+"Vac";
                            savePetDetails.saveData(fileName, petVaccine.getText().toString()+",");
                            petVaccine.setText("");
                        }
                    }
            );
            Button picButton = (Button) findViewById(R.id.pic);
            picButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dispatchTakePictureIntent();

                        }
                    }
            );
        }

   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_pet, menu);
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
