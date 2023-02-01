/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaDispenser;

import static javax.swing.UIManager.getString;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author Andreas
 */
public class LanguageSelection extends Operation {
     
    
   
    
    
    /**
     *
     */
    @Override
    public  void doOperation(){ //Imprime las opciones de idioma
        for(int i=0;i<6;i++){
            super.getDispenser().setOption(i, null);
        }
        super.getDispenser().setOption(0, java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("ESPAÑOL"));
        super.getDispenser().setOption(1, java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("INGLES"));
        super.getDispenser().setOption(2, java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("CATALAN"));
        super.getDispenser().setOption(3, java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("VASCO"));
        super.getDispenser().setOption(5, java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("MENU PRINCIPAL"));
        
        char t = super.getDispenser().waitEvent(30);
        if(t=='A'){
            super.getMultiplex().setLang("Español");
            
        }
        if(t=='B'){
            super.getMultiplex().setLang("English");
            
        }
        if(t=='C'){
            super.getMultiplex().setLang("Catalan");
            
        }
        if(t=='D'){
            super.getMultiplex().setLang("Vasco");
            
        }
        if(t=='F'){
            super.getMultiplex().getMm().doOperation();
        }
        super.getMultiplex().getMm().doOperation();
    }

    public LanguageSelection(CinemaTicketDispenser dispenser, Multiplex m) {
        super(dispenser, m);
       
    }
    
   
    
    
    @Override
    public String getTitle(){
        
        return java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("CAMBIAR IDIOMA");
        
    }
    
    
    
    
}
