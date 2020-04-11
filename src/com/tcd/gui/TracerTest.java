package com.tcd.gui;

import tracer.*;
import tracer.paths.*;

import java.util.ArrayList;

import com.tcd.spotify.YearCanvas;

import processing.core.PApplet;

public class TracerTest extends PApplet {
	ArrayList<Path> paths;
	ArrayList<Tracer> tracers;
	boolean synchronizeTracing; // control with '1' key
	boolean drawSegments; // control with '2' keys
	// animation
	float u = 0;
	float du = 0.005f;
	//layout
	int cellSize = 125;
	int rowSize = 5;
	int colSize = 4;
	public void settings() {
		size(rowSize*cellSize, colSize*cellSize, P2D);
		createPaths();
		tracers = createTracers(paths);
	}

	public void createPaths() {
		paths = new ArrayList<Path>();
		float pathRadius = (float) (0.4 * cellSize);
		Path.addOneOfEachPathType(pathRadius, paths);
		for (Path p : paths) {
			p.setStrokeWeight(1.5f);
			p.setStrokeColor(0);
			p.setFill(false);
		}
		reposition(paths, cellSize);
	}

	public ArrayList<Tracer> createTracers(ArrayList<Path> paths) {
		ArrayList<Tracer> tracers = new ArrayList<Tracer>();
		for (Path p : paths) {
			float tracerSpeed = (synchronizeTracing) ? du : (du * 200) / p.getLength();
			tracers.add(new Tracer(p, 0, tracerSpeed));
		}
		return tracers;
	}

	public void reposition(ArrayList<Path> paths, int cellSize) {
		int x = cellSize / 2;
		int y = cellSize / 2;

		for (Path p : paths) {
			p.translate(x, y);

			x += cellSize;
			if (x >= width) {
				x = cellSize / 2;
				y += cellSize;
			}
		}
	}

	public void draw() {
		// update tracers
		for (Tracer t : tracers) {
			t.step();
		}

		// draw
		background(255);

		if (!drawSegments) {
			// draw paths
			for (Path p : paths) {
				p.draw(g);
			}
		}

		if (drawSegments) {
			// draw segments
			for (Tracer t : tracers) {
				Path p = t.getPath();
				float dist = (float) ((synchronizeTracing) ? 0.4 : (50 / p.getLength()));
				float start = Path.remainder(t.getInput1D() - dist, 1);
				float end = t.getInput1D();
				strokeWeight(1);
				p.draw(g, start, end);
			}
		}

		// draw tracers
		strokeWeight(6);
		stroke(0);
		for (Tracer t : tracers) {
			point(t.x, t.y);
		}
	}
	
	public static void main(String args[]) {
		String[] a = { "MAIN" };
		System.out.println("RUN!!");
		PApplet.runSketch(a, new TracerTest());
	}
}
