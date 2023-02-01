/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaDispenser;

import java.io.FileNotFoundException;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

/**
 *
 * @author Andreas
 */
public class PerformPayment extends Operation {

    private UrjcBankServer banco = new UrjcBankServer();

    public PerformPayment(CinemaTicketDispenser c, Multiplex m) {
        super(c, m);
    }

    public void doOperation(int cont, Session s) throws FileNotFoundException, CommunicationException { //Realizacion del pago
        super.getDispenser().setOption(0, java.util.ResourceBundle.getBundle("idiomas/Español").getString("ACEPTAR"));
        super.getDispenser().setOption(1, java.util.ResourceBundle.getBundle("idiomas/Español").getString("MENU PRINCIPAL"));
            char x = super.getDispenser().waitEvent(30);
            if (x == '1') {
                super.getDispenser().retainCreditCard(false);
                    char o = super.getDispenser().waitEvent(30);
                    if (o == 'A') {
                        if(banco.comunicationAvaiable()==true){
                            banco.doOperation(super.getDispenser().getCardNumber(),cont);
                            super.getDispenser().expelCreditCard(10);
                            
                            
                        }
                    } else if (o == 'B') {
                        s.getOccupiedSeatSet().clear();
                        super.getMultiplex().start();
                    }

            } else if (x == 'A') {
                doOperation(cont, s);
            } else if (x == 'B') {

                s.getOccupiedSeatSet().clear();
                super.getMultiplex().start();
            }

        
    }

    @Override
    public String getTitle() {
        return java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("PAGAR ENTRADAS");
    }

    @Override
    public void doOperation() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody //NOI18N
    }

}
