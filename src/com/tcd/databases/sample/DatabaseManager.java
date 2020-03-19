package com.tcd.databases.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tcd.gui.SplashScreen;

//https://bitbucket.org/xerial/sqlite-jdbc/src

public class DatabaseManager {
	private static Connection connection = null;

	private static void executeInserts() throws ClassNotFoundException {
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");
		String line = "";
		// Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			statement.executeUpdate("drop table if exists spotify_decade");
			statement.executeUpdate(
					"create table spotify_decade (Number string, title string, artist string, genre string, year string, bpm string, energy string, dance string, loudness string, liveness string, valence string, duration string, acoustic string, speech string, popularity string)");
			// statement.executeUpdate("insert into person values(1, 'leo')");
			// statement.executeUpdate("insert into person values(2, 'yui')");

			String csvFile = System.getProperty("user.dir") + File.separator + "data\\2010-2019.csv";
			BufferedReader br = null;

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] row = line.split(",");
				String insertStatement = "insert into spotify_decade values(";
				for (int i = 0; i < row.length; i++) {
					insertStatement = insertStatement + '"' + row[i] + "\",";
				}
				insertStatement = insertStatement.substring(0, insertStatement.length() - 1) + ")";
				statement.executeUpdate(insertStatement);
			}
			br.close();
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void executeQuery(String query) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			// read the result set
			System.out.println("title = " + rs.getString("title"));
		}
		rs.close();
		statement.close();
	}

	public static void invokeWithSplash(String function, String splashImage, String splashText, String query)
			throws IOException, InterruptedException {
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
						executeInserts();
						break;
					case "EXECUTE_QUERY":
						executeQuery(query);
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
		System.out.println("Finished~");
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		invokeWithSplash("CREATE_DB", "musicloading_small.gif", "Creating DB....", "");
		invokeWithSplash("EXECUTE_QUERY", "musicloading_small.gif", "Running Query....",
				"select * from spotify_decade where Number=\"80\"");

	}

}
