package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TicTacToe extends JFrame {
	
	private static final long serialVersionUID = -7316299504675729229L;
	private static final ImageIcon ICON_X = new ImageIcon("img\\x-image.png");
	private static final ImageIcon ICON_O = new ImageIcon("img\\o-image.png");	
	private static final int[][] WINNING_COMBOS = {{0, 1, 2}, {0, 3, 6}, {0, 4, 8}, {1, 4, 7}, {2, 5, 8}, {2, 4, 6}, {3, 4, 5}, {6, 7, 8}};
	
	private static ArrayList<Integer> xList = new ArrayList<Integer>();
	private static ArrayList<Integer> oList = new ArrayList<Integer>();
	private static boolean isXTurn = true;
	private static boolean isDone = false;
	private static int numberOfTurns = 0;
	
	JPanel[] gamePanels = {new JPanel(), new JPanel(), new JPanel()};
	JPanel infoPanel = new JPanel();
	JButton[] gameButtons = {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()};
	JLabel infoLabel = new JLabel("Currently Up: X", SwingConstants.CENTER);
	JButton restartButton = new JButton("Restart");
	
	public TicTacToe() {
		super("Tic-Tac-Toe");
		setupGamePanels();
		for (JPanel p : gamePanels) {
			p.setLayout(new GridLayout(1, 3));
			add(p);
		}
		setupInfoPanel();
		add(infoPanel);
		setSize(900,800);
		setResizable(true);
		setLayout(new GridLayout(4, 1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void setupInfoPanel() {
		infoPanel.setLayout(new GridLayout(2, 1));
		infoLabel.setFont(new Font("Serif", Font.PLAIN, 64));
		infoPanel.add(infoLabel);
		JPanel restartPanel = new JPanel();
		restartPanel.setLayout(new GridLayout(1, 3));
		restartPanel.add(new JLabel());
		restartButton.setFont(new Font("Serif", Font.PLAIN, 64));
		restartButton.setBackground(Color.LIGHT_GRAY);
		restartButton.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		restartButton.setFocusPainted(false);
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		restartPanel.add(restartButton);
		restartPanel.add(new JLabel());
		infoPanel.add(restartPanel);
	}
	
	public void reset() {
		numberOfTurns = 0;
		isDone = false;
		isXTurn = true;
		infoLabel.setText("Currently Up: X");
		xList.clear();
		oList.clear();
		for (int i = 0; i < gameButtons.length; i++) {
			gameButtons[i].setBackground(Color.WHITE);
			gameButtons[i].setIcon(null);
		}
	}
	
	public void updateInfoLabel() {
		String currentlyUp = isXTurn ? "Currently Up: X" : "Currently Up: O";
		infoLabel.setText(currentlyUp);
	}
	
	public void setupGamePanels() {
		for (int i = 0; i < gameButtons.length; i++) {
			int temp = i;
			JButton button = gameButtons[i];
			button.setBackground(Color.WHITE);
			button.setFocusPainted(false);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean wasNull = setGamePanel(button, temp);
					aiPlay(wasNull);
				}
			});
			if (i == 1 || i == 7) { 
				button.setBorder(BorderFactory.createMatteBorder(0, 17, 0, 17, Color.BLACK));
			} else if (i == 3 || i == 5) {
				button.setBorder(BorderFactory.createMatteBorder(17, 0, 17, 0, Color.BLACK));
			} else if (i == 4) {
				button.setBorder(BorderFactory.createMatteBorder(17, 17, 17, 17, Color.BLACK));
			}
			if (i < 3) {
				gamePanels[0].add(button);
			} else if (i >= 3 && i < 6) {
				
				gamePanels[1].add(button);
			} else {
				gamePanels[2].add(button);
			}
		}
	}
	
	public boolean setGamePanel(JButton b, int i) {
	  boolean wasNull = b.getIcon() == null;
		if (wasNull && !isDone) {
			if (isXTurn) {
				b.setIcon(ICON_X);
				xList.add(i);
				isXTurn = false;
			} else {
				b.setIcon(ICON_O);
				oList.add(i);
				isXTurn = true;
			}
			numberOfTurns++;
			checkForWin();
			if (!isDone) {
				if (numberOfTurns > 8) {
					infoLabel.setText("TIE");
					isDone = true;
					return false;
				}
				updateInfoLabel();
			}
		}
		return wasNull;
	}
	
	public void checkForWin() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < WINNING_COMBOS.length; j++) {
				if (i == 0) {
					checkForWinInd(true, i, j);
				} else {
					checkForWinInd(false, i, j);
				}
			}
		}
	}
	
	public void checkForWinInd(boolean isX, int i, int j) {
	  ArrayList<Integer> list = isX ? xList : oList;
 	  if (list.contains(WINNING_COMBOS[j][0]) && list.contains(WINNING_COMBOS[j][1]) && list.contains(WINNING_COMBOS[j][2])) {
      for (int k = 0; k < 3; k++) {
        gameButtons[WINNING_COMBOS[j][k]].setBackground(Color.GREEN);
      }
      String winner = isX ? "X" : "O";
      infoLabel.setText(winner + " WINS");
      isDone = true;
    }
	}
	
	public ArrayList<Integer> getAllEmpty() {
	  ArrayList<Integer> list = new ArrayList<Integer>();
	  for (int i = 0; i < 9; i++) {
	    if (!xList.contains(i) && !oList.contains(i)) {
	      list.add(i);
	    }
	  }
	  return list;
	}
	
	public int getRandomEmpty() {
	  ArrayList<Integer> list = this.getAllEmpty();
	  if (!list.isEmpty()) {
	    Random rand = new Random();
	    return list.get(rand.nextInt(list.size()));
	  }
	  return -1;
	}
	
	public void aiPlay(boolean wasNull) {
	  if (wasNull && !isDone) {
	    int rand = this.getRandomEmpty();
	    setGamePanel(this.gameButtons[rand], rand);
	  }
	}
	
}
