import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Animacija extends JPanel implements ActionListener, KeyListener {
	public int cas = 37;
//	public static int st = 0; //stevilo, ki ga dobimo iz podatkov iz glasbene datoteke
//	public static int kotnahitrost = 0;
	public static String oblika = "tocke";
	public int[] audioData;
	public long zacetniCas;
	public long dolzinaPesmi;
	public long dolzinaAudioData;
	Complex[] kompSeznam;
	
	public Timer tm = new Timer(cas, this); //�tevilo pove na koliko ms se izvede funkcija actionPerformed
	int w=0, velW=1; // w je trenuten kot �rte, velW slu�i kot HITROST (se pri�teva w-ju in tako ga pove�uje). Ko pove�ujemo vrednost velW,
					// pospe�imo palico
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
		int r = intenziteta();
		try{
		Complex[] l = frekvenca();

		
		g.setColor(Color.RED);
		
		
		
		if(oblika == "krogi")
		{	//g.fillRect(150, 150, 300, 300);
			n = 500; // razdeli� ravnino na kote - vpliva na kotno hitrost
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
		else if(oblika == "fft")
		{		int stevec = 9;
				for(Complex z : l)
				{
					g.drawLine(stevec, 600, stevec,(int) (600-(Math.atan((z.abs())/4000)*600/Math.PI)));
					stevec += 18;
				}

		}
		else if(oblika == "tocke")
		{		
				for(Complex z : l)
				{
					g.fillOval((int)(300+(Math.atan((z.re)/4000)*600/Math.PI)),(int) (300+(Math.atan((z.im)/4000)*600/Math.PI)), 10, 10);
					//System.out.println("im: " + z.im);
					
				}
				

		}
		else if(oblika == "lomljenka")
		{		int x = 300;
				int y = 300;
				for(Complex z : l)
				{
					g.drawLine(x, y, (int)(300+(Math.atan((z.re)/4000)*600/Math.PI)), (int) (300+(Math.atan((z.im)/4000)*600/Math.PI)));
					//System.out.println("im: " + z.im);
					x = (int) z.re;
					y = (int) z.im;
				}
				

		}
		else
		{
		n = 100;// razdeli� ravnino na kote - vpliva na kotno hitrost
		
		g.drawLine(300, 300, (int)(300-r),(int) (300+r));
		g.drawLine(300, 300, (int)(300-r),(int) (300-r));
		g.drawLine(300, 300, (int)(300+r),(int) (300+r));
		g.drawLine(300, 300, (int)(300+r),(int) (300-r));
		g.drawLine(300, 300, (int)(300-r),300);
		g.drawLine(300, 300, (int)(300+r),300);
		g.drawLine(300, 300, 300,(int) (300+r));
		g.drawLine(300, 300, 300,(int) (300-r));
		g.setColor(Color.BLUE);
		g.drawLine(300, 300, (int)(300+r*Math.cos((Math.PI*2*(w)/n))),(int) (300+r*Math.sin(-Math.PI*2*(w)/n)));//ri�e desno �rto
		g.drawLine(300, 300, (int)(300-r*Math.cos((Math.PI*2*(w)/n))),(int) (300+r*Math.sin(-Math.PI*2*(w)/n)));// ri�e levo �rto
		}
		
		}
		catch (Exception e)
		{
			tm.stop();
		}
		
		}

	
	

	public int intenziteta()
	{
		long trenutniCas = System.currentTimeMillis() - zacetniCas;
		int mestoVSeznamu = (int) Math.abs(trenutniCas*dolzinaAudioData/dolzinaPesmi);
		int di = (int) 64; //(dolzinaAudioData/dolzinaPesmi); //st podatkov v sekundi,,,   Fourie://64;//to mora biti potenca �tevila 2!!!! 
		int dt = (int) (0.5 * di); //ms
		double moc = 0;
		double C = 1/(Math.log(1+(dt+1))); //obratna vrednost delne harmoni�ne vrste
		double G = 1/((1-Math.pow(0.5, dt))*2); //obratna vrednost delne geometrijske vrste
		
		for(int i = mestoVSeznamu, j = 1; i<dolzinaAudioData && i > mestoVSeznamu-dt; i-- ,j++)
		{
//			moc += audioData[i]*C/j; //z uporabo harmoni�ne vrste
			moc += audioData[i]*G/(Math.pow(2, j)); //z uporavo geometrijske vrste
//			moc += audioData[i]/100; //konstantna ute�
		}
		

		
		
		return (int) (Math.atan(moc/5000)*600/Math.PI);
	}
		

	
	public Complex[] frekvenca()
	{
		long trenutniCas = System.currentTimeMillis() - zacetniCas;
		int mestoVSeznamu = (int) Math.abs(trenutniCas*dolzinaAudioData/dolzinaPesmi);
		int di = (int) 64; //(dolzinaAudioData/dolzinaPesmi); //st podatkov v sekundi,,,   Fourie://64;//to mora biti potenca �tevila 2!!!! 
		int dt = (int) (0.5 * di); //ms
		Complex[] kompSeznam = new Complex[dt];
		
		for(int i = mestoVSeznamu, j = 1; i<dolzinaAudioData && i > mestoVSeznamu-dt; i-- ,j++)
		{
			Complex kompStevilo = new Complex(this.audioData[i],0);
			kompSeznam[j-1] = kompStevilo;
		}
		
		if (kompSeznam.length==0){
			kompSeznam[0]=Complex(0,0);
			return kompSeznam;
		}
		

		

		
		return FFT.fft(kompSeznam);
	}
		
	
	
		
		
		
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
//	}


	private Complex Complex(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) //na vsake "cas = 5ms" se izvede ta akcija (pri�teje se kot) in potem se pokli�e repaint();
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
