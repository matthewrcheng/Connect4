import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class App extends JFrame 
	implements Runnable, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static int[] gameState = {0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0,
							  0,0,0,0,0,0,0};
	
	private int turn = 0;
	
	private int baseSize = 100;
	
	private Component board;
	private JPanel menu;
	
	public App() {
		initMenu();
		
		//Thread play = new Thread(this);
		//play.start();
	}
	
	private void initMenu() {
		// menu = add(new MainMenu());
		menu = new JPanel();
		setSize(4*baseSize,4*baseSize);
		setResizable(false);
		setTitle("Connect Four");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JButton playone = new JButton("One Player");
		JButton playtwo = new JButton("Two Players");
		ActionListener al2 = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        initUI();
		        closeMenu();
		    }
		};

		playtwo.addActionListener(al2);
		
		JButton exit = new JButton("Exit");
		ActionListener ale = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        System.exit(0);
		    }
		};

		exit.addActionListener(ale);
		
		menu.add(playone);
		menu.add(playtwo);
		menu.add(exit);
		
		this.getContentPane().add(menu);
	}
	
	private void initUI() {
		board = add(new Board());
		
		setSize(7*baseSize,6*baseSize);
		
		setTitle("Connect Four");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JButton r = new JButton("Reset"); // Create File menu
		JButton mm = new JButton("Main Menu"); // Create Elements menu
		JButton e = new JButton("Exit");
	    
	    r.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reset();
			}
	    });
	    
	    mm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				remove(board);
				setJMenuBar(null);
				initMenu();
			}
		});
	    
	    e.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		System.exit(0);
	    	}
	    });
	    
	    menuBar.add(r); // Add the file menu
	    menuBar.add(mm); // Add the element menu
		menuBar.add(e);
	    
		addMouseListener(this);
		
		
	}
	
	private void closeMenu() {
		this.getContentPane().remove(menu);
		
	}
	
	private void openMenu() {
		this.getContentPane().add(menu);
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
		// if winner, reset
		if (checkWin(row,col)) {
			int option = JOptionPane.showConfirmDialog(null,
					"Play Again?","Player " + turn%2+1 + " wins!", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				reset();
			} else {
				reset();
				remove(board);
				setJMenuBar(null);
				initMenu();
			}
			return;
//			reset();
//			
//			JDialog jd = new JDialog(this);
//			
//			jd.setSize(200,200);
//			jd.setLocationRelativeTo(null);
//			
//			JLabel jl = new JLabel("Player " + turn%2+1 + " wins!");
//			JButton pa = new JButton("Play Again");
//			JButton mm = new JButton("Main Menu");
//			
//			pa.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					reset();
//				}
//			});
//			mm.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					reset();
//					remove(board);
//					setJMenuBar(null);
//					initMenu();
//				}
//			});
//			
//			jd.add(jl);
//			jd.add(pa);
//			jd.add(mm);
//			
//			jd.setVisible(true);
//			return;
		}
		// if tie, reset
		if (turn == 41) { 
			int option = JOptionPane.showConfirmDialog(null,
					"Play Again?","It's a tie!", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				reset();
			} else {
				reset();
				remove(board);
				setJMenuBar(null);
				initMenu();
			}
			return;
//			JDialog jd = new JDialog(this);
//			jd.setLayout(new FlowLayout());
//	        jd.setBounds(500, 300, 400, 300);
//			JLabel jl = new JLabel("It's a tie!");
//			JButton pa = new JButton("Play Again");
//			JButton mm = new JButton("Main Menu");
//			pa.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					reset();
//				}
//			});
//			mm.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					reset();
//					Window win = SwingUtilities.getWindowAncestor(board);
//			        if (win != null) {
//			            win.dispose();  // dispose of it
//			        }
//					openMenu();
//				}
//			});
//			jd.add(jl);
//			jd.add(pa);
//			jd.add(mm);
//			jd.setVisible(true);
//			return;
		}
		// increment turn
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
