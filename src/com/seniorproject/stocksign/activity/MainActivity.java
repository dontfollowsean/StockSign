package com.seniorproject.stocksign.activity;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.R.id;
import com.seniorproject.stocksign.R.layout;
import com.seniorproject.stocksign.R.menu;
import com.seniorproject.stocksign.R.string;
import com.seniorproject.stocksign.database.CSVReader;
import com.seniorproject.stocksign.database.ConnectToKinveyTask;
import com.seniorproject.stocksign.database.DataEntry;
import com.seniorproject.stocksign.database.DownloadPriceDataTask;
import com.seniorproject.stocksign.database.DownloadRatioDataTask;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.database.StockDataSource;

import com.seniorproject.stocksign.debugging.Debugger;
import com.seniorproject.stocksign.fragment.DownloadImageTask;
import com.seniorproject.stocksign.fragment.DownloadMarketDataTask;
import com.seniorproject.stocksign.fragment.HomeSectionFragment;
import com.seniorproject.stocksign.fragment.MarketSectionFragment;


import com.seniorproject.stocksign.searching.SearchStockActivity;





import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.java.Query;

/**
 * First Activity to be displayed when application is run.
 * 
 * @author Sean Wilkinson
 * @since 1.0
 *
 */
public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	
	public static StockDataSource datasource;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	//public static StockDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);



		datasource = new StockDataSource(this);

		
		
		
		
		//new DownloadRatioDataTask().execute();
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	public void performSearch() {
		Intent doSearch = new Intent(MainActivity.this,SearchStockActivity.class);
		startActivity(doSearch);
		
		/*
		You can perform textual searches on fields using Regular Expressions. 
		This can be done with Query.regex. For example, to filter a table view
		by event name using a search bar:
			EditText searchBar = (EditText) findViewById(R.id.search_bar);
			Query query = new Query();
			query.regEx("name","searchText");
			AsyncAppData<EventEntity> searchedEvents = mKinveyClient.appData("events", EventEntity.class);
			searchedEvents.get(query, new KinveyListCallback<EventEntity>() {
  			@Override
  			public void onSuccess(EventEntity[] event) { ... }
			});
		 */
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		// Configure the search info and add any event listeners

	    // Get the SearchView and set the searchable configuration
	    //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    //SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	    // Assumes current activity is the searchable activity
	    //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    //searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
	    //searchView.setSubmitButtonEnabled(true);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	  
	  	Context context = getApplicationContext();
		CharSequence refreshtoast = "Refreshing...";
		int duration = Toast.LENGTH_SHORT;
	    // Handle item selection
		
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	            //open settings activity
	            return true;
	        case R.id.action_refresh:
	        	Toast.makeText(context, refreshtoast, duration).show();;
	            return true;
	        case R.id.action_search:
	        	performSearch();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/*@Override
	protected void onResume() {
		datasource.open();
	    super.onResume();
	}
	
	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
	*/
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment;
			switch (position){
			case 0:
				fragment = new HomeSectionFragment();
				break;
				
			case 1:
				fragment = new MarketSectionFragment();
				break;
				
			case 2:
				fragment = new HomeSectionFragment();
				break;
				
			default:
				fragment = new DummySectionFragment();
				break;
			}
			
			/*if(position == 0)
				fragment = new DummySectionFragment();
			else
				fragment = new HomeSectionFragment();*/
			Debugger.info("Position = ", Integer.toString(position));

			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_home).toUpperCase(l);
			case 1:
				return getString(R.string.title_markets).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section3).toUpperCase(l);
			case 4:
				return getString(R.string.title_section3).toUpperCase(l);
				
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText("Test Page #" + Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			
			
			return rootView;
		}
	}

}
