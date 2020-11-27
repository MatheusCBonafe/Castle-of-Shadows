package com.acidbliss.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.acidbliss.main.Game;
import com.acidbliss.world.Camera;
import com.acidbliss.world.World;

public class Player extends Entity{

	public boolean right, up, left, down;
	public static final int TILE_SIZE = 16;
	public int rightDir = 0, leftDir = 1;
	public int dir = rightDir;
	public double speed = 1.4;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean isMoved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	public static double life = 100, maxLife = 100;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*TILE_SIZE), 0, TILE_SIZE, TILE_SIZE);
		}
		for (int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*TILE_SIZE), TILE_SIZE, TILE_SIZE, TILE_SIZE);
		}
	}	
	

	public void tick() {
		isMoved = false;
		if (right && World.isFree((int)(x+speed), this.getY())) {
			isMoved = true;
			dir = rightDir;
			x += speed;
		}
		else if (left && World.isFree((int)(x-speed), this.getY())) {
			isMoved = true;
			dir = leftDir;
			x-=speed;
		}
		if (up && World.isFree(this.getX(),(int)(y-speed))) { 
			isMoved = true;
			y -= speed;
		}
		else if (down && World.isFree(this.getX(),(int)(y+speed))) {
			isMoved = true;
			y += speed;
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
		checkCollisionPotion();	
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*TILE_SIZE - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*TILE_SIZE - Game.HEIGHT);

		}
	}
	
	public void checkCollisionPotion() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if (current instanceof Potion) {
				if (Entity.isColliding(this, current)) {
					life+=10;
					if (life >= 100) 
						life = 100;
					Game.entities.remove(current);
				}
			}
		}
	}

	public void render(Graphics g) {
		if (dir == rightDir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if (dir == leftDir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y,	 null);
		}
	}
}
