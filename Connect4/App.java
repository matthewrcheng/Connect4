import java.awt.EventQueue;
import javax.swing.JFrame;

public class App extends JFrame implements Runnable {
	
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
}
