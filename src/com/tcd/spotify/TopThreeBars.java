package com.tcd.spotify;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;

public class TopThreeBars extends PApplet {
	PVector p1 = new PVector(0, 0, 0);
	PVector p2 = new PVector(0, -100, 0);
	PVector p3 = new PVector(-100, -100, 0);
	PVector p4 = new PVector(-100, 0, 0);
	PVector p5 = new PVector(0, 0, -100);
	PVector p6 = new PVector(0, -100, -100);
	PVector p7 = new PVector(-100, -100, -100);
	PVector p8 = new PVector(-100, 0, -100);
	PVector p9 = new PVector(-50, 0, 0);
	PVector p10 = new PVector(-100, -80, 0);
	PVector p11 = new PVector(-100, -80, -100);
	PVector p12 = new PVector(-50, 0, -100);

	public TopThreeBars() {
	}

	public PShape createBar(int x, int y, int z, int height, PImage img) {
		PShape cube;
		//pushMatrix();
		translate(x, y, z);
		cube = createShape();
		cube.beginShape(QUAD);
		//img.resize(50, height);
		cube.texture(img);
		cube.vertex(p1.x, p1.y, p1.z + height, 1024, 0); // upper surface
		cube.vertex(p2.x, p2.y, p2.z + height, 0, 768);
		cube.vertex(p3.x, p3.y, p3.z + height, 1024, 768);
		cube.vertex(p4.x, p4.y, p4.z + height, 0, 0);

		cube.vertex(p1.x, p1.y, p1.z + height, 1024, 768); // right surface
		cube.vertex(p5.x, p5.y, p5.z, 0, 768);
		cube.vertex(p6.x, p6.y, p6.z, 1024, 768);
		cube.vertex(p2.x, p2.y, p2.z + height, 0, 0);

		cube.vertex(p3.x, p3.y, p3.z + height); // left
		cube.vertex(p7.x, p7.y, p7.z);
		cube.vertex(p8.x, p8.y, p8.z);
		cube.vertex(p4.x, p4.y, p4.z + height);

		cube.vertex(p8.x, p8.y, p8.z); // bottom
		cube.vertex(p5.x, p5.y, p5.z);
		cube.vertex(p6.x, p6.y, p6.z);
		cube.vertex(p7.x, p7.y, p7.z);

		cube.vertex(p8.x, p8.y, p8.z, 0, 0); // front
		cube.vertex(p4.x, p4.y, p4.z + height, 50, 0);
		cube.vertex(p1.x, p1.y, p1.z + height, 0, height);
		cube.vertex(p5.x, p5.y, p5.z, 50, height);

		cube.vertex(p6.x, p6.y, p6.z); // back
		cube.vertex(p2.x, p2.y, p2.z + height);
		cube.vertex(p3.x, p3.y, p3.z + height);
		cube.vertex(p7.x, p7.y, p7.z);

		cube.endShape(CLOSE);
		//popMatrix();

		return cube;
	}

}
