/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaDispenser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;

/**
 *
 * @author Andreas
 */
public class MovieTicketSale extends Operation {

    MultiplexState state;
    PerformPayment pago = new PerformPayment(super.getDispenser(), super.getMultiplex());
    Theater t;
    Session s;
    int cont = 0;
    List<String> ticket = new ArrayList<>();
    List<List<String>> listaTickets = new ArrayList<>();

    public MovieTicketSale(CinemaTicketDispenser c, Multiplex m) throws FileNotFoundException, IOException, ClassNotFoundException {
        super(c, m);

        try {
            deserializeMultiplexState();
        } catch (FileNotFoundException ex) {
            state = new MultiplexState(super.getDispenser(), super.getMultiplex());
        }

    }

    @Override
    public void doOperation() { //El metodo que incluye la seleccion del teatro, sesion y sitios ademas del pago 
        try {
            super.getDispenser().setMenuMode();
            t = selectTheater();
            s = selectSession(t);
            try {
                selectSeats(s);
            } catch (IOException ex) {
                Logger.getLogger(MovieTicketSale.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MovieTicketSale.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                try {
                    performPayment();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            } catch (CommunicationException ex) {
                System.err.println(ex.getMessage());
            }
            
            try {
                serializeMultiplexState();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            MainMenu mm = new MainMenu(super.getDispenser(),super.getMultiplex());
            mm.doOperation();
            
        } catch (IOException ex) {
            Logger.getLogger(MovieTicketSale.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MovieTicketSale.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getTitle() {

        return java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("COMPRAR ENTRADAS");

    }

    public void performPayment() throws FileNotFoundException, CommunicationException, IOException { //Este metodo llama a la clase PerformPayment para realizar el pago de las entradas

        if (cont == 0) {

            s.getOccupiedSeatSet().clear();
            doOperation();
        } else {

            super.getDispenser().setMessageMode();
            super.getDispenser().setTitle(java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("INSERTE LA TARJETA DE CREDITO"));
            super.getDispenser().setDescription(cont + java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString(" ENTRADAS PARA {0} -> {1}€"), new Object[] {t.getFilm().getName(), computePrice()}));
            pago.doOperation(cont, s);
            serializeMultiplexState();
            for (int k = 0; k < listaTickets.size(); k++) {
                super.getDispenser().print(listaTickets.get(k));
            }
            
        }

    }

    public void presentSeats() throws FileNotFoundException { //este metodo muestra por pantalla los sitios de cada teatro y cada sesion
        for (int i = 1; i <= t.getMaxRow(); i++) {
            for (int j = 1; j <= t.getMaxCol(); j++) {
                super.getDispenser().markSeat(i, j, 0);
            }
        }

        for (Seat s : t.getSeatsSet()) {
            super.getDispenser().markSeat(s.getRow(), s.getCol(), 2);
        }
        for (Seat ss : s.getOccupiedSeatSet()) {
            super.getDispenser().markSeat(ss.getRow(), ss.getCol(), 1);
        }
    }

    public int computePrice() { //calcula el precio total a pagar
        return (cont * t.getPrice());
    }

    public void serializeMultiplexState() throws FileNotFoundException, IOException { //serializa el Multiplex
        FileOutputStream outputStream = new FileOutputStream("./src/recursos/estadopelis.bin"); //NOI18N
        ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);
        objectStream.writeObject(state);
    }

    public void deserializeMultiplexState() throws FileNotFoundException, IOException, ClassNotFoundException { //deserializa el Multiplex
        FileInputStream outputStream = new FileInputStream("./src/recursos/estadopelis.bin"); //NOI18N
        ObjectInputStream objectStream = new ObjectInputStream(outputStream);
        state = (MultiplexState) objectStream.readObject();
    }

    public Theater selectTheater() { //Seleccion del teatro

        for (int i = 0; i < 4; i++) {
            super.getDispenser().setOption(i, state.getTheaterList().get(i).getFilm().getName());
        }
        super.getDispenser().setOption(5, java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("MENU PRINCIPAL"));

        char x = super.getDispenser().waitEvent(30);
        if (x == 'A') {
            t = (Theater) state.getTheater(0);
            return t;

        } else if (x == 'B') {
            t = (Theater) state.getTheater(1);
            return t;

        } else if (x == 'C') {
            t = (Theater) state.getTheater(2);
            return t;

        } else if (x == 'D') {
            t = (Theater) state.getTheater(3);
            return t;
        } else if (x == 'F') {
            super.getMultiplex().getMm().doOperation();

        } else if (x == '1') {
            dispenser.retainCreditCard(false);
            dispenser.expelCreditCard(10);
            selectTheater();
        }

        return t;
    }

    public Session selectSession(Theater t) { //Seleccion de la sesion

        for (int i = 0; i < t.getListaSesiones().size(); i++) {
            super.getDispenser().setOption(i, t.getListaSesiones().get(i).getHour());
        }
        for (int i = t.getListaSesiones().size(); i < 6; i++) {
            super.getDispenser().setOption(i, null);
        }
        super.getDispenser().setOption(5, java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("MENU PRINCIPAL"));
        super.getDispenser().setImage(String.valueOf(t.getFilm().getPoster()));
        super.getDispenser().setDescription(t.getFilm().getDescription());
        char n = super.getDispenser().waitEvent(30);

        if (n == 'A') {
            s = (Session) t.getListaSesiones().get(0);
            return s;

        } else if (n == 'B') {
            s = (Session) t.getListaSesiones().get(1);
            return s;

        } else if (n == 'C') {
            s = (Session) t.getListaSesiones().get(2);
            return s;

        } else if (n == 'D') {
            s = (Session) t.getListaSesiones().get(3);
            return s;
        } else if (n == 'F') {
            super.getMultiplex().getMm().doOperation();

        } else if (n == '1') {
            dispenser.retainCreditCard(false);
            dispenser.expelCreditCard(10);
            selectSession(t);
        }
        return s;
    }

    public int selectSeats(Session s) throws FileNotFoundException, IOException, ClassNotFoundException { //Seleccion de los asientos
        char y = 0;
        int filaAnterior = 0;
        int colAnterior = 0;
        super.getDispenser().setTheaterMode(t.getMaxRow(), t.getMaxCol());
        super.getDispenser().setOption(0, pago.getTitle());
        super.getDispenser().setOption(1, java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("MENU PRINCIPAL"));
        dispenser.setTitle(java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("ELIJA ASIENTOS "));
        presentSeats();
        while (cont < 4) {

            y = super.getDispenser().waitEvent(30);
            if (y == 'A') {
                
                return cont;
            } else if (y == 'B') {

                MainMenu mm = new MainMenu(super.getDispenser(),super.getMultiplex());
                mm.doOperation();

            } else if (y != 0 && y != '1') {

                byte col = (byte) (y & 0xFF);
                byte row = (byte) ((y & 0xFF00) >> 8);
                dispenser.setTitle((java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("FILA: ")) + row + 
                        java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("ASIENTO: ") + col); //NOI18N  //NOI18N
                dispenser.markSeat(row, col, 1);

                if (filaAnterior == row && colAnterior == col) {
                    dispenser.setTitle(java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("ASIENTO OCUPADO"));
                } else {
                    s.getOccupiedSeatSet().add(new Seat(row, col));
                    ticket = new ArrayList();
                    ticket.add(java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("ENTRADA PARA {0}"), new Object[] {t.getFilm().getName()}));
                    ticket.add(" ==================="); //NOI18N
                    ticket.add(java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("TEATRO {0}"), new Object[] {String.valueOf(t.getNumber())}));
                    ticket.add(s.getHour());
                    ticket.add(java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("FILA {0}"), new Object[] {row}));
                    ticket.add(java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("ASIENTO {0}"), new Object[] {col}));
                    ticket.add(java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("idiomas/"+super.getMultiplex().getLang()).getString("PRECIO {0}"), new Object[] {t.getPrice()}));
                    listaTickets.add(ticket);
                    cont++;
                }
                filaAnterior = row;
                colAnterior = col;

            } else if (y == '1') {
                dispenser.retainCreditCard(false);
                dispenser.retainCreditCard(false);
                selectSeats(s);
            }
        }
        while (cont < 5) {
            char m = super.getDispenser().waitEvent(30);
            if (m == 'A') {
                return cont;

            } else if (m == 'B') {
                MainMenu mm = new MainMenu(super.getDispenser(),super.getMultiplex());
                mm.doOperation();
            } else if (m == '1') {
                dispenser.retainCreditCard(false);
                dispenser.expelCreditCard(10);
                selectSeats(s);
            }
        }

        return cont;
    }

}
