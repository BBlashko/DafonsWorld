package Main;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Window {
	private static JFrame window;
	
	public static void main(String[] args) {
		
		//custom cursor
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursor = toolkit.getImage("Resources/HUD/cursor.png");
		
		//set the Jframe Window
		JFrame window = new JFrame("Game");
		window.setUndecorated(true);
		window.setCursor(toolkit.createCustomCursor(cursor, new Point(14,14), "cursor"));
		window.setContentPane(new ControlPanel(window));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		window.pack();
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		System.out.println("Window Load... Success");
	}
}
