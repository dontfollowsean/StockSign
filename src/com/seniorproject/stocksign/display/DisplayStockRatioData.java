package com.seniorproject.stocksign.display;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.seniorproject.stocksign.R;

public class DisplayStockRatioData extends Activity {

	TextView stockName;
	TextView stockTicker;
	TextView stockCountry;
	TextView stockSector;
	TextView stockIndustry;
	TableLayout ratioTable;
	// Stock currentStock;
	String[] ratioDataArray;
	String[] ratioDataNames;
	int callingActivityID;

	public void initialize(Intent i) {
		stockName = (TextView) findViewById(R.id.tvStockname);
		stockTicker = (TextView) findViewById(R.id.tvStockticker);
		stockCountry = (TextView) findViewById(R.id.tvStockCountryValue);
		stockSector = (TextView) findViewById(R.id.tvStockSectorValue);
		stockIndustry = (TextView) findViewById(R.id.tvStockIndustryValue);
		ratioTable = (TableLayout) findViewById(R.id.tlStockratios);
		// get data
		Bundle b = i.getBundleExtra("ratioData");
		ratioDataArray = b.getStringArray("values");
		ratioDataNames = b.getStringArray("keySet");
		callingActivityID = i.getIntExtra("activityID", 0);
	}

	/*
	 * public void createRatioDataArray(Stock s) { ratioDataArray = new
	 * String[RatioConstants.totalRatios]; ratioDataArray[RatioConstants.ticker]
	 * = s.getTicker(); ratioDataArray[RatioConstants.company] = s.getCompany();
	 * ratioDataArray[RatioConstants.sector] = s.getSector();
	 * ratioDataArray[RatioConstants.industry] = s.getIndustry();
	 * ratioDataArray[RatioConstants.country] = s.getCountry();
	 * ratioDataArray[RatioConstants.PE] = s.getPe();
	 * ratioDataArray[RatioConstants.FPE] = s.getForward_pe();
	 * ratioDataArray[RatioConstants.PEG] = s.getPeg();
	 * ratioDataArray[RatioConstants.PS] = s.getPs();
	 * ratioDataArray[RatioConstants.PB] = s.getPb();
	 * ratioDataArray[RatioConstants.PC] = s.getPc();
	 * ratioDataArray[RatioConstants.PFCF] = s.getPriceFreeCashFlow();
	 * ratioDataArray[RatioConstants.dYield] = s.getDividendYield();
	 * ratioDataArray[RatioConstants.PO] = s.getPayoutRatio();
	 * ratioDataArray[RatioConstants.EPSTHISYR] = s.getEpsgThisYear();
	 * ratioDataArray[RatioConstants.EPSNEXTYEAR] = "Missing";
	 * ratioDataArray[RatioConstants.EPS5] = s.getEpsgPast5Years();
	 * ratioDataArray[RatioConstants.EG] = s.getEpsgNext5Years();
	 * ratioDataArray[RatioConstants.SALES5] = s.getSalesgPast5Years();
	 * ratioDataArray[RatioConstants.FLOAT] = "Missing";
	 * ratioDataArray[RatioConstants.INSIOWN] = s.getInsiderOwnership();
	 * ratioDataArray[RatioConstants.INSITRANS] = "Missing";
	 * ratioDataArray[RatioConstants.INSTOWN] = "Missing";
	 * ratioDataArray[RatioConstants.INSTTRANS] =
	 * s.getInstitutionalTransactions(); ratioDataArray[RatioConstants.SHORT] =
	 * s.getFloatShort(); ratioDataArray[RatioConstants.ROA] =
	 * s.getReturnOnAssets(); ratioDataArray[RatioConstants.ROE] =
	 * s.getReturnOnEquity(); ratioDataArray[RatioConstants.CR] =
	 * s.getCurrentRatio(); ratioDataArray[RatioConstants.QR] =
	 * s.getQuickRatio(); ratioDataArray[RatioConstants.LTDE] =
	 * s.getLtDebtEquity(); ratioDataArray[RatioConstants.PM] = "Missing";
	 * ratioDataArray[RatioConstants.BETA] = "Missing";
	 * ratioDataArray[RatioConstants.WVOL] = "Missing";
	 * ratioDataArray[RatioConstants.WVOL] = "Missing";
	 * ratioDataArray[RatioConstants.RSI] = s.getRsi();
	 * ratioDataArray[RatioConstants.AVGVOL] = "Missing";
	 * ratioDataArray[RatioConstants.RVOL] = "Missing";
	 * ratioDataArray[RatioConstants.TOTALSCORE] = "Missing";
	 * ratioDataArray[RatioConstants.DIVSCORE] = "Missing";
	 * ratioDataArray[RatioConstants.GROWTHSCORE] = "Missing"; }
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaystockdata);
		initialize(getIntent());
		// I can also get the calling activity ID but its unnecessary at this
		// time
		displayRatioData();
	}

	public void displayRatioData() {

		stockName.setText(ratioDataArray[0].substring(1));
		stockTicker.setText(ratioDataArray[36]);
		stockCountry.setText(ratioDataArray[1]);
		stockSector.setText(ratioDataArray[34]);
		stockIndustry.setText(ratioDataArray[12]);

		int[] alternatingRGBColor = new int[3];
		int alternator = 0;

		// 36 needs to be changed to a variable as soon as we update Kinvey

		// Go through each item in the array
		for (int current = 2; current < 36; current++) {
			// display all ratios besides Sector and Industry
			if (current != 12 && current != 34) {

				if (alternator == 0) {
					alternatingRGBColor[0] = 209;// red
					alternatingRGBColor[1] = 209;// green
					alternatingRGBColor[2] = 209;// blue
					alternator = 1;
				} else {
					alternatingRGBColor[0] = 232;// red
					alternatingRGBColor[1] = 232;// green
					alternatingRGBColor[2] = 232;// blue
					alternator = 0;
				}

				// Create a TableRow and give it an ID
				TableRow tr = new TableRow(this);
				tr.setId(100 + current);
				tr.setBackgroundColor(Color.rgb(alternatingRGBColor[0],
						alternatingRGBColor[1], alternatingRGBColor[2]));
				tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT));

				// Create a TextView to hold the label of the ratio
				TextView labelTV = new TextView(this);
				labelTV.setId(200 + current);
				labelTV.setText(ratioDataNames[current]);
				labelTV.setTextColor(Color.BLACK);
				labelTV.setTextSize(15);
				labelTV.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT));
				tr.addView(labelTV);

				// Create a TextView to hold the value of the ratio
				TextView valueTV = new TextView(this);
				valueTV.setId(300 + current);
				valueTV.setText(ratioDataArray[current]);
				valueTV.setTextColor(Color.BLACK);
				valueTV.setTextSize(15);
				valueTV.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT));
				tr.addView(valueTV);

				// Add the TableRow to the TableLayout
				ratioTable.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			}
		}
	}

}
