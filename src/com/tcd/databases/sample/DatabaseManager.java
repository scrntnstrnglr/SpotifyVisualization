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
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;
import com.tcd.gui.SplashScreen;
import com.tcd.spotify.VisualizerSettings;
import com.tcd.utils.SplashProvider;

//https://bitbucket.org/xerial/sqlite-jdbc/src

public class DatabaseManager {
	private static Connection connection = null;
	private static Logger logger;
	private static Statement statement;
	private static ResultSet rs;
	
	public DatabaseManager() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
	}
	
	public Connection getConnection() {
		return connection;
	}


	public void executeInserts(String csvFile, String tableName, String query) throws ClassNotFoundException {
		String line = "";
		try {
			statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			statement.executeUpdate("drop table if exists " +tableName);
			//statement.executeUpdate(
			//		"create table spotify_decade (Number string, title string, artist string, genre string, year string, bpm string, energy string, dance string, loudness string, liveness string, valence string, duration string, acoustic string, speech string, popularity string)");
			statement.executeUpdate(query);
			//String csvFile = System.getProperty("user.dir") + File.separator + "data\\2010-2019.csv";
			BufferedReader br = null;

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] row = line.split(",");
				String insertStatement = "insert into "+tableName+" values(";
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

	public ResultSet executeQuery(String query) throws SQLException {
		statement = connection.createStatement();
		rs = statement.executeQuery(query);
		return rs;
	}

	public void closeConnection() throws SQLException {
		statement.close();
		rs.close();
		connection.close();
		
	}
	

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {
		
		
		HashMap<String,Object> inputs = new HashMap<String,Object>();
		inputs.put("tableName", VisualizerSettings.table_name);
		inputs.put("dataSet", VisualizerSettings.dataSet);
		inputs.put("insertQuery", VisualizerSettings.query);
		SplashProvider.invokeWithSplash("CREATE_DB", inputs,"musicloading_small.gif", "Creating DB....", "");
		SplashProvider.invokeWithSplash("EXECUTE_QUERY", inputs,"musicloading_small.gif", "Running Query....","select * from spotify_decade where Number=\"80\"");
		
		//DatabaseManager dbManager = new DatabaseManager();
		//dbManager.executeInserts(VisualizerSettings.dataSet, VisualizerSettings.table_name, VisualizerSettings.query);
		/*
		String selectQuery = "select * from spotify_decade where rowid<=3";
		ResultSet rs = dbManager.executeQuery(selectQuery);
		while(rs.next()) {
			System.out.println(rs.getString("spch"));
		} 
		
		String maxQuery = "select max(bpm) m from spotify_decade";
		ResultSet rs = dbManager.executeQuery(maxQuery);
		while(rs.next()) {
			System.out.println(rs.getString("m"));
		}
		dbManager.closeConnection(); */
	}

}


