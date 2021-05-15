package com.skylabs.baselogic;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SaveJsonTest {

	String fileName = "test.json";

	@Test
	public void test() {
		try {
			Util.SaveString(fileName, "test");
			File fl = new File(fileName);
			assertEquals(fl.exists(), true);
			fl.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Ошибка. Файл не создан");
		}
	}

}
