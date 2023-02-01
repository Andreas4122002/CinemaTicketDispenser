/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaDispenser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;


/**
 *
 * @author Andreas
 */
public class Film implements Serializable{
    private String name;
    private File poster;
    private int duration;
    private String description;

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    public Film(File poster) throws FileNotFoundException{
        this.poster=poster;
        
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public File getPoster() {
        return poster;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }
    
    
}
