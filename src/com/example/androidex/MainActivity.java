package com.example.androidex;
import com.example.androidex.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.animation.ValueAnimator;
import java.lang.Object;
import java.lang.Thread;
import java.util.Random;
import java.lang.Math;


@SuppressLint("NewApi")
public class MainActivity extends Activity{
	ImageView image1;
	ImageView image2;
	ImageView image3;
	ImageView image4;
	LinearLayout linear;
	int posX1 = 0;
	int posY1 = 0;
	int posX[];
	int posY[];
	int objnum = 0;
	int gravity = 0;
	int currY = 0;
	int velo = 0;
	
	public native int add();
	public native void testString(String str);

	@SuppressLint("NewApi")
	class BackThread2 extends Thread{
		boolean stop = false;
		ImageView image2;
		int acc = 0;
		int velocity = 0;
		int mynum;
		BackThread2(){}
		BackThread2(ImageView image, int objNum){
			image2 = image;
			mynum = objNum;
		}
		public void run(){
			//image2.offsetTopAndBottom(20);
			Random rand = new Random();
			posX[mynum] = 800;
			posY[mynum] = rand.nextInt(500);
			image2.setX(800);
			image2.setY(posY[mynum]);
			acc = rand.nextInt(6) + 1;
			while(!stop){
				velocity += acc;
				int h1 = image5.getHeight();
				int h2 = image2.getHeight();
				if((h1 + h2) / 2 > Math.abs(posY[mynum] - flyY) ){
					if(0 < posX[mynum] - flyX && posX[mynum] - flyX < 300){
						velocity = -20;
					}
				}
				MainActivity.this.runOnUiThread(new Runnable()
				{
					@Override
					public void run(){
						if(posX[mynum] >= 0){
							posX[mynum] -= velocity;
							image2.setX(posX[mynum]);
						}
						else{
							Random rand = new Random();
							posX[mynum] = 800;
							posY[mynum] = rand.nextInt(500);
							image2.setX(posX[mynum]);
							image2.setY(posY[mynum]);
							velocity = 0;
							arriveCnt++;
						}
					}

				});
				try{
					Thread.sleep(100);
				}
				catch(Exception e){

				}
			}
		}
	}


	@SuppressLint("NewApi")
	class BackThread extends Thread{
		boolean stop = false;
		ImageView image1;
		int acc = 3;
		BackThread(){}
		BackThread(ImageView image){
			image1 = image;
		}
		public void run(){

			while(!stop){
				MainActivity.this.runOnUiThread(new Runnable()
				{
					@Override
					public void run(){
						if(400 >= currY){
							currY += velo;
							image1.setY(currY);
						}
						else if(velo < 0){
							currY += velo;
							image1.setY(currY);
						}
					}

				});
				if(400 >= currY){
					velo += acc;
				}
				else if(velo < 0){
					velo += acc;
				}
				else{
					velo = 0;
				}
				try{
					Thread.sleep(100);
				}
				catch(Exception e){

				}
			}
		}
	}
	int arriveCnt = 0;
	BackThread myThread;
	BackThread2 myThread2;
	BackThread2 myThread3;
	BackThread2 myThread4;
	GameoverCheck gameoverTh;

	@SuppressLint("NewApi")
	class GameoverCheck extends Thread{
		GameoverCheck(){}
		public void run(){
			while(true){
				if(isOverlaped(0, image2) || isOverlaped(1, image3)
						|| isOverlaped(2, image4)){
					myThread.stop = true;
					myThread2.stop = true;
					myThread3.stop = true;
					myThread4.stop = true;
					stoptask = true;
					Intent intent=new Intent(MainActivity.this, MainActivity2.class);

					startActivity(intent);
					break;
				}
			}
		}
		boolean isOverlaped(int num, ImageView comp){
			int h1 = image1.getHeight();
			int h2 = comp.getHeight();
			if(posX[num] <= 10 && arriveCnt > 4){
				//if(h2/2 > Math.abs(currY - posY[num]) + 50){
				if((h1 + h2)/2 > Math.abs(currY - posY[num])){
					return true;
				}
			}

			return false;
		}
	}

	boolean stoptask = false;
	int flyAcc = 0;
	int flyVelo = 10;
	int flyX = 0, flyY = 0;
	@SuppressLint("NewApi")
	class FPGAinput extends Thread{
		FPGAinput(){}
		public void run(){
			while(!stoptask){
				int key = add();
				if(key == 4){
					flyVelo = 1;
				}
				else if(key == 1){
					flyY -= 10;
				}
				else if(key == 7){
					flyY += 10;
				}
				flyVelo += flyAcc;
				flyX += flyVelo;
				if(flyX > 700) flyX = 0;
				MainActivity.this.runOnUiThread(new Runnable()
				{
					@Override
					public void run(){
						image5.setY(flyY);
						image5.setX(flyX);
					}

				});
				try{
					Thread.sleep(100);
				}
				catch(Exception e){

				}
			}
		}
	}
	FPGAinput fpgaTh;
	ImageView image5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.loadLibrary("ndk-exam");
		
		linear = (LinearLayout)findViewById(R.id.container);
		image1 = (ImageView)findViewById(R.id.imageView1);
		image2 = (ImageView)findViewById(R.id.imageView2);
		image3 = (ImageView)findViewById(R.id.imageView3);
		image4 = (ImageView)findViewById(R.id.imageView4);
		image5 = (ImageView)findViewById(R.id.imageView5);
		Button btn=(Button)findViewById(R.id.newactivity);
		posX = new int[5];
		posY = new int[5];
		myThread = new BackThread(image1);
		myThread.start();
		myThread2 = new BackThread2(image2, objnum);
		objnum++;
		myThread2.start();
		myThread3 = new BackThread2(image3, objnum);
		objnum++;
		myThread3.start();
		myThread4 = new BackThread2(image4, objnum);
		objnum++;
		myThread4.start();

		OnClickListener listener=new OnClickListener(){
			public void onClick(View v){
				//Intent intent=new Intent(MainActivity.this, MainActivity2.class);
				//startActivity(intent);
				//posY -= 1;
				image1.offsetLeftAndRight(posX1);
				//image1.offsetTopAndBottom(posY);
				velo = -22;
			}
		};
		btn.setOnClickListener(listener);
		gameoverTh = new GameoverCheck();
		gameoverTh.start();
		fpgaTh = new FPGAinput();
		fpgaTh.start();
	}

}
