package com.xs.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<File> folders = new ArrayList<>();
        File file = new File("sddsdsd.jpg");
        File file2 = new File("dssdsd.jpg");
        folders.add(file);
        folders.add(file2);

        for (File folder :
                folders) {
            File[] f = folder.listFiles();
            for (File ff :
                    f) {
                Log.e(TAG, "onCreate: "+ff.getName());

            }
        }
    }
}
