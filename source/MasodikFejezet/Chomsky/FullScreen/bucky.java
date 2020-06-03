import java.awt.*;
import javax.swing.JFrame;

public class bucky extends JFrame {
	public static void main(String[] args) {
		
		DisplayMode dm = new DisplayMode(800,600,16, DisplayMode.REFRESH_RATE_UNKNOWN);
		bucky b = new bucky();
		b.run(dm);
		
	}

	public void run(DisplayMode dm) {
		getContentPane().setBackground(Color.DARK_GRAY);
		setForeground(Color.WHITE);
		setFont(new Font("Arial", Font.PLAIN, 24));
		
		Screen s = new Screen();
		try {
			s.setFullScreen(dm, this);
			try {
				Thread.sleep(5000);
			}catch(Exception ex) {} 
		}finally {
			s.restoreScreen();
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g); 
		g.drawString("Ez Egy Szoveg", 200, 200);
	}
}

