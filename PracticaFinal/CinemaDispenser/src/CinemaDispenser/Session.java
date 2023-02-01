/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaDispenser;

import java.util.Set;
import java.io.Serializable;
import java.util.HashSet;

/**
 *
 * @author Andreas
 */
public class Session implements Serializable {
    private String hour;
    private Set<Seat> occupiedSeatSet = new HashSet<>();
    
    public Session(String a){
        hour = a;
    }
    


    public Set<Seat> getOccupiedSeatSet() {
        return occupiedSeatSet;
    }

    public String getHour() {
        return hour;
    }
    


}

