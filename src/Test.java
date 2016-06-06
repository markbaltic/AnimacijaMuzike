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
	static String datoteka ="Commercial DEMO - 15.mp3.wav";
	static String datoteka2 = "test.mp3.wav";
	static String datoteka1 = "03 Gramatik - War Of The Currents.mp3";
	static String datoteka4 = "test3.wav";
	static Pretvornik pDatoteka;
	static long zacetniCas = 0;
	static int dolzinaSeznamaAmplitud;
	static ArrayList<Integer> seznamEnergij;
	static Animacija anim;
	static Clip clip;
	
	public static void play(String datoteka) throws Exception{
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
            
            clip.start();
            
            zacetniCas = System.currentTimeMillis();
        }
        catch (Exception e) 
        {
            System.out.println("Napaka");
        }
        
     // Zagon animacije
    	anim = new Animacija(seznamAmplitud, zacetniCas, dolzinaPesmi);
    	anim.setBackground(Color.ORANGE);
    	
    	
    			
        
	}
	
	
	 



	public static void main(String[] args) throws Exception {		
		play(datoteka); 

        
		//Naredim okno in za�enem animacijo
		
		
		final JFrame okno = new JFrame();
		
		okno.setTitle("Animacija Muzike");
		okno.setSize(600, 600);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.add(anim);
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Oblika");
		menuBar.add(fileMenu);
		JMenuItem krog = new JMenuItem(new AbstractAction("Krog")
		{
			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "krog";
			}
		});
		JMenuItem crta = new JMenuItem(new AbstractAction("Crta")
		{
			public void actionPerformed(ActionEvent e)
			{
				Animacija.oblika = "crta";
			}
		});
		
		JMenuItem datoteka1 = new JMenuItem(new AbstractAction("Datoteka")
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(""));
                int result = fileChooser.showOpenDialog(new JPanel());
                if (result != JFileChooser.APPROVE_OPTION) {
                    System.exit(0);
                }
                Test.datoteka = fileChooser.getSelectedFile().getPath();
                try {
                	//Tu je potrebno animacijo nekako ustaviti in jo pognati na novo.
                	clip.stop();
					play(Test.datoteka);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                System.out.println(Test.datoteka);
			}
		});
		
		fileMenu.add(krog);
		fileMenu.add(crta);
		fileMenu.add(datoteka1);

		
		okno.setJMenuBar(menuBar);
		okno.setVisible(true);
        
		
		
		
		
     

        
        
        
        
        //double povprecje = awc.vsota/dolzina;
        //System.out.println("Povprečje: " + povprecje);
        //System.out.println(awc.duration);
        


        

				
			
//		Animacija.st = Math.abs(podatek);
//		Animacija.kotnahitrost = podatek;//namesto podatek2 vstaviš vrednost hitrosti.
	}



		
	}


