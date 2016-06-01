import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TestSound {

	public static void main(String[] args) throws Exception {
		AudioWaveformCreator awc = new AudioWaveformCreator("mono_16bit.wav");
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
        System.out.println(awc.duration);
        int dolzina = awc.seznam.size();
        int cas = (int) ((awc.duration/dolzina)*1000);
        try {        	
        	for (int i=0; i < dolzina; i++){
        		Thread.sleep(cas);
        		System.out.println(awc.seznam.get(i));
        	}            
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        //System.out.println(awc.seznam);

	}

}
