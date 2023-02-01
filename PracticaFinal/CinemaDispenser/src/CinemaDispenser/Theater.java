/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaDispenser;


import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

/**
 *
 * @author Andreas
 */
public class Theater implements Serializable{
    private int number;
    private int price;
    private final Set<Seat> seatsSet = new HashSet<>();
    private Film film;
    private List<Session> listaSesiones = new ArrayList<>();
    File f1;
    File f2;
    
    
    public Theater(File f1, File f2) throws FileNotFoundException, IOException{ //Cada vez que se crea un teatro, busca con expresiones regulares los datos de cada pelicula y su teatro
        //falta el try y catch
        this.f1=f1;
        this.f2=f2;
        loadSeats();
        Scanner sc = new Scanner(f1);
        Pattern p1 = Pattern.compile("[0-9]+");
        Pattern p2 = Pattern.compile("[a-zA-Z]+\\.[a-zA-Z]+");
        Pattern p3 = Pattern.compile("([0-9][0-9]:[0-9][0-9])");
        Pattern p4 = Pattern.compile("\\s[0-9 a-zA-Z-: ,-.ñÑáéíóúÁÉÍÓÚ()]+");
        //Leo el numero del teatro
        String a = sc.findInLine(p1); 
        number = (Integer) Integer.parseInt(a);
        //Leo las sesiones
        for(int i=1;i<=6;i++){
            sc.nextLine();
        }
        String linea = sc.findInLine(p4);
        Matcher matcher = p3.matcher(linea);
        while(matcher.find()){
            String hora = matcher.group();
            Session sesion = new Session(hora);
            listaSesiones.add(sesion);
        }                
        //Leo el precio por entrada
        for(int i=1;i<=4;i++){
            sc.nextLine();
        }
        String c = sc.findInLine(p1);
        price = (Integer) Integer.parseInt(c);
        sc.close();    
        //Leo el poster y creo la pelicula del teatro
        sc = new Scanner(f1);
        for(int i=1;i<=8;i++){
            sc.nextLine();
        }
        String d = sc.findInLine(p2);
        File f3 = (File) new File("./src/recursos/"+d);
        film = new Film(f3);
        sc.close();
        //Leo el titulo de la pelicula del teatro
        sc = new Scanner(f1);
        for(int i=0;i<2;i++){
            sc.nextLine();
        }
        String e =  sc.findInLine(p4);
        film.setName(e);
        //Leo la descripcion
        for(int i=0;i<2;i++){
            sc.nextLine();
        }
        String f = sc.findInLine(p4);
        film.setDescription(f);
        //Leo la duracion
        for(int i=0;i<8;i++){
            sc.nextLine();
        }
        String g = sc.findInLine(p1);
        int h = (Integer) Integer.parseInt(g);
        film.setDuration(h);

    }   
    
    

    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }

    public Set<Seat> getSeatsSet() {
        return seatsSet;
    }

    public Film getFilm() {
        return this.film;
    }
    
    public int getMaxCol() throws FileNotFoundException{ //Devuelve el numero maximo de columnas de una sesion
        int numCol = 0;
        int maxNumCol =0;
        Scanner sc = new Scanner(f2);
        while(sc.hasNextLine()){
        String linea = sc.nextLine();
        char[] arrayC = linea.toCharArray();
        numCol = arrayC.length;
        if(numCol>=maxNumCol){
            maxNumCol=numCol;
        }

        }
        return maxNumCol;
    }
    
    public int getMaxRow() throws FileNotFoundException{ //Devuelve el numero maximo de filas de una sesion
        int rows=1;
        Scanner sc = new Scanner(f2);
        while (sc.hasNextLine()) {
            sc.nextLine();
            if(sc.hasNextLine()){
            rows++;
            }
        }
        return rows;   
    }
    
    public  void loadSeats() throws FileNotFoundException, IOException { //Crea todos los sitios del teatro
        Scanner sc = new Scanner(f2);
            for (int i = 1; i <=getMaxRow(); i++) {
                String line = sc.nextLine();
                char[] toCharArray = line.toCharArray();
                for (int j = 0; j < toCharArray.length; j++) {
                    if (toCharArray[j] == '*') seatsSet.add(new Seat(i, j+1));
                }
            }
    }
           
        

         


    public List<Session> getListaSesiones() {
        return listaSesiones;
    }

    
        
    

}
