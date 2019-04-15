package gui;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import main.FilePrinter;
import main.Kiosk;
import main.KioskThread;
import main.Location;

import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * This class is used to create the GUI to control and monitor scraping operations.
 * GuiWindow objects are only created by the InitialWindow class.
 * Creates KioskThreads and controls the activation of threads while displaying output.
 * 
 * @author KaranjitGahunia
 *
 */
public class GuiWindow implements ActionListener {

	private KioskThread north;
	private KioskThread south;
	private KioskThread city;
	
	private JFrame frame;
	private JTextArea textArea;
	

	/**
	 * Default constructor
	 * Initializes the KioskThread objects and starts the threads.
	 * Calls private method initialize to setup the GUI. 
	 */
	public GuiWindow() {		
		this.north = new KioskThread(new Kiosk(Location.NORTH));
		this.south = new KioskThread(new Kiosk(Location.SOUTH));
		this.city = new KioskThread(new Kiosk(Location.CITY));		
		
		north.start();
		south.start();
		city.start();
		
		initialize();
	}

	/**
	 * Private method called by GuiWindow constructor.
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		
		/* 
		 * buttons array to store the JToggleButton and JButton objects
		 * Has to be of type JComponent to incorporate both types of buttons.
		 * This is passed to ExitButtonListener so that the buttons can be disabled when program is closing.
		 */
		JComponent[] buttons = new JComponent[9];
		
		// Disable closing of window by pressing "X" in top right corner in order to safely stop all threads.
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		// Set horizontal gap between components.
		BorderLayout borderLayout = (BorderLayout) frame.getContentPane().getLayout();
		borderLayout.setHgap(20);
		
		frame.setTitle("Kiosk Scraping Tool");
		
		// Create the frame in the centre of the screen with a fixed size. Frame shouldn't be resizable.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((int) ((screenSize.getWidth() - 450) / 2), (int) ((screenSize.getHeight() - 300) / 2), 450, 300);
		frame.setMaximumSize(frame.getSize());
		frame.setResizable(false);
		
		// Create horizontal container box and add to the top of the frame.
		Box northPanelsHoriz = Box.createHorizontalBox();
		frame.getContentPane().add(northPanelsHoriz, BorderLayout.NORTH);
		
		// Create vertical container box and add to the left of the northPanelsHoriz box.
		Box westButtonsVert = Box.createVerticalBox();
		northPanelsHoriz.add(westButtonsVert);
		
		// Create panel to store thread activation and log activation buttons and add to westButtonsVert.
		JPanel cityButtonsPanel = new JPanel();
		westButtonsVert.add(cityButtonsPanel);
		cityButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
		
		JLabel lblCity = new JLabel("City");
		cityButtonsPanel.add(lblCity);
		
		// Create thread start button and add to panel. Add to buttons array too.
		JToggleButton cityThreadToggle = new JToggleButton("Start");		
		cityButtonsPanel.add(cityThreadToggle);
		buttons[0] = cityThreadToggle;
		
		// Create thread toggle button and add to panel. Add to buttons array and disable button.
		JToggleButton cityLogsToggle = new JToggleButton("Enable Logs");
		cityLogsToggle.setEnabled(false);
		cityButtonsPanel.add(cityLogsToggle);
		buttons[1] = cityLogsToggle;
		
		// Create panel to store thread activation and log activation buttons and add to westButtonsVert.
		JPanel southButtonsPanel = new JPanel();
		FlowLayout fl_southButtonsPanel = (FlowLayout) southButtonsPanel.getLayout();
		fl_southButtonsPanel.setHgap(20);
		westButtonsVert.add(southButtonsPanel);
		
		JLabel lblSouth = new JLabel("South");
		southButtonsPanel.add(lblSouth);
		
		// Create thread start button and add to panel. Add to buttons array too.
		JToggleButton southThreadToggle = new JToggleButton("Start");
		southButtonsPanel.add(southThreadToggle);
		buttons[2] = southThreadToggle;
		
		// Create thread toggle button and add to panel. Add to buttons array and disable button.
		JToggleButton southLogsToggle = new JToggleButton("Enable Logs");
		southLogsToggle.setEnabled(false);
		southButtonsPanel.add(southLogsToggle);
		buttons[3] = southLogsToggle;
		
		// Create panel to store thread activation and log activation buttons and add to westButtonsVert.
		JPanel northButtonsPanel = new JPanel();
		FlowLayout fl_northButtonsPanel = (FlowLayout) northButtonsPanel.getLayout();
		fl_northButtonsPanel.setHgap(20);
		westButtonsVert.add(northButtonsPanel);
		
