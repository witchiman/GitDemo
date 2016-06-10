import java.awt.*;
import java.awt.event.KeyEvent;

public class Blood {
	private int x, y, w, h;
	private int[][] pos ={
						{200,202},{210,210},{220,205},{245,210},{260,215},{280,230},{300,257},{315,260},{340,280},{360,300},{386,311},{400,290},{381,250},{350,225},
						{345,210},{310,210},{285,204},{250,200},{245,191},{230,196},{214,200},{210,205},{207,202}
	                      };
	int step = 0;
	TankClient tc ;
	private boolean live = true;
	
	Blood(TankClient tc) {
		x = pos[step][0];
		y = pos[step][1];
		this.tc =  tc;
		w = h = 15;
	}
	
	public void draw(Graphics g) {
		if(!isLive()) return;
		
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y,w , h);	
		move();
		g.setColor(c);
	}
	
	public void move() {
		step ++;
		if(step == pos.length) step = 0;
		x = pos[step][0];
		y = pos[step][1];
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void keyRealesed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_F3)live = true;		
	}
	
}
