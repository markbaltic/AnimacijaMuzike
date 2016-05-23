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
	public int cas = 5;
	public static int st = 0; //stevilo, ki ga dobimo iz podatkov iz glasbene datoteke
	public static String oblika = "krog";
	
	Timer tm = new Timer(cas, this); //število pove na koliko ms se izvede funkcija actionPerformed
	int w=0, velW=1; // w je trenuten kot èrte, velW služi kot HITROST (se prišteva w-ju in tako ga poveèuje). Ko poveèujemo vrednost velW,
					// pospešimo palico
	int n = 100; //del kroga
	int r = 100; //radij
	

	public Animacija(){ 
		tm.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		if(oblika == "krog")
		{
			g.drawOval(300, 300, r, r);
			g.drawOval(300-r, 300-r, r, r);
			g.drawOval(300-r, 300, r, r);
			g.drawOval(300, 300-r, r, r);
		}
		else
		{
		g.drawLine(300, 300, (int)(300+r*Math.cos((Math.PI*2*(w)/n))),(int) (300+r*Math.sin(-Math.PI*2*(w)/n)));//riše desno èrto
		g.drawLine(300, 300, (int)(300-r*Math.cos((Math.PI*2*(w)/n))),(int) (300+r*Math.sin(-Math.PI*2*(w)/n)));// riše levo èrto
		}

	}
	



	@Override
	public void actionPerformed(ActionEvent e) //na vsake "cas = 5ms" se izvede ta akcija (prišteje se kot) in potem se poklièe repaint();
	{
		r = st*st/100;
//		System.out.println(st);
		w += velW;
		if(r<0)
		{
		r = 0;
		}
		repaint();
	}


	@Override
	public void keyPressed(KeyEvent e) 
	{
		int c = e.getKeyCode();
		
		if(c == KeyEvent.VK_LEFT)
		{
			velW += -1;
		}
		if(c == KeyEvent.VK_UP)
		{
			r += 10; 
		}
		if(c == KeyEvent.VK_RIGHT)
		{
			velW += 1;
		}
		if(c == KeyEvent.VK_DOWN)
		{
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

	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