		JLabel lblNorth = new JLabel("North");
		lblNorth.setHorizontalAlignment(SwingConstants.CENTER);
		northButtonsPanel.add(lblNorth);
		
		// Create thread start button and add to panel. Add to buttons array too.
		JToggleButton northThreadToggle = new JToggleButton("Start");
		northButtonsPanel.add(northThreadToggle);
		buttons[4] = northThreadToggle; 
		
		// Create thread toggle button and add to panel. Add to buttons array and disable button.
		JToggleButton northLogsToggle = new JToggleButton("Enable Logs");
		northLogsToggle.setEnabled(false);
		northButtonsPanel.add(northLogsToggle);
		buttons[5] = northLogsToggle; 
		
		// Create vertical container box and add to the left of northPanelsHoriz box.
		Box eastButtonsVert = Box.createVerticalBox();
		northPanelsHoriz.add(eastButtonsVert);
		// Set alignment of components in eastButtonsVert to center.
		eastButtonsVert.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Add panel to contain clear console button. Add panel to eastButtonsVert.
		JPanel clearConsolePanel = new JPanel();
		eastButtonsVert.add(clearConsolePanel);
		
		// Create button to clear console. Add to clearConsolePanel and buttons array.
		JButton clearConsoleButton = new JButton("Clear console");
		clearConsolePanel.add(clearConsoleButton);
		buttons[6] = clearConsoleButton;
		
		// Set action command for button interaction and set listener to this.
		clearConsoleButton.setActionCommand("clear console");
		clearConsoleButton.addActionListener(this);
		
		// Add panel to contain directory button. Add panel to eastButtonsVert.
		JPanel directoryPanel = new JPanel();
		eastButtonsVert.add(directoryPanel);
		
		// Create button to open directory where files are stored. Add to directoryPanel and buttons array.
		JButton directoryButton = new JButton("Directory");
		directoryPanel.add(directoryButton);
		buttons[7] = directoryButton;
		
		// Set action command for button interaction and set listener to this.
		directoryButton.setActionCommand("directory");
		directoryButton.addActionListener(this);
		
		// Add panel to contain exit button. Add panel to eastButtonsVert.
		JPanel exitPanel = new JPanel();
		eastButtonsVert.add(exitPanel);
		
		// Create button to exit program. Add to exitPanel and buttons array.
		JButton exitButton = new JButton("Exit");
		exitPanel.add(exitButton);
		buttons[8] = exitButton;
		
		// Set action listener to new ExitButtonListener. Pass buttons array and KioskThread objects.
		exitButton.addActionListener(new ExitButtonListener(buttons, north, city, south));
		
		// Create new JTextArea to display program output messages to update user.
		textArea = new JTextArea();
		textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setMargin(new Insets(10,10,10,10));
		textArea.setEditable(false);
		
		// Create a JScrollPane, add the textArea to it, and then add the JScrollPane to the frame.
		// This is to make the textArea scrollable, rather than having it constantly increase in size.
		JScrollPane textScrollPane = new JScrollPane(textArea);
		textScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(textScrollPane, BorderLayout.CENTER);
		
		// Set the itemListeners for each threadToggle button as a new ThreadToggleListener
		cityThreadToggle.addItemListener(new ThreadToggleListener(cityThreadToggle, cityLogsToggle, city));
		southThreadToggle.addItemListener(new ThreadToggleListener(southThreadToggle, southLogsToggle, south));
		northThreadToggle.addItemListener(new ThreadToggleListener(northThreadToggle, northLogsToggle, north));
		
		// Set the itemListeners for each logsToggle button as a new LogsToggleListener
		cityLogsToggle.addItemListener(new LogsToggleListener(cityLogsToggle, city));
		southLogsToggle.addItemListener(new LogsToggleListener(southLogsToggle, south));
		northLogsToggle.addItemListener(new LogsToggleListener(northLogsToggle, north));	
		
		// Direct all system output to the textArea.
		// System.err will remain the same.
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(printStream);
		
		// Display the frame
		frame.setVisible(true);
	}
	
	/**
	 * Overrides default actionPerformed.
	 * Method required as class implements ActionListener.
	 * Used to provide functionality to directoryButton and clearConsoleButton.
	 * 
	 * @param eve - ActionEvent containing information on event.
	 */
	@Override
	public void actionPerformed(ActionEvent eve) {
		// If directoryButton is clicked, open the directory in windows explorer.
		if ("directory".equals(eve.getActionCommand())) {
			try {
				Desktop.getDesktop().open(new File(FilePrinter.directory));
				System.out.println("> Opening webpage directory");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// If clearConsoleButton is clicked, replace the text in textArea.
		if ("clear console".equals(eve.getActionCommand())) {
			textArea.setText("> Console cleared\n");
		}
	}
}
