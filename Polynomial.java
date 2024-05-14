public class Polynomial {
	double[] coef;

	public Polynomial() {
		coef = new double[1];
		coef[0] = 0.0;
	}

	public Polynomial(double[] coef) {
		this.coef = coef;
	}

	Polynomial add(Polynomial p) {
		double[] larger = Math.max(coef.length, p.coef.length) == coef.length ? coef : p.coef;
		double[] ret = new double[larger.length];
		for (int i = 0; i < Math.max(coef.length, p.coef.length); i++) {
			if (i >= Math.min(coef.length, p.coef.length)) {
				ret[i] = larger[i];
			}
			else {
				ret[i] = coef[i] + p.coef[i];
			}
		}
		return new Polynomial(ret);
	}

	double evaluate(double x) {
		double ret = 0.0;
		for (int i = 0; i < coef.length; i++) {
			ret += coef[i] * Math.pow(x, i);
		}
		return ret;
	}

	boolean hasRoot(double n) {
		return evaluate(n) == 0.0;
	}
}