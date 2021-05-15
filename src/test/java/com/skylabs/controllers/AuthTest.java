package com.skylabs.controllers;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class AuthTest {

	static AuthController AC;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AC = new AuthController();
	}

	@Test
	public void test() {
		assertTrue(AC.Authorize("admin", "admin")==2);
	}

}
