package com.dhakre.rohit;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	static DB db = new DB();

	public static void main(String[] args) throws SQLException, IOException {
		db.runExecute("truncate crawlerRecord;");
		new Main().processPage("http://www.myntra.com");
	}

	public void processPage(String url) throws SQLException, IOException {
		String[] support = { "http", "https" };
		UrlValidator validUrl = new UrlValidator(support);
		if (validUrl.isValid(url)) {
			// check if the given URL is already in database
			String sql = "select * from crawlerRecord where url = '" + url + "';";
			ResultSet rs = db.runExecuteQuery(sql);
			if (rs.next()) {
			} else {
				// store the URL to database to avoid parsing again
				sql = "insert into crawlerRecord (url) values ('" + url + "');";
				db.runExecute(sql);
				// get useful Information
				Document doc = Jsoup.connect(url).get();
				if (doc.text().contains("jwellery")) {
					System.out.println(url);
				}
				Elements e = doc.select("a[href]");
				for (Element link : e) {
					if (link.attr("href").contains("myntra.com")) {
						processPage(link.attr("abs:href"));
					}
				}
			}
		}
	}
}
