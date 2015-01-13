package com.example.ardemo1;

import java.io.IOException;
import java.util.Locale;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
public class MainActivity extends Activity {

	SurfaceView cameraPreview;
	SurfaceHolder previewHolder;
	final static String TAG = "AR";
	final static String GPS_TAG = "ARGPS";
	Camera camera;
	boolean inPreview;
	boolean cameraReady;
	SensorManager sensorManager;
	SensorEventListener sensorListener;
	int orientationSensor;
	float headingAngle;
	float pitchAngle;
	float rollAngle;
	
	int accelerometerSensor;
	float xAxis;
	float yAxis;
	float zAxis;
	
	LocationManager locationManager;
	double latitude;
	double longitude;
	double altitude;
	LocationListener locationListener;
	
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
					//选取像素数最高的预览分辨率
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
			String text = String.format(Locale.US,"Find the best resolution (width:%d,height:%d)",result.width,result.height);
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
		Log.i(TAG,"Surface onCreate");
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
				Log.i(TAG,"callback surfaceChanged");
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
				Log.i(TAG,"callback surfaceCreated");
				try
				{
					camera.setPreviewDisplay(holder);
				}
				catch(IOException exp)
				{
					cameraReady = false;
					Log.e(TAG,"Exception in surfaceChanged! " + exp.getMessage());
				}
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				if (camera != null)
				{
					
					if (inPreview)
					{
						camera.stopPreview();
					}
					inPreview = false;
					camera.release();
					camera = null;
				}
				Log.i(TAG,"callback surfaceDestoryed");
			}
			
		};
		
		previewHolder.addCallback(surfaceCallback);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		// create about sensor
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		orientationSensor = Sensor.TYPE_ORIENTATION;
		accelerometerSensor = Sensor.TYPE_ACCELEROMETER;
		sensorListener = new SensorEventListener()
		{

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
				{
					headingAngle = event.values[0];
					pitchAngle = event.values[1];
					rollAngle = event.values[2];
					Log.i(TAG,"headingAngle: " + String.valueOf(headingAngle));
					Log.i(TAG,"pitchAngle: " + String.valueOf(pitchAngle));
					Log.i(TAG,"rollAngle: " + String.valueOf(rollAngle));
					
				}
				else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
				{
					xAxis = event.values[0];
					yAxis = event.values[1];
					zAxis = event.values[2];
					Log.i(TAG,"xAxis: " + String.valueOf(xAxis));
					Log.i(TAG,"yAxis: " + String.valueOf(yAxis));
					Log.i(TAG,"zAxis: " + String.valueOf(zAxis));
				}
			}
			
		};
		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		locationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				altitude = location.getAltitude();
				Log.d(GPS_TAG,"latitude: " + String.valueOf(latitude));
				Log.d(GPS_TAG,"longitude: " + String.valueOf(longitude));
				Log.d(GPS_TAG,"altitude: " + String.valueOf(altitude));
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i(TAG,"Surface onPause");
		if (camera != null)
		{
			if (inPreview)
			{
				camera.stopPreview();
			}
			inPreview = false;
			camera.release();
			camera = null;
		}
		if (sensorManager != null)
		{
			sensorManager.unregisterListener(sensorListener);
		}
		if (locationManager != null)
		{
			locationManager.removeUpdates(locationListener);
		}
		super.onPause();
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG,"Surface onResume");
		camera = Camera.open();
		if (camera == null)
		{
			Log.e(TAG,"Camera not ready");
			cameraReady = false;
		}
		else
		{
			cameraReady = true;
		}
		if (sensorManager != null)
		{
			sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(orientationSensor),SensorManager.SENSOR_DELAY_NORMAL);
			//register the listener for accelerometer.
			sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(accelerometerSensor),SensorManager.SENSOR_DELAY_NORMAL);
		}
		if (locationManager != null)
		{
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locationListener);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
