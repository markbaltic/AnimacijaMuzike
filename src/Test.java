import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
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
import javax.swing.event.MenuListener;



public class Test {
	public static int podatek;
	static String datoteka4 = "03 Gramatik - War Of The Currents.mp3.wav";
	static String datoteka5 ="mono_16bit.wav";
	static String datoteka2 ="Commercial DEMO - 15.mp3";
	static String datoteka ="test.mp3";
	static String datoteka1 = "";
	static Pretvornik pDatoteka = new Pretvornik(datoteka);

	public static void main(String[] args) throws Exception {
		
		String petvorjenaDatoteka = pDatoteka.pretvorimp3towav();
		System.out.println(petvorjenaDatoteka);
		Animacija anim = new Animacija(null);
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
		
		
		
		
		AudioWaveformCreator awc = new AudioWaveformCreator(petvorjenaDatoteka);
        try {
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
        }
        catch (Exception e) {
            System.out.println("Napaka");
        }
        
        int[] seznam = awc.createAudioInputStream();
        System.out.println(seznam.length + "trara");
        int dolzina = seznam.length;
        //double povprecje = awc.vsota/dolzina;
        //System.out.println("Povprečje: " + povprecje);
        //System.out.println(awc.duration);
        
        int dolzinaPesmi = (int) awc.dolzinaPesmi();
        int cas = (int) ((((dolzinaPesmi))/dolzina)*1000);
        long zacetniCas = System.currentTimeMillis();
        System.out.println(dolzina);
        try {        	
        	//for (int i=0; i < dolzina; i++){        		
        	
        	Thread.sleep(cas);
        	long trenutniCas = System.currentTimeMillis() - zacetniCas;
        	int mestoVSeznamu = (int) trenutniCas*dolzina/dolzinaPesmi;
        	int podatek = seznam[mestoVSeznamu];
        	
        	Color barva = new Color(podatek);
        	anim.setBackground(barva);

        			
        		
        	Animacija.st = Math.abs(podatek);
        	Animacija.kotnahitrost = podatek;//namesto podatek2 vstaviš vrednost hitrosti.
        		
        		
        	
        	//}            
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
	}

}
