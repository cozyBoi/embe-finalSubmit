package com.example.androidex;

import com.example.androidex.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View.OnTouchListener;

public class MainActivity2 extends Activity{
	
	int xDelta;
	int yDelta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		
		
		//image1.setOnTouchListener(onTouchListener());
	}
	/*
	OnTouchListener onTouchListener(){
		return new OnTouchListener(){
			
		}
	}*/

}
