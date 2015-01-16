package com.example.ch3marker;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import edu.dhbw.andar.interfaces.OpenGLRenderer;
import edu.dhbw.andar.util.GraphicsUtil;
public class CustomRender implements OpenGLRenderer{
	private float[] ambientlight1 = {.3f,.3f,.3f,1.f};
	private float[] diffuselight1 = {.7f,.7f,.7f,1.0f};
	private float[] specularlight1 = {0.6f,0.6f,0.6f,1.0f};
	private float[] lightposition1 = {20.0f,-40.0f,100.0f,1.0f};
	
	private FloatBuffer lightPositionBuffer1 = GraphicsUtil.makeFloatBuffer(lightposition1);
	private FloatBuffer specularLightBuffer1 = GraphicsUtil.makeFloatBuffer(specularlight1);
	private FloatBuffer diffuseLightBuffer1 = GraphicsUtil.makeFloatBuffer(diffuselight1);
	private FloatBuffer ambientLightBuffer1 = GraphicsUtil.makeFloatBuffer(ambientlight1);
	@Override
	public void draw(GL10 arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initGL(GL10 gl) {
		//disable color material
		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		
		//lightting
		gl.glEnable(GL10.GL_LIGHTING);
		
		//cullface
		gl.glEnable(GL10.GL_CULL_FACE);
		//depthtest
		gl.glEnable(GL10.GL_DEPTH_TEST);
		//normalize
		gl.glEnable(GL10.GL_NORMALIZE);
		
		
	}

	@Override
	public void setupEnv(GL10 gl) {
		//enable lighting
		gl.glEnable(GL10.GL_LIGHTING);
		//setup the light1 property
		gl.glLightfv(GL10.GL_LIGHT0,GL10.GL_AMBIENT,ambientLightBuffer1);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseLightBuffer1);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularLightBuffer1);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPositionBuffer1);
		//enable light1
		gl.glEnable(GL10.GL_LIGHT0);
		//disable texcoordarray
		gl.glDisable(GL10.GL_TEXTURE_COORD_ARRAY);
		//disable texture
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		initGL(gl);
	}
	

}
