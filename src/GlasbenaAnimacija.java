import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class GlasbenaAnimacija {
	

    /**
     * Test client.
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	
    	

        Draw draw1 = new Draw("Test client 1");

        
        AudioWaveformCreator awc = new AudioWaveformCreator("mono_16bit.wav", "test.png");
        try {
            File yourFile = new File("mono_16bit.wav");
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
        		draw1.clear();        		
        		draw1.filledSquare(.50, .50, podatek/1000); //(podatek+1)/1000);
        		Thread.sleep(cas);
        		Color barva2 = new Color(125,125,125);
        		draw1.setPenColor(barva2);
        		draw1.filledSquare(.50, .50, podatek/1000); //(podatek+1)/1000);
        		
        		int prerac = (int) Math.exp(podatek);
        		draw1.setPenRadius(0.03);
        		//draw1.circle(0, 0, podatek);
        		
        		draw1.arc(.5, .5, .3, podatek, .45);
        		
        		//System.out.println(podatek);
        		
        	}            
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        
    }

}