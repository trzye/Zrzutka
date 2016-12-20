//Zadanie 2
//Klasa
public class Walec {
	//Pole klasy Walec
    private double promien;

    //Pole klasy Walec
    private double wysokosc;

    //Konstruktor klasy Walec
    public Walec(double promien, double wysokosc) {
        this.promien = promien;
        this.wysokosc = wysokosc;
    }

    //Metoda klasy walec
    public void show(){
        double pole = Math.PI * Math.pow(promien, 2);
        System.out.println("promien: " + promien);
        System.out.println("wysokosc: " + wysokosc);
        System.out.println("pole podstawy: " + pole);
        System.out.println("objetosc walca: " + pole * wysokosc);
    }

    public double getPromien() {
        return promien;
    }

    public double getWysokosc() {
        return wysokosc;
    }
}
