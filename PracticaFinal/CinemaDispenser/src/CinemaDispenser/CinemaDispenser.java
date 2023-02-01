/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package CinemaDispenser;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Andreas
 */
public class CinemaDispenser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        Multiplex m = new Multiplex(); 
        m.start(); //Inicio del programa
    }
    
    
    
}
