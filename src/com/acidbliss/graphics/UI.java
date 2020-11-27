package com.acidbliss.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.acidbliss.entities.Player;

public class UI {
	

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(8, 4, 70, 6);
		g.setColor(Color.RED);
		g.fillRect(8, 4, (int)(Player.life/Player.maxLife*70), 6);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 7));
		g.drawString((int)Player.life+"/"+(int)Player.maxLife,32,9);
	}
}
