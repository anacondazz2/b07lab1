import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Polynomial {
	double[] coef;
	int[] exp;

	public Polynomial() {
		coef = new double[1];
		coef[0] = 0.0;
		exp = new int[1];
		exp[0] = 0;
	}

	public Polynomial(double[] coef, int[] exp) {
		this.coef = coef;
		this.exp = exp;
	}

	public Polynomial(File file) throws FileNotFoundException {
		Scanner scanner = new Scanner(file);
		String line = scanner.nextLine();
		scanner.close();

		String[] terms = line.split("(?=[+-])");
		coef = new double[terms.length];
		exp = new int[terms.length];
		for (int i = 0; i < terms.length; i++) {
			String term = terms[i];

			double termCoef;
			int termExp;

			if (term.contains("x")) {
				// Term contains both coefficient and exponent.
				String[] parts = term.split("x");
				termCoef = Double.parseDouble(parts[0]);
				// Check x^1 case. 
				if (parts.length > 1) {
					termExp = Integer.parseInt(parts[1]);
				}
				else {
					termExp = 1;
				}
			} else {
				// Term contains only coefficient.
				termCoef = Double.parseDouble(term);
				termExp = 0;
			}
			coef[i] = termCoef;
			exp[i] = termExp;
		}
	}

	Polynomial add(Polynomial p) {
		int largest = exp[0];
		for (int i = 1; i < exp.length; i++) {
			if (exp[i] > largest) {
				largest = exp[i];
			}
		}
		largest++;
		// Convert coef to contain zeroes.
		double[] newCoef = new double[largest];
		for (int i = 0; i < exp.length; i++) {
			int e = exp[i];
			newCoef[e] = coef[i];
		}
		// Convert p.coef to contain zeroes.
		largest = p.exp[0];
		for (int i = 1; i < p.exp.length; i++) {
			if (p.exp[i] > largest) {
				largest = p.exp[i];
			}
		}
		largest++;
		double[] pnewCoef = new double[largest];
		for (int i = 0; i < p.exp.length; i++) {
			int e = p.exp[i];
			pnewCoef[e] = p.coef[i];
		}
		// Add the two new Coefs.
		double[] larger = Math.max(newCoef.length, pnewCoef.length) == newCoef.length ? newCoef : pnewCoef;
		double[] ret = new double[larger.length];
		for (int i = 0; i < Math.max(newCoef.length, pnewCoef.length); i++) {
			if (i >= Math.min(newCoef.length, pnewCoef.length)) {
				ret[i] = larger[i];
			}
			else {
				ret[i] = newCoef[i] + pnewCoef[i];
			}
		}
		// Generate the new exp array.
		int[] b = new int[ret.length];
		int j = 0;
		for (int i = 0; i < ret.length; i++) {
			if (ret[i] != 0.0) {
				b[j] = i;
				j++;
			}
		}
		int[] retExp = new int[j];
		for (int i = 0; i < j; i++) {
			retExp[i] = b[i];
		}
		// Remove zeroes from ret.
		j = 0;
		double[] c = new double[ret.length];
		for (int i = 0; i < ret.length; i++) {
			if (ret[i] != 0.0) {
				c[j] = ret[i];
				j++;
			}
		}
		double[] retCoef = new double[j];
		for (int i = 0; i < j; i++) {
			retCoef[i] = c[i];
		}

		return new Polynomial(retCoef, retExp);
	}

	/**
	 * Multiplies this polynomial by the given term and returns the result.
	 *
	 * @param p        The polynomial to be multiplied.
	 * @param termCoef The coefficient of the term to be multiplied.
	 * @param termExp  The exponent of the term to be multiplied.
	 * @return The product of this polynomial and the given term.
	 */
	Polynomial multiplyByTerm(Polynomial p, double termCoef, int termExp) {
		double[] newCoef = new double[p.coef.length];
		int[] newExp = new int[p.exp.length];
		for (int i = 0; i < p.coef.length; i++) {
			newCoef[i] = p.coef[i] * termCoef;
			newExp[i] = p.exp[i] + termExp;
		}
		return new Polynomial(newCoef, newExp);
	}

	Polynomial multiply(Polynomial p) {
		Polynomial result = new Polynomial();
		for (int i = 0; i < coef.length; i++) {
			double termCoef = coef[i];
			int termExp = exp[i];
			Polynomial q = multiplyByTerm(p, termCoef, termExp);
			result = result.add(q);
		}
		return result;
	}

	double evaluate(double x) {
		double ret = 0.0;
		for (int i = 0; i < coef.length; i++) {
			ret += coef[i] * Math.pow(x, exp[i]);
		}
		return ret;
	}

	boolean hasRoot(double n) {
		return evaluate(n) == 0.0;
	}

	void saveToFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		PrintWriter writer = new PrintWriter(file);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < coef.length; i++) {
			if (coef[i] != 0.0) {
				if (coef[i] > 0 && i > 0) { // After first coef.
					builder.append("+");
				}
				builder.append(coef[i]);
				if (exp[i] == 1) {
					builder.append("x");
				}
				else if (exp[i] != 0) {
					builder.append("x").append(exp[i]);
				}
			}
		}
		writer.println(builder.toString());
		writer.close();
	}
}