package fr.epsi.widgets.explorer;

import javafx.scene.control.TreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;

public class RemoteExplorerTest extends AbstractExplorerTest {

    public RemoteExplorerTest() {
        super(Mockito.spy(new RemoteExplorer()));
    }

    @Before
    public void setUp() throws Exception {
        rootNode = new TreeItem<String>("Server");
        explorer.setRoot(rootNode);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitializeNodesWithoutConnection() throws Exception {
        explorer.initializeNodes();

        assertFalse(explorer.getRoot().isExpanded());
        assertFalse(explorer.getRoot().getChildren().isEmpty());
    }
}