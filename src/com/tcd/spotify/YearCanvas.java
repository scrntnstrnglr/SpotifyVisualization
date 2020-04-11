package com.tcd.spotify;

import java.io.File;
import java.io.FileInputStream;

import shapes3d.*;
import tracer.Tracer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.css.ViewCSS;

import com.tcd.databases.DTable;
import com.tcd.databases.sample.DatabaseManager;

import com.tcd.utils.SplashProvider;

import controlP5.Bang;
import controlP5.Button;
import controlP5.CColor;
import controlP5.Canvas;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.Knob;
import controlP5.Range;
import controlP5.Slider;
import controlP5.Slider2D;
import controlP5.Textlabel;
import g4p_controls.GControlMode;
import g4p_controls.GSlider;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;
import processing.data.Table;
import processing.data.TableRow;
import processing.event.MouseEvent;
import peasy.*;
import peasy.org.apache.commons.math.*;
import peasy.org.apache.commons.math.geometry.*;
import gifAnimation.*;

public class YearCanvas extends PApplet {

	PVector p1 = barPositions.get("p1");
	PVector p2 = barPositions.get("p2");
	PVector p3 = barPositions.get("p3");
	PVector p4 = barPositions.get("p4");
	PVector p5 = barPositions.get("p5");
	PVector p6 = barPositions.get("p6");
	PVector p7 = barPositions.get("p7");
	PVector p8 = barPositions.get("p8");
	PVector p9 = barPositions.get("p9");
	PVector p10 = barPositions.get("p10");
	PVector p11 = barPositions.get("p11");
	PVector p12 = barPositions.get("p12");
	private static Logger logger;
	private static int SCREEN_WIDTH, SCREEN_HEIGHT;
	private final String title;
	private static ControlP5 cp5;
	private static Canvas cc;
	private static Slider yearSlider;
	private static String yearValue = "2010";
	private static Knob yearKnob;
	private static HashMap<String, DTable> yearsTopData;
	private static DatabaseManager dbManager;
	private static Textlabel labeltop1, labeltop2, labeltop3;
	private static Bang labelArtist1, labelArtist2, labelArtist3;
	private static Textlabel labelTitle1, labelTitle2, labelTitle3;
	private static Textlabel labelGenre1, labelGenre2, labelGenre3;
	private static PImage webImg, photo, photo1;
	private static final float radius = VisualizerSettings.ECLIPSE_RADIUS;
	private static GSlider sdr, sdr2, sdr3, sdr4, sdr5, sdr6, sdr7, sdr8;
	private static Knob knob1, knob2, knob3, knobfindyourSong, knobextra;
	private static PShape cube1, cube2, cube3, cube4, cube5;
	private static PeasyCam cam;
	PGraphics maskImage;
	PImage img, animationImg;
	TopThreeBars bars;
	private static HashMap<String, PVector> barPositions = VisualizerSettings.barPositions;
	private static HashMap<String, LinkedList<GSlider>> sliders;
	private static HashMap<String, LinkedList<Knob>> knobs;
	float start = 1.9f;
	float radians = (PI * (360 / 8)) / 180;
	private static Properties maxminProps;
	private static Button topButton, downButton, refreshButton;
	private static Gif yearAnimation;
	private static Table wholeTable;
	private static Slider2D slider2D;
	private int slider2DValuePosition = 3;
	private static LinkedList<String> yLabels = new LinkedList<String>();

	YearCanvas() throws IOException, InterruptedException, ClassNotFoundException, SQLException {
		this.title = VisualizerSettings.TITLE;
		logger = Logger.getLogger(YearCanvas.class);
		PropertyConfigurator.configure("properties" + File.separator + "log4j.properties");
		loadDataSet();

		maxminProps = new Properties();
		maxminProps.load(new FileInputStream("properties" + File.separator + "maxminvalue.properties"));

	}

	private void loadDataSet() throws IOException, InterruptedException, ClassNotFoundException, SQLException {
		logger.info("Loading data...");
		// SplashProvider.invokeWithSplash("CREATE_DB",
		// VisualizerSettings.loadDataInputs, "musicloading_small.gif","Loading....",
		// "");
		dbManager = new DatabaseManager();
		dbManager.executeInserts(VisualizerSettings.dataSet, VisualizerSettings.table_name, VisualizerSettings.query);
		logger.info("Database Created!");
	}

	public void settings() {
		logger.info("Setting Year Canvas");
		SCREEN_WIDTH = VisualizerSettings.SCREEN_WIDTH;
		SCREEN_HEIGHT = VisualizerSettings.SCREEN_HEIGHT;
		wholeTable = loadTable(VisualizerSettings.dataSet, "header");
		size(SCREEN_WIDTH, SCREEN_HEIGHT, P3D);
	}

	public void setup() {
		cp5 = new ControlP5(this);
		LinkedList<String> years = new LinkedList<String>();
		try {
			years = new LinkedList<String>(getYears());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		yearSlider = cp5.addSlider("yearValue").setPosition(1, 2)
				.setRange(Integer.parseInt(years.get(0)), Integer.parseInt(years.get(years.size() - 1))).setValue(2010)
				.setNumberOfTickMarks(10).setSliderMode(Slider.FIX).setBroadcast(true).setCaptionLabel("")
				.setDecimalPrecision(0).setWidth(VisualizerSettings.SCREEN_WIDTH).showTickMarks(false).setHeight(40);
		yearSlider.setColorBackground(color(255, 255, 255));
		yearSlider.setVisible(false);
		yearSlider.setValue(2019);

		sliders = new HashMap<String, LinkedList<GSlider>>();
		knobs = new HashMap<String, LinkedList<Knob>>();

		sliders = getSliderMap(sliders, "position2", width / 2 - 350, height / 2 - 270, false);
		knob2 = cp5.addKnob("knobValue2").setRange(0, 100).setValue(50).setPosition(width / 2 - 392, height / 2 - 312)
				.setRadius(radius).setNumberOfTickMarks(100).setTickMarkLength(1).snapToTickMarks(true).hideTickMarks()
				.setColorForeground(color(255)).setColorBackground(color(0, 160, 100))
				.setColorActive(color(255, 255, 0)).setDragDirection(Knob.VERTICAL).setDecimalPrecision(0).setLock(true)
				.setCaptionLabel("");

		sliders = getSliderMap(sliders, "position1", width / 2, height / 2 - 270, false);
		knob1 = cp5.addKnob("knobValue1").setRange(0, 100).setValue(50).setPosition(width / 2 - 40, height / 2 - 312)
				.setRadius(radius).setNumberOfTickMarks(100).setTickMarkLength(1).snapToTickMarks(true).hideTickMarks()
				.setColorForeground(color(255)).setColorBackground(color(0, 160, 100))
				.setColorActive(color(255, 255, 0)).setDragDirection(Knob.VERTICAL).setDecimalPrecision(0).setLock(true)
				.setCaptionLabel("");

		sliders = getSliderMap(sliders, "position3", width / 2 + 350, height / 2 - 270, false);
		knob3 = cp5.addKnob("knobValue3").setRange(0, 100).setValue(70).setPosition(width / 2 + 308, height / 2 - 312)
				.setRadius(radius).setNumberOfTickMarks(100).setTickMarkLength(1).snapToTickMarks(true).hideTickMarks()
				.setColorForeground(color(255)).setColorBackground(color(0, 160, 100))
				.setColorActive(color(255, 255, 0)).setDragDirection(Knob.HORIZONTAL).setDecimalPrecision(0)
				.setLock(true).setCaptionLabel("");

		sliders = getSliderMap(sliders, "findsongsliders", width / 2 - 700, height / 2 - 270, true);
		knobfindyourSong = cp5.addKnob("knobfindyoursong").setRange(0, 100).setValue(50)
				.setPosition(width / 2 - 741, height / 2 - 312).setRadius(radius).setNumberOfTickMarks(100)
				.setTickMarkLength(1).snapToTickMarks(true).hideTickMarks().setColorForeground(color(255))
				.setColorBackground(color(0, 160, 100)).setColorActive(color(255, 255, 0))
				.setDragDirection(Knob.VERTICAL).setDecimalPrecision(0).setLock(false).setCaptionLabel("").setValue(80);

		sliders = getSliderMap(sliders, "extra", width / 2 + 700, height / 2 - 270, true);
		knobextra = cp5.addKnob("knobextra").setRange(0, 100).setValue(50)
				.setPosition(width / 2 + 660, height / 2 - 312).setRadius(radius).setNumberOfTickMarks(100)
				.setTickMarkLength(1).snapToTickMarks(true).hideTickMarks().setColorForeground(color(255))
				.setColorBackground(color(0, 160, 100)).setColorActive(color(255, 255, 0))
				.setDragDirection(Knob.VERTICAL).setDecimalPrecision(0).setLock(true).setCaptionLabel("").setValue(0);

		slider2D = cp5.addSlider2D("slider2d").setPosition(width / 2 + 400, height - 420).setSize(420, 220)
				.setMinX(2010).setMaxX(2019).setMinY(10).setMaxY(0).setValue(2014, 5).setColorValue(0)
				.setCaptionLabel("");
		PImage topImg = loadImage("img\\arrows\\up.png");
		PImage topClickedImg = loadImage("img\\arrows\\upclicked.png");
		topImg.resize(20, 20);
		topClickedImg.resize(20, 20);
		PImage downImg = loadImage("img\\arrows\\down.png");
		PImage downClickedImg = loadImage("img\\arrows\\downclicked.png");
		downImg.resize(20, 20);
		downClickedImg.resize(20, 20);
		topButton = cp5.addButton("top").setPosition(width / 2 + 367, height / 2 + 90)
				.setImages(topImg, topClickedImg, topImg).setSize(20, 20);
		downButton = cp5.addButton("down").setPosition(width / 2 + 367, height / 2 + 210)
				.setImages(downImg, downClickedImg, downImg).setSize(20, 20);
		yLabels.add("Tempo");
		yLabels.add("Energy");
		yLabels.add("Danceability");
		yLabels.add("Popularity");
		yLabels.add("Loudness");
		yLabels.add("Liveness");
		yLabels.add("Valence");
		// animationImg = loadImage("img\\loading_gifs\\original.gif");
		// animationImg.resize(50, 50);
		yearAnimation = new Gif(this, "img\\loading_gifs\\music-resized.gif");
		yearAnimation.play();
		/*
		 * genreButton = cp5.addButton("Genre").setPosition(width / 2 - 50, height -
		 * 155) .setWidth(100).setHeight(20).setLabel("Show Genres").setValue(1);
		 */
	}

	public void draw() {
		background(216, 252, 231);
		createBorders();
		createYearSlider();
		text("This is spotify", 10, 10);
		// fill(255);
		// stroke(55, 210, 4);
		// fill(255);
		// rect(100, 100, 200, 200);
		// fill(yearSlider.getValue());
		DTable thisYearTable = new DTable();
		try {
			String query = "select * from spotify_decade where year='" + (int) yearSlider.getValue() + ""
					+ "' order by popularity desc";
			thisYearTable = getDataForYear(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int blur = VisualizerSettings.PHOTO_BLUR;
		boolean gray = VisualizerSettings.PHOTO_GRAYNESS;

		HashMap<String, Float> songPropertiesInput = new HashMap<String, Float>();
		songPropertiesInput.put("duration", sliders.get("findsongsliders").get(0).getValueF());
		songPropertiesInput.put("dance", sliders.get("findsongsliders").get(1).getValueF());
		songPropertiesInput.put("acoustic", sliders.get("findsongsliders").get(2).getValueF());
		songPropertiesInput.put("loudness", sliders.get("findsongsliders").get(3).getValueF());
		songPropertiesInput.put("speechness", sliders.get("findsongsliders").get(4).getValueF());
		songPropertiesInput.put("bpm", sliders.get("findsongsliders").get(5).getValueF());
		songPropertiesInput.put("liveness", sliders.get("findsongsliders").get(6).getValueF());
		songPropertiesInput.put("energy", sliders.get("findsongsliders").get(7).getValueF());
		songPropertiesInput.put("valence", knobfindyourSong.getValue());

		pushMatrix();
		cam = new PeasyCam(this, -50, -100, 200, 600);
		cam.setActive(false);
		int pop1 = Integer.parseInt(thisYearTable.getData("popularity", 1));
		int pop2 = Integer.parseInt(thisYearTable.getData("popularity", 2));
		int pop3 = Integer.parseInt(thisYearTable.getData("popularity", 3));
		PImage img1 = loadImage(thisYearTable.getData("img", 1), "png");
		PImage img2 = loadImage(thisYearTable.getData("img", 2), "png");
		PImage img3 = loadImage(thisYearTable.getData("img", 3), "png");
		int u = 600;
		int v = 600;
		cube1 = createBar(u, v, (pop1 - 50) * 4, img1);
		cube2 = createBar(u, v, (pop2 - 50) * 4, img2);
		cube3 = createBar(u, v, (pop3 - 50) * 4, img3);

		pushMatrix();
		rotateX(PI / 3);
		shape(cube1);
		popMatrix();

		pushMatrix();
		rotateX(PI / 3);
		translate(-180, 0);
		shape(cube2);
		popMatrix();

		pushMatrix();
		rotateX(PI / 3);
		translate(180, 0);
		shape(cube3);
		popMatrix();
		popMatrix();

		HashMap<String, Integer> genreGroupedBy = new HashMap<String, Integer>(thisYearTable.getDataGroupedBy("genre"));
		int total = 0;
		for (int count : genreGroupedBy.values()) {
			total += count;
		}
		int multiplier = width / total;
		HashMap<String, Integer> genreBarData = new HashMap<String, Integer>();
		Iterator itr = genreGroupedBy.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry pair = (Map.Entry) itr.next();
			genreBarData.put(pair.getKey().toString(), (int) pair.getValue() * multiplier);
		}

		pushMatrix();
		// stroke(10);
		// rect(5,5,300,100);
		itr = genreBarData.entrySet().iterator();
		HashMap<String, LinkedList<Float>> genreLocation = new HashMap<String, LinkedList<Float>>();
		float startx = 1, starty = height - 50, red = 211, green = 84, blue = 0;
		for (String key : genreBarData.keySet()) {
			fill(red, green, blue);
			rect(startx, starty, genreBarData.get(key), 50);
			fill(0);
			float gx = (genreBarData.get(key) / 2 + startx);
			float gy = starty + 30;
			textAlign(CENTER);

			text(key, gx, gy);
			LinkedList<Float> genreLocationList = new LinkedList<Float>();
			genreLocationList.add(gx);
			genreLocationList.add(gy);
			genreLocation.put(key, genreLocationList);
			startx += genreBarData.get(key);
			blue += 50;
			red -= 20;
		}
		popMatrix();

		pushMatrix();
		noFill();
		smooth();

		pushMatrix();
		// strokeWeight(4);
		String textTitle, textArtist, textGenre;
		textTitle = thisYearTable.getData("title", 2);
		textArtist = thisYearTable.getData("artist", 2);
		textAlign(CENTER);
		// fill(255);
		text(textTitle, width / 2 - 190, height - 260);
		text(textArtist, width / 2 - 190, height - 245);
		noFill();
		// stroke(255);
		bezier(width / 2 - 190, height - 235, width / 2 - 190, height - 150, width / 2, height - 245, width / 2,
				height - 150);

		knob2.setValue(Integer.parseInt(thisYearTable.getData("valence", 2)));
		// textAlign(CENTER);
		textTitle = thisYearTable.getData("title", 1);
		textArtist = thisYearTable.getData("artist", 1);
		// fill(255);
		text(textTitle, width / 2, height - 260);
		text(textArtist, width / 2, height - 245);
		noFill();
		// stroke(255);
		bezier(width / 2, height - 235, width / 2, height - 150, width / 2, height - 245, width / 2, height - 150);

		knob1.setValue(Integer.parseInt(thisYearTable.getData("valence", 1)));

		textTitle = thisYearTable.getData("title", 3);
		textArtist = thisYearTable.getData("artist", 3);
		// textAlign(CENTER);
		// fill(255);
		text(textTitle, width / 2 + 160, height - 260);
		text(textArtist, width / 2 + 160, height - 245);
		noFill();
		// stroke(255);
		bezier(width / 2 + 160, height - 235, width / 2 + 160, height - 150, width / 2, height - 245, width / 2,
				height - 150);

		knob3.setValue(Integer.parseInt(thisYearTable.getData("valence", 3)));

		popMatrix();
		popMatrix();

		createCircularController(sliders);
		setSliderValues(1, thisYearTable);
		setSliderValues(2, thisYearTable);
		setSliderValues(3, thisYearTable);

		pushMatrix();
		strokeWeight(2);
		fill(162, 247, 134);
		noStroke();
		ellipse(width / 2 - 350, height / 2 - 270, 290, 290);
		ellipse(width / 2, height / 2 - 270, 290, 290);
		ellipse(width / 2 + 350, height / 2 - 270, 290, 290);
		ellipse(width / 2 - 700, height / 2 - 270, 290, 290);
		ellipse(width / 2 + 700, height / 2 - 270, 290, 290);
		stroke(1);
		popMatrix();

		HashMap<Integer, Integer> sliderToLabelMapping = new HashMap<Integer, Integer>(
				VisualizerSettings.SLIDER_TO_LABEL_MAPPING);
		// labels along slider circles
		for (int i = 0; i < 8; i++) {
			pushMatrix();
			fill(0);
			translate(width / 2 - 350, height / 2 - 270);
			translate(150 * cos((start + (start + radians)) / 2), 150 * sin((start + (start + radians)) / 2));
			rotate((start + (start + radians)) / 2 + PI / 2);
			textAlign(CENTER);
			int value = sliderToLabelMapping.get(i);
			char label = sliders.get("position2").get(value).tag.charAt(0);
			float percentage = Math.round(sliders.get("position2").get(value).getValueF() * 100);
			text(label + ":" + percentage + "%", 0, 0);
			start += radians;
			popMatrix();
		}

		// drawSliderRectangles(yearSlider.getValue());
		// image(yearAnimation,width/2-20,height-110);
		createFindYourSongView(getSong(songPropertiesInput, wholeTable).get(0));
		create2DSliderYLabels(width / 2 + 400, height - 420, 420, 220);
		workWith2DSliderValues(yLabels.get(slider2DValuePosition), slider2D.getArrayValue(0),
				slider2D.getArrayValue(1));
	}

	private void create2DSliderYLabels(float x, float y, float width, float height) {
		fill(0);
		textAlign(CENTER);
		pushMatrix();
		translate(x - 20, y + height / 2);
		rotate(-PI / 2);
		text(yLabels.get(slider2DValuePosition), 0, 0);
		popMatrix();
	}

	public void top(int theValue) {
		int newValue = slider2DValuePosition + 1;
		if (newValue == yLabels.size()) {
			slider2DValuePosition = 0;
		} else {
			slider2DValuePosition = newValue;
		}
	}

	public void down(int theValue) {
		int newValue = slider2DValuePosition - 1;
		if (newValue == -1) {
			slider2DValuePosition = yLabels.size() - 1;
		} else {
			slider2DValuePosition = newValue;
		}
	}

	public List<HashMap<String, String>> getSong(HashMap<String, Float> propertyValues, Table dataSet) {
		float offset = 1.0f;
		HashMap<String, String> songDetails;
		HashMap<Float, List<HashMap<String, String>>> songScoreDetails = new HashMap<Float, List<HashMap<String, String>>>();
		float duration = 0.0f, dance = 0.0f, acoustic = 0.0f, loudness = 0.0f, speechness = 0.0f, bpm = 0.0f,
				liveness = 0.0f, energy = 0.0f, valence = 0.0f, overallScore = 0.0f;
		for (TableRow row : dataSet.rows()) {
			if (propertyValues.containsKey("duration"))
				duration = Math.abs(row.getFloat("dur") - propertyValues.get("duration"));
			if (propertyValues.containsKey("dance"))
				dance = Math.abs(row.getFloat("dnce") - propertyValues.get("dance"));
			if (propertyValues.containsKey("acoustic"))
				acoustic = Math.abs(row.getFloat("acous") - propertyValues.get("acoustic"));
			if (propertyValues.containsKey("loudness"))
				loudness = Math.abs(row.getFloat("dB") - propertyValues.get("loudness"));
			if (propertyValues.containsKey("speechness"))
				speechness = Math.abs(row.getFloat("spch") - propertyValues.get("speechness"));
			if (propertyValues.containsKey("bpm"))
				bpm = Math.abs(row.getFloat("bpm") - propertyValues.get("bpm"));
			if (propertyValues.containsKey("liveness"))
				liveness = Math.abs(row.getFloat("live") - propertyValues.get("liveness"));
			if (propertyValues.containsKey("energy"))
				energy = Math.abs(row.getFloat("nrgy") - propertyValues.get("energy"));
			if (propertyValues.containsKey("valence"))
				valence = Math.abs(row.getFloat("val") - propertyValues.get("valence"));

			overallScore = duration + dance + acoustic + loudness + speechness + bpm + liveness + energy + valence;

			songDetails = new HashMap<String, String>();
			songDetails.put("title", row.getString("title"));
			songDetails.put("artist", row.getString("artist"));
			songDetails.put("genre", row.getString("top genre"));
			songDetails.put("year", row.getString("year"));
			songDetails.put("pop", row.getString("pop"));
			songDetails.put("img", row.getString("img"));
			songDetails.put("valence", row.getString("val"));
			if (songScoreDetails.containsKey(overallScore)) {
				ArrayList<HashMap<String, String>> thisScoreSongs = new ArrayList<HashMap<String, String>>(
						songScoreDetails.get(overallScore));
				thisScoreSongs.add(songDetails);
				songScoreDetails.put(overallScore, thisScoreSongs);
			} else {
				ArrayList<HashMap<String, String>> thisScoreSongs = new ArrayList<HashMap<String, String>>();
				thisScoreSongs.add(songDetails);
				songScoreDetails.put(overallScore, thisScoreSongs);
			}

		}

		List<Float> overallScoresList = new ArrayList<Float>(songScoreDetails.keySet());
		Collections.sort(overallScoresList);
		float leastScore = overallScoresList.get(0);
		return songScoreDetails.get(leastScore);

	}

	public List<HashMap<String, String>> getSong(HashMap<String, Float> propertyValues, float year, Table dataSet) {

		HashMap<String, String> songDetails;
		HashMap<Float, List<HashMap<String, String>>> songScoreDetails = new HashMap<Float, List<HashMap<String, String>>>();
		float duration = 0.0f, dance = 0.0f, acoustic = 0.0f, loudness = 0.0f, speechness = 0.0f, bpm = 0.0f,
				liveness = 0.0f, energy = 0.0f, valence = 0.0f, overallScore = 0.0f, pop = 0.0f;
		for (TableRow row : dataSet.rows()) {
			if (row.getFloat("year") == year) {
				if (propertyValues.containsKey("Liveness"))
					liveness = Math.abs(row.getFloat("live") - propertyValues.get("Liveness"));
				if (propertyValues.containsKey("Loudness"))
					loudness = Math.abs(row.getFloat("dB") - propertyValues.get("Loudness"));
				if (propertyValues.containsKey("Popularity"))
					pop = Math.abs(row.getFloat("pop") - propertyValues.get("Popularity"));
				if (propertyValues.containsKey("Danceability"))
					dance = Math.abs(row.getFloat("dnce") - propertyValues.get("Danceability"));
				if (propertyValues.containsKey("Energy"))
					energy = Math.abs(row.getFloat("nrgy") - propertyValues.get("Energy"));
				if (propertyValues.containsKey("Valence"))
					valence = Math.abs(row.getFloat("val") - propertyValues.get("Valence"));
				if (propertyValues.containsKey("Tempo"))
					bpm = Math.abs(row.getFloat("bpm") - propertyValues.get("Tempo"));

				overallScore = duration + dance + acoustic + loudness + speechness + bpm + liveness + energy + valence
						+ pop;

				songDetails = new HashMap<String, String>();
				songDetails.put("title", row.getString("title"));
				songDetails.put("artist", row.getString("artist"));
				songDetails.put("genre", row.getString("top genre"));
				songDetails.put("year", row.getString("year"));
				songDetails.put("pop", row.getString("pop"));
				songDetails.put("img", row.getString("img"));
				songDetails.put("valence", row.getString("val"));

				if (songScoreDetails.containsKey(overallScore)) {
					ArrayList<HashMap<String, String>> thisScoreSongs = new ArrayList<HashMap<String, String>>(
							songScoreDetails.get(overallScore));
					thisScoreSongs.add(songDetails);
					songScoreDetails.put(overallScore, thisScoreSongs);
				} else {
					ArrayList<HashMap<String, String>> thisScoreSongs = new ArrayList<HashMap<String, String>>();
					thisScoreSongs.add(songDetails);
					songScoreDetails.put(overallScore, thisScoreSongs);
				}
			}

		}

		List<Float> overallScoresList = new ArrayList<Float>(songScoreDetails.keySet());
		Collections.sort(overallScoresList);
		float leastScore = overallScoresList.get(0);
		return songScoreDetails.get(leastScore);

	}

	private void createFindYourSongView(HashMap<String, String> foundSong) {

		PImage artistImage = loadImage(foundSong.get("img"), "png");
		String artist = foundSong.get("artist");
		String title = foundSong.get("title");
		String genre = foundSong.get("genre");
		String year = foundSong.get("year");
		String pop = foundSong.get("pop");
		String valence = foundSong.get("valence");

		int imgWidth = 100, imgHeight = 100;
		float imgX = 130, imgY = height - 560;
		artistImage.resize(imgWidth, imgHeight);
		textAlign(LEFT);
		noFill();
		strokeWeight(4);
		rect(imgX, imgY, imgWidth, imgHeight, 10);
		image(artistImage, imgX, imgY);
		text("Artist: " + artist, imgX + imgWidth + 10, imgY + 15);
		text("Title: " + title, imgX + imgWidth + 10, imgY + 35);
		text("Genre: " + genre, imgX + imgWidth + 10, imgY + 55);
		text("Year: " + year, imgX + imgWidth + 10, imgY + 75);
		text("Popularity: " + pop + "%", imgX + imgWidth + 10, imgY + 95);
		// knobfindyourSong.setValue(Float.parseFloat(valence));

		fill(255);
		strokeWeight(2);
		float graphX = imgX, graphY = imgY + 140, graphWidth = 420, graphHeight = 220;
		rect(graphX, graphY, graphWidth, graphHeight, 10);
		float gapY = 20, gapX = 35;
		HashMap<Integer, Float> locY = new HashMap<Integer, Float>(), locX = new HashMap<Integer, Float>();
		;
		int total = 9;
		for (int i = 1; i <= 10; i++) {
			strokeWeight(0.5f);
			float startX = graphX + 30, startY = graphY + (gapY * i);
			float endX = startX + graphWidth - 40, endY = graphY + (gapY * i);
			float xaxisLoc = startX + (gapX * i);
			line(startX, startY, endX, endY);
			locY.put(total--, startY);
			locX.put(2010 + (i - 1), xaxisLoc);
		}
		for (int yearValue : locX.keySet()) {
			textAlign(CENTER);
			fill(0);
			textSize(9);
			text(yearValue + "", locX.get(yearValue), height - 205);
			textSize(12);
		}
		strokeWeight(2);
		float red = 211, green = 84, blue = 0;
		HashMap<Integer, Integer> artistData = new HashMap<Integer, Integer>(getArtistData(wholeTable, artist, "pop"));
		plotArtistTrendLines(artistData, locX, locY, red, green, blue);
		line(width / 2 - 800, height - 175, width / 2 - 800 + 50, height - 175);
		fill(0);
		textAlign(LEFT);
		text("Popularity", width / 2 - 800 + 55, height - 170);
		blue += 150;
		red -= 60;
		HashMap<Integer, Integer> artistDataVal = new HashMap<Integer, Integer>(
				getArtistData(wholeTable, artist, "val"));
		plotArtistTrendLines(artistDataVal, locX, locY, red, green, blue);
		line(width / 2 - 660, height - 175, width / 2 - 660 + 50, height - 175);
		fill(0);
		text("Valence", width / 2 - 660 + 55, height - 170);
		blue += 150;
		red -= 60;
		HashMap<Integer, Integer> artistDataEnergy = new HashMap<Integer, Integer>(
				getArtistData(wholeTable, artist, "nrgy"));
		plotArtistTrendLines(artistDataEnergy, locX, locY, red, green, blue);
		line(width / 2 - 520, height - 175, width / 2 - 520 + 50, height - 175);
		fill(0);
		text("Energy", width / 2 - 520 + 55, height - 170);
	}

	private void plotArtistTrendLines(HashMap<Integer, Integer> artistData, HashMap<Integer, Float> locX,
			HashMap<Integer, Float> locY, float red, float green, float blue) {
		List<Integer> artistDataKeys = new ArrayList<Integer>(artistData.keySet());
		Collections.sort(artistDataKeys);
		stroke(red, green, blue);
		for (int i = 0; i < artistDataKeys.toArray().length - 1; i++) {
			float xCordStart = locX.get(artistDataKeys.toArray()[i]);
			float yCordStart = locY.get(artistData.get(artistDataKeys.toArray()[i]));
			float xCordEnd = locX.get(artistDataKeys.toArray()[i + 1]);
			float yCordEnd = locY.get(artistData.get(artistDataKeys.toArray()[i + 1]));
			line(xCordStart, yCordStart, xCordEnd, yCordEnd);
		}
	}

	private HashMap<Integer, Integer> getArtistData(Table dataSet, String artist, String property) {
		HashMap<Integer, Integer> artistData = new HashMap<Integer, Integer>();
		for (TableRow row : dataSet.rows()) {
			if (row.getString("artist").equals(artist)) {
				int pop = 0;
				if (Float.parseFloat(row.getString(property)) > 1) {
					pop = Math.round(Float.parseFloat(row.getString(property)) / 10);
				} else {
					pop = Math.round(Float.parseFloat(row.getString(property)) * 10);
				}
				int year = Integer.parseInt(row.getString("year"));
				if (pop == 10)
					pop--;
				artistData.put(year, pop);
			}
		}
		int start = 2010;
		for (int i = 0; i <= 9; i++) {
			if (!artistData.containsKey(start)) {
				artistData.put(start, 0);
			}
			start++;
		}
		return artistData;
	}

	private float getUnscaledValue(String property, float scaledValue) {
		float unscaledValue = 0.0f;
		float propMax = Float.parseFloat(maxminProps.getProperty(property + "_max"));
		float propMin = Float.parseFloat(maxminProps.getProperty(property + "_min"));
		float diff = propMax - propMin;
		unscaledValue = (scaledValue * diff) + propMin;
		return unscaledValue;
	}

	private HashMap<String, LinkedList<GSlider>> getSliderMap(HashMap<String, LinkedList<GSlider>> sliderMap,
			String key, int x, int y, boolean workable) {
		LinkedList<GSlider> thisPositionSliderList = new LinkedList<GSlider>();
		sdr = new GSlider(this, x + radius, y - 15, 100, 28, 10);
		sdr.tag = "Duration";
		sdr.setEnabled(workable);
		sdr2 = new GSlider(this, x + 15, y + radius, 100, 28, 10);
		sdr2.setRotation((float) 1.57, GControlMode.CORNERS);
		sdr2.tag = "Danceability";
		sdr2.setEnabled(workable);
		sdr3 = new GSlider(this, x - radius, y + 15, 100, 28, 10);
		sdr3.setRotation((float) 3.141, GControlMode.CORNERS);
		sdr3.tag = "Acousticness";
		sdr3.setEnabled(workable);
		sdr4 = new GSlider(this, x - 15, y - radius, 100, 28, 10);
		sdr4.setRotation((float) 4.712, GControlMode.CORNERS);
		sdr4.tag = "Loudness";
		sdr4.setEnabled(workable);
		sdr5 = new GSlider(this, x + radius, y + 15, 100, 28, 10);
		sdr5.setRotation((float) 0.7853975, GControlMode.CORNERS);
		sdr5.tag = "Speechness";
		sdr5.setEnabled(workable);
		sdr6 = new GSlider(this, x - 15, y + radius, 100, 28, 10);
		sdr6.setRotation((float) 2.3561925, GControlMode.CORNERS);
		sdr6.tag = "BPM";
		sdr6.setEnabled(workable);
		sdr7 = new GSlider(this, x - radius, y - 15, 100, 28, 10);
		sdr7.setRotation((float) 3.92699, GControlMode.CORNERS);
		sdr7.tag = "Liveness";
		sdr7.setEnabled(workable);
		sdr8 = new GSlider(this, x + 15, y - radius, 100, 28, 10);
		sdr8.setRotation((float) 5.49778, GControlMode.CORNERS);
		sdr8.tag = "Energy";
		sdr8.setEnabled(workable);

		thisPositionSliderList.add(sdr);
		thisPositionSliderList.add(sdr2);
		thisPositionSliderList.add(sdr3);
		thisPositionSliderList.add(sdr4);
		thisPositionSliderList.add(sdr5);
		thisPositionSliderList.add(sdr6);
		thisPositionSliderList.add(sdr7);
		thisPositionSliderList.add(sdr8);
		sliderMap.put(key, thisPositionSliderList);
		return sliderMap;
	}

	private void createCircularController(HashMap<String, LinkedList<GSlider>> sliders) {

		for (String position : sliders.keySet()) {
			LinkedList<GSlider> sliderList = new LinkedList<GSlider>(sliders.get(position));
			GSlider sdr = sliderList.get(0), sdr2 = sliderList.get(1), sdr3 = sliderList.get(2),
					sdr4 = sliderList.get(3), sdr5 = sliderList.get(4), sdr6 = sliderList.get(5),
					sdr7 = sliderList.get(6), sdr8 = sliderList.get(7);

			line(sdr.getX() + (sdr.getValueF() * 100), sdr.getY() + 10, sdr5.getX() + (sdr5.getValueF() * 60),
					(sdr5.getY() + 15) + (sdr5.getValueF() * 60));
			line(sdr5.getX() + (sdr5.getValueF() * 60), (sdr5.getY() + 15) + (sdr5.getValueF() * 60), sdr2.getX() - 15,
					sdr2.getY() + (sdr2.getValueF() * 100));
			line(sdr2.getX() - 15, sdr2.getY() + (sdr2.getValueF() * 100), (sdr6.getX() - 15) - (sdr6.getValueF() * 60),
					sdr6.getY() + (sdr6.getValueF() * 60));
			line((sdr6.getX() - 15) - (sdr6.getValueF() * 60), sdr6.getY() + (sdr6.getValueF() * 60),
					sdr3.getX() - (sdr3.getValueF() * 100), sdr3.getY() - 15);
			line(sdr3.getX() - (sdr3.getValueF() * 100), sdr3.getY() - 15, sdr7.getX() - (sdr7.getValueF() * 60),
					(sdr7.getY() - 15) - (sdr7.getValueF() * 60));
			line(sdr7.getX() - (sdr7.getValueF() * 60), (sdr7.getY() - 15) - (sdr7.getValueF() * 60), sdr4.getX() + 15,
					sdr4.getY() - (sdr4.getValueF() * 100));
			line(sdr4.getX() + 15, sdr4.getY() - (sdr4.getValueF() * 100), sdr8.getX() + 15 + (sdr8.getValueF() * 60),
					sdr8.getY() + 10 - (sdr8.getValueF() * 70));
			line(sdr8.getX() + 15 + (sdr8.getValueF() * 60), sdr8.getY() + 10 - (sdr8.getValueF() * 70),
					sdr.getX() + (sdr.getValueF() * 100), sdr.getY() + 15);

		}
	}

	private static void setSliderValues(int position, DTable table) {
		// bpm //energy //dance //loudness //liveness //duration //acoustic //speech
		LinkedList<GSlider> sliderList = sliders.get("position" + position);

		sliderList.get(0).setValue(Float.parseFloat(table.getData("duration", position))); // e
		sliderList.get(1).setValue(Float.parseFloat(table.getData("dance", position))); // s
		sliderList.get(2).setValue(Float.parseFloat(table.getData("acoustic", position))); // w
		sliderList.get(3).setValue(Float.parseFloat(table.getData("loudness", position))); // north
		sliderList.get(4).setValue(Float.parseFloat(table.getData("speech", position))); // se
		sliderList.get(5).setValue(Float.parseFloat(table.getData("bpm", position))); // sw
		sliderList.get(6).setValue(Float.parseFloat(table.getData("liveness", position))); // nw
		sliderList.get(7).setValue(Float.parseFloat(table.getData("energy", position))); // ne

	}

	private PImage getPhoto(DTable thisYearTable, int position) {
		return loadImage(thisYearTable.getData("img", position), "png");
	}

	private DTable getDataForYear(String query) throws SQLException {
		// String query = "select * from spotify_decade where year='" + year + "' order
		// by popularity desc";
		ResultSet rs = dbManager.executeQuery(query);
		DTable thisTableData = new DTable(rs, query);
		rs.close();
		return thisTableData;

	}

	private LinkedList<String> getYears() throws SQLException {
		String query = "select distinct year from spotify_decade where year not in ('year') order by 1";
		ResultSet rs = dbManager.executeQuery(query);
		DTable yearsTable = new DTable(rs, query);
		return yearsTable.getData("year");
	}

	private PShape createBar(int u, int v, int height, PImage img) {
		PShape cube;
		pushMatrix();
		// translate(x,y,z);
		cube = createShape();
		cube.beginShape(QUAD);
		cube.texture(img);
		cube.textureMode(IMAGE);
		cube.vertex(p1.x, p1.y, p1.z + height); // upper surface 0 0
		cube.vertex(p2.x, p2.y, p2.z + height);
		cube.vertex(p3.x, p3.y, p3.z + height);
		cube.vertex(p4.x, p4.y, p4.z + height);

		cube.vertex(p1.x, p1.y, p1.z + height); // right surface
		cube.vertex(p5.x, p5.y, p5.z);
		cube.vertex(p6.x, p6.y, p6.z);
		cube.vertex(p2.x, p2.y, p2.z + height);

		cube.vertex(p3.x, p3.y, p3.z + height); // left
		cube.vertex(p7.x, p7.y, p7.z);
		cube.vertex(p8.x, p8.y, p8.z);
		cube.vertex(p4.x, p4.y, p4.z + height);

		cube.vertex(p8.x, p8.y, p8.z); // bottom
		cube.vertex(p5.x, p5.y, p5.z);
		cube.vertex(p6.x, p6.y, p6.z);
		cube.vertex(p7.x, p7.y, p7.z);

		cube.vertex(p8.x, p8.y, p8.z, 0, v); // front
		cube.vertex(p4.x, p4.y, p4.z + height, 0, 0);
		cube.vertex(p1.x, p1.y, p1.z + height, u, 0);
		cube.vertex(p5.x, p5.y, p5.z, u, v);

		cube.vertex(p6.x, p6.y, p6.z); // back
		cube.vertex(p2.x, p2.y, p2.z + height);
		cube.vertex(p3.x, p3.y, p3.z + height);
		cube.vertex(p7.x, p7.y, p7.z);

		cube.endShape(CLOSE);
		popMatrix();

		return cube;
	}

	private void createBorders() {
		line(80, 40, 80, height - 150);
		line(80, 40, 420, 40);
		line(420, 40, 420, height / 2);
		line(420, height / 2, width / 2 - 350, height / 2);
		line(width / 2 - 350, height / 2, width / 2 - 350, height - 150);
		line(80, height - 150, width / 2 - 350, height - 150);

		line(width - 80, 40, width - 80, height - 150);
		line(width - 80, 40, width - 420, 40);
		line(width - 420, 40, width - 420, height / 2);
		line(width - 420, height / 2, width / 2 + 350, height / 2);
		line(width / 2 + 350, height / 2, width / 2 + 350, height - 150);
		line(width / 2 + 350, height - 150, width - 80, height - 150);

	}

	@Override
	public void mouseWheel(MouseEvent event) {
		// TODO Auto-generated method stub
		super.mouseWheel(event);
		if (mouseY >= 25 && !knobfindyourSong.isActive()) {
			float currentValue = yearSlider.getValue();
			float newValue = currentValue - event.getCount();
			if (newValue >= 2010 && newValue <= 2019) {
				yearSlider.setValue(newValue);
			}
		}
	}

	private void drawSliderRectangles(float year) {
		String yearString = "2010";
		int yearValue = Integer.parseInt(yearString);
		float offset = (year - 2010) * 2 + 1;
		float rectWidth = 36;
		float startX = width / 2 - (rectWidth / 2) * offset, startY = height - 150;
		for (int i = 1; i <= 10; i++) {
			pushMatrix();
			stroke(1);
			noFill();
			rect(startX, startY, rectWidth, 20, 10);
			textAlign(CENTER);
			textSize(10);
			text(yearValue, startX + rectWidth / 2, startY + 13);
			yearValue++;
			startX += rectWidth;
			popMatrix();
		}
	}

	private void createYearSlider() {
		int minYear = 2010, maxYear = 2019;
		// yearSlider.setValue(2019);
		float activeYear = yearSlider.getValue();
		String yearString = String.format("%.0f", activeYear);
		int numberOnLeft = Integer.parseInt(yearString) - minYear;
		int numberOnRight = maxYear - Integer.parseInt(yearString);
		strokeWeight(1);
		float red = 100, green = 180, blue = 90;
		fill(red, green, blue);
		noStroke();
		arc(width / 2, height - 50, 150, 150, PI, TWO_PI, PIE);
		// rect(0,height-75,width/2-70,25);
		// rect(width/2+70,height-75,width/2-70,25);
		float xLeft = width / 2 - 120, yLeft = height - 58, xRight = width / 2 + 120, yRight = height - 58;
		float rectXLeft = width / 2 - 170, rectYLeft = height - 75, rectLeftWidth = 100, rectLeftHeight = 25;
		float rectXRight = width / 2 + 70, rectYRight = height - 75, rectRightWidth = 100, rectRightHeight = 25;
		int currentYear = Integer.parseInt(yearString);

		for (int i = 1; i <= numberOnLeft; i++) {
			currentYear--;
			fill(red, green, blue);
			float rectXLeftOrig = rectXLeft;
			if (i == numberOnLeft) {
				rectLeftWidth = rectXLeft + rectLeftWidth;
				rectXLeft = 0;
			}
			rect(rectXLeft, rectYLeft, rectLeftWidth, rectLeftHeight);
			fill(0);
			if (i == numberOnLeft) {
				text(currentYear + "", rectXLeftOrig + 30, yLeft);
			} else {
				text(currentYear + "", rectXLeft + 30, yLeft);
			}
			xLeft -= 80;
			rectXLeft -= rectLeftWidth;
			red += 20;
			green -= 20;
		}

		red = 100;
		green = 180;
		blue = 90;
		fill(red, green, blue);
		for (int i = 1; i <= numberOnRight; i++) {
			currentYear++;
			fill(red, green, blue);
			if (i == numberOnRight) {
				rectRightWidth = width - rectXRight;
			}
			rect(rectXRight, rectYRight, rectRightWidth, rectRightHeight);
			fill(0);
			text(currentYear + "", rectXRight + 30, yRight);
			xRight += 80;
			rectXRight += rectRightWidth;
			red += 20;
			green -= 20;
			// blue+=40;
		}
		fill(255);
		arc(width / 2, height - 50, 100, 100, PI, TWO_PI, PIE);
		fill(100, 250, 90);
		stroke(1);
		arc(width / 2, height - 50, 170, 170, PI + (3 * QUARTER_PI / 2), TWO_PI - (3 * QUARTER_PI / 2), PIE);
		fill(255);
		stroke(1);
		arc(width / 2, height - 50, 120, 120, PI + (3 * QUARTER_PI / 2), TWO_PI - (3 * QUARTER_PI / 2), PIE);
		fill(0);
		textAlign(CENTER);
		text(yearString, width / 2, height - 118);

	}

	private void workWith2DSliderValues(String property, float year, float propertyLevel) {
		HashMap<String, Float> propertyValues = new HashMap<String, Float>();
		if(property.equalsIgnoreCase("valence") || property.equalsIgnoreCase("popularity")) {
			propertyValues.put(property, Float.parseFloat(String.format("%.0f", propertyLevel))*10);
		}else {
			propertyValues.put(property, Float.parseFloat(String.format("%.0f", propertyLevel))/10);
		}
		HashMap<String, String> retrievedSong = new HashMap<String, String>(
				getSong(propertyValues, Float.parseFloat(String.format("%.0f", year)), wholeTable).get(0));
		
	}

	public static void main(String args[]) {
		// logger.info("Initializing Visualization");
		String[] a = { "MAIN" };
		try {
			PApplet.runSketch(a, new YearCanvas());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
