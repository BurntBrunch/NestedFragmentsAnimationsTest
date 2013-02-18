package com.example.nestedfragmentsanimationstest;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity {
	
	private static Random random = new Random(System.currentTimeMillis());
	
	public static class FragmentA1 extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = new View(container.getContext());
			v.setBackgroundColor(Color.MAGENTA);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			params.weight = 1;
			v.setLayoutParams(params);
			return v;
		}
	}
	public static class FragmentA2 extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = new View(container.getContext());
			v.setBackgroundColor(Color.BLACK);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			params.weight = 1;
			v.setLayoutParams(params);
			
			return v;
		}
	}
	
	public static class FragmentA extends Fragment{
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			getChildFragmentManager()
				.beginTransaction()
				.add(android.R.id.list, new FragmentA1())
				.add(android.R.id.list, new FragmentA2())
				.commit();
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			LinearLayout layout = new LinearLayout(container.getContext());
			layout.setBackgroundColor(Color.RED);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setId(android.R.id.list);
			
			return layout;
		}
	}
	
	
	public static class FragmentB extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = new View(container.getContext());
			v.setBackgroundColor(random.nextInt());
			
			return v;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getSupportFragmentManager().beginTransaction()
			.replace(android.R.id.content, new FragmentA())
			.commit();
		
		FragmentManager.enableDebugLogging(true);
		
		findViewById(android.R.id.content).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeFragment();
			}
		});
	}
	
	private void changeFragment() {
		getSupportFragmentManager()
			.beginTransaction()
			.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, 
					R.anim.in_from_left, R.anim.out_to_right)
			.addToBackStack(null)
			.replace(android.R.id.content, new FragmentB())
			.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
