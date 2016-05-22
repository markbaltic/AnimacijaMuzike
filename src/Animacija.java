import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Animacija extends JPanel implements ActionListener, KeyListener {
	
	Timer tm = new Timer(5, this);
	int w=0, velW=0;
	int n = 100; //del kroga
	int r = 100; //radij
	
//	int x=0, y=0, velX=0, velY=0;
	
	public Animacija(){
		tm.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawLine(300, 300, (int)(300+r*Math.cos((Math.PI*2*(w)/n))),(int) (300+r*Math.sin(-Math.PI*2*(w)/n)));
		
//		g.fillRect(x, y, 50, 50);
		
	}
	



	@Override
	public void actionPerformed(ActionEvent e)
	{

		w += velW;
		if(r<0)
		{
		r = 0;
		}
		repaint();
		
//		if(x < 0)
//		{
//			velX = 0;
//			x = 0;
//		}
//		if(x > 550)
//		{
//			velX = 0;
//			x = 550;
//		}
//		if(y < 0)
//		{
//			velY = 0;
//			y = 0;
//		}
//		if(y > 550)
//		{
//			velY = 0;
//			y = 550;
//		}
//		x = x + velX;
//		y += velY;
//		repaint();
		
	}


	@Override
	public void keyPressed(KeyEvent e) 
	{
		int c = e.getKeyCode();
		
		if(c == KeyEvent.VK_LEFT)
		{
//			velX = -1;
//			velY = 0;
			velW += -1;
		}
		if(c == KeyEvent.VK_UP)
		{
//			velX = 0;
//			velY = -1;
			r += 10; 
		}
		if(c == KeyEvent.VK_RIGHT)
		{
//			velX = 1;
//			velY = 0;
			velW += 1;
		}
		if(c == KeyEvent.VK_DOWN)
		{
//			velX = 0;
//			velY = 1;
			r -= 10;
		}
		if(c == KeyEvent.VK_S)
		{
			velW = 0;
		}
		if(c == KeyEvent.VK_2)
		{
			r = 200;
		}
		if(c == KeyEvent.VK_3)
		{
			r = 300;
		}
	}


	@Override
	public void keyReleased(KeyEvent e)
	{
//		velX = 0;
//		velY = 0;
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
