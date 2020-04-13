package com.tcd.spotify;

import java.io.File;
import java.util.HashMap;

import processing.core.PVector;

public class VisualizerSettings {

	public final static int SCREEN_WIDTH = 1900;
	public final static int SCREEN_HEIGHT = 940;
	public final static String TITLE = "SPOTIFY TOP 100";
	public final static HashMap<String, Object> loadDataInputs = new HashMap<String, Object>(loadDataInputsFunction());
	public final static String dataSet = System.getProperty("user.dir") + File.separator + "data\\2010-2019-normalised.csv";
	public final static String table_name = "spotify_decade";
	public final static String query = "create table " + table_name
			+ " (Number string, title string, artist string, genre string, year string, bpm string, energy string, "
			+ "dance string, loudness string, liveness string, valence string, duration string, acoustic string, "
			+ "speech string, popularity string, img string)";
	public final static int CANVAS1_WIDTH=SCREEN_WIDTH - 1509;
	public final static int CANVAS1_HEIGHT=SCREEN_HEIGHT;
	public final static boolean PHOTO_GRAYNESS = false;
	public final static int PHOTO_BLUR = 2;
	public final static float ECLIPSE_RADIUS = 42f;
	public final static HashMap<Integer,Integer> SLIDER_TO_LABEL_MAPPING = new HashMap<Integer,Integer>(loadSliderToLabelMapping());
	public final static HashMap<String,PVector> barPositions = new HashMap<String,PVector>(loadBarPositions());
	public final static int BACKGROUND_COLOR = 0;
	public final static boolean SHOW_RADIAL_GRAPH_LABELS = true;
	public final static String tipText = "Press 'N' for next tip and 'P' for previous tip";
	public final static String hintText = "Click for guided tour";
	
	private static HashMap<String,PVector> loadBarPositions(){
		HashMap<String,PVector> positions = new HashMap<String,PVector>();
		positions.put("p1", new PVector(0,0,0));
		positions.put("p2", new PVector(0, -100, 0));
		positions.put("p3", new PVector(-100, -100, 0));
		positions.put("p4", new PVector(-100, 0, 0));
		positions.put("p5", new PVector(0, 0, -100));
		positions.put("p6", new PVector(0, -100, -100));
		positions.put("p7", new PVector(-100, -100, -100));
		positions.put("p8",new PVector(-100, 0, -100));
		positions.put("p9", new PVector(-50, 0, 0));
		positions.put("p10",new PVector(-100, -80, 0));
		positions.put("p11", new PVector(-100, -80, -100));
		positions.put("p12", new PVector(-50, 0, -100));
		return positions;
	}
	
	private static HashMap<String, Object> loadDataInputsFunction() {
		String table_name = "spotify_decade";
		String dataSet = System.getProperty("user.dir") + File.separator + "data\\2010-2019.csv";
		String query = "create table " + table_name
				+ " (Number string, title string, artist string, genre string, year string, bpm string, energy string, dance string, loudness string, liveness string, valence string, duration string, acoustic string, speech string, popularity string)";
		String selectQuery = "select * from spotify_decade where Number=\"80\"";
		HashMap<String, Object> inputData = new HashMap<String, Object>();
		inputData.put("tableName", table_name);
		inputData.put("insertQuery", query);
		inputData.put("dataSet", dataSet);
		return inputData;
	}
	
	private static HashMap<Integer,Integer> loadSliderToLabelMapping(){
		HashMap<Integer,Integer> mapping = new HashMap<Integer,Integer>();
		mapping.put(0,5);
		mapping.put(1,2);
		mapping.put(2,6);
		mapping.put(3,3);
		mapping.put(4,7);
		mapping.put(5,0);
		mapping.put(6,4);
		mapping.put(7,1);
		return mapping;
	}
}
