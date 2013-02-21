package com.example.nestedfragmentsanimationstest;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
		private FragmentA1 a1;
		private FragmentA2 a2;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			a1 = new FragmentA1();
			a2 = new FragmentA2();
			
			FragmentTransaction ft = 
					getChildFragmentManager()
						.beginTransaction();
			if(savedInstanceState == null)
				ft
					.add(android.R.id.list, a1, "tag1")
					.add(android.R.id.list, a2, "tag2");
			ft
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
		
		public void removeChildren(FragmentTransaction ft) {
			ft
				.setCustomAnimations(R.anim.zero_anim, R.anim.zero_anim, R.anim.zero_anim, R.anim.zero_anim)
				.remove(a1)
				.remove(a2);
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
			.replace(android.R.id.content, new FragmentA(), "fragment_a")
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
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction trans = fm
			.beginTransaction()
			.addToBackStack(null);
		
		if(fm.findFragmentByTag("fragment_a") != null &&
				fm.findFragmentByTag("fragment_a").isVisible())
			((FragmentA) fm.findFragmentByTag("fragment_a")).removeChildren(trans);
		
		trans
			.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, 
				R.anim.in_from_left, R.anim.out_to_right)
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
