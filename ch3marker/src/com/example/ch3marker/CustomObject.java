package com.example.ch3marker;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

import edu.dhbw.andar.ARObject;
import edu.dhbw.andar.pub.SimpleBox;
import edu.dhbw.andar.util.GraphicsUtil;


public class CustomObject extends ARObject {
	private FloatBuffer mat_flash;
	private FloatBuffer mat_ambient;
	private FloatBuffer mat_flash_shiny;
	private FloatBuffer mat_diffuse;
	float[] diffuseColor;
	private SimpleBox box = new SimpleBox();
	
	public CustomObject(String name, String patternName, double markerWidth,
			double[] markerCenter,float[] ambient,float[] diffuse,float [] flash,float [] flash_shiny) {
		super(name, patternName, markerWidth, markerCenter);
		mat_flash = GraphicsUtil.makeFloatBuffer(flash);
		mat_ambient = GraphicsUtil.makeFloatBuffer(ambient);
		mat_diffuse = GraphicsUtil.makeFloatBuffer(diffuse);
		mat_flash_shiny = GraphicsUtil.makeFloatBuffer(flash_shiny);
		diffuseColor = diffuse;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GL10 arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public final void draw(GL10 gl)
	{
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SHININESS,mat_flash_shiny);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT,mat_ambient);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_DIFFUSE,mat_diffuse);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SPECULAR,mat_flash);
		gl.glTranslatef(.0f, .0f, 12.0f);
		gl.glColor4f(diffuseColor[0],diffuseColor[1],diffuseColor[2],diffuseColor[3]);
		box.draw(gl);
		
		
		
	}
}
