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
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import javax.swing.event.MenuListener;



public class Test {
	public static int podatek;
	static String datoteka2 ="Commercial DEMO - 15.mp3";
	static String datoteka ="test.mp3.wav";
	static String datoteka1 = "03 Gramatik - War Of The Currents.mp3.wav";
	static Pretvornik pDatoteka = new Pretvornik(datoteka);
	static long zacetniCas = 0;



	public static void main(String[] args) throws Exception {
		//Dobim podatke iz pesmi
		String petvorjenaDatoteka = pDatoteka.pretvorimp3towav();
		System.out.println(petvorjenaDatoteka + ":ime glasbene datoteke");
		AudioWaveformCreator awc = new AudioWaveformCreator(petvorjenaDatoteka);
		int[] seznamAmplitud = awc.createAudioInputStream();
		int dolzinaSeznamaAmplitud = seznamAmplitud.length;
		System.out.println(seznamAmplitud.length + ": dolzina seznama amplitud");
        int dolzinaPesmi = (int) awc.dolzinaPesmi();
        System.out.println(dolzinaPesmi + ": dolzina pesmi v sekundah");
        int cas = (int) ((((dolzinaPesmi))/dolzinaSeznamaAmplitud)*1000); // Je smiselno to imet, èe ne bomo imeli Thread.sleep ?
        
        //Naredi seznam energij
        ArrayList<Integer> seznamEnergij = new ArrayList<Integer>(dolzinaSeznamaAmplitud/100);
        int stevec = 0;
        int energija = 0;
        for (int i: seznamAmplitud){
        	if(stevec < 100)
        	{
        		energija += i*i/((100-stevec)*(100-stevec));
        		++ stevec;
        	}
        	else
        	{
        		seznamEnergij.add(energija);
        		energija = 0;
        		stevec=0;
        	}
        	
        }
        //Predvajam pesem
        try 
        {
            File yourFile = new File(petvorjenaDatoteka);
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

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
        
		
        //Naredim okno in zaženem animacijo
		Animacija anim = new Animacija(seznamEnergij, zacetniCas, dolzinaPesmi);
		anim.setBackground(Color.ORANGE);
		
		JFrame okno = new JFrame();
		
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
		fileMenu.add(krog);
		fileMenu.add(crta);

		
		okno.setJMenuBar(menuBar);
		okno.setVisible(true);
		
		
		
		
		
     

        
        
        
        
        //double povprecje = awc.vsota/dolzina;
        //System.out.println("PovpreÄje: " + povprecje);
        //System.out.println(awc.duration);
        


        

				
			
//		Animacija.st = Math.abs(podatek);
//		Animacija.kotnahitrost = podatek;//namesto podatek2 vstaviÅ¡ vrednost hitrosti.
	}



		
	}


