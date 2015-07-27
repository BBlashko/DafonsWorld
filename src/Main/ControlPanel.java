package Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Entity.Player;
import GameState.GameStateManager;

public class ControlPanel extends JPanel implements Runnable, KeyListener, MouseWheelListener, MouseListener, MouseMotionListener {
	

	private static final long serialVersionUID = 2221564594355987670L;
	
	public static final int HEIGHT = 700;
	public static final int WIDTH = 1200;
	public static final int SCALE = 2;
	
	private Player player;
	
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000/FPS;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private GameStateManager gsm;
	private Stats stats;
	
	private JFrame window;
	private int mouseX = 0;
	private int mouseY = 0;
	private int mouseDx = 0;
	private int mouseDy = 0;
	
	public ControlPanel(final JFrame window){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		this.window = window;
		
		System.out.println("Control Panel Load... Success");
		
	}
	public void addNotify(){
		super.addNotify();
		if(thread == null) {
			System.out.println("add notify");
			thread = new Thread(this);
			addKeyListener(this);
			addMouseWheelListener(this);
			addMouseMotionListener(this);
			addMouseListener(this);
			thread.start();
		}
	}
	public void init(){
		this.setBackground(new Color(50, 50, 50, 0));
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		gsm = new GameStateManager();
		stats = new Stats();
		System.out.println("Control Panel initialization... Success");
	}
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		//gameLoop
		

		while(running){
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			
			elapsed = System.nanoTime() - start;
			wait  = targetTime - elapsed / 1000000;
			if(wait < 0){
				wait = 5;
			}
			
			try{
				
				
				Thread.sleep(wait);
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	private void update(){
		
		gsm.update();
		
	}
	private void draw(){
		
		gsm.draw(g);
	}
	private void drawToScreen(){
		Graphics g2 = getGraphics();
		g2.drawImage(image,0,0, WIDTH, HEIGHT, null);
		g2.dispose();
	}
	public void keyTyped(KeyEvent key){
	}
	public void keyPressed(KeyEvent key){
		gsm.keyPressed(key.getKeyCode());
		
	}
	public void keyReleased(KeyEvent key){
		gsm.keyReleased(key.getKeyCode());
	}
	public void mouseWheelMoved(MouseWheelEvent event) {
		gsm.mouseWheelMoved(event);
		
	}
	public void mouseClicked(MouseEvent event) {
		gsm.mouseClicked(event);
		
	}
	public void mouseEntered(MouseEvent event) {
		gsm.mouseEntered(event);
		
	}
	public void mouseExited(MouseEvent event) {
		gsm.mouseExited(event);
		
	}
	public void mousePressed(MouseEvent event) {
		gsm.mousePressed(event);
		if(gsm.getState() == 0 || gsm.getState() == 1){
			mouseX = event.getX();
			mouseY = event.getY();
			getComponentAt(mouseX, mouseY);
		}
	}
	public void mouseReleased(MouseEvent event) {
		gsm.mouseReleased(event);
		
	}
	public void mouseDragged(MouseEvent event) {
		gsm.mouseDragged(event);
		if(gsm.getState() == 0 || gsm.getState() == 1){
			int windowX = window.getX();
			int windowY = window.getY();
			
			
			mouseDx = (windowX + event.getX()) - (windowX + mouseX);
			mouseDy = (windowY + event.getY()) - (windowY + mouseY);
			
			int setX = windowX + mouseDx;
			int setY = windowY + mouseDy;
			window.setLocation(setX, setY);
		}
	}
	public void mouseMoved(MouseEvent event) {
		gsm.mouseMoved(event);
		
	}
}
