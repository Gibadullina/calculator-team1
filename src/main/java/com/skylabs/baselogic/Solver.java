package com.skylabs.baselogic;

public class Solver {
	static double[] k = {1.15, 1.1, 1.12, 1.3, 1.15 }; //районные коэф
	static double[] e = {1000, 300, 500, 100, 200}; //стоимость детали
	
	static final double mrot = 12130d;
	
	private void LoadStates(String path) {
		
	}
	
	public static double Solve(double perc, double c, double nt, double pt, double lt, int indexProduct, int indexCoeff, boolean useMrot) {
		double m, result;
		result = 0;
		
		if (nt > 0 && lt < c) { //если параметры премии заданы и было произведено деталей свыше нормы
			m = e[indexProduct]*c + (c-lt)/nt*pt;
		}
		else { // иначе считаем без учета премии
			m = e[indexProduct]*c;
		}
		if (useMrot && m < mrot) {
			result = mrot*k[indexCoeff]*(1-perc/100);
		} else {
			result = m*k[indexCoeff]*(1-perc/100);
		}
		return result;
	}
}
