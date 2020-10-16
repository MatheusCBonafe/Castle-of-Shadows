package com.acidbliss.world;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class World {
	
	public World(String path) { //declaring the Buffered Image here helps save memory
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for (int i = 0; i < pixels.length; i++) {
				//TODO
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  	
		
	}
}
