package flightmill;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author wjrfo
 */
public class Config {

    private int hertz = 100;                // data collection rate per channel
    private double armLengthBug = 5.5;      // length of arm to insect
    private double armLengthFlag = 6.0;     // length of arm to sensor (opposite bug)
    private int sampleLength = 2 * 60 * 60; // sample length in seconds, e.g 2 hours
    private int numberOfChannels = 5;       // number of bugs being monitored
    
    private static final String HERTZ = "hertz";
    private static final String ARMLENGTHBUG = "armLengthBug";
    private static final String ARMLENGTHFLAG = "armLengthFlag";
    private static final String SAMPLELENGTH = "sampleLength";
    private static final String NUMBEROFCHANNELS = "numberOfChannels";

    private static final String ConfigDir = "AppData\\Local\\FlightMill";  // directory under user.home

    private java.util.Properties properties = new java.util.Properties();
    private String propertiesFileName = "config.xml";

    public static void main(String[] args) {
        Config config = new Config();
        config.saveProperties();
        
    }
    
    static Config loadConfig() {

        Config config = new Config();
        config.loadProperties();
        return config;
    }

    public void loadProperties() {
        File localDir = new File(System.getProperty("user.home"), ConfigDir);
        if (localDir.exists()) {
            File localFile = new File(localDir, propertiesFileName);
            if (localFile.exists()) {
                try {
                    InputStream is = new FileInputStream(localFile);
                    properties.loadFromXML(is);
                    setHertz(Integer.parseInt(properties.getProperty(HERTZ)));
                    setArmLengthBug(Double.parseDouble(properties.getProperty(ARMLENGTHBUG)));
                    setArmLengthFlag(Double.parseDouble(properties.getProperty(ARMLENGTHFLAG)));
                    setSampleLength(Integer.parseInt(properties.getProperty(SAMPLELENGTH)));
                    setNumberOfChannels(Integer.parseInt(properties.getProperty(NUMBEROFCHANNELS)));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void saveProperties() {

        properties.setProperty(HERTZ, getHertz() + "");
        properties.setProperty(ARMLENGTHBUG, getArmLengthBug() + "");
        properties.setProperty(ARMLENGTHFLAG, getArmLengthFlag() + "");
        properties.setProperty(SAMPLELENGTH, getSampleLength() + "");
        properties.setProperty(NUMBEROFCHANNELS, getNumberOfChannels()+ "");

        File localDir = new File(System.getProperty("user.home"), ConfigDir);
        if (!localDir.exists()) {
            localDir.mkdir();
        }
        File localFile = new File(localDir, propertiesFileName);
        OutputStream os;
        try {
            os = new FileOutputStream(localFile);
            properties.storeToXML(os, "flight mill");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    /**
     * @return the hertz
     */
    public int getHertz() {
        return hertz;
    }

    /**
     * @param hertz the hertz to set
     */
    public void setHertz(int hertz) {
        this.hertz = hertz;
    }

    /**
     * @return the armLengthBug
     */
    public double getArmLengthBug() {
        return armLengthBug;
    }

    /**
     * @param armLengthBug the armLengthBug to set
     */
    public void setArmLengthBug(double armLengthBug) {
        this.armLengthBug = armLengthBug;
    }

    /**
     * @return the armLengthFlag
     */
    public double getArmLengthFlag() {
        return armLengthFlag;
    }

    /**
     * @param armLengthFlag the armLengthFlag to set
     */
    public void setArmLengthFlag(double armLengthFlag) {
        this.armLengthFlag = armLengthFlag;
    }
    
        /**
     * @return the sampleLength
     */
    public int getSampleLength() {
        return sampleLength;
    }

    /**
     * @param sampleLength the sampleLength to set
     */
    public void setSampleLength(int sampleLength) {
        this.sampleLength = sampleLength;
    }


    /**
     * @return the numberOfChannels
     */
    public int getNumberOfChannels() {
        return numberOfChannels;
    }

    /**
     * @param numberOfChannels the numberOfChannels to set
     */
    public void setNumberOfChannels(int numberOfChannels) {
        this.numberOfChannels = numberOfChannels;
    }
//</editor-fold>

}
