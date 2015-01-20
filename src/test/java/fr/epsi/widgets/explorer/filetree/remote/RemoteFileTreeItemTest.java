package fr.epsi.widgets.explorer.filetree.remote;

import fr.epsi.service.command.FTPCommandFactory;
import fr.epsi.widgets.explorer.FileTreeItemTest;
import fr.epsi.widgets.explorer.filetree.FileTreeItem;
import javafx.scene.control.TreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FTPCommandFactory.class)
public class RemoteFileTreeItemTest extends FileTreeItemTest {

    public static final String HOSTNAME = "server";

    public RemoteFileTreeItemTest()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(RemoteFileTreeItem.class);
    }

    @Before
    public void setUp() throws Exception {
        testRoots = new File[2];

        File firstDirectoryMock = Mockito.spy(new File("test-directory-1"));
        Mockito.doReturn(true).when(firstDirectoryMock).isDirectory();
        testRoots[0] = firstDirectoryMock;

        File secondDirectoryMock = Mockito.spy(new File("test-directory-2"));
        Mockito.doReturn(true).when(secondDirectoryMock).isDirectory();
        testRoots[1] = secondDirectoryMock;

        rootNode = new TreeItem<String>(HOSTNAME);
        rootNode.getChildren().add(new RemoteFileTreeItem(testRoots[0]));
        rootNode.getChildren().add(new RemoteFileTreeItem(testRoots[1]));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGenerateChildNodesWithConnection() throws Exception {
//        FileTreeItem firstNode = (FileTreeItem) rootNode.getChildren().get(0);
//        FileTreeItem secondNode = (FileTreeItem) rootNode.getChildren().get(1);
//
//        firstNode.generateChildNodes();
//        secondNode.generateChildNodes();
//
//
//        LsCommand mockedFtpCommand = Mockito.spy(new LsCommand());
//
//        PowerMockito.when(FTPCommandFactory.class, "createCommand", "ls").thenReturn(new File[] { new File("test-file-1"), new File("test-file-2") });

    }

    @Test
    public void testGenerateChildNodesWithoutConnection() throws Exception {
        FileTreeItem firstNode = (FileTreeItem) rootNode.getChildren().get(0);
        FileTreeItem secondNode = (FileTreeItem) rootNode.getChildren().get(1);

        firstNode.generateChildNodes();
        secondNode.generateChildNodes();

        assertEquals(0, firstNode.getChildren().size());
        assertEquals(0, secondNode.getChildren().size());
    }
}