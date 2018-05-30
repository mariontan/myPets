package com.example.andorid.mypets;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.BufferedInputStream;

/**
 * Created by S410P on 5/22/2018.
 */
public class SavePetDetails extends Application {
    /**private String petName = "";
    private String petType = "";
    private String petWeight = "";
    private String petSex = "";
    private String petBirthday = "";*/

    public SavePetDetails(){}

    /**public String getPetName(){return petName;}
    public String getPetType(){return petType;}
    public String getPetWeight(){return petWeight;}
    public String getPetSex(){return petSex;}
    public String getPetBirthday(){return petBirthday;}

    public void setPetName(String petName){this.petName=petName;}
    public void setPetType(String petType){this.petType=petType;}
    public void setPetWeight(String petWeight){this.petWeight=petWeight;}
    public void setPetBirthday(String petBirthday) {this.petBirthday = petBirthday;}
    public void setPetSex(String petSex) {this.petSex = petSex;}*/

    public void saveData(String filename,String petData){
        if(FileIO.isStorageReady()){
            Log.i("INFO", "saving pet data");
            FileIO.write(filename,petData.getBytes());
            Log.i("INFO", "pet data saved");
        }
    }
    //converts data from storage to a string
    public String getData(String filename){
        try{
            if(FileIO.isStorageReady()){
                //Log.i("INFO","Reading file...");
                BufferedInputStream is = new BufferedInputStream(FileIO.getFileInputStream(filename));
                String dataStr = "";
                int cInp = 0;
                while(is.available()>0){
                    cInp = is.read();
                    dataStr += (char)(cInp);
                }
                is.close();
                //Log.i("INFO", "Reading done: " + dataStr);
                return dataStr;
            }
        }catch(Exception e){
            Log.e("ERROR","Exception occurred"+e.getMessage());
        }
        return"";
    }
    // to clear a all the edit text of a view
    public void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }
            // if layout has subclass
            /*if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup) findViewById(resource)); */
        }
    }
}
