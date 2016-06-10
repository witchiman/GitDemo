import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Yard extends Frame {
	PaintThread paintThread = new PaintThread();
	public static final int ROWS = 30;
	public static final int COLS = 30;
	public static final int BLOCK_SIZE = 15;
	Snake s = new Snake(this);
	Egg e = new Egg();
	Image offScreenImage = null;
	private int score = 0;
	public boolean gameOver = false;
	private Font fontGameOver = new Font("ËÎÌå",Font.BOLD,60);
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(COLS * BLOCK_SIZE,ROWS * BLOCK_SIZE);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage,0, 0, null);
	}
	public void launch() {
		this.setLocation(200,200);
		this.setSize(COLS *BLOCK_SIZE, ROWS * BLOCK_SIZE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			 
		});
		this.setVisible(true);
		new Thread(paintThread).start();
		this.addKeyListener(new keyMonitor());
	} 
	public static void main(String[] args) {
	    Yard yard = new Yard();
	    yard.launch();
		
	}
	
	public void stop() {
		gameOver = true;
	}
	
	@Override
	public void paint(Graphics g) {
		Color c =g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0 , 0, COLS * BLOCK_SIZE, ROWS *BLOCK_SIZE);
		g.setColor(Color.DARK_GRAY);
		for(int i=1;i<ROWS;i++) {
			g.drawLine(0, BLOCK_SIZE * i, BLOCK_SIZE * COLS, BLOCK_SIZE * i);
		}	
		for(int i=1;i<COLS;i++) {
			g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, BLOCK_SIZE * COLS);
		}
		
		g.setColor(Color.YELLOW);
		g.drawString("Score:" + score, 10, 40);	
		
		if(gameOver) {
			g.setFont(fontGameOver);
			g.drawString("ÓÎÏ·½áÊø", 110,180);			
			paintThread.gameOver();			
		}
		g.setColor(c);
		s.eat(e);
		s.draw(g);	
	}
	
	
	class PaintThread implements Runnable {
		private boolean running = true;
		@Override
		public void run() {
			while(running) {
				repaint();
				try {
					Thread.sleep(200);					
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		public void gameOver() {
			running = false;
		}
		
		public void gameNotOver() {
			running = true;
		}
		
	}
	
	private class keyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			s.keyPressed(e);
		}
		
	}
}
