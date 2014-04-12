package com.seniorproject.stocksign.kinveyconnection;

public interface KinveyConstants {

	static String RATIO_TABLE = "StockDataTable";
	
	static String TICKER_COLUMN = "Ticker";
	static String DIVSCORE_COLUMN = "Dividend_Score";
	static String GROWTHSCORE_COLUMN = "Growth_Score";
	static String TOTALSCORE_COLUMN = "Total_Score";
	
	static String SCORES_ARRAY = "scores";
	static String TICKER_ARRAY = "tickers";
	static String SCORES_BUNDLE = "scoreData";
	
	static String RATIO_BUNDLE = "ratioData";
	static String TICKER_SINGLE = "ticker";
	
	static String TOP_RATED_STOCKS_TITLE = "Top Rated Stocks";
	static String TOTAL_SCORE_STOCKS_TITLE = "Total Score";
	static String DIV_SCORE_STOCKS_TITLE = "Dividend Score";
	static String GROWTH_SCORE_STOCKS_TITLE = "Growth Score";
	
	int AUTOCOMPLETE_ROW_LIMIT = 5;
	int PRICE_DATA_SECTION_LIMIT = 100;
	int SCORES_SECTION_LIMIT = 20;
	
	float DIVSCORE_LIMIT = 90;
	float GROWTHSCORE_LIMIT = 70;
	float TOTALSCORE_LIMIT = 50;
}
