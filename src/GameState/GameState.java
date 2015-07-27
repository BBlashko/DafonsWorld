package GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract void mouseWheelMoved(MouseWheelEvent event);
	public abstract void mouseClicked(MouseEvent event);
	public abstract void mouseEntered(MouseEvent event);
	public abstract void mouseExited(MouseEvent event);
	public abstract void mousePressed(MouseEvent event);
	public abstract void mouseReleased(MouseEvent event);
	public abstract void mouseDragged(MouseEvent event);
	public abstract void mouseMoved(MouseEvent event);
}