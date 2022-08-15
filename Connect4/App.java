import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Random;
import java.lang.Math;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
	
	private boolean single = false;
	
	private Random rand = new Random();
	
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
		ActionListener al1 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				single = true;
				initUI();
				closeMenu();
			}
		};
		playone.addActionListener(al1);
		
		JButton playtwo = new JButton("Two Players");
		ActionListener al2 = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	single = false;
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
					"Play Again?","Player " + (turn%2+1) + " wins!", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				reset();
			} else {
				reset();
				remove(board);
				setJMenuBar(null);
				initMenu();
			}
			return;
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
		}
		if (single) {
			int move = computerTurn();
			// if winner, reset
			if (checkWin(move/7,move%7)) {
				int option = JOptionPane.showConfirmDialog(null,
						"Play Again?","Player " + (turn%2+1) + " wins!", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					reset();
				} else {
					reset();
					remove(board);
					setJMenuBar(null);
					initMenu();
				}
				return;
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
			}
		}
		// increment turn
		turn++;
	}
	
	private SolvingTree generateChildren(SolvingTree root, int[] curState) {
		for (int col = 0; col<7; col++) {
			int row = 5;
			boolean found = true;
			while (curState[col+(row*7)] != 0) {
				row--;
				if (row == -1) {
					found = false;
					break;
				}
			}
			if (!found) {
				continue;
			}
			root.addChild(new SolvingTree(col+(row*7)));
		}
		return root;
	}
	
	private int evaluate(int player, int index, int[] curState) {
		int opp = (player == 1) ? 2 : 1;
		boolean foundOpp = false;
		int best;
		// check horizontal
		int count = 1;
		for (int i = index-1; i>=(index-(index%7)); i--) {
			if (curState[i] == player) {
				count++;
			} else if (curState[i] == opp && ((index+7-(index%7))-i)<4){
				foundOpp = true;
				break;
			} else {
				break;
			}
		}
		for (int i = index+1; i<(index+7-(index%7)); i++) {
			if (curState[i] == player) {
				count++;
			} else if (curState[i] == opp && (i-(index-(index%7)))<4){
				foundOpp = true;
				break;
			} else {
				break;
			}
		}
		if (foundOpp) {
			count = 0;
		}
		best = count;
		int bests = 1;
		// check vertical, cannot go higher than current piece
		count = 1;
		foundOpp = false;
		for (int i = index+7; i<42; i+=7) {
			if (curState[i] == player) {
				count++;
			} else if (curState[i] == opp && i<28){
				foundOpp = true;
				break;
			} else {
				break;
			}
		}
		if (foundOpp) {
			count = 0;
		}
		if (count > best) {
			best = count;
		} else if (count == best) {
			bests++;
		}
		// check positive diagonal
		count = 1;
		foundOpp = false;
		int i = index%7-1;
		int j = index/7+1;
		while (i >= 0 && j < 6) {
			if (curState[j*7+i] == player) {
				count++;
				i--;
				j++;
			} else if (curState[j*7+i] == opp && (i>=3 || j<4)){
				foundOpp = true;
				break;
			} else {
				break;
			}
		}
		i = index%7+1;
		j = index/7-1;
		while (i < 7 && j >= 0) {
			if (curState[j*7+i] == player) {
				count++;
				i++;
				j--;
			} else if (curState[j*7+i] == opp && (i<=3 || j>2)){
				foundOpp = true;
				break;
			} else {
				break;
			}
		}
		if (foundOpp) {
			count = 0;
		}
		if (count > best) {
			best = count;
			bests = 1;
		} else if (count == best) {
			bests++;
		}
		// check negative diagonal
		count = 1;
		foundOpp = false;
		i = index%7-1;
		j = index/7-1;
		while (i >= 0 && j >= 0) {
			if (curState[j*7+i] == player) {
				count++;
				i--;
				j--;
			} else if (curState[j*7+i] == opp && (i>=3 || j>2)){
				foundOpp = true;
				break;
			} else {
				break;
			}
		}
		i = index%7+1;
		j = index/7+1;
		while (i < 7 && j < 6) {
			if (curState[j*7+i] == player) {
				count++;
				i++;
				j++;
			} else if (curState[j*7+i] == opp && (i<=3 || j<4)){
				foundOpp = true;
				break;
			} else {
				break;
			}
		}
		if (foundOpp) {
			count = 0;
		}
		if (count > best) {
			best = count;
			bests = 1;
		} else if (count == best) {
			bests++;
		}
		switch (best) {
			case 0:
				return 0;
			case 1:
				return 1*bests;
			case 2:
				return 5*bests;
			case 3:
				return 10*bests;
			default:
				return 10000;
		}
	}
	
	/*
	private int[] recursiveDecision(int iteration, int[] tempState) {
		if (iteration == 1) {
			int[] subState = tempState.clone();
			subState[sst.index] = 1;
			int subscore = evaluate(1, sst.index, subState);
			if (subscore > bestSub) {
				count = 1;
				bestSub = subscore;
			} else if (subscore == bestSub) {
				count++;
			}
		} else {
			
		}
		int score = 0;
		int count = 0;
		return new int[] {score, count};
	}
	*/
	
	private int computerTurn() {
		SolvingTree tree = new SolvingTree(-1);
		int[] tempState;
		
		tree = generateChildren(tree, gameState);
		
		int index = 0;
		int best = -10000000;
		int bestCount = 100;
		for (SolvingTree st : tree.children) {
			tempState = gameState.clone();
			tempState[st.index] = 2;
			generateChildren(st, tempState);
			int score = evaluate(2, st.index, tempState);
			int bestSub = -100;
			int count = 1;
			// calculate turn 1 score
			for (SolvingTree sst : st.children) {
				int[] subState = tempState.clone();
				subState[sst.index] = 1;
				int subscore = evaluate(1, sst.index, subState);
				if (subscore > bestSub) {
					count = 1;
					bestSub = subscore;
				} else if (subscore == bestSub) {
					count++;
				}
			}
			score -= bestSub;
			if (score > best || (score == best && count < bestCount)) {
				best = score;
				bestCount = count;
				index = st.index;
			}
		}
		
		gameState[index] = 2;
		turn++;
		return index;
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
