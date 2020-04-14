package com.tcd.gui;

import controlP5.Canvas;
import processing.core.PGraphics;
import processing.core.PImage;

public class TipScreen extends Canvas{
	private float x,y;
	private int width,height,tipNumber;
	private String message;
	private PImage img;
	public TipScreen(float x, float y, int tipNumber, int width,int height, String message, PImage img) {
		this.x=x;
		this.y=y;
		this.message=message;
		this.width=width;
		this.height=height;
		this.tipNumber=tipNumber;
		this.img=img;
	}
	
	public void draw(PGraphics pg) {
		pg.smooth();
		pg.fill(0);
		pg.strokeWeight(4);
		pg.stroke(120);
		pg.rect(x, y, width, height);
		pg.image(img,x,y);
		pg.fill(255);
		pg.textSize(13);
		pg.text("Tool tip "+tipNumber, x+40, y+25);
		pg.text(message, x+40, y+45);
	}

}
