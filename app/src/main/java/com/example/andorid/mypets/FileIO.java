package com.example.andorid.mypets;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by S410P on 5/22/2018.
 */
public class FileIO {
    public static boolean isStorageReady(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    public static String getStorage(){
        return Environment.getExternalStorageDirectory().toString()+"/MyPets/petDetails/";
    }

    public static void write(String filename, byte[] data){
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
            if(filename.equals("files")||filename.substring(filename.length()-3).equals("Vac")){
                fileOut = new FileOutputStream(fOutput, true);//true for appending
                fileOut.write(data);
                fileOut.close();
            }
            else {
                fileOut = new FileOutputStream(fOutput, false);//false is no append
                fileOut.write(data);
                fileOut.close();
            }
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


    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String mCurrentPhotoPath;
        String timeStamp = "today";
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        //Log.i("INFO","folder: "+mCurrentPhotoPath);
        return image;
    }
}
