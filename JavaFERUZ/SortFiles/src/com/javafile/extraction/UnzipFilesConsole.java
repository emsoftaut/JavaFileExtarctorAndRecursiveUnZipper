package com.javafile.extraction;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

public class UnzipFilesConsole extends JPanel implements ActionListener {
	
	private final static int PANEL_WIDTH = 300;
	private final static int PANEL_HEIGHT = 100;
	private JButton search, chooseFile;
	private String zip_file;
	private Application app;
	
	
	public UnzipFilesConsole() {
		super();
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		search = new JButton("Generate Student Folder");
		chooseFile = new JButton("Choose Project Folder");
		search.addActionListener(this);
		chooseFile.addActionListener(this);
		this.add(chooseFile);
		this.add(search);
		
	
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == search) {
			System.out.println("search");
			 if(zip_file.equalsIgnoreCase(" ")){
                 JOptionPane.showMessageDialog(null,"Please Select a File");
             }
			 
			 
			 
			 
           File interDestinationFolder = FileSystemView.getFileSystemView().getHomeDirectory();
           interDestinationFolder = interDestinationFolder.getAbsoluteFile();
           interDestinationFolder.mkdirs();
           String interDestinationPath = interDestinationFolder+"\\Output";

           System.out.println(interDestinationPath);
           String DESTINATION_DIRECTORY = zip_file;

           Unzipper unzip = new Unzipper();
           if (unzip.unzipToFile(zip_file,interDestinationPath )) {
               System.out.println("Succefully unzipped to the directory "
                       + interDestinationPath);
           } else {
               System.out.println("There was some error during extracting archive to the directory "
                       + interDestinationPath);
           }
			 
 
			 
		}
		else if(e.getSource() == chooseFile) {
			//System.out.println("chooseFile");
			
			File currentDir = FileSystemView.getFileSystemView().getHomeDirectory();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(currentDir);
            fileChooser.setDialogTitle("Choose the Zip File!");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

            if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                //TODO
            }
            System.out.println("You choose this"+fileChooser.getSelectedFile().getAbsoluteFile());
            zip_file = fileChooser.getSelectedFile().getAbsoluteFile().toString();
		}
		
	}

	
	 public static void main(String[] args) {
		 JFrame frame = new JFrame("FILE PICKER");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			UnzipFilesConsole myPanel = new UnzipFilesConsole();
			frame.getContentPane().add(myPanel);  
			//add instance of MyGUI to the frame
			frame.pack(); 
			//resize frame to fit our Jpanel
			//Position frame on center of screen 
			Toolkit tk = Toolkit.getDefaultToolkit();	
			Dimension d = tk.getScreenSize();	
			int screenHeight = d.height;	
			int screenWidth = d.width;
		    frame.setLocation(new Point((screenWidth/2)-(frame.getWidth()/2),(screenHeight/2)-(frame.getHeight()/2)));
			//show the frame	
		    frame.setVisible(true);
	    }



	
}
