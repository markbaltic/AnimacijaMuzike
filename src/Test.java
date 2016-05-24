import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

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
	static String datoteka = "mono_16bit.wav";
	static Pretvornik pDatoteka = new Pretvornik(datoteka);

	public static void main(String[] args) throws Exception {
		
		String petvorjenaDatoteka = pDatoteka.pretvorimp3towav();
		System.out.println(petvorjenaDatoteka);
		Animacija anim = new Animacija();
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
		
		
		
		
		AudioWaveformCreator awc = new AudioWaveformCreator(petvorjenaDatoteka, "test.png");
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
        
        awc.createAudioInputStream();
        int dolzina = awc.seznam.size();       
        int cas = (int) ((awc.duration/dolzina)*1000);
        try {        	
        	for (int i=0; i < dolzina; i++){
        		int podatek = awc.seznam.get(i);
        		Thread.sleep(cas);
        		Color barva = new Color(podatek*160000);
        		anim.setBackground(barva);
        		Animacija.st = podatek;
        		
        		System.out.println(podatek);
        	}            
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
	}

}
