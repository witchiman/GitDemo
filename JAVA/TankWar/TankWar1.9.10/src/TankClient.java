import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.util.List;

public class TankClient extends Frame {
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600; 
		
	Tank myTank  = new Tank(50,50,true,Direction.STOP,this);
	List<Tank> tanks = new ArrayList<Tank>();
	
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	
	Image offScreenImage = null;	
	
	public NetClient nc = new NetClient(this);
	ConnDialog dialog = new ConnDialog();
	
	@Override
	public void paint(Graphics g) {
		
		g.drawString("missiles:" + missiles.size(), 30, 50);
		g.drawString("explodes count:" + explodes.size(),30, 70);
		g.drawString("tanks count:" + tanks.size(),30, 90);
		
		myTank.draw(g);	
		for(int i=0;i<missiles.size();i++) {
			Missile m = missiles.get(i);
			if(m.hitTank(myTank)){
				TankDeadMsg msg1 = new TankDeadMsg(myTank.id);
				nc.send(msg1);
				MissileDeadMsg msg2= new MissileDeadMsg(m.tankId, m.id);
				nc.send(msg2);
			}
			//m.hitTanks(tanks);
			m.draw(g);
		}	
		
		for(int i=0;i<explodes.size();i++) {
			Explode e = explodes.get(i);
			e.draw(g);			
		}
		
		for(int i=0;i<tanks.size();i++) {
			Tank t = tanks.get(i);
			t.draw(g);
		}
		
		
		
	}
	
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);			
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = g.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
		
	}

	public void launchFrame() {
		/*for(int i=0; i<10; i++) {
			tanks.add(new Tank(50 + 50 *(i+1), 50, false, Direction.D, this));
		}*/
		
		//nc.connect("127.0.0.1", TankServer.TCP_PORT);
		
		this.setLocation(400,100);
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter() {
		
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.setVisible(true);
		
		new Thread(new PaintThread()).start();
		this.addKeyListener(new KeyMonitor());
	}
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();

	}
	
	private class PaintThread implements  Runnable {

		@Override
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}				
						
		}
		
	}
	
	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_C) {
				dialog.setVisible(true);
				
			}else myTank.keyPressed(e);
		}
		
	}
	
	class ConnDialog extends Dialog {
		
		Button b = new Button("È·¶¨");
		TextField tfIP = new TextField("127.0.0.1", 12);
		TextField tfPort = new TextField("" + TankServer.TCP_PORT, 4);
		TextField tfUDPPort = new TextField("2223", 4);
		public ConnDialog() {
			super(TankClient.this, true);
			
			this.setLayout(new FlowLayout());
			this.setLocation(300, 300);
			this.add(new Label("IP:"));
			this.add(tfIP);
			this.add(new Label("Port:"));
			this.add(tfPort);
			this.add(new Label("UDPPort:"));
			this.add(tfUDPPort);
			this.add(b);
			this.pack();
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					setVisible(false);
				}				
			});
			
			b.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					String IP = tfIP.getText().trim();
					int port = Integer.valueOf(tfPort.getText().trim());
					int udpPort = Integer.valueOf(tfUDPPort.getText().trim());
					nc.setUdpPort(udpPort);
					nc.connect(IP, port);
					setVisible(false);
					
				}
			});
		}
	}

}
