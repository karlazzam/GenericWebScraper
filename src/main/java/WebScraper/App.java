package WebScraper;

public class App 
{
	
	/**
	 * Example using the scrape builder
	 */
    public static void main( String[] args )
    {
    	Scraper scrape = new Scraper
    			.url("https://www.basketball-reference.com/teams/BOS/2018.html")
    			.getElementById("content") //gets div id = content
    			.getElementById("all_per_game") //gets div id = all_per_game which is inside div id = content
    			.getElementByClass("sortable stats_table") //gets div class = sortable stats_table which is inside div id = all_per_game
    			.getTableRows() //gets table rows under this div class
    			.build();
    	
    	System.out.println(scrape.toString()); //gets deepest div you scraped
    	System.out.println("--------");
    	System.out.println(scrape.getData()); //same as toString()
    	System.out.println("--------");
    	System.out.println(scrape.getTableRows()); //gets table rows as list of strings  
    }
}
