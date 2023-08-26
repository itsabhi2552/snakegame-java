import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Game extends JFrame {
	
	Game() {
		
		add(new Game_Panel());
		setVisible(true);
		setResizable(false);
		setTitle("Snake_Game");
		setBounds(300, 100, 750, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class Game_Panel extends JPanel implements ActionListener, KeyListener {
	
	private Timer timer;
	private int size = 4;
	private int score = 0;
	private int rndm_x = 0;
	private int rndm_y = 0;
	private int x[] = new int[104];
	private int y[] = new int[104];
	
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = true;
	private boolean game_over = false;
	private boolean start_game = false;
	
	Game_Panel() {
		
		for(int value = 84, i = 0; i < size; value -= 21, i++) {
			
			x[i] = value;
			y[i] = 63;
		}
		
		timer = new Timer(120, this);
		timer.start();
		
		addKeyListener(this);
		setFocusable(true);
		Food_Location();
	}
	
	public void Food_Location() {
		
		rndm_x = (int)Math.round(Math.random()*32+1);
		rndm_y = (int)Math.round(Math.random()*19+1);
		rndm_x *= 21;
		rndm_y *= 21;
		
		if((rndm_x >= 588) && (rndm_y <= 42)) {
			
			Food_Location();
		}
		
		for(int i = 0; i < size ; i++) {
			
			if((rndm_x == x[i]) && (rndm_y  == y[i])) {
				
				Food_Location();
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(rndm_x, rndm_y, 10, 10);
		g.setColor(Color.BLACK);
		g.setFont(new Font("serif", Font.BOLD, 18));
		g.drawString("Score : " +score, 619, 42);
		
		for(int i = 0; i <= 714; i += 21) {
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(i, 0, 20, 20);
			g.fillRect(i, 441, 20, 20);
			
			if( i <= 399) {
				
				g.fillRect(0, i + 21, 20, 20);
				g.fillRect(714, i + 21, 20, 20);
			}
		}
		
		for(int i = 0; i < size; i++) {
			
			if(i == 0) {
				
				g.setColor(Color.BLACK);
				g.fillRect(x[0], y[0], 20, 20);
			}
			else {
				
				g.setColor(Color.GRAY);
				g.fillRect(x[i], y[i], 20, 20);
			}
		}
		
		if((start_game == false) && (game_over == false)) {
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("< press space to start >", 170, 225);
		}
		
		if(score == 100) {
			
			game_over = true;
			start_game = false;
			g.setColor(Color.BLACK);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("!! you win the game !!", 181, 175);
			g.drawString("< press enter to restart >", 160, 275);
		}
		
		if((game_over == true) && (score != 100)) {
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("!! Game Over !!", 232, 185);
			g.drawString("< press enter to restart >", 160, 265);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if((x[0] == rndm_x) && (y[0] == rndm_y)) {
			
			size++;
			score++;
			Food_Location();
		}
		
		for(int i = size; i > 0; i--) {
			
			if((x[0] == x[i]) && (y[0] == y[i])) {
				
				game_over = true;
				start_game = false;
			}
		}
		
		if((x[0] < 21) || (x[0] > 693) || (y[0] < 21) || (y[0] > 420) ) {
			
			game_over = true;
			start_game = false;
		}
		
		if(start_game) {
			
			for(int i = size; i > 0; i--) {
				
				x[i] = x[i-1];
				y[i] = y[i-1];
			}
			
			if(right) {
				
				x[0] += 21;
			}
			
			if(left) {
				
				x[0] -= 21;
			}
			
			if(up) {
				
				y[0] -= 21;
			}
			
			if(down) {
				
				y[0] += 21;
			}
		}
		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		try {
			
			Thread.sleep(10);
		}
		
		catch(Exception ex){
			
		}
			
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			
			if(game_over == true) {
				
				size = 4;
				score = 0;
				
				up = false;
				down = false;
				left = false;
				right = true;
				game_over = false;
				
				for(int value = 84, i = 0; i < size; value -= 21, i++) {
					
					x[i] = value;
					y[i] = 63;
				}
				
				repaint();
				Food_Location();
			}	
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			
			if((start_game == false) && (game_over == false)) {
				
				start_game = true;
			}
			
			else{
				
				start_game = false;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
			if((left == false) && (start_game == true))  {
				
				right = true;
				up = false;
				down = false;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			
			if((right == false) && (start_game == true)) {
				
				left = true;
				up = false;
				down = false;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			
			if((down == false) && (start_game == true))  {
				
				up = true;
				right = false;
				left = false;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			
			if((up == false) && (start_game == true))  {
				
				down = true;
				right = false;
				left = false;
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
}

class Snake_Game {
	public static void main(String args[])
	{
		new Game();
	}
}