import java.io.FileNotFoundException;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

public class Pretvornik {
    public static void main(String [] args) throws FileNotFoundException, JavaLayerException{
    	Converter converter = new Converter();
    	converter.convert("test.mp3", "test.wav");

    }

}