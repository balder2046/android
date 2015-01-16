package com.example.ch3marker;

import edu.dhbw.andar.ARObject;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends AndARActivity {
	
	private ARObject someObject;
	private ARToolkit artoolkit;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		CustomRender render = new CustomRender();
		setNonARRenderer(render);
		try 
		{
			artoolkit = getArtoolkit();
			float [] ambientcolor = new float[]{0.0f,1.0f,0.0f,1.0f};
			float [] flash_shiny = new float[]{50.0f};
			someObject = new CustomObject("test","marker_at16.patt",80.0f,new double[]{0,0},ambientcolor,ambientcolor,ambientcolor,flash_shiny);
			artoolkit.registerARObject(someObject);
			ambientcolor = new float[]{1.0f,.0f,.0f,1.0f};
			someObject = new CustomObject("test","marker_peace16.patt",80.0f,new double[]{0,0},ambientcolor,ambientcolor,ambientcolor,flash_shiny);
			artoolkit.registerARObject(someObject);
			ambientcolor = new float[]{.0f,.0f,1.0f,1.0f};
			someObject = new CustomObject("test","marker_peace16.patt",80.0f,new double[]{0,0},ambientcolor,ambientcolor,ambientcolor,flash_shiny);
			artoolkit.registerARObject(someObject);
			ambientcolor = new float[]{1.0f,.0f,1.f,1.f};
			someObject = new CustomObject("test","marker_peace17.patt",80.0f,new double[]{0,0},ambientcolor,ambientcolor,ambientcolor,flash_shiny);
			artoolkit.registerARObject(someObject);
			
			
		}
		catch(AndARException exp)
		{
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		
	}

}
