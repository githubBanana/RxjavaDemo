package com.xs.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import rx.Observer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button mBtn = (Button) findViewById(R.id.btn_test);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        testThread();
    }

    private void testThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {


                File[] files = getFileDir().listFiles();
                List<File> list = Arrays.asList(files);
                for (File f :
                        list) {
                    if (f.getName().endsWith(".c")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "run: "+f.getName() );
                                Toast.makeText(MainActivity.this,f.getName(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        }).start();

    }

    private void testRxJava() {

        Observer<String> obsever = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

    }






    private File getFileDir() {
        File dir = getExternalCacheDir();
        if (!dir.exists()) {
            dir.mkdir();
        }
        String path = dir.getAbsolutePath() + File.separator + "test.c";
        String path2 = dir.getAbsolutePath() + File.separator + "test11.c";

        File file = new File(path);
        File file2 = new File(path2);

        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dir;
    }


}
