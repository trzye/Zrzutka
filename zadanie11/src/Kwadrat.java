//Zadanie 1 i 3
public class Kwadrat {
	private double bok;

    public Kwadrat(double bok){
        this.bok = bok;
    }

    public void show(){
        double poleKwadratu = bok * bok;
        System.out.println("bok: " + bok);
        System.out.println("pole: " + poleKwadratu);
        System.out.println("objetosc szescianu: " + poleKwadratu * bok);
    }

    //Zadanie 3
    public Walec przygotujWalec(){
        double wysokoscWalca = bok;
        double promienWalca = bok / 2;
        return new Walec(promienWalca, wysokoscWalca);
    }

    public double getBok() {
        return bok;
    }
}
