import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MenuListener;

import javazoom.jl.decoder.JavaLayerException;



public class Test {
	public static int podatek;
	static String datoteka1 ="Commercial DEMO - 15.mp3.wav";
	static String datoteka2 = "test.mp3.wav";
	static String datoteka5 = "03 Gramatik - War Of The Currents.mp3.wav";
	static String datoteka4 = "test3.wav";
	static String datoteka7 = "1 minute dance music.mp3";
	static String datoteka = "Westminster-chimes.mp3";
	static Pretvornik pDatoteka;
	static long zacetniCas = 0;
	static int dolzinaSeznamaAmplitud;
	static ArrayList<Integer> seznamEnergij;
	static Animacija anim;
	static Clip clip;
	static long clipTime = 0; 
	static long premor = 0;

	
	public static void play(String datoteka, long clipTime) throws Exception{
		//Dobim podatke iz pesmi
		//Test.datoteka = datoteka;
		pDatoteka = new Pretvornik(datoteka);
		String petvorjenaDatoteka = pDatoteka.pretvorimp3towav(datoteka);
		System.out.println(petvorjenaDatoteka + ":ime glasbene datoteke");
		AudioWaveformCreator awc = new AudioWaveformCreator(petvorjenaDatoteka);
		int[] seznamAmplitud = awc.createAudioInputStream();
		dolzinaSeznamaAmplitud = seznamAmplitud.length;
		System.out.println(seznamAmplitud.length + ": dolzina seznama amplitud");
        int dolzinaPesmi = (int) awc.dolzinaPesmi();
        System.out.println(dolzinaPesmi + ": dolzina pesmi v sekundah");
        int cas = (int) ((((dolzinaPesmi))/dolzinaSeznamaAmplitud)*1000); // Je smiselno to imet, �e ne bomo imeli Thread.sleep ?
		
      //Naredi seznam energij
//        int interval = 1000;
//        seznamEnergij = new ArrayList<Integer>(dolzinaSeznamaAmplitud/interval);
//        int stevec = 0;
//        int energija = 0;
//        
//        for (int i: seznamAmplitud){
//        	if(stevec < interval)
//        	{
//        		//energija += i*i/((interval-stevec)*(interval-stevec));
//        		energija += Math.abs(i);
//        		++ stevec;
//        	}
//        	else
//        	{
//        		seznamEnergij.add(energija);
//        		energija = 0;
//        		stevec=0;
//        	}
//        	
//        }
        
      //Predvajam pesem
        try 
        {
            File yourFile = new File(petvorjenaDatoteka);
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

        
		//Naredim okno in za�enem animacijo
		
		
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
			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "krogi";
			}
		});
		JMenuItem crta = new JMenuItem(new AbstractAction("Crta")
		{
			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "crta";
			}
		});
		JMenuItem krogec = new JMenuItem(new AbstractAction("Krogec")
		{
			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "krogec";
			}
		});
		
		JMenuItem fft = new JMenuItem(new AbstractAction("Fourier")
		{
			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "fft";
			}
		});
		
		JMenuItem tocke = new JMenuItem(new AbstractAction("Tocke")
		{
			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "tocke";
			}
		});
		
		JMenuItem lomljenka = new JMenuItem(new AbstractAction("Lomljenka")
		{
			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "lomljenka";
			}
		});
		
		JMenuItem pavza = new JMenuItem(new AbstractAction("Pavza")
		{


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
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(""));
                int result = fileChooser.showOpenDialog(new JPanel());
                if (result == JFileChooser.APPROVE_OPTION) {
                	Test.datoteka = fileChooser.getSelectedFile().getPath();
                	//System.exit(0);
                    try {
                    	//Tu je potrebno animacijo nekako ustaviti in jo pognati na novo.
                    	clip.stop();
                    	clipTime = 0;
    					play(Test.datoteka, clipTime);
    				} catch (Exception e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
                    System.out.println(Test.datoteka);

                }
                else if (result == fileChooser.CANCEL_OPTION){
                	
                }
                //fileChooser.CANCEL_OPTION;
                
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
        
		
		
		
		
     

        
        
        
        
        //double povprecje = awc.vsota/dolzina;
        //System.out.println("Povprečje: " + povprecje);
        //System.out.println(awc.duration);
        


        

				
			
//		Animacija.st = Math.abs(podatek);
//		Animacija.kotnahitrost = podatek;//namesto podatek2 vstaviš vrednost hitrosti.
	}



		
	}


