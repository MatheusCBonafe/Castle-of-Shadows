package com.acidbliss.world;

public class Camera {

	public static int x = 0;
	public static int y = 0;
	
	public static int clamp(int currentPos, int min, int max) {
		if (currentPos < min) 
			currentPos = min;
		
		if (currentPos > max) 
			currentPos = max;
		return currentPos;
	}
}
	
	

