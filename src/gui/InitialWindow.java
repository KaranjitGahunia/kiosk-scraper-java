package gui;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import main.JSONParser;

/**
 * This class is used to create the GUI to start the program and select the directory.
 * InitialWindow objects are only created by the Main class.
 * Prompts user for directory to store HTML files and creates GuiWindow object.
 * 
 * @author KaranjitGahunia
 *
 */
public class InitialWindow implements ActionListener {

	private JFrame frame;

	/**
	 * Default constructor.
	 * Calls private initialize method to create GUI.
	 */
	public InitialWindow() {
		initialize();
	}

	/**
	 * Private method called by InitialWindow constructor.
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Create the frame in the center of the screen with a fixed size. Frame shouldn't be resizable.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((int) ((screenSize.getWidth() - 450) / 2), (int) ((screenSize.getHeight() - 300) / 2), 450, 300);
		frame.setResizable(false);
		
		// Title label for frame
		JLabel lblKioskScrapingTol = new JLabel("Kiosk Scraping Tool");
		lblKioskScrapingTol.setFont(new Font("Source Code Pro ExtraLight", Font.PLAIN, 18));
		lblKioskScrapingTol.setHorizontalAlignment(SwingConstants.CENTER);
		lblKioskScrapingTol.setBounds(0, 0, 434, 80);
		frame.getContentPane().add(lblKioskScrapingTol);

		// Start button. Should bring up JFileChooser. ActionListener is this.
		JButton startButton = new JButton("Start");
		startButton.setActionCommand("start");
		startButton.addActionListener(this);
		startButton.setBounds(50, 160, 120, 50);
		frame.getContentPane().add(startButton);

		// Exit button. Closes application. ActionListener is this.
		JButton exitButton = new JButton("Exit");
		exitButton.setActionCommand("exit");
		exitButton.addActionListener(this);
		exitButton.setBounds(257, 160, 120, 50);
		frame.getContentPane().add(exitButton);
		
		// Show the frame
		frame.setVisible(true);
	}

	/**
	 * Method to show frame of this object.
	 * Used by InvalidFilesDialog class.
	 */
	public void displayWindow() {
		this.frame.setVisible(true);
	}

	/**
	 * Overrides default actionPerformed.
	 * Method required as class implements ActionListener.
	 * Used to provide functionality to startButton and exitButton.
	 * 
	 * @param eve - ActionEvent containing information on event.
	 */
	@Override
	public void actionPerformed(ActionEvent eve) {
		// If startButton is clicked, open JFileChooser through selectDirectory method.
		if ("start".equals(eve.getActionCommand())) {
			selectDirectory();
		}
		// If exitButton is clicked, terminate the application thread.
		if ("exit".equals(eve.getActionCommand())) {
			System.exit(0);
		}
	}

	/**
	 * Opens up a JFileChooser that prompts the user to select a folder.
	 * That folder will store the HTML, CSS, and JS files used by this application.
	 */
	public void selectDirectory() {
		// Create a new JFileChooser object with default path set to C drive.
		JFileChooser chooser = new JFileChooser("C:\\Users\\em12259\\Documents\\kiosktest");
		// Perform "Go Up" action for JFileChooser to change the default path to "This PC".
		chooser.getActionMap().get("Go Up").actionPerformed(null);;
		// Only allow selection and viewing of directories in JFileChooser.
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		// Set the title for the popup
		chooser.setDialogTitle("Select your folder to store scraped files");
		// Open and display the JFileChooser. Store the returned value.
		int returnVal = chooser.showOpenDialog(frame);
		/*
		 * If the user successfully selected a directory, hide the InitialWindow frame.
		 * Call checkDirectory method to see if the selected directory contains the required files.
		 * Otherwise, will revert to showing and focusing on the InitialWindow frame.
		 */
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			frame.setVisible(false);
			checkDirectory(chooser.getSelectedFile());
		}
	}
	
	/**
	 * Checks to see if the selected File object contains the required files.
	 * File object should be a folder/directory as that is the only case where this is called.
	 * If it contains all files, create a GuiWindow object and dispose of InitialWindow frame.
	 * Otherwise, create a InvalidFilesDialog pop-up to show what files are missing.
	 * Method is called by selectDirectory method and InvalidFilesDialog class.
	 * 
	 * @param selectedFolder - File object that should be a directory.
	 */
	public void checkDirectory(File selectedFolder) {
		// Get all the files inside selectedFolder and store in array.
		File[] folderFiles = selectedFolder.listFiles();

		// Create array of the names of the required files.
		String[] requiredFileNames = {"city.html", "south.html", "north.html"};
		// Create ArrayList to store these values. Iterate through array and add to ArrayList.
		// Use ArrayList as it is easier to remove items from it that from an array.
		ArrayList<String> filesMissing = new ArrayList<String>();
		for (String fileName : requiredFileNames) {
			filesMissing.add(fileName);
		}
		
		// For each file in the folderFiles array:
		// if it isn't a folder and the name matches something in the requiredFiles ArrayList, remove that String from the ArrayList.
		for (File f : folderFiles) {
			if (f.isFile() && filesMissing.contains(f.getName())) {
				filesMissing.remove(f.getName());
			}
		}
		
		// If the filesMissing ArrayList is now empty, that means that the selectedFolder contains all the required files.
		// In that case, set the static FilePrinter.directory variable to the selectedFolder's path, create GuiWindow object, and dispose of this objects frame.
		// Otherwise, create and show a InvalidFilesDialog object which displays what files are missing and presents options to resolve this issue.
		if (filesMissing.isEmpty()) {
			System.out.println(selectedFolder.getAbsolutePath() + "\\");
			JSONParser.directory = selectedFolder.getAbsolutePath() + "\\";
			new GuiWindow();
			this.frame.dispose();
		} else {
			InvalidFilesDialog dialog = new InvalidFilesDialog(this, filesMissing.toArray(new String[filesMissing.size()]), selectedFolder.getAbsolutePath());
			dialog.setVisible(true);
		}
	}
}