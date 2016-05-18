import java.awt.Color;

import javax.swing.JFrame;

public class Test {

	public static void main(String[] args) {
		
		Animacija p = new Animacija();
		p.setBackground(Color.ORANGE);
		
		JFrame okno = new JFrame();
		
		okno.setTitle("Proba");
		okno.setSize(600, 600);
		okno.setVisible(true);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.add(p);
	}

}
