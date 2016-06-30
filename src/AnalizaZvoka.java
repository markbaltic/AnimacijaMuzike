import java.awt.Color;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

//Glasbeno datoteko pretvorimo v seznam bajtov. Nato te damo v seznam "audioData", ki kjer so zbrana cela števila,
//ki ponazarjajo amplitudo zvoka v èasu.


public class AnalizaZvoka {
    AudioInputStream audioInputStream;
    Vector<Line2D.Double> lines = new Vector<Line2D.Double>();
    String errStr;
    double duration, seconds;
    File file;
    String fileName;
    String waveformFilename;
    Color imageBackgroundColor = new Color(20,20,20);
    ArrayList<Integer> seznam = new ArrayList<Integer>();
    ArrayList<Vector<Integer>> sezVektorjev = new ArrayList<Vector<Integer>>();
    int vsota = 0;
    public int dolzinaSezStevil;
	public int[] audioData;
	
	
    public AnalizaZvoka(String fileName) throws UnsupportedAudioFileException, IOException {
        this.file = new File(fileName);
        
    }

    public int[] createAudioInputStream() throws Exception {
        if (file != null && file.isFile()) {
            try {
                errStr = null;
                audioInputStream = AudioSystem.getAudioInputStream(this.file);
           
            } catch (Exception ex) { 
                throw ex;
            }
        } else {

        }
        AudioFormat format = audioInputStream.getFormat();
        byte[] audioBytes = new byte[
                              (int) (audioInputStream.getFrameLength() 
                              * format.getFrameSize())];
        audioInputStream.read(audioBytes);
        
        int[] audioData = null;
        if (format.getSampleSizeInBits() == 16) {
            int nlengthInSamples = audioBytes.length / 2;
            audioData = new int[nlengthInSamples];
            if (format.isBigEndian()) {
               for (int i = 0; i < nlengthInSamples; i++) {
                    /* First byte is MSB (high order) */
                    int MSB = (int) audioBytes[2*i];
                    /* Second byte is LSB (low order) */
                    int LSB = (int) audioBytes[2*i+1];
                    audioData[i] = MSB << 8 | (255 & LSB);
                }
            } else {
                for (int i = 0; i < nlengthInSamples; i++) {
                    /* First byte is LSB (low order) */
                    int LSB = (int) audioBytes[2*i];
                    /* Second byte is MSB (high order) */
                    int MSB = (int) audioBytes[2*i+1];
                    audioData[i] = MSB << 8 | (255 & LSB);
                }
            }        
            
            
            
        }
        else if (format.getSampleSizeInBits() == 8) {
            int nlengthInSamples = audioBytes.length;
            audioData = new int[nlengthInSamples];
            if (format.getEncoding().toString().startsWith("PCM_SIGN")) {
                for (int i = 0; i < audioBytes.length; i++) {
                    audioData[i] = audioBytes[i];                         
                }
            } else {
                for (int i = 0; i < audioBytes.length; i++) {
                    audioData[i] = audioBytes[i] - 128;
                }
            }
       }
        
      
        
        return audioData;
    
    }
    
    public double dolzinaPesmi() throws UnsupportedAudioFileException, IOException{
    	audioInputStream = AudioSystem.getAudioInputStream(this.file);
    	long milliseconds = (long)((audioInputStream.getFrameLength() * 1000) / audioInputStream.getFormat().getFrameRate());
        return milliseconds;
    }
    
    
    public void signal() throws UnsupportedAudioFileException, IOException{
		
		
	}
    
}
    
    

	
    
    

           
        

        
