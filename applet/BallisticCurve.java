import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import javax.script.*;

import javax.swing.JPanel;
import java.awt.image.*;
import java.util.*;
import java.util.Timer;
import java.text.*;

public class BallisticCurve extends Applet implements ActionListener 
{
	Button okButton;
	TextField grawitacja;
	TextField opor;
	TextField Xo;
	TextField Yo;
	TextField Vx;
	TextField Vy;
	//TextField masa;
	//TextField predkosc;
	//TextField kat;

	
	double grawitacjaD = 9.81; 
	double oporD = 1.0;
	double XoD = 0.0;
	double YoD = 0.0;
	double VxD = 70.0;
	double VyD = 70.0;
	double czasD;
	//double masaD = 1.0;
	//double predkoscD = 70.0;
	//double katD = 70.0; 
	
	double skalaX = 1.0;
	double skalaY = 1.0;
	double kontrola = 100.0;
	int maxY = 500;
	
	double wysokosc, czas, odleglosc;
	
	//ustawiamy wszystkie pola na stronie 
	public void init()
	{
		setLayout(null);
		okButton = new Button("CALCULATE");
		okButton.addActionListener(this);
		grawitacja = new TextField("9.81",100);
		grawitacja.addActionListener(this);
		opor = new TextField("1.0",100);
		opor.addActionListener(this);
		Xo = new TextField("0.0", 100);
		Xo.addActionListener(this);
		Yo = new TextField("0.0", 100);
		Yo.addActionListener(this);
		Vx = new TextField("70.0", 100);
		Vx.addActionListener(this);
		Vy = new TextField("70.0", 100);
		Vy.addActionListener(this);
		//masa = new TextField("1.0",100);
		//masa.addActionListener(this);
		//predkosc = new TextField("70.0",100);
		//predkosc.addActionListener(this);
		//kat = new TextField("70",100);
		//kat.addActionListener(this);
		
		okButton.setBounds(20,20,100,30);
		grawitacja.setBounds(150,20,50,30);
		opor.setBounds(210,20,50,30);
		Xo.setBounds(270,20,50,30);
		Yo.setBounds(330,20,50,30);
		Vx.setBounds(390,20,50,30);
		Vy.setBounds(450,20,50,30);
		//masa.setBounds(270,20,50,30);
		//predkosc.setBounds(330,20,50,30);
		//kat.setBounds(390,20,50,30);
		
		add(okButton);
		add(grawitacja);
		add(opor);
		add(Xo);						
		add(Yo);
		add(Vx);
		add(Vy);
		//add(masa);
		//add(predkosc);
		//add(kat);	
	}
	
	//rysujemy wykres oraz oznaczenia
	 public void paint(Graphics g)
	{
		//oznaczenie formularzy
		g.drawString("  Gravity       Resistance        x0               y0               Vx0              Vy0", 150, 10);
		
		// rysowanie osi X
		g.drawLine(20, 530, 550, 530);
		g.drawLine(550, 530, 540, 520);
		g.drawLine(550, 530, 540, 540);

		// rysowanie osi Y
		g.drawLine(20, 530, 20, 80);
		g.drawLine(20, 80, 30, 90);
		g.drawLine(20, 80, 10, 90);
		
		//ustawianie danych tekstowych
		setScale();
		g.drawString("Result: ", 20, 600);
		g.drawString("Throw time: " + Double.toString(kontrola) + " sek", 20, 615);
		g.drawString("Throw max high: " + Double.toString(wysokosc) + " m", 20, 630);
		g.drawString("Throw lenght: " + Double.toString(getX(kontrola) * skalaX * skalaY) + " m", 20, 655);
		g.drawString(Double.toString(skalaX*530) + " m", 530, 550);
		g.drawString(Double.toString(skalaY*530) + " m", 30, 80);
		
		
		//rysowanie paraboli
		rysuj(g);
	}
		
