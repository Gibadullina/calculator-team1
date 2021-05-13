package com.skylabs.baselogic;

public class Solver {
	static final double mrot = 12130d;
	
	public static double Solve(double perc, double c, double nt, double pt, double lt, double e, double k, boolean useMrot) {
		double m, result;
		result = 0;
		
		if (nt > 0 && lt < c) { //если параметры премии заданы и было произведено деталей свыше нормы
			m = e*c + (c-lt)/nt*pt;
		}
		else { // иначе считаем без учета премии
			m = e*c;
		}
		if (useMrot && m < mrot) {
			result = mrot*k*(1-perc/100);
		} else {
			result = m*k*(1-perc/100);
		}
		return result;
	}
}
