package com.tcd.utils;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tcd.databases.DTable;
import com.tcd.databases.sample.DatabaseManager;
import com.tcd.gui.SplashScreen;

public class SplashProvider {

	public static DatabaseManager dbManager = null;
	private static Object output = null;
	private static Logger logger;

	public synchronized static Object invokeWithSplash(String function, HashMap<String, Object> inputs, String splashImage,
			String splashText, String query) throws IOException, InterruptedException {
		PropertyConfigurator.configure("properties" + File.separator + "log4j.properties");
		SplashScreen splash = new SplashScreen("img" + File.separator + "loading_gifs" + File.separator + splashImage,
				splashText);

		Thread thread1 = new Thread() {
			public void run() {
				splash.setVisible(true);
			}

			@Override
			public void interrupt() {
				// TODO Auto-generated method stub
				super.interrupt();
				splash.dispose();
			}
		};
		Thread thread2 = new Thread() {
			public void run() {
				try {
					switch (function) {
					case "CREATE_DB":
						dbManager = new DatabaseManager();
						String table_name = inputs.get("tableName").toString();
						String insertQuery = inputs.get("insertQuery").toString();
						String dataSet = inputs.get("dataSet").toString();
						dbManager.executeInserts(dataSet, table_name, insertQuery);
						break;
					case "EXECUTE_QUERY":
						if (dbManager != null) {
							//System.out.println(query);
							output = new DTable(dbManager.executeQuery(query),query);}
						else
							logger.error("Database Connection not established!!");
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		thread1.start();
		thread2.start();
		thread2.join();
		thread1.interrupt();
		//System.out.println("Finished~");
		return output;
	}

	/*
	 * public static void invokeWithSplash(String function, String splashImage,
	 * String splashText, String query) throws IOException, InterruptedException {
	 * SplashScreen splash = new SplashScreen("img" + File.separator +
	 * "loading_gifs" + File.separator + splashImage, splashText);
	 * 
	 * Thread thread1 = new Thread() { public void run() { splash.setVisible(true);
	 * }
	 * 
	 * @Override public void interrupt() { // TODO Auto-generated method stub
	 * super.interrupt(); splash.dispose(); } }; Thread thread2 = new Thread() {
	 * public void run() { try { switch (function) { case "CREATE_DB":
	 * DatabaseManager.executeInserts(); break; case "EXECUTE_QUERY":
	 * DatabaseManager.executeQuery(query); } } catch (ClassNotFoundException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } catch (SQLException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } } };
	 * thread1.start(); thread2.start(); thread2.join(); thread1.interrupt();
	 * System.out.println("Finished~");
	 * 
	 * }
	 */
}
