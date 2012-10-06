package com.inkoniq.croptool;

import com.inkoniq.croptool.viewobjects.CroppingTool;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class CropToolActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((CroppingTool)findViewById(R.id.crop_tool)).setmTargetImage(BitmapFactory.decodeResource(getResources(), R.drawable.arrahman));
    }
}