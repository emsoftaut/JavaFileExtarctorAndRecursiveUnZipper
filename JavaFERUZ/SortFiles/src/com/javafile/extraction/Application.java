package com.javafile.extraction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.filechooser.FileSystemView;

/**
 * @author em8149
 *
 */
public class Application {

    /**
     * @param args
     * @throws IOException
     */

    private ArrayList<File> messFolderArry;
    private ArrayList<File> fileAbsolutePath;
    private FindJavaFiles findJavaFiles;

    public Application(FindJavaFiles findJavaFiles) {
        messFolderArry = new ArrayList<>();
        fileAbsolutePath = new ArrayList<>();
        this.findJavaFiles = findJavaFiles;
    }

    public void sortTheMess(File file, String destinationDir) {
        File[] f = file.listFiles();
        Set<File> sortMess = new HashSet<>();
        String folderName = "";
        String studentName = "";

        for(File f1 : f) {
            if(f1.getName().substring(f1.getName().length()-4, f1.getName().length()).equalsIgnoreCase("java")) {
                fileAbsolutePath.add(new File(f1.getAbsolutePath()));
                folderName = f1.getAbsolutePath().substring(destinationDir.length(),destinationDir.length()+49);
                studentName = folderName.substring(14, 21);
                folderName = folderName + " " + studentName;
                sortMess.add(new File(folderName));
            }
        }

        for(File f2 : sortMess) {
            createMessFolder(new File(destinationDir + f2));
        }
    }


    private void createMessFolder(File file) {
        file.mkdir();
        messFolderArry.add(file);
    }


    public void display(String destDir) {
        String comp1, comp2 = "";
        System.out.println("Hi");
        String javaClassName = "";
        for(File f: messFolderArry) {
            for(File str: fileAbsolutePath) {
                comp1 = f.toString().substring(destDir.length(), destDir.length() + 21);
                comp2 = str.toString().substring(destDir.length(), destDir.length() + 21);
                if(comp1.equalsIgnoreCase(comp2)) {
                    javaClassName = str.toString().substring(destDir.length() + 50);
                    findJavaFiles.copyFilesSourceDest(str.toString(), f.toString() + "\\" +javaClassName);
                    str.delete();
                }
            }
        }
    }
}
