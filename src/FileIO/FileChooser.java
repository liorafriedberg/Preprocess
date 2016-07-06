package FileIO;

import javax.swing.JFileChooser;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FileChooser extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private final String newline = "\n";
	JButton inputButton1;
	JButton inputButton2;
	JButton saveButton;

	File input1, input2, output = null;

	JTextArea log;
	JFileChooser fc;

	public FileChooser() {
		super(new BorderLayout());

		// Create the log first, because the action listeners
		// need to refer to it.
		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		// Create a file chooser
		fc = new JFileChooser();

		// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Create the open buttons.
		inputButton1 = new JButton("Open SurveyGizmo Data File", createImageIcon("src\\images\\Open16.gif"));
		inputButton1.addActionListener(this);
		inputButton2 = new JButton("Open MTurk Data File", createImageIcon("src\\images\\Open16.gif"));
		inputButton2.addActionListener(this);

		// Create the save button.
		saveButton = new JButton("Select Output File", createImageIcon("src\\images\\Save16.gif"));
		saveButton.addActionListener(this);

		// For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(inputButton1);
		buttonPanel.add(inputButton2);
		buttonPanel.add(saveButton);

		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.CENTER);
	}

	/**
	 * Opens a GUI that allows a user to choose a file to open.
	 * 
	 * @return Returns a File object if the user-specified file was successfully
	 *         opened. Null if not.
	 */
	private File inputButtonAction() {
		int returnVal = fc.showOpenDialog(FileChooser.this);
		File file = null;

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			log.append("Opening: " + file.getName() + "." + newline);
			if (!file.canRead()) {
				log.append("Cannot open specified file for reading." + newline);
				return null;
			}
		} else {
			log.append("Open command cancelled by user." + newline);
		}
		log.setCaretPosition(log.getDocument().getLength());
		return file;
	}

	private File saveButtonAction() {
		int returnVal = fc.showSaveDialog(FileChooser.this);
		File file = null;

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			log.append("Saving at: " + file.getName() + "." + newline);
			if (!file.canWrite()) {
				log.append("Couldn't save file - can't write to selected filename." + newline);
				return null;
			}
		} else {
			log.append("Save command cancelled by user." + newline);
		}
		log.setCaretPosition(log.getDocument().getLength());
		return file;
	}

	public void actionPerformed(ActionEvent e) {
		// Handle open button actions.
		if (e.getSource() == inputButton1) {
			input1 = inputButtonAction();

		} else if (e.getSource() == inputButton2) {
			input2 = inputButtonAction();
		}

		else if (e.getSource() == saveButton) {
			// Handle save button action.
			output = saveButtonAction();
		}
	}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = FileChooser.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

}
