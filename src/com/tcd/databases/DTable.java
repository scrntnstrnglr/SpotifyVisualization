package com.tcd.databases;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import com.tcd.spotify.VisualizerSettings;
import com.tcd.utils.SplashProvider;

public class DTable {

	private static int rowCount, coloumnCount;
	private static ResultSet resultSet;
	private static ResultSetMetaData resultSetMetaData;
	private static int row = 0;
	private static HashMap<String, LinkedList<String>> dataSet;
	private String query;

	public DTable() {

	}

	public DTable(ResultSet resultSet, String query) throws SQLException {
		this.resultSet = resultSet;
		resultSetMetaData = resultSet.getMetaData();
		this.query=query;
		createDataSet();
	}
	
	public String getQuery() {
		return this.query;
	}

	public int getColumnCount() throws SQLException {
		return resultSetMetaData.getColumnCount();
	}

	private static void createDataSet() throws SQLException {
		dataSet = new HashMap<String, LinkedList<String>>();
		for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
			dataSet.put(resultSetMetaData.getColumnName(i), new LinkedList<String>());
		}
		while (resultSet.next()) {
			Iterator itr = dataSet.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry pair = (Map.Entry) itr.next();
				String key = pair.getKey().toString();
				LinkedList<String> appendedList = new LinkedList<String>(dataSet.get(key));
				appendedList.add(resultSet.getString(key));
				dataSet.put(key, appendedList);
			}
		}
		resultSet.close();
	}

	public void displayDataSet() {
		Iterator itr = dataSet.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry pair = (Map.Entry) itr.next();
			String key = pair.getKey().toString();
			System.out.println(key + "-->" + dataSet.get(key));
		}
	}

	public void displayDataSet(HashMap<String, LinkedList<String>> dataSet) {
		Iterator itr = dataSet.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry pair = (Map.Entry) itr.next();
			String key = pair.getKey().toString();
			System.out.println(key + "-->" + dataSet.get(key));
		}
	}

	public HashMap<String, LinkedList<String>> getData() {
		return dataSet;
	}

	public HashMap<String, LinkedList<String>> getData(int limit) {
		HashMap<String, LinkedList<String>> returnDataSet = new HashMap<String, LinkedList<String>>();
		Iterator itr = dataSet.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry pair = (Map.Entry) itr.next();
			String key = pair.getKey().toString();
			LinkedList<String> subList = new LinkedList<String>();
			int count = 0;
			for (String item : dataSet.get(key)) {
				subList.add(item);
				count++;
				if (count == limit)
					break;
			}
			returnDataSet.put(key, subList);
			count++;
		}
		return returnDataSet;
	}

	public HashMap<String, LinkedList<String>> getData(List<String> columnNames) {
		HashMap<String, LinkedList<String>> returnDataSet = new HashMap<String, LinkedList<String>>();
		Iterator itr = dataSet.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry pair = (Map.Entry) itr.next();
			String key = pair.getKey().toString();

			if (columnNames.contains(key)) {
				returnDataSet.put(key, dataSet.get(key));
			}
		}
		return returnDataSet;
	}
	

	public HashMap<String, LinkedList<String>> getData(List<String> columnNames, int limit) {
		HashMap<String, LinkedList<String>> returnDataSet = new HashMap<String, LinkedList<String>>();
		Iterator itr = dataSet.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry pair = (Map.Entry) itr.next();
			String key = pair.getKey().toString();
			if (columnNames.contains(key)) {
				LinkedList<String> subList = new LinkedList<String>();
				int count = 0;
				for (String item : dataSet.get(key)) {
					subList.add(item);
					count++;
					if (count == limit)
						break;
				}

				returnDataSet.put(key, subList);
			}
		}
		return returnDataSet;
	}

	public LinkedList<String> getData(String columnName) {
		return dataSet.get(columnName);
	}
	
	public HashMap<String, Integer> getDataGroupedBy(String columnName) {
		HashMap<String, Integer> returnDataSet = new HashMap<String, Integer>();
		LinkedList<String> genres = new LinkedList<String>(dataSet.get(columnName));
		for(String genre : genres) {
			if(returnDataSet.containsKey(genre)) {
				int count = returnDataSet.get(genre);
				returnDataSet.put(genre, ++count);
			}else {
				returnDataSet.put(genre, 1);
			}
		}
		return returnDataSet;
	}

	public String getData(String columnName, int position) {
		int pos = 1;
		for (String item : dataSet.get(columnName)) {
			if (pos == position)
				return item;
			pos++;
		}
		return "";
	}
	
	public HashMap<String,String> getSong(HashMap<String,Float> propertyValues){
		HashMap<String,String> songProperties = new HashMap<String,String>();
		ArrayList<HashMap<String,String>> songList = new ArrayList<HashMap<String,String>>();
		System.out.println(dataSet);
		return songProperties;
		
	}
	
 /*
	public static void main(String args[]) throws IOException, InterruptedException, SQLException {
		SplashProvider.invokeWithSplash("CREATE_DB", VisualizerSettings.loadDataInputs, "musicloading_small.gif",
				"Loading....", "");

		String year = "2019";
		String query = "select * from spotify_decade where year=" + year + " order by popularity desc";
		Table myQueryTable = (Table) SplashProvider.invokeWithSplash("EXECUTE_QUERY", VisualizerSettings.loadDataInputs,
				"musicloading_small.gif", "Loading....", query);

		List<String> colnames = new ArrayList<String>();
		colnames.add("artist");
		colnames.add("title");
		colnames.add("popularity");
	} */

}
