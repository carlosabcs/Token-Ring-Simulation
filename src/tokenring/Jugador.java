package tokenring;

import java.util.Scanner;

    public class Jugador implements Runnable{
        private String nombre;
        private int turno;
        volatile private Boolean testimonio;
        private Jugador siguiente;
        private Jugador anterior;
        private Balon balon;
        volatile private Escaner sc;

        public Escaner getSc() {
            return sc;
        }

        public void setSc(Escaner sc) {
            this.sc = sc;
        }

        public Jugador(String nombre, int turno, Balon balon) {
            this.nombre = nombre;
            this.turno = turno;
            this.balon = balon;
            testimonio = false;
        }
        public Jugador getAnterior() {
            return anterior;
        }

        public void setAnterior(Jugador anterior) {
            this.anterior = anterior;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getTurno() {
            return turno;
        }

        public void setTurno(int turno) {
            this.turno = turno;
        }

        public Boolean getTestimonio() {
            return testimonio;
        }

        public void setTestimonio(Boolean testimonio) {
            this.testimonio = testimonio;
        }

        public Jugador getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Jugador siguiente) {
            this.siguiente = siguiente;
        }

        public void darPataditas(){
            this.balon.aumentarPataditas();        
        }

        public void recibirTestimonio(){
            System.out.println(this.getNombre() + ", deseas recibir el testimonio (1/0)");
            int rpta = sc.getSc().nextInt();
            if(rpta == 1)   this.testimonio = true;
            else
                this.pasarTestimonio();
        }

        public void  pasarTestimonio(){
            this.testimonio = false;
            System.out.println("Yo, "+this.getNombre()+ ", estoy pasando el testimonio a "+ this.getSiguiente().getNombre());
            this.siguiente.recibirTestimonio();
        }

        public void recibeEscaner(){
            if(this.sc.tomarEscaner()){
                System.out.println("Escaner tomado por " + this.getNombre());
            }else{
                System.out.println("Yo, "+this.getNombre()+", no pude tomar el escáner.");
            }
        }

        public void entregaEscaner(){
            this.sc.dejarEscaner();
            if(this.siguiente.getTestimonio()){
                this.siguiente.recibeEscaner();
            }else{
                this.siguiente.entregaEscaner();
            }

        }

        @Override
        public void run(){
            try{
                while(true){
                    if(this.testimonio && this.balon.tomarBalon()){
                        this.darPataditas();
                        this.balon.dejarBalon();
                        this.pasarTestimonio();
                        System.out.print(this.getNombre() + ", ¿seguiras jugando?(1/0)");
                        int response = sc.getSc().nextInt();
                        this.sc.dejarEscaner();
                        if(response == 0){
                                this.anterior.setSiguiente(this.siguiente);
                                this.siguiente.setAnterior(this.anterior);
                                break;
                        }
                    }
                }   
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
