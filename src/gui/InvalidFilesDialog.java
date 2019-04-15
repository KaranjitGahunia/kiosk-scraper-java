import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
/**
 * This class creates and shows a Dialog box showing what required files are missing.
 * Also provides options to generate the files (copies files over) or to select a different directory.
 * Used by InitialWindow when selecting directory to store files in.
 * 
 * @author KaranjitGahunia
 *
 */
public class InvalidFilesDialog extends JDialog implements ActionListener {

	private JPanel contentPanel = new JPanel();
	private InitialWindow initFrame;
	private String[] missingFiles;
	private String directory;

	/**
	 * Default constructor for dialog.
	 * Initializes local variables and calls initialize method.
	 * 
	 * @param initFrame - InitalWindow object that created this object.
	 * @param missingFiles - String array containing the missing file names.
	 * @param directory - String containing chosen directory file path. 
	 */
	public InvalidFilesDialog(InitialWindow initFrame, String[] missingFiles, String directory) {
		this.initFrame = initFrame;
		this.missingFiles = missingFiles;
		this.directory = directory;
		initialize();
	}
	
	/**
	 * Creates and shows the GUI aspect of this object.
	 */
	private void initialize() {
		// Disable closing operation of this GUI.
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		// Open dialog in centre of screen. Fixed size. 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) ((screenSize.getWidth() - 450) / 2), (int) ((screenSize.getHeight() - 300) / 2), 450, 300);
		setResizable(false);
		
		// Use borderlayout, add border, and add a panel to center of contentPane.
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			// Main title label at top of screen.
			JLabel mainLabel = new JLabel("Couldn't find the required files in the selected directory");
			mainLabel.setBounds(19, 11, 387, 20);
			mainLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
			contentPanel.add(mainLabel);
		}
		{
			// JTextArea containing list of missing files.
			JTextArea missingFilesTextArea = new JTextArea();
			missingFilesTextArea.setEditable(false);
			missingFilesTextArea.setText("The following files weren't found:");
			for (String fileName : missingFiles) {
				missingFilesTextArea.append("\n> " + fileName);
			}
			missingFilesTextArea.setBounds(19, 67, 387, 102);
			contentPanel.add(missingFilesTextArea);
		}
		
		// JButton to generate files. Listener is this.
		JButton generateButton = new JButton("Generate missing files");
		generateButton.setBounds(19, 200, 179, 38);		
		generateButton.setActionCommand("generate");
		generateButton.addActionListener(this);
		contentPanel.add(generateButton);
		
		// JButton to select different folder. Listener is this.
		JButton selectFolderButton = new JButton("Select a different folder");
		selectFolderButton.setBounds(227, 200, 179, 38);
		selectFolderButton.setActionCommand("select folder");
		selectFolderButton.addActionListener(this);
		contentPanel.add(selectFolderButton);
	}

	@Override
	public void actionPerformed(ActionEvent eve) {
		if ("generate".equals(eve.getActionCommand())) {
			// copy files over from base files to selected directory
			for (String fileName : missingFiles) {
				copyFile(fileName);
			}
			// Close this dialog and show initFrame window 
			this.dispose();
			initFrame.displayWindow();
			// Call checkDirectory method to proceed to GUIWindow through initFrame.
			initFrame.checkDirectory(new File(directory));
		}
		if ("select folder".equals(eve.getActionCommand())) {
			// Close this dialog and show initFrame window
			this.dispose();
			initFrame.displayWindow();
			// Prompt user with JFileChooser for a directory. 
			initFrame.selectDirectory();
		}
	}
	
	/**
	 * Copies file from source directory to given directory.
	 * Used to add missing files to directory.
	 * 
	 * @param fileName - String representing name of file that is missing.
	 */
	private void copyFile(String fileName) {
		// TODO Get files as stream for .jar implementation
		Path existingFile = new File("src\\base files\\" + fileName).toPath();
		Path newFile = new File(directory + "\\" + fileName).toPath();
		try {
			Files.copy(existingFile, newFile);
		} catch (IOException e) {
			System.err.println("Error while copying " + existingFile.toString() + " to " + newFile.toString());
			e.printStackTrace();
		}
	}
}
