package fr.epsi.service.command.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;

public class LsCommandTest extends FTPCommandTest {

    @Before
    public void setUp() throws Exception {
        mockedFtpCommand = Mockito.spy(new LsCommand());

        assertFalse(mockedFtpCommand.isExecuted());
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    @Test
    public void testExecute() throws Exception {
        super.testExecute();


    }
}