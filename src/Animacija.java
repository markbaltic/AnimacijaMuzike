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
	public int cas = 20;
//	public static int st = 0; //stevilo, ki ga dobimo iz podatkov iz glasbene datoteke
//	public static int kotnahitrost = 0;
	public static String oblika = "krogi";
	public int[] audioData;
	public long zacetniCas;
	public long dolzinaPesmi;
	public long dolzinaAudioData;
	
	public Timer tm = new Timer(cas, this); //število pove na koliko ms se izvede funkcija actionPerformed
	int w=0, velW=1; // w je trenuten kot èrte, velW služi kot HITROST (se prišteva w-ju in tako ga poveèuje). Ko poveèujemo vrednost velW,
					// pospešimo palico
	int n = 500; //del kroga
	int r = 100; //radij

	

	public Animacija(){ 
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		
	}
	
	public void nastaviAnimacijo(int[] seznamAmplitud, long zacetniCas, long dolzinaPesmi, long clipTime){
		tm.stop();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		this.audioData = seznamAmplitud;
		this.dolzinaAudioData = seznamAmplitud.length;
		this.zacetniCas = zacetniCas + clipTime;
		this.dolzinaPesmi = dolzinaPesmi;
		tm.start();
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int r = this.intenziteta();
		g.setColor(Color.RED);
		
		
		
		if(oblika == "krogi")
		{	//g.fillRect(150, 150, 300, 300);
			n = 500; // razdeliš ravnino na kote - vpliva na kotno hitrost
			double X = 300;
			double Y = 300;
			int R = 40;
			
//			g.drawOval(300, 300, r, r);
//			g.drawOval(300-r, 300-r, r, r);
//			g.drawOval(300-r, 300, r, r);
//			g.drawOval(300, 300-r, r, r);
//			g.drawLine(300, 0, 300, 600);
//			g.drawLine(0, 300, 600, 300);
//			g.fillOval(150, 150, 300, 300);
			g.fillOval((int)((X-R)+(R/2+r)*Math.cos((Math.PI*2*(w)/n))), (int) ((Y-R)+(R/2+r)*Math.sin(-Math.PI*2*(w)/n)), 2*R, 2*R);
			g.fillOval((int)((X-R)+(R/2+r)*Math.sin((Math.PI*2*(w)/n))), (int) ((Y-R)+(R/2+r)*Math.cos(-Math.PI*2*(w)/n)), 2*R, 2*R);
			g.fillOval((int)((X-R)-(R/2+r)*Math.cos((Math.PI*2*(w)/n))), (int) ((Y-R)-(R/2+r)*Math.sin(-Math.PI*2*(w)/n)), 2*R, 2*R);
			g.fillOval((int)((X-R)-(R/2+r)*Math.sin((Math.PI*2*(w)/n))), (int) ((Y-R)-(R/2+r)*Math.cos(-Math.PI*2*(w)/n)), 2*R, 2*R);



//			g.fillOval(300-r, 300, 150, 150);
//			g.fillOval(300, 300-r, 150, 150);
			
//			g.fillOval((int)(300+r*Math.cos((Math.PI*2*(w)/n))),(int) (300+r*Math.sin(-Math.PI*2*(w)/n)), 150, 150);
		}
		else if(oblika == "krogec")
		{
				g.fillOval(300-(r/2), 300-(r/2), r, r);
		}
		else
		{
		n = 100;// razdeliš ravnino na kote - vpliva na kotno hitrost
		
		g.drawLine(300, 300, (int)(300-r),(int) (300+r));
		g.drawLine(300, 300, (int)(300-r),(int) (300-r));
		g.drawLine(300, 300, (int)(300+r),(int) (300+r));
		g.drawLine(300, 300, (int)(300+r),(int) (300-r));
		g.drawLine(300, 300, (int)(300-r),300);
		g.drawLine(300, 300, (int)(300+r),300);
		g.drawLine(300, 300, 300,(int) (300+r));
		g.drawLine(300, 300, 300,(int) (300-r));
		g.setColor(Color.BLUE);
		g.drawLine(300, 300, (int)(300+r*Math.cos((Math.PI*2*(w)/n))),(int) (300+r*Math.sin(-Math.PI*2*(w)/n)));//riše desno èrto
		g.drawLine(300, 300, (int)(300-r*Math.cos((Math.PI*2*(w)/n))),(int) (300+r*Math.sin(-Math.PI*2*(w)/n)));// riše levo èrto
		}
		
		}

	
	

	public int intenziteta()
	{
		long trenutniCas = System.currentTimeMillis() - zacetniCas;
		int mestoVSeznamu = (int) Math.abs(trenutniCas*dolzinaAudioData/dolzinaPesmi);
		int di = (int) (dolzinaAudioData/dolzinaPesmi); //st podatkov v sekundi,,,   Fourie://64;//to mora biti potenca števila 2!!!! 
		int dt = (int) (0.5 * di); //ms
		double moc = 0;
		double C = 1/(Math.log(1+(dt+1))); //obratna vrednost delne harmoniène vsote
		
		for(int i = mestoVSeznamu, j = 1; i<dolzinaAudioData && i > mestoVSeznamu-dt; i-- ,j++)
		{
			moc += audioData[i]*C/j;
		}
		
		
		return (int) (Math.atan(moc/5000)*600/Math.PI);
		
		
		
		
//		if(mestoVSeznamu < this.dolzinaAudioData)
//		{
//		
//		
//		//Complex[] kompSeznam = new Complex[0];
//		//frekvenca.fft(kompleksno);
//		Complex[] kompSeznam = new Complex[dt];
//		for(int i = mestoVSeznamu, k=0; i > mestoVSeznamu-dt;  k++,i--)
//		{
//			
//			Complex kompStevilo = new Complex(this.audioData[i],0);
//			kompSeznam[k] = kompStevilo;
//			//Complex[] sezStevil;
//			moc += Math.abs(this.audioData[i])/(mestoVSeznamu - i + 1);
//			
//		}
//		//System.out.println(kompSeznam1);
//		//FFT frekvenca = new FFT();		
//		System.out.println("frekvenca: " + FFT.fft(kompSeznam)[0]);
//		System.out.println("moc: " + (Math.atan(moc/(dt*dt))*(1000/Math.PI)));
//		System.out.println("surovo: " + audioData[mestoVSeznamu]);
//		}
////		if(mestoVSeznamu < dolzinaAudioData)
////		{
////			//r = audioData[mestoVSeznamu]/10;
////				return (int) audioData[mestoVSeznamu]/50000;
////			}
//		
//		//System.out.println("moc: " + (Math.atan(moc/(dt*dt))*(1000/Math.PI)));
//		
//		
//		return (int) (Math.pow(Math.atan(moc/(dt*dt))*(1000/Math.PI),1.5)*0.03);
	}


	@Override
	public void actionPerformed(ActionEvent e) //na vsake "cas = 5ms" se izvede ta akcija (prišteje se kot) in potem se poklièe repaint();
	{
		w += velW;
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
