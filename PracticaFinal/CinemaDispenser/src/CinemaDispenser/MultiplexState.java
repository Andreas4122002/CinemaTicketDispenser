/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaDispenser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import sienens.CinemaTicketDispenser;
/**
 *
 * @author Andreas
 */
class MultiplexState implements Serializable {
    
    
    private List<Theater> theaterList = new ArrayList<>();
    
    

    public MultiplexState(CinemaTicketDispenser c, Multiplex m) throws FileNotFoundException { //Constructor que crea todos los teatros que a su vez crean las peliculas, sesiones y sitios
        for (int i=1;i<=4;i++){
            File f1 = new File("./src/recursos/Movie"+i+".txt");
            int a;
            try (Scanner sc = new Scanner(f1)) {
                Pattern p = Pattern.compile("[0-9]+");
                String matchString = sc.findInLine(p);
                a = Integer.parseInt(matchString);
                File f2 = new File("./src/recursos/Theater"+a+".txt");
                Theater teatro = new Theater(f1,f2);
                theaterList.add(teatro);
            }
            catch(Exception e){
                System.err.println(e.getMessage());
            }
            
        }
        
    }
    
    
    public Theater getTheater(int i){
        return theaterList.get(i);
    }
    
    public int getNumberOfTheaters(){
        return theaterList.size();
    }

    public List<Theater> getTheaterList() {
        return theaterList;
    }
    
    
    
}
