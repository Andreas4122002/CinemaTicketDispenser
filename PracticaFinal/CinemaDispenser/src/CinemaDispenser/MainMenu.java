/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaDispenser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author Andreas
 */
public class MainMenu extends Operation {

    
    LanguageSelection language ;
    MovieTicketSale compra;
    public List<Operation> operationList = new ArrayList<>();
    

    public MainMenu(CinemaTicketDispenser dispenser, Multiplex m) throws FileNotFoundException, IOException, ClassNotFoundException {
        super(dispenser, m);
        language = new LanguageSelection(super.getDispenser(), super.getMultiplex());
        compra = new MovieTicketSale(super.getDispenser(), super.getMultiplex());
        operationList.add(language);
        operationList.add(compra);

    }

    @Override
    public void doOperation() {//presenta el menu principal
        presentMenu();
        while (true) {
            char d = super.getDispenser().waitEvent(30);
            if (d == 'A') {
                language.doOperation();
            } else if (d == 'B') {
                compra.doOperation();
            } else if (d == '1') {
                dispenser.retainCreditCard(false);
                dispenser.expelCreditCard(10);
            }
        }
    }

    @Override
    public String getTitle() {

        return java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("ELEGIR IDIOMA");

    }

    public void presentMenu() {
        super.getDispenser().setMenuMode();
        super.getDispenser().setDescription("");
        super.getDispenser().setImage("");
        super.getDispenser().setTitle("");
        for (int i = 0; i < operationList.size(); i++) {
            super.getDispenser().setOption(i, operationList.get(i).getTitle());
        }
        for (int i = operationList.size(); i < 6; i++) {
            super.getDispenser().setOption(i, null);
        }
        

    }
    
    public MovieTicketSale getCompra() {
        return compra;
    }
}
