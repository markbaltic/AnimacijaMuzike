import java.io.FileNotFoundException;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

public class Pretvornik {
	
	public static String datoteka = null;
	
	
	public Pretvornik(String datoteka) {
		super();
		Pretvornik.datoteka = datoteka;
	}

	public static String dolociKoncnico(){
		String koncnica = String.valueOf(datoteka.charAt(datoteka.length()-3));
		koncnica = koncnica + String.valueOf(datoteka.charAt(datoteka.length()-2));
		koncnica = koncnica + String.valueOf(datoteka.charAt(datoteka.length()-1));
		return koncnica;
	}
	
	public static boolean pravilnostDatoteke(){
		if (dolociKoncnico().equals("mp3")){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static String pretvorimp3towav() throws JavaLayerException{
		if (pravilnostDatoteke()){
			Converter converter = new Converter();
			converter.convert(datoteka, datoteka + ".wav");
			return (datoteka + ".wav");
		}
		else{
			return datoteka;
		}
		
	}
		


}