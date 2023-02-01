/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaDispenser;

import java.io.FileNotFoundException;
import java.io.IOException;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author Andreas
 */
public class Multiplex {
    
   CinemaTicketDispenser dispenser;
   Operation mm; 

    public Operation getMm() {
        return mm;
    }

    public String getLang() {
        return lang;
    }
           
    private String lang;
    
    public  void start() throws FileNotFoundException{
        this.setLang("Español");
        mm.doOperation();
        
        
    }    

    public Multiplex() throws FileNotFoundException, IOException, ClassNotFoundException {
        dispenser = new CinemaTicketDispenser();
        mm = new MainMenu(dispenser,this);      
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
    




 
}
