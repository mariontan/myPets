package com.example.andorid.mypets;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by S410P on 5/22/2018.
 */
public class FileIO {
    public static boolean isStorageReady(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    public static String getStorage(){
        return Environment.getExternalStorageDirectory().toString()+"/MyPets/petDetails/";//saving to an external directory

    }
    //for string inputs
    public static void write(String filename, byte[] data, Boolean append){
        File fOutputDir = new File(getStorage());
        File fOutput =new File(getStorage(),filename+".txt");

        FileOutputStream fileOut = null;

        try{
            if(!fOutputDir.exists()){
                fOutputDir.mkdirs();
            }
            if(!fOutputDir.exists()){
                fOutputDir.createNewFile();
            }
            fileOut = new FileOutputStream(fOutput, append);//true for appending, false to overwrite file for the same fileName
            fileOut.write(data);
            fileOut.close();
        }catch(FileNotFoundException e){
            Log.e("ERROR", "File not found:" + fOutput.toString());
        }catch (Exception e){
            Log.e("ERROR","Exception occurred"+ e.getMessage());
        }
        return;
    }
    public static FileInputStream getFileInputStream(String filename){
        File fInput = new File(getStorage(),filename+".txt");
        /*Check if file exists*/
        if(!fInput.exists()){
            Log.i("INFO","file name does not exists");
            return null;
        }
        //Log.i("INFO", "Accessing file:"+ fInput.toString());
        //Log.i("INFO","         Exists"+fInput.exists());
        FileInputStream fileIn = null;
        try{
            fileIn = new FileInputStream(fInput);
        }catch (Exception e){
            Log.e("ERROR","Exception occurred"+e.getMessage());
        }
        return fileIn;
    }

    public static File createImageFile(String name,Context context) throws IOException {
        // Create an image file name
        String mCurrentPhotoPath;
        String timeStamp = new SimpleDateFormat("MMddyyyy_HHmmss").format(new Date());
        String imageFileName = name+"Pic_" + timeStamp + "_";
        Log.i("INFO",imageFileName);
        //File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);//making the picture only accessible to the app itself
        //Log.i("INFO",Environment.DIRECTORY_PICTURES);
        File storageDir = new File(getStorage());// saving to the same directory as the text files
        /*File image = File.createTempFile(
                imageFileName,  /* prefix */
        //        ".jpg",         /* suffix */
        //        storageDir      /* directory */
        //);*/
        File image = new File(storageDir,imageFileName+".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        //Log.i("INFO","folder: "+mCurrentPhotoPath);
        return image;
    }
}
