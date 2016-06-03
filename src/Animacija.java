import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Animacija extends JPanel implements ActionListener, KeyListener {
	public int cas = 37;
//	public static int st = 0; //stevilo, ki ga dobimo iz podatkov iz glasbene datoteke
//	public static int kotnahitrost = 0;
	public static String oblika = "krog";
	public ArrayList<Integer> audioData;
	public long zacetniCas;
	public long dolzinaPesmi;
	public long dolzinaAudioData;
	
	Timer tm = new Timer(cas, this); //število pove na koliko ms se izvede funkcija actionPerformed
	int w=0, velW=0; // w je trenuten kot èrte, velW služi kot HITROST (se prišteva w-ju in tako ga poveèuje). Ko poveèujemo vrednost velW,
					// pospešimo palico
	int n = 500; //del kroga
	int r = 100; //radij

	

	public Animacija(ArrayList<Integer> audioData, long zacetniCas, long dolzinaPesmi){ 
		tm.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		this.audioData = audioData;
		this.dolzinaAudioData = audioData.size();
		this.zacetniCas = zacetniCas;
		this.dolzinaPesmi = dolzinaPesmi;
		
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(r > 0){
		g.setColor(Color.RED);
		}
		else{
			g.setColor(Color.BLUE);
		}
		
		g.fillOval(300-(r/2), 300-(r/2), r, r);
//		if(oblika == "krog")
//		{	//g.fillRect(150, 150, 300, 300);
//			n = 50000; // razdeliš ravnino na kote - vpliva na kotno hitrost
//			double X = 300;
//			double Y = 300;
//			int R = 80;
//			
////			g.drawOval(300, 300, r, r);
////			g.drawOval(300-r, 300-r, r, r);
////			g.drawOval(300-r, 300, r, r);
////			g.drawOval(300, 300-r, r, r);
//			g.drawLine(300, 0, 300, 600);
//			g.drawLine(0, 300, 600, 300);
////			g.fillOval(150, 150, 300, 300);
//			g.fillOval((int)((X-R)+(R/2+r)*Math.cos((Math.PI*2*(w)/n))), (int) ((Y-R)+(R/2+r)*Math.sin(-Math.PI*2*(w)/n)), 2*R, 2*R);
//			g.fillOval((int)((X-R)+(R/2+r)*Math.sin((Math.PI*2*(w)/n))), (int) ((Y-R)+(R/2+r)*Math.cos(-Math.PI*2*(w)/n)), 2*R, 2*R);
//			g.fillOval((int)((X-R)-(R/2+r)*Math.cos((Math.PI*2*(w)/n))), (int) ((Y-R)-(R/2+r)*Math.sin(-Math.PI*2*(w)/n)), 2*R, 2*R);
//			g.fillOval((int)((X-R)-(R/2+r)*Math.sin((Math.PI*2*(w)/n))), (int) ((Y-R)-(R/2+r)*Math.cos(-Math.PI*2*(w)/n)), 2*R, 2*R);
//
//
//
////			g.fillOval(300-r, 300, 150, 150);
////			g.fillOval(300, 300-r, 150, 150);
//			
////			g.fillOval((int)(300+r*Math.cos((Math.PI*2*(w)/n))),(int) (300+r*Math.sin(-Math.PI*2*(w)/n)), 150, 150);
//		}
//		else
//		{
//		n = 100000;// razdeliš ravnino na kote - vpliva na kotno hitrost
//		g.drawLine(300, 300, (int)(300+r*Math.cos((Math.PI*2*(w)/n))),(int) (300+r*Math.sin(-Math.PI*2*(w)/n)));//riše desno èrto
//		g.drawLine(300, 300, (int)(300-r*Math.cos((Math.PI*2*(w)/n))),(int) (300+r*Math.sin(-Math.PI*2*(w)/n)));// riše levo èrto
//		g.drawLine(300, 300, (int)(300-r),(int) (300+r));
//		g.drawLine(300, 300, (int)(300-r),(int) (300-r));
//		g.drawLine(300, 300, (int)(300+r),(int) (300+r));
//		g.drawLine(300, 300, (int)(300+r),(int) (300-r));
//		g.drawLine(300, 300, (int)(300-r),300);
//		g.drawLine(300, 300, (int)(300+r),300);
//		g.drawLine(300, 300, 300,(int) (300+r));
//		g.drawLine(300, 300, 300,(int) (300-r));
//		
//		}

	}
	



	@Override
	public void actionPerformed(ActionEvent e) //na vsake "cas = 5ms" se izvede ta akcija (prišteje se kot) in potem se poklièe repaint();
	{
		long trenutniCas = System.currentTimeMillis() - zacetniCas;
		int mestoVSeznamu = (int) Math.abs(trenutniCas*dolzinaAudioData/dolzinaPesmi); //TA JE BIL NEGATIVEN. ZAKAJ???
		int moc = 2000;
//		if(mestoVSeznamu > 100){
//		for(int i = -100; i < 100 ; ++i)
//		{
//			moc += Math.abs(audioData[mestoVSeznamu + i]);
//		}
//		}
		if(mestoVSeznamu < dolzinaAudioData)
		{
		//r = audioData[mestoVSeznamu]/10;
			r = (int) Math.sqrt(audioData.get(mestoVSeznamu))/500;
		}
//		System.out.println(st + ":st");
//		w += kotnahitrost;
//		if(r<0)
//		{
//		r = 0;
//		}
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
