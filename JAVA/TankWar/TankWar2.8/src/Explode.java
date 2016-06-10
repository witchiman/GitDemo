import java.awt.*;

public class Explode {
	int x,y;
	private boolean live = true;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] imgs = {
			tk.createImage(Explode.class.getClassLoader().getResource("0.gif")),
			tk.createImage(Explode.class.getClassLoader().getResource("1.gif")),
			tk.createImage(Explode.class.getClassLoader().getResource("2.gif")),
			tk.createImage(Explode.class.getClassLoader().getResource("3.gif")),
			tk.createImage(Explode.class.getClassLoader().getResource("4.gif")),
			tk.createImage(Explode.class.getClassLoader().getResource("5.gif")),
			tk.createImage(Explode.class.getClassLoader().getResource("6.gif")),
			tk.createImage(Explode.class.getClassLoader().getResource("7.gif")),
			tk.createImage(Explode.class.getClassLoader().getResource("8.gif")),
			tk.createImage(Explode.class.getClassLoader().getResource("9.gif")),
			tk.createImage(Explode.class.getClassLoader().getResource("10.gif"))
			
	};
	
	private boolean init = false;
	
	int step = 0;
	private TankClient tc;
	
	Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(!live) return;
		
		
		if(step == imgs.length) {
			tc.explodes.remove(this);
			live = false;
			step = 0;
			return;
		}
		
		g.drawImage(imgs[step], x, y, null);
		step++;
	}
	
}
