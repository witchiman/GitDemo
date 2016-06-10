import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missile {
	public static final int XSPEED = 20;
	public static final int YSPEED = 20;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	int x,y;
	Direction dir;
	private boolean live =true;
	private TankClient tc;
	private boolean good;
	

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] missileImgs = null;
	
	private static Map<String, Image> imgs = new HashMap<String, Image>(); 
	
	static {
		missileImgs = new Image[] {
				tk.createImage(Tank.class.getClassLoader().getResource("missileD.gif")),		
				tk.createImage(Tank.class.getClassLoader().getResource("missileL.gif")),
				tk.createImage(Tank.class.getClassLoader().getResource("missileD.gif")),
				tk.createImage(Tank.class.getClassLoader().getResource("missileLU.gif")),
				tk.createImage(Tank.class.getClassLoader().getResource("missileR.gif")),
				tk.createImage(Tank.class.getClassLoader().getResource("missileRD.gif")),				
				tk.createImage(Tank.class.getClassLoader().getResource("missileRU.gif")),				
				tk.createImage(Tank.class.getClassLoader().getResource("missileU.gif"))
		};
		
		imgs.put("D", missileImgs[0]);
		imgs.put("L", missileImgs[1]);
		imgs.put("LD", missileImgs[2]);
		imgs.put("LU", missileImgs[3]);
		imgs.put("R", missileImgs[4]);
		imgs.put("RD", missileImgs[5]);
		imgs.put("RU", missileImgs[6]);
		imgs.put("U", missileImgs[7]);
	}
	
	public boolean isLive() {
		return live;
	}


	public Missile(int x, int y,boolean good, Direction dir, TankClient tc) {		
		this.x = x;
		this.y = y;
		this.good = good;
		this.dir = dir;
		this.tc  =tc;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		
		switch(dir) {
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:			
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);	
			break;
		
		}
		move();
	}

	private void move() {
		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= XSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:			
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		}
		
		if(x<0 || y<0 || x>TankClient.GAME_WIDTH || y>TankClient.GAME_HEIGHT) {
			live = false;					
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean hitTank(Tank t) {
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {
			if(!good) {
				t.setLife(t.getLife() - 20);
				if(t.getLife() <=0) t.setLive(false);
			}else t.setLive(false);
			
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			this.live = false;
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			if(hitTank(tanks.get(i))) return true;
		}
		
		return false;
	}
	
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		
		return false;
	}
	
}
