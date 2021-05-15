package com.skylabs.tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class SolverTest {

	@Test
	public void test() {
		Double result = Solver.Solve(13, 15, 5, 1000, 10, 1000, 1.15f, true);
		assertTrue(result == 16007.999668121338); 
	}

}
