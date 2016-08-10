package com.xs.rxjavademo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
//                glideTest();
                testRxJava();

            }
        });

//        testThread();

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

       /* Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("nihao");
                subscriber.onCompleted();
            }
        });
        Subscription subscription = observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this,"onCompleted",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(MainActivity.this,"s",Toast.LENGTH_SHORT).show();
            }
        });*/


        String[] array = {"111","222"};
        Subscription subscription1 = Observable.from(array).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .map(new Func1<String, File>() {
                    @Override
                    public File call(String s) {
                        return new File(s);
                    }
                }).subscribe(new Subscriber<File>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: " );
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(File file) {

                        Log.e(TAG, "onNext: "+file.getAbsolutePath() + file.exists());
                    }
                });

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


    private void glideTest() {
        final ImageView mIv = (ImageView) findViewById(R.id.iv);
        Glide.with(mIv.getContext())
                .load(Uri.parse("http://nuuneoi.com/uploads/source/playstore/cover.jpg"))
//                .load("http://nuuneoi.com/uploads/source/playstore/cover.jpg")
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher) //占位图
                .error(android.R.drawable.ic_dialog_alert)
                .centerCrop()
                .into(new BitmapImageViewTarget(mIv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                         RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                        roundedBitmapDrawable.setCircular(false);
                        getView().setImageDrawable(roundedBitmapDrawable);
                    }
                });

        final ImageView mIv2 = (ImageView) findViewById(R.id.iv2);
        new Thread(new Runnable() {
            Bitmap bitmap = null;
            @Override
            public void run() {
                try {
                     bitmap = Glide.with(mIv2.getContext())
                            .load(Uri.parse("http://nuuneoi.com/uploads/source/playstore/cover.jpg"))
                            .asBitmap()
                             .centerCrop()
                             .into(300,300)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mIv2.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    }


