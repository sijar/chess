package chess.utility;

import chess.constants.IConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * April 5, 2010, 12:26:20 PM
 * @author Sijar Ahmed
 */
public class FileReaderUtility {

    private static File file;
    private static StringBuilder fileContent;
    private static BufferedReader fileReader;
    private static String fileLine;


    /**
     * Default Constructor
     * <!-- Please keep it needed for Serialization to WSDL -->
     */
    public FileReaderUtility(){
    }


    /**
     * Method will read an entire {@code File} and returns it content as {@code String}
     *
     * @param path
     * @return {@code File} content as {@code String}
     */
    public static String readEntireFileContent(String path) {
        fileContent = new StringBuilder();
        //System.out.println("Path :" + path);
        try {
            file = new File(path);
            //System.out.println("File :" + file);
            //System.out.println("File Absolute Path:" + file.getAbsolutePath());
            //System.out.println("File Canonical Path:" + file.getCanonicalPath());
            //System.out.println("File Path:" + file.getPath());
            if (file.isFile()) {
                //System.out.println("File Size:" + file.length());
                fileReader = new BufferedReader(new FileReader(file));
                while ((fileLine = fileReader.readLine()) != null) {
                    fileContent.append(fileLine);
                }
            }else {
            	throw new IOException("File not found");
            }
        } catch (IOException ex) {
            Logger.getLogger(FileReaderUtility.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(FileReaderUtility.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return fileContent.toString();
    }


    /**
     * Method return the file size in MB
     *
     * @param file
     * @return
     */
    static double getFileSizeInMB(File file){
        if(file == null){
            return 0;
        }
        double fileSize = file.length();
        fileSize /= (1024*1024); //convert to MB
        return fileSize;
    }


    /**
     * Method to locate the Image Folder in the application
     *
     * @param originalPath
     * @return
     */
    public static String locateImageFolder(String unformattedPath){
        try{
        //System.out.print("\n%% Current Directory :" + unformattedPath);
        if(unformattedPath.endsWith("BoardMatrix")){
            unformattedPath += File.separatorChar;
            unformattedPath += "src";
        }else if(unformattedPath.endsWith(":\\")){
            unformattedPath = unformattedPath.substring(0,unformattedPath.indexOf(":\\") + 1);
        }
        unformattedPath += IConstants.IMAGE_PATH_NEW;
        //System.out.print("\n%% Current Directory :" + unformattedPath);
        }catch(Exception e){
            e.printStackTrace();
        }
        return unformattedPath;
    }


}