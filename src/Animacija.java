import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Animacija extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int cas = 37;
	public static String oblika = "tocke";
	public int[] audioData;
	public long zacetniCas;
	public long dolzinaPesmi;
	public long dolzinaAudioData;
	private int ft = 512; //Fourier: to mora biti potenca števila 2
	Complex[] kompSeznam;
	
	public Timer tm = new Timer(cas, this); //število pove na koliko ms se izvede funkcija actionPerformed
	int w=0, velW=1; // w je trenuten kot èrte, velW služi kot HITROST (se prišteva w-ju in tako ga poveèuje). Ko poveèujemo vrednost velW,
					// pospešimo palico
	int n = 500; //del kroga
	int r = 100; //radij

	

	public Animacija(){ 		

		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		
	}
	
	public void nastaviAnimacijo(int[] seznamAmplitud, long zacetniCas, long dolzinaPesmi, long clipTime){
		tm.stop();
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
		{	
			n = 500; // razdeliš ravnino na kote - vpliva na kotno hitrost
			double X = 300;
			double Y = 300;
			int R = 40;
			
			g.fillOval((int)((X-R)+(R/2+r)*Math.cos((Math.PI*2*(w)/n))), (int) ((Y-R)+(R/2+r)*Math.sin(-Math.PI*2*(w)/n)), 2*R, 2*R);
			g.fillOval((int)((X-R)+(R/2+r)*Math.sin((Math.PI*2*(w)/n))), (int) ((Y-R)+(R/2+r)*Math.cos(-Math.PI*2*(w)/n)), 2*R, 2*R);
			g.fillOval((int)((X-R)-(R/2+r)*Math.cos((Math.PI*2*(w)/n))), (int) ((Y-R)-(R/2+r)*Math.sin(-Math.PI*2*(w)/n)), 2*R, 2*R);
			g.fillOval((int)((X-R)-(R/2+r)*Math.sin((Math.PI*2*(w)/n))), (int) ((Y-R)-(R/2+r)*Math.cos(-Math.PI*2*(w)/n)), 2*R, 2*R);

		}
		else if(oblika == "krogec")
		{			
				g.fillOval(300-(r/2), 300-(r/2), r, r);
		}
		else if(oblika == "fft")
		{		int konstanta = (int) 600/ft;
				int stevec = (600- ft )/2;
				for(Complex z : l)
				{
					g.drawLine(stevec, 600, stevec,(int) (600-(Math.atan((z.abs())/4000)*600/Math.PI)));
					stevec += konstanta;
				}

		}
		else if(oblika == "tocke")
		{		
				for(Complex z : l)
				{
					g.fillOval((int)(300+(Math.atan((z.re)/4000)*600/Math.PI)),(int) (300+(Math.atan((z.im)/4000)*600/Math.PI)), 10, 10);

					
				}
				

		}
		else if(oblika == "lomljenka")
		{		int x = 300;
				int y = 300;
				for(Complex z : l)
				{
					g.drawLine(x, y, (int)(300+(Math.atan((z.re)/4000)*600/Math.PI)), (int) (300+(Math.atan((z.im)/4000)*600/Math.PI)));
					x = (int) z.re;
					y = (int) z.im;
				}
				

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
		catch (Exception e)
		{
			tm.stop();
		}
		
		}

	
	

	public int intenziteta()
	{
		long trenutniCas = System.currentTimeMillis() - zacetniCas;
		int mestoVSeznamu = (int) Math.abs(trenutniCas*dolzinaAudioData/dolzinaPesmi);
		int di = (int) (dolzinaAudioData/dolzinaPesmi); //st podatkov v sekundi
		int dt = (int) (0.5 * di); //ms
		double moc = 0;
		@SuppressWarnings("unused")
		double C = 1/(Math.log(1+(dt+1))); //obratna vrednost delne harmoniène vrste
		double G = 1/((1-Math.pow(0.5, dt))*2); //obratna vrednost delne geometrijske vrste
		
		for(int i = mestoVSeznamu, j = 1; i<dolzinaAudioData && i > mestoVSeznamu-dt; i-- ,j++)
		{
//			moc += audioData[i]*C/j; //z uporabo harmoniène vrste
			moc += audioData[i]*G/(Math.pow(2, j)); //z uporavo geometrijske vrste
//			moc += audioData[i]/100; //konstantna utež
		}
		

		
		
		return (int) (Math.atan(moc/5000)*600/Math.PI);
	}
		

	
	public Complex[] frekvenca()
	{
		long trenutniCas = System.currentTimeMillis() - zacetniCas;
		int mestoVSeznamu = (int) Math.abs(trenutniCas*dolzinaAudioData/dolzinaPesmi);
		Complex[] kompSeznam = new Complex[ft];
		
		for(int i = mestoVSeznamu, j = 1; i<dolzinaAudioData && i > mestoVSeznamu-ft; i-- ,j++)
		{
			Complex kompStevilo = new Complex(this.audioData[i],0);
			kompSeznam[j-1] = kompStevilo;
		}
		
		return FFT.fft(kompSeznam);
	}		
		




	@Override
	public void actionPerformed(ActionEvent e) //na vsake "cas = 37ms" se izvede ta akcija (prišteje se kot) in potem se poklièe repaint();
	{
		w += velW;
		repaint();
	}


	

}
