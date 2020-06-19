package com.javafile.extraction;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author em8149
 *
 */
public class FindJavaFiles {

    private Set<File> studentFile;
    private ArrayList<String> destinationArry;
    private ArrayList<String> sourceArry;
    private String sourcePath;
    private String destinationPath;

    /**
     * CONSTRUCTOR FOR INITIALIZING GLOBAL VARIABLES AND ARRAYLISTS
     */
    public FindJavaFiles() {
        this.sourcePath = "";
        this.destinationPath = "";
        studentFile = new HashSet<>();
        destinationArry = new ArrayList<>();
        sourceArry = new ArrayList<>();
    }

    /**
     * THIS FUNCTION GOES THROUGH THE ENTIRE DIRECTORY AND
     * ONLY ADDS FILES ENDING WITH .java EXTENSION TO THE studentFile<> HASHSET
     */
    public void getJavaFiles(File sourceFile) {
        File[] sourceFileArry = sourceFile.listFiles();
        if(sourceFileArry != null) {
            for(File file : sourceFileArry) {
                if(file.isDirectory()) {
                    getJavaFiles(file.getAbsoluteFile());
                }
                else {
                    if(file.getName().
                            substring(file.getName().length() - 4, file.getName().length())
                            .equalsIgnoreCase("java")) {
                        studentFile.add(file.getParentFile());
                    }
                }
            }
        }
    }

    /**
     * THIS FUNCTION CREATES THE DESTINATION FOLDER BASED ON THE ENTERIES IN THE studentFile<> ARRAYLIST
     * @param  sourcePath IS USED TO MAINTAIN A COPY OF THE ORIGINAL PATH AND IS USED LATER IN THE CODE
     * @param  destinationPath MAINTIANS THE DESTINATION PATH WITHOUT THE STUDENT NAME AND ASSIGNS TO THE GLOBAL VARIABLE
     * destPath MAINTAINS THE DESTINATION PATH WITH THE STUDENT NAME FOR CRAETING A FOLDER WITH STUDENT NAME
     */
    public void createDestinationStudentsFolder(String sourcePath, String destinationPath) throws IOException {
        this.sourcePath = sourcePath;
        String subStuFile = "";
        String studentName = "";
        String destPath = "";
        for(File stuFile: studentFile) {
            subStuFile = stuFile.toString().substring(sourcePath.length()+1, stuFile.toString().length());
            if(subStuFile.indexOf("\\") != -1) {
                studentName = subStuFile.substring(49, subStuFile.indexOf("\\"));

            }
            else {
                studentName = subStuFile.substring(49);

            }

            studentName = studentName.replaceAll(".zip|-| |_", "");
            System.out.println(studentName);
            //studentName = studentName.toUpperCase();


            if(studentName.equalsIgnoreCase("ASSIGNMENT1PROGRAMMING2")){
                System.out.println(studentName);
                System.out.println(stuFile.getAbsoluteFile());
            }
           // System.out.println(stuFile.getAbsoluteFile());
          // System.out.println("----------------------------");
            this.destinationPath = destinationPath;
            destPath = this.destinationPath + studentName;
            File directory = new File(destPath);
            directory.mkdirs();
        }
    }

    /**
     * THIS FUNCTION CONSTRUCTS THE PATH OF THE SOURCE JAVA FILES
     */
    public void constructSourcePath(File sourceFile) {
        File[] sourceFileArry = sourceFile.listFiles();
        if(sourceFileArry != null) {
            for(File file : sourceFileArry) {
                if(file.isDirectory()) {
                    constructSourcePath(file.getAbsoluteFile());
                }
                else {
                    if(file.getName().
                            substring(file.getName().length() - 4, file.getName().length())
                            .equalsIgnoreCase("java")) {
                        sourceArry.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    /**
     * THIS FUNCTION CONSTRUCTS THE PATH OF THE DESTINATION JAVA FILE
     */
    public void constructDestinationPath() {
        String str2 = "";
        String pathStr = "";
        for(String str : sourceArry) {
            str2 = str.substring(this.sourcePath.length() + 50);
            pathStr = str2.substring(0, str2.indexOf("\\")).replaceAll(".zip|-| |_", "");
            //pathStr = str.replaceAll(".zip|-| |_", "");
            this.destinationArry.add(this.destinationPath + pathStr + getJavaClassName(str));
        }
    }

    /**
     * THIS PRIVATE METHOD RETURNS THE EXACT NAME OF THE JAVA FILE SPECIFIED BY THE STUDENT
     * THIS IS A PRIVATE METHOD AND USED IN THE constructDestinationPath() METHOD.
     */
    private String getJavaClassName(String path) {
        String className = "";
        String classParent = "";
        File file = new File(path);
        classParent = file.getParent();
        className = path.substring(classParent.length(), path.length());
        return className;
    }

    /**
     * THIS METHODS MAPS THE EXACT SOURCE PATH TO THE TARGET PATH
     * A DIRECT one-to-one MAPPING IS MAINTAINED BETWEEN SOURCE java FILES AND DESTINATION java FILES
     * THE MAPPING IS one-to-one BECAUSE DESTINATION PATH IS CREATED FROM THE SOURCE PATH
     * METHOD copyFilesSourceDest(source, destination) IS ALSO CALLED FROM THIS METHOD
     */
    public void sourceDestinationMapping() {
        int count = 0;
        String destinationPath = "";
        for(String sourcePath: sourceArry) {
            destinationPath = destinationArry.get(count);
            copyFilesSourceDest(sourcePath,destinationPath);
            count++;
        }
        System.out.println("Process Completed");
    }

    /**
     * THIS PRIVATE METHOD COPIES FILES FROM SOURCE FOLDER TO DESTINATION FOLDER
     */
    public void copyFilesSourceDest(String source, String destination) {
        Path sourcePath = Paths.get(source);
        Path destinationPath = Paths.get(destination);
        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("PROCESS COMPLETED");
    }
}


