import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;



public class AnimacijaMuzike {
	public static int podatek;
	static String datoteka = "1 minute dance music.mp3"; // Privzeta datoteka, ki mora biti .mp3 ali .wav

	static Pretvornik pretvoriDatoteko; 
	static long zacetniCas = 0;
	static int dolzinaSeznamaAmplitud;
	static Animacija anim;
	static Clip clip;
	static long clipTime = 0; 
	static long premor = 0;

	
	public static void play(String datoteka, long clipTime) throws Exception{
		//Dobim podatke iz pesmi		
		pretvoriDatoteko = new Pretvornik(datoteka);
		String pretvorjenaDatoteka = pretvoriDatoteko.pretvorimp3towav(datoteka);
		System.out.println(pretvorjenaDatoteka + ":ime glasbene datoteke");
		AnalizaZvoka awc = new AnalizaZvoka(pretvorjenaDatoteka);
		int[] seznamAmplitud = awc.createAudioInputStream();
		dolzinaSeznamaAmplitud = seznamAmplitud.length;
		System.out.println(seznamAmplitud.length + ": dolzina seznama amplitud");
        int dolzinaPesmi = (int) awc.dolzinaPesmi();
        System.out.println(dolzinaPesmi + ": dolzina pesmi v sekundah");
        
        //Predvajam pesem
        try 
        {
            File yourFile = new File(pretvorjenaDatoteka);
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            

            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            
            clip.setMicrosecondPosition(clipTime);
            clip.start();
            
            
            zacetniCas = System.currentTimeMillis();
        }
        catch (Exception e) 
        {
            System.out.println("Napaka");
        }
        

    	
    	
        // Zagon animacije
       	anim.nastaviAnimacijo(seznamAmplitud, zacetniCas, dolzinaPesmi, clipTime);
       	
    			
        
	}
	
	
	 



	public static void main(String[] args) throws Exception {		
		anim = new Animacija();
		anim.setBackground(Color.ORANGE);
		play(datoteka, clipTime); 

        
		//Naredim okno in zazenem animacijo
		
		
		final JFrame okno = new JFrame();
		
		
		
		okno.setTitle("Animacija Muzike");
		okno.setSize(600, 600);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.add(anim);
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Oblika");
		JMenu predvajanje = new JMenu("Predvajanje");
		menuBar.add(fileMenu);
		menuBar.add(predvajanje);
		JMenuItem krogi = new JMenuItem(new AbstractAction("Krogi")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "krogi";
			}
		});
		JMenuItem crta = new JMenuItem(new AbstractAction("Crta")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "crta";
			}
		});
		JMenuItem krogec = new JMenuItem(new AbstractAction("Krogec")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "krogec";
			}
		});
		
		JMenuItem fft = new JMenuItem(new AbstractAction("Fourier")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "fft";
			}
		});
		
		JMenuItem tocke = new JMenuItem(new AbstractAction("Tocke")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "tocke";
			}
		});
		
		JMenuItem lomljenka = new JMenuItem(new AbstractAction("Lomljenka")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "lomljenka";
			}
		});
		
		JMenuItem pavza = new JMenuItem(new AbstractAction("Pavza")
		{


			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				

				clipTime= clip.getMicrosecondPosition();

				clip.stop();
				anim.tm.stop();
				premor = System.currentTimeMillis();
				

			}
		});
		JMenuItem igraj= new JMenuItem(new AbstractAction("Igraj")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				
				if(premor != 0)
				{
				premor = System.currentTimeMillis() - premor;
				
				clip.setMicrosecondPosition(clipTime);
				clip.start();
				anim.zacetniCas += premor;
				anim.tm.start();
				clipTime = 0;
				}

			}
		});
		
		JMenuItem datoteka1 = new JMenuItem(new AbstractAction("Datoteka")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(""));
                int result = fileChooser.showOpenDialog(new JPanel());
                if (result == JFileChooser.APPROVE_OPTION) {
                	AnimacijaMuzike.datoteka = fileChooser.getSelectedFile().getPath();
                    try {
                    	clip.stop();
                    	clipTime = 0;
    					play(AnimacijaMuzike.datoteka, clipTime);
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
                    System.out.println(AnimacijaMuzike.datoteka);

                }
                else if (result == fileChooser.CANCEL_OPTION){
                	
                }
                
 			}
		});
		
		fileMenu.add(krogi);
		fileMenu.add(crta);
		fileMenu.add(krogec);
		fileMenu.add(fft);
		fileMenu.add(tocke);
		fileMenu.add(lomljenka);
		
		predvajanje.add(igraj);
		predvajanje.add(pavza);
		predvajanje.add(datoteka1);
		
		okno.setJMenuBar(menuBar);
		okno.setVisible(true);
  
	}		
	}