	//pobieramy dane z formularza po nacisnieciu przycisku
	//wykonujemy metode ustawiajaca skale oraz rysujemy parabole
	public void actionPerformed(ActionEvent ae)
    {
        Object src = ae.getSource();
        if(src == okButton)
        {
			grawitacjaD = Double.parseDouble(grawitacja.getText());
            oporD = Double.parseDouble(opor.getText());
			XoD = Double.parseDouble(Xo.getText());
            YoD = Double.parseDouble(Yo.getText());
			VxD = Double.parseDouble(Vx.getText());
            VyD = Double.parseDouble(Vy.getText());
			YoD = (-1)*YoD;
			//masaD = Double.parseDouble(masa.getText());
			//predkoscD = Double.parseDouble(predkosc.getText());
			//katD = Double.parseDouble(kat.getText());	
			setScale();	
			repaint();			
            return;
        }
    }
	
	
	//public double getX(double t)
	//{
	//	double radiany = Math.toRadians(katD);
	//	double wynik = predkoscD*t*Math.cos(radiany);
	//	wynik = wynik/skalaX;
//		wynik = wynik/skalaY;
//		return wynikX;
//	}
	
	//pobieramy wartosc Y dla czasu t w odpowiedniej skali
//	public double getY(double t)
//	{
//		double radiany = Math.toRadians(katD);
//		double wynik = predkoscD*t*Math.sin(radiany);
//		wynik = wynik - grawitacjaD*t*t/2;
//		wynik = wynik/skalaX;
//		wynik = wynik/skalaY;
//		return wynikY;
//	}

	//pobieramy wartosc X dla czasu t w odpowiedniej skali
	public double getX(double t)
	{
		double wynik;
		wynik = XoD + VxD/oporD * (1.0 - Math.pow(2.7182, (-1)*oporD*t));
		wynik = wynik/skalaX;
		wynik = wynik/skalaY;
		return wynik;
	}
	
	//pobieramy wartosc Y dla czasu t w odpowiedniej skali
	public double getY(double t)
	{
		double wynik;
		wynik = YoD + VyD/oporD + grawitacjaD/(oporD*oporD) - grawitacjaD*t/oporD - ((oporD * VyD + grawitacjaD)/(oporD*oporD))*Math.pow(2.7182, (-1)*oporD*t);
		wynik = wynik/skalaX;
		wynik = wynik/skalaY;
		return wynik;
	}
	
	//ustawiamy skale dla danego eksperymentu, aby parabaola miescila sie zawsze na wykresie
	public void setScale()
	{
		//poczatkowe wartosci i zmienne pomocnicze
		kontrola = 100.0;
		czasD = 0.0;
		skalaX = 1.0;
		skalaY = 1.0;
		maxY = 530;	
		int x, y, y2;
		int pom = (int)getY(czasD);
		
		//petla sprawdzajaca kontrole czasu w ktorej nalezy przestac rysowac wykres
		for(int i=0; i<100000; i++)
		{
			x = (int)getX(czasD);
			y = (int)getY(czasD);
			y2 = pom - y;
			if(pom+530+y2 < maxY)
			{
				maxY = pom+530+y2;
			}
			if(pom+530+y2 > 530) 
			{
				kontrola = czasD;
				break;	
			}
			czasD = czasD + 0.01;
		}
		
		//ustawiamy skaleX
		skalaX = getX(kontrola);
		skalaX = skalaX/(440.0);
		if(skalaX < 1.0) skalaX = 1.0;
		
		//ustawiamy skaleY
		if(maxY < 0)
		{
			maxY = maxY * (-1);
			wysokosc = maxY + 440;
			//maxY = maxY + 500;
		}
		else
		{
			wysokosc = 530 - maxY;
		}
		skalaY = wysokosc/(440.0);
		if(skalaY < 1.0) skalaY = 1.0;
		
	}
	
	//rysowanie paraboli
	public void rysuj(Graphics grafika)
	{
		grafika.setColor(Color.red);
		czasD = 0.0;
		int x, y, y2;
		int pom = (int)getY(czasD);
		for(int i=0; i<10000; i++)
		{
			x = (int)getX(czasD);
			y = (int)getY(czasD);
			y2 = pom - y;
			if(pom+530+y2 > 530) 
			{
				break;	
			}
			czasD = czasD + 0.01;
			grafika.drawLine(x+20, pom+530+y2, x+20, pom+530+y2);
		}
	}
	
}

