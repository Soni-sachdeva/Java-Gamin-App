package brickBrackerGame;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

	private int totalBricks = 21;

	private Timer timer;

	private int score = 0;

	private int playerX = 310;
	private int ballpositionX = 150;
	private int ballpositionY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;

	private boolean play = false;

	private int delay = 8;

	private MapGenerator map;

	public GamePlay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paint(Graphics g) {
		
		// background
		
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);

		// draw map
		
		map.draw((Graphics2D) g);
		
		//score
		
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590,30);
		
		// border

		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(692, 0, 3, 592);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(692, 0, 3, 592);
		
		// the paddle

		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 4);

		// the ball
		
		g.setColor(Color.yellow);
		g.fillOval(ballpositionX, ballpositionY, 20, 20);
		
		if(ballpositionY > 570) {
			play = false;
			ballXdir= 0;
			ballYdir= 0;
			g.setColor(Color.red);
			g.setFont(new Font("times new roman", Font.BOLD, 25));
			g.drawString("Game Over", 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Press enter to restart", 185, 350);
		}
		
		if(totalBricks <=0) {
			play = false;
			ballXdir= 0;
			ballYdir= 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won", 260, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Press enter to restart", 230, 350);
		}

		g.dispose();  // to exit (in case of swing)
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		timer.start();
		if (play) {
			if (new Rectangle(ballpositionX, ballpositionY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}

			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0) {

						int brickX = j* map.brickWidth + 80;
						int brickY = i* map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;

						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballpositionX, ballpositionY, 20, 20);
						Rectangle brickRect = rect;

						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;

							if (ballpositionX + 19 <= brickRect.x
									|| ballpositionX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
							break A;
						}

					}

				}
			}
		
			ballpositionX += ballXdir;
			ballpositionY += ballYdir;

			if (ballpositionX < 0) {
				ballXdir = -ballXdir;
			}

			if (ballpositionY < 0) {
				ballYdir = -ballYdir;
			}

			if (ballpositionX > 670) {
				ballXdir = -ballXdir;
			}
		}

		repaint();

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballpositionX = 120;
				ballpositionY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score =0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				
				repaint();
			}
		}
	}

	private void moveLeft() {
		// TODO Auto-generated method stub
		play = true;
		playerX -= 20;
	}

	private void moveRight() {
		// TODO Auto-generated method stub

		play = true;
		playerX += 20;
	}

}
