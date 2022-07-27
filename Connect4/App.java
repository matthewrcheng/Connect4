import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class App extends JFrame 
	implements Runnable, MouseListener{
	
	static int[] gameState = {0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0,
							  0,2,0,0,0,0,0,
							  1,1,2,0,0,0,0};
	
	public App() {
		initUI();
	}
	
	private void initUI() {
		add(new Board());
		
		int baseSize = 100;
		setSize(7*baseSize,6*baseSize);
		
		setTitle("Connect Four");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		addMouseListener(this);
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> {
			App ex = new App();
			ex.setVisible(true);
		});
	}
	
	@Override
	public void run() {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("clicked!");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
