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
		inputButton1 = new JButton("Open SurveyGizmo Data File", createImageIcon("/images/Open16.gif"));
		inputButton1.addActionListener(this);
		inputButton2 = new JButton("Open MTurk Data File", createImageIcon("/images/Open16.gif"));
		inputButton2.addActionListener(this);

		// Create the save button.
		saveButton = new JButton("Save Output File", createImageIcon("/images/Save16.gif"));
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

	// TODO: change signature to return the file contents.
	private void inputButton1Action(ActionEvent e) {
		int returnVal = fc.showOpenDialog(FileChooser.this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// TODO: This is where we open the first file.
			log.append("Opening: " + file.getName() + "." + newline);
		} else {
			log.append("Open command cancelled by user." + newline);
		}
		log.setCaretPosition(log.getDocument().getLength());
	}

	// TODO: change signature to return the file contents.
	private void inputButton2Action(ActionEvent e) {
		int returnVal = fc.showOpenDialog(FileChooser.this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// TODO: This is where we open the first file.
			log.append("Opening: " + file.getName() + "." + newline);
		} else {
			log.append("Open command cancelled by user." + newline);
		}
		log.setCaretPosition(log.getDocument().getLength());
	}

	private void saveButtonAction(ActionEvent e) {
		int returnVal = fc.showSaveDialog(FileChooser.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// TODO: This is where we save the file.
			log.append("Saving: " + file.getName() + "." + newline);
		} else {
			log.append("Save command cancelled by user." + newline);
		}
		log.setCaretPosition(log.getDocument().getLength());
	}

	public void actionPerformed(ActionEvent e) {
		// Handle open button actions.
		if (e.getSource() == inputButton1) {
			inputButton1Action(e);

		} else if (e.getSource() == inputButton2) {
			inputButton2Action(e);
		}

		else if (e.getSource() == saveButton) {
			// Handle save button action.
			saveButtonAction(e);
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
