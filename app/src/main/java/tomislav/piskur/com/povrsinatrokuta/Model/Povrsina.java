package tomislav.piskur.com.povrsinatrokuta.Model;

/**
 * Created by srs on 15.05.2018.
 */

public class Povrsina {

    private double a, b, c;

    public Povrsina(double a, double b, double c){

        this.a = a;
        this.b = b;
        this.c = c;

    }

    public double povrsina(){

        double s = (a + b + c)/2.0d;
        double x = ((s) * (s-a) * (s-b)* (s-c));
        return Math.sqrt(x);

    }

}
