package com.skylabs.baselogic;

public class Solver extends BaseSolver{

//Расчёт по формуле
public static double Solve(double _perc, double _c, double _nt, double _pt, double _lt, double _e, double _k, boolean _useMrot) {
	perc = _perc; // налог
	c = _c; // количество деталей
	nt = _nt; // количество деталей на получение полной премии
	pt = _pt; // премия
	lt = _lt; // норма
	e = _e; // цена детали
	k = _k; // районный коэф
	useMrot = _useMrot; //используется ли мрот
	
	double result = 0;
	double m = 0;
	if (nt > 0 && lt < c) { //если параметры премии заданы и было произведено деталей свыше нормы
		m = e*c + (c-lt)/nt*pt;
	}
	else { // иначе считаем без учета премии
		m = e*c;
	}
	if (useMrot && m < mrot) { //Учет МРОТ
		result = mrot*k*(1-perc/100);
	} else {
		result = m*k*(1-perc/100);
	}
	
	lastResult = result;
	return result;
	}
}