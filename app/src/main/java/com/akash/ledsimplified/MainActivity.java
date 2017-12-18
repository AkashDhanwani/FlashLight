package com.akash.ledsimplified;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    ToggleButton thSwitch;
    boolean hasFlash;
    boolean isFlashOn;
    private Camera camera;
    Camera.Parameters params;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Stage","We are in onCreate");

        thSwitch = (ToggleButton) findViewById(R.id.thSwitch);

        PackageManager pm = getApplicationContext().getPackageManager();
        hasFlash = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if(!hasFlash)
        {
            Toast.makeText(this,"No Flash",Toast.LENGTH_LONG).show();
            return;
        }//end of if

        thSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFlashOn)
                    turnOffFlash();
                else
                    turnOnFlash();

            }//end of onClick
        });//end of thSwitch

    }//end of onCreate

    void turnOnFlash()
    {
        if(!isFlashOn)
        {
            params = camera.getParameters();
            params.setFlashMode(params.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }//end of if
    }//end of turnOnFlash

    void turnOffFlash()
    {
        if(isFlashOn)
        {
            params = camera.getParameters();
            params.setFlashMode(params.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = false;
        }//end of if
    }//end of turnOffFlash

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Stage","We are in onStart");
        if(camera == null)
        {
            try {
                camera = Camera.open();
                turnOnFlash();
            }
            catch (RuntimeException e)
            {
                Toast.makeText(getApplicationContext(),"Camera Not Found",Toast.LENGTH_LONG).show();
            }
        }//end of if
    }//end of onStart

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Stage","We are in onResume");
        if(isFlashOn)
        turnOnFlash();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Stage","We are in onPause");
        if(isFlashOn)
        turnOnFlash();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Stage","We are in onStop");
        if(isFlashOn) {
            turnOnFlash();
        }
            //camera.release();
        //camera = null;
    }//end of onStop

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Stage","We are in onDestroy");
        camera.release();
        camera = null;
    }
}//end of class
