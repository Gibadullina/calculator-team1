package com.skylabs.baselogic;

public abstract class BaseSolver {
	static final double mrot = 12130d;
	static double perc,c,nt,pt,lt,e,k;
	static boolean useMrot;
	static double lastResult = 0;

	//возвращаем последнее расчитанное значение
	protected static double GetLastRes()
	{
		return lastResult;
	}
}