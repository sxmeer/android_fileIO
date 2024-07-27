package com.example.files;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    private static final String TAG = "MainActivity";
    private final String EXTENSION = ".txt";
    private final int WRITE_REQUEST_CODE = 400;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(permissions, WRITE_REQUEST_CODE);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    private void getSdCardName(){

        String currentTimeStamp = "" + System.currentTimeMillis();
        /*File[] Dirs = ContextCompat.getExternalFilesDirs(this.getApplicationContext(), null);
        File path = (Dirs[1]);
        String pathSD = Dirs[1].toString();
        int firstOpen = pathSD.indexOf("/");
        int secondOpen = pathSD.indexOf("/", firstOpen + 1);
        int thirdOpen = pathSD.indexOf("/", secondOpen + 1);
        String filename = pathSD.substring(firstOpen, thirdOpen + 1);
        path = new File(filename,currentTimeStamp + EXTENSION);
        Toast.makeText(getApplicationContext(),/*pathSD.split("/")[2]
        //filename,Toast.LENGTH_SHORT).show();

        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(currentTimeStamp);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "writeToSdCard: ");
        */
        /*String secStore = System.getenv("sdcard");
        Log.d(TAG, "getSdCardName: "+secStore);
        File f_secs = new File(secStore,"sameer12345.txt");
        try {
            FileWriter fileWriter = new FileWriter(f_secs);
            fileWriter.write(currentTimeStamp);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "writeToSdCard: ");
        */
    }

    private void writeToSdCard(){
        String currentTimeStamp = "" + System.currentTimeMillis();
        File createdFile = new File(Environment.getExternalStorageDirectory(), currentTimeStamp + EXTENSION);
        try {
            FileWriter fileWriter = new FileWriter(createdFile);
            fileWriter.write(currentTimeStamp);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "writeToSdCard: ");
    }

    private void writeToExternalDirectory() {
        String currentTimeStamp = "" + System.currentTimeMillis();
        if(Environment.isExternalStorageEmulated()){
            Toast.makeText(this, "sd card found", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "sdcard not found", Toast.LENGTH_SHORT).show();
        }
        File[] externalStorage = getApplicationContext().getExternalFilesDirs(null);
        File sdCard = externalStorage[1];
        File createdFile = new File(sdCard, currentTimeStamp + EXTENSION);
        try {
            FileWriter fileWriter = new FileWriter(createdFile);
            fileWriter.write(currentTimeStamp);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                writeToExternalDirectory();
                //writeToSdCard();
                //getSdCardName();
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_REQUEST_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Granted.
                }
                else{
                    //Denied.
                    button.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}

//getFilesDir getCacheDir stores in internal storage and cannot be seen
//getExternalFilesDir getExternalCacheDir also stores in internal storage but it can be seen
//getExternalStorageDir requires runtime permission for writing also stores in internal storage and can be seen
//getExternalFilesDirs returns list of internal/external storage as a file array and select one of it for storing cache and files
