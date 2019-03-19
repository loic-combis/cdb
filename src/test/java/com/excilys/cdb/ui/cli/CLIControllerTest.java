package com.excilys.cdb.ui.cli;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.ui.PageProvider;
import com.excilys.cdb.ui.UIController;

public class CLIControllerTest {

	private CLIController cli;

	@Before
	public void setUp() {
		cli = new CLIController();
	}

	@Test
	public void instanceOfController() {
		assertTrue(cli instanceof UIController);
	}

	@Test
	public void instanceOfPageProvider() {
		assertTrue(cli instanceof PageProvider);
	}
}