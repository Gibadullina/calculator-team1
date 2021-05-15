package com.skylabs.controllers;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class RegisterTest {
    static RegisterController testRegister;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testRegister= new RegisterController ();
	}

	@Test
	public void test() {
		assertFalse(testRegister.Register("admin", "admin"));
	}

}