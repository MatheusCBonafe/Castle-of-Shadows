package com.acidbliss.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.acidbliss.entities.Ammo;
import com.acidbliss.entities.Enemy;
import com.acidbliss.entities.Entity;
import com.acidbliss.entities.Potion;
import com.acidbliss.entities.Weapon;
import com.acidbliss.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(String path) { //declaring the Buffered Image here helps save memory
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
				for (int xx = 0; xx < map.getWidth(); xx++) {
					for (int yy = 0; yy < map.getHeight(); yy++) {
						int pixelAtual = pixels[xx + (yy * map.getWidth())];
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_FLOOR);
						
						switch(pixelAtual) {
							case 0xFF000000:
								tiles[xx + (yy * WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_FLOOR);
							break;
							
							case 0xFFFFFFFF:
								tiles[xx + (yy * WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_WALL);
							break;
							
							case 0xFF0026FF:
								Game.player.setX(xx*TILE_SIZE);
								Game.player.setY(yy*TILE_SIZE);
							break;
							
							case 0xFFFF0000:
								Enemy en = new Enemy(xx*TILE_SIZE, yy*TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.ENEMY_EN);
								Game.entities.add(en);
								Game.enemies.add(en);
							break;							
							
							case 0xFFFF6A00:
								Game.entities.add(new Weapon(xx*TILE_SIZE, yy*TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.WEAPON_EN));
							break;
							
							case 0xFF4CFF00:
								Potion pot = new Potion(xx*TILE_SIZE, yy*TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.POTION_EN);
								pot.setMask(8, 8, 8, 8);
								Game.entities.add(pot);
								
							break;
							
							case 0xFFFFD800:
								Game.entities.add(new Ammo(xx*TILE_SIZE, yy*TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.AMMO_EN));
							break;

						}
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE-1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE-1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) 
					continue;
			
				Tile tile = tiles[xx + (yy * WIDTH)];
					tile.render(g);
			}
		}
	}
	
}
