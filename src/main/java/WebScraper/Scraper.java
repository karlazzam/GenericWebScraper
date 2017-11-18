package WebScraper;

import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Scraper {

	private final String url;
	private final String getData;
	private List<String> tableRowsList = new ArrayList<String>();

	private Scraper(url urlLink) {
		this.url = urlLink.url;
		this.getData = urlLink.getData;
		this.tableRowsList = urlLink.tableRowsList;
	}

	public String getUrl() {
		return url;
	}
	
	public String getData() {
		return getData;
	}
	
	public List<String> getTableRows() {
		return tableRowsList;
	}

	@Override
	public String toString() {
		return getData;
	}
	
	/**
	 * Called the builder class url which is bad naming convention but makes it cleaner when building scraper object
	 */

	public static class url {
		private String url;
		private String getData;
		private List<String> tableRowsList = new ArrayList<String>();
		private Element element = null;
		private Elements elements;
		private Document doc;		

		public url(String url) {

			this.url = url;

			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
			}

			List<Comment> comments = findAllComments(doc);

			for (Comment comment : comments) {
				String data = comment.getData();
				comment.after(data);
				comment.remove();
			}
		}

		public url getElementById(String getElementById) {
			element = doc.getElementById(getElementById);
			getData = element.text();
			return this;
		}
		
		public url getElementByClass(String getElementByClass) {
			
			if(!Objects.isNull(element)) {
				elements = element.getElementsByClass(getElementByClass);
			}
			else {
				elements = doc.getElementsByClass(getElementByClass);
			}
			getData = elements.text();
			return this;
		}
		
		public url getTableRows() {
			elements = elements.select("tr");
			
			//start at 1 to skip table header
			for (int i = 1; i < elements.size(); i++) { 
				tableRowsList.add(elements.get(i).text());
			}
			return this;
		}

		public Scraper build() {
			return new Scraper(this);
		}

		/**
		 * Remove all comments from document (html page) so that no divs are hidden
		 */
		
		private List<Comment> findAllComments(Document doc) {
			List<Comment> comments = new ArrayList<Comment>();
			for (Element element : doc.getAllElements()) {
				for (Node n : element.childNodes()) {
					if (n.nodeName().equals("#comment")) {
						comments.add((Comment) n);
					}
				}
			}
			return Collections.unmodifiableList(comments);
		}
	}
}
