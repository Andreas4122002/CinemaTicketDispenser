/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaDispenser;

import sienens.CinemaTicketDispenser;

/**
 *
 * @author Andreas
 */
    public abstract class Operation {

    
    public Multiplex m;
    public CinemaTicketDispenser dispenser;
    
    
    
    public abstract void doOperation();

    public Operation( CinemaTicketDispenser dispenser, Multiplex m) {
        this.dispenser = dispenser;
        this.m=m;

    }
    
    public CinemaTicketDispenser getDispenser(){
        return dispenser;
    }
    
    
    public abstract String getTitle();

    public Multiplex getMultiplex() {
        return m;
    }
    
    
    
    
    
    
    
    
    
}
 