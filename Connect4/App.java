import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JFrame;

public class App extends JFrame 
	implements Runnable, MouseListener{
	
	static int[] gameState = {0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0};
	
	private int turn = 0;
	
	private int baseSize = 100;
	
	private Component board;
	
	public App() {
		initUI();
		
		//Thread play = new Thread(this);
		//play.start();
	}
	
	private void initUI() {
		board = add(new Board());
		
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
		int col = e.getX()/baseSize;
		int row = 5;
		while (gameState[col+(row*7)] != 0) {
			row--;
			if (row == -1) {
				return;
			}
		}
		gameState[col+(row*7)] = turn%2 + 1; 
		board.repaint();
		if (checkWin(row,col)) {
			reset();
			return;
		};
		turn++;
	}
	
	private void reset() {
		Arrays.fill(gameState, 0);
		turn = 0;
		board.repaint();
	}
	
	private boolean checkWin(int r, int c) {
		int count = 0;
		int target = turn % 2 + 1;
		for (int i = 0; i < 7; i++) {
			if (gameState[7*r + i] == target) {
				count++;
			}
			else {
				count = 0;
			}
			if (count == 4) {
				return true;
			}
		}
		count = 0;
		for (int j = 0; j < 6; j++) {
			if (gameState[7*j + c] == target) {
				count++;
			}
			else {
				count = 0;
			}
			if (count == 4) {
				return true;
			}
		}
		count = 0;
		int i = c+0, j = r+0;
		while (i > 0 && j > 0) {
			i--;
			j--;
		}
		while (i < 7 && j < 6) {
			if (gameState[7*j + i] == target) {
				count++;
			}
			else {
				count = 0;
			}
			if (count == 4) {
				return true;
			}
			i++;
			j++;
		}
		count = 0;
		i = c+0;
		j = r+0;
		while (i > 0 && j < 5) {
			i--;
			j++;
		}
		System.out.println(i);
		System.out.println(j);
		while (i < 7 && j >= 0) {
			if (gameState[7*j + i] == target) {
				count++;
			}
			else {
				count = 0;
			}
			if (count == 4) {
				return true;
			}
			i++;
			j--;
		}
		return false;
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
