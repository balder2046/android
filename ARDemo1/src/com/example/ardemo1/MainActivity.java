package com.example.ardemo1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
public class MainActivity extends Activity {

	SurfaceView cameraPreview;
	SurfaceHolder previewHolder;
	static String TAG = "AR";
	Camera camera;
	boolean inPreview;
	private Camera.Size getBestPreviewSize(int width,int height,Camera.Parameters params)
	{
		Camera.Size result = null;
		int areasize = 0;
		for (Camera.Size size : params.getSupportedPreviewSizes())
		{
			if (width > size.width && height > size.height)
			{
				if (result == null)
				{
					result = size;
					areasize = size.width * size.height;
				}
				else {
					if (areasize < size.width * size.height)
					{
						result = size;
						areasize = size.width * size.height;
					}
				}
			}
		}
		if (result != null) 
		{
			String text = String.format("Find the best resolution (width:{0},height:{1})",width,height);
			Log.i(TAG,text);
		}	
		else
		{
			Log.i(TAG,"Not found resolution at width:" + width + " height: " + height);
		}
		
		return result;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inPreview = false;
		cameraPreview = (SurfaceView)findViewById(R.id.cameraPreview);
		previewHolder = cameraPreview.getHolder();
		SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback()
		{

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub
				Camera.Parameters parameters = camera.getParameters();
				Camera.Size size = getBestPreviewSize(width,height,parameters);
				if (size != null)
				{
					parameters.setPreviewSize(size.width, size.height);
					camera.setParameters(parameters);
					camera.startPreview();
					inPreview = true;
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				try
				{
					camera.setPreviewDisplay(holder);
				}
				catch(Throwable t)
				{
					Log.e(TAG,"Exception in surfaceChanged!");
				}
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		previewHolder.addCallback(surfaceCallback);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
				
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (inPreview)
		{
			camera.stopPreview();
		}
		inPreview = false;
		camera.release();
		camera = null;
		super.onPause();
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		camera = Camera.open();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
