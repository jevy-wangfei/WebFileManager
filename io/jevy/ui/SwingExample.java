package io.jevy.ui;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
 
/**
 * Basic Swing example.
 */
public class SwingExample {
    public static void main(String[] args) {
 
        // Make sure all Swing/AWT instantiations and accesses are done on the
        // Event Dispatch Thread (EDT)
        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	JFrame.setDefaultLookAndFeelDecorated(true);
				try {
					UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
					//UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
					//��������������
					UIManager.getLookAndFeelDefaults().put("defaultFont",
							 new Font("Microsoft Yahei",Font.PLAIN,12));
				} catch (Exception e) {
					e.printStackTrace();
				}
                // Create a JFrame, which is a Window with "decorations", i.e.
                // title, border and close-button
                JFrame f = new JFrame("Swing Example Window");
 
                // Set a simple Layout Manager that arranges the contained
                // Components
                f.setLayout(new FlowLayout());
 
                // Add some Components
                f.add(new JLabel("Hello, world!"));
                f.add(new JButton("Press me!"));
                f.add(new JTextField(30));
 
                // "Pack" the window, making it "just big enough".
                f.pack();
 
                // Set the default close operation for the window, or else the
                // program won't exit when clicking close button
                //  (The default is HIDE_ON_CLOSE, which just makes the window
                //  invisible, and thus doesn't exit the app)
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
                // Set the visibility as true, thereby displaying it
                f.setVisible(true);
            }
        });
    }
}