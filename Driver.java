import java.io.File;
import java.io.FileNotFoundException;

public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double[] c1 = { 6, 5 };
        int[] e1 = { 0, 3 };
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = { -2, -9 };
        int[] e2 = { 1, 4 };
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        System.out.println("s(1) = " + s.evaluate(1));
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        double[] c11 = { 5, 2 };
        int[] e11 = { 1, 2 };
        Polynomial p11 = new Polynomial(c11, e11);

        // --- SORTED TEST CASE ---
        double[] c22 = { 8, -11, 1 };
        int[] e22 = { 0, 1, 3 };
        // --- UNSORTED TEST CASE ---
        // double[] c22 = { 8, 1, -11 };
        // int[] e22 = { 0, 3, 1 };
        Polynomial p22 = new Polynomial(c22, e22);

        // Test add p11, p22.
        Polynomial ppp = p11.add(p22);
        // Correct polynomial after adding: 8 - 6x + 2x^2 + x^3

        // Test multiply p11, p22.
        Polynomial ss = p11.multiply(p22);
        // Correct polynomial after multiplying: 40x - 39x^2 - 22x^3 + 5x^4 + 2x^5
        // Test Polynomial(File file).
        File file = new File("polynomial.txt");
        Polynomial pp = new Polynomial();
        try {
            pp = new Polynomial(file);
        } catch (FileNotFoundException e) {
            // Handle the exception here
            e.printStackTrace();
        }
        try {
            pp.saveToFile("polynomial2.txt");    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}