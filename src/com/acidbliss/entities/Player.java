package com.acidbliss.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.acidbliss.main.Game;

public class Player extends Entity{

	public boolean right, up, left, down;
	public int rightDir = 0, leftDir = 1;
	public int dir = rightDir;
	public double speed = 2.0;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean isMoved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
		}
		for (int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
		}
	}	
	

	public void tick() {
		isMoved = false;
		if (right) {
			isMoved = true;
			dir = rightDir;
			x+=speed;
		}
		else if (left) {
			isMoved = true;
			dir = leftDir;
			x-=speed;
		}
		if (up) { 
			isMoved = true;
			y-=speed;
		}
		else if (down) {
			isMoved = true;
			y+=speed;
		}
		if (isMoved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0; 
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if (dir == rightDir) {
			g.drawImage(rightPlayer[index], this.getX(), this.getY(), null);
		} else if (dir == leftDir) {
			g.drawImage(leftPlayer[index], this.getX(), this.getY(), null);
		}
	}
}
