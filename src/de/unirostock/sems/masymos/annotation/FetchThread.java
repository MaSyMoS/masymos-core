package de.unirostock.sems.masymos.annotation;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.apache.log4j.Logger;

import de.unirostock.sems.masymos.configuration.Property;
import de.unirostock.sems.masymos.database.Manager;
import de.unirostock.sems.masymos.database.traverse.DocumentTraverser;
import de.unirostock.sems.masymos.util.IndexText;

/**
*
* Copyright 2016 Ron Henkel (GPL v3)
* @author ronhenkel
*/
public class FetchThread extends Thread {
	
	final Logger logger = Logger.getLogger(FetchThread.class);

	private String url;
	private String uri;
	private long number;
	private GraphDatabaseService graphDB = Manager.instance().getDatabase();
	private Index<Node> annoFull = Manager.instance()
			.getAnnotationIndex();
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	private static final int timeout = 20 * 1000; // 20 seconds timeout

	public FetchThread(String uri, String url, long number) {
		super(url);
		this.url = url;
		this.uri = uri;
		this.number = number;
	}

	@Override
	public void run() {
		if (StringUtils.isBlank(url))
			return;
		String text = null;

		InputStream stream = null;

		try {
			
			URL connection, base, next;
			HttpURLConnection urlCon;
			String location;
			int times;
			HashMap<String,Integer> visited = new HashMap<>();
			
			
			while(true) {

				connection = new URL(url);
				urlCon = (HttpURLConnection) connection.openConnection();
				
				urlCon.setConnectTimeout(timeout);
				urlCon.setReadTimeout(timeout);
				
				urlCon.setInstanceFollowRedirects(false);
				
				// avoid redirecting-loop
				times = visited.compute(url, (key, count) -> count == null ? 1 : count + 1);
			    if (times > 3) {
			    	logger.info("Thread #" + number + ": Stuck in redirection loop. (Original URI: "+uri+")");
			    	break;
			    }
				
				if (urlCon.getResponseCode() == 301 || urlCon.getResponseCode() == 302){
					location = urlCon.getHeaderField("Location");
					base = new URL(url);
					next = new URL(base,location);
					url = next.toExternalForm();
					continue; 
				}else if (urlCon.getResponseCode() != 200) {
					location = uri;
					base = new URL(url);
					next = new URL(base,location);
					url = next.toExternalForm();
					continue;
				}
				break;
			}
			
			stream = urlCon.getInputStream();
			text = IOUtils.toString(stream);
			Document doc = Jsoup.parse(text);
			text = doc.text();
			
		} catch (MalformedURLException mue) {
			logger.info("Thread #" + number + ": Malformed URL: " + url);
			// do nothing
		} catch (IOException ioe) {
			// do nothing
			logger.error("Thread #" + number
					+ ": I/O error or timeout when reading URL: " + url);
		} finally {
			IOUtils.closeQuietly(stream);
		}

		if (StringUtils.isBlank(text)) return; 
		
		try {
			text = IndexText.shrinkTextToLuceneTermLength(text);
		} catch (Exception e1) {
			logger.error(e1.getMessage());
			return;
		}

		try (Transaction tx = graphDB.beginTx()) 
		{
			Node resource = annoFull.get(Property.General.URI,uri).getSingle();
			if (resource==null) {
				resource = DocumentTraverser.findSingleResourceNodeByURI(uri);
				if (resource==null) throw new NoSuchElementException("URI is not in the index nor in the database");
				annoFull.add(resource, Property.General.URI, uri);
				logger.warn("Thread #" + number + ": No node found in index, added " + uri);
			}
		
			annoFull.add(resource, Property.General.RESOURCETEXT, text);
			resource.setProperty(Property.General.IS_INDEXED, true);				

			tx.success();
			
		} catch (NoSuchElementException e) {
			logger.error("Thread #" + number + ": Thread " + url
					+ " FAILED - multiple entries with same URI: " + uri);
			logger.error(e.getMessage());
		} finally {
			logger.info("Thread #" + number + ": " + url
					+ " terminated at " + dateFormat.format(new Date()));
					
		}
	}

}
