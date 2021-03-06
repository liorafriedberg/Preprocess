import javax.swing.*;

import fileIO.FileChooser;

public class ResultsProcessorGUIDriver {
	
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("SurveyGizmo and MTurk Results Processor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new FileChooser());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
        
	}

}
