import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class Test {

	public static void main(String[] args) throws Exception {
		
		Animacija p = new Animacija();
		p.setBackground(Color.ORANGE);
		
		JFrame okno = new JFrame();
		
		okno.setTitle("Proba");
		okno.setSize(600, 600);
		okno.setVisible(true);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.add(p);
		//Graphics g1 = null;
		//p.risikvadrate(g1, 42);
		
		Pretvornik mp3 = new Pretvornik();
		AudioFormat wav = new AudioFormat(0, 0, 0, false, false);
		//mp3.getAudioDataBytes(, wav);
		
		AudioWaveformCreator awc = new AudioWaveformCreator("test.wav", "test.png");
        try {
            File yourFile = new File("test.wav");
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
        		//int podatek = awc.seznam.get(i);
        		int podatek = awc.seznam.get(i);
        		Thread.sleep(cas);
        		Color barva = new Color(podatek);
        		p.setBackground(barva);
        		
        		System.out.println(podatek);
        	}            
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
	}

}