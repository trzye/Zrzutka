
public class Main {

	public static void main(String[] args) {
		System.out.println("Zadanie 1");
		System.out.println("X: " + A.X);
		System.out.println("Y: " + A.Y);
		//A.X = 10; nie mozna
		A.Y = 10; //mozna
		
		System.out.println("\nZadanie 2");
		B b = new B(); //Zostanie wypisany adres, czyli juz w momencie konstrukcji obiekt istnieje
		
		System.out.println("\nZadanie 3");
		C c1 = new C(1,1,"c1");
		C c2 = new C(2,2,"c2");
		C c3 = new C(3,3,"c3");
		c1.display(); 
		c2.display();
		c3.display(); // Wszedzie STRING bedzie c3, poniewaz to pole statyczne
	
		System.out.println("\nZadanie 4");
		String napis = "Ala ma kota";
		System.out.println(napis.charAt(2));
		System.out.println(napis.charAt(4));
		System.out.println(napis.indexOf("k"));
		String[] wyrazy = napis.split(" ");
		for(int j=0; j<wyrazy.length; j++){
			System.out.println(wyrazy[j]);
		}
		
		System.out.println("\nZadanie 5 i 6 - Walec");
		Walec walec = new Walec(10,20);
		walec.show();
		new KulaNa(walec).show();
		new KulaW(walec).show();

		System.out.println("\nZadanie 5 i 6 - Sześcian");
		Kwadrat kwadrat = new Kwadrat(10);
		kwadrat.show();
		new KulaNa(kwadrat).show();
		new KulaW(kwadrat).show();

	}

}

class A {
	final static int X = 1;
	static int Y;
}

class B {
	public B(){
		System.out.println(this);
	}
}

class C {
	static String STRING;
	int int1;
	int int2;
	
	public C(int int1, int int2, String nowyString){
		this.int1 = int1;
		this.int2 = int2;
		STRING = nowyString; 
	}
	
	public void display(){
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return "C [int1=" + int1 + ", int2=" + int2 + ", STRING=" + STRING + "]";
	}
	
}

class KulaW {

	private double promien;

	public KulaW(Walec walec){
		double polowaWysokosciWalca = walec.getWysokosc() / 2;
		if(walec.getPromien() < polowaWysokosciWalca)
			promien = walec.getPromien();
		else
			promien = polowaWysokosciWalca;
	}
	public KulaW(Kwadrat kwadrat){
		promien = kwadrat.getBok() / 2;
	}

	public void show(){
		System.out.println("promien kuli [w]: " + promien);
	}
}

class KulaNa {

	private double promien;

	public KulaNa(Walec walec){
		double srednicaPodstawyWalca = walec.getPromien() * 2;
		if(srednicaPodstawyWalca > walec.getPromien())
			promien = srednicaPodstawyWalca;
		else
			promien = walec.getPromien();
	}

	public KulaNa(Kwadrat kwadrat){
		promien = kwadrat.getBok();
	}

	public void show(){
		System.out.println("promien kuli [na]: " + promien);
	}
}