import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

/**
 * Class used to redirect system output to a JTextArea component.
 * Used in GuiWindow class to redirect output to JTextArea so that
 * the logs are displayed and updated in real-time.
 * 
 * @author KaranjitGahunia
 *
 */
public class CustomOutputStream extends OutputStream {
	private JTextArea textArea;
	
	/**
	 * Default constructor for CustomOutputStream class.
	 * Initializes textArea variable.
	 * 
	 * @param textArea - JTextArea object
	 */
	public CustomOutputStream(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	@Override
	public void write(int b) throws IOException {
		// redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}