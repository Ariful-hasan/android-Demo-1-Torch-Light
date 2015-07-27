package com.example.hurrican;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	
	ImageButton btn_torch;
	Camera camera;
	boolean isLightOn=false;
	Parameters param;
	PackageManager manager;
	MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		onStop();
		btn_torch=(ImageButton)findViewById(R.id.imageButton1);
		manager=this.getPackageManager();
		
		// Detect this device has camera??????
		if(!manager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			Log.e("ERROR","The device has no Camera");
			return;
		}
		
		camera=Camera.open();
		param=camera.getParameters();
		
		btn_torch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isLightOn){
					Log.i("info", "torch is turn off");
					btn_torch.setImageResource(R.drawable.btn_switch_off);
					param.setFlashMode(Parameters.FLASH_MODE_OFF);
					//player=MediaPlayer.create(MainActivity.this, R.raw.light_switch_off);
					playSound();
					camera.setParameters(param);
					camera.stopPreview();
					isLightOn=false;
				}else{
					Log.i("info", "torch is turn on");
					btn_torch.setImageResource(R.drawable.btn_switch_on);
					param.setFlashMode(Parameters.FLASH_MODE_TORCH);
					playSound();
					camera.setParameters(param);
					camera.startPreview();
					isLightOn=true;
				}
				
			}
		});
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void playSound(){
        if(isLightOn){
            player = MediaPlayer.create(MainActivity.this, R.raw.light_switch_off);
        }else{
            player = MediaPlayer.create(MainActivity.this, R.raw.light_switch_on);
        }
        player.setOnCompletionListener(new OnCompletionListener() {
 
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        player.start();
    }
	@Override
	protected void onStop() {
		super.onStop();
		if(camera!=null){
			camera.release();
		}
	}

}
