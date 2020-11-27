package com.acidbliss.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.acidbliss.main.Game;
import com.acidbliss.world.Camera;
import com.acidbliss.world.World;

public class Enemy extends Entity{
	
	private double speed = 0.6;
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;
	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	private BufferedImage[] sprites;
	
	


	public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(128, 16, 16, 16);

	}
	
	public void tick() {
		if (isCollidingWithPlayer() == false) {
			if (x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
				&& !isColliding((int)(x+speed), this.getY())) {
				x+=speed;
			} else if (x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
					   && !isColliding((int)(x-speed), this.getY())) {
				x-=speed;
			}
			if (y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
				&& !isColliding(this.getX(), (int)(y+speed))) {
				y+=speed;
			} else if (y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))
					   && !isColliding(this.getX(), (int)(y-speed)) ) {
				y-=speed;
			}
		} else {
			if (Game.rand.nextInt(100) < 10) {
				Player.life-=Game.rand.nextInt(3);
				if (Player.life <= 0) {
					System.exit(1);
				}
			}
				System.out.println("Life: "+ Player.life);
			}
		
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0; 
			}
		}
	}
	
	public boolean isCollidingWithPlayer() {
		
		Rectangle currentEnemy = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		
		return currentEnemy.intersects(player);
		
	}
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle currentEnemy = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskw, e.getY() + masky, maskw, maskh);
			if (currentEnemy.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}
}
