package fr.epsi.widgets.explorer;

import javafx.scene.control.TreeItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class AbstractExplorerTest {

    public static final String HOSTNAME = "test-computer";

    private AbstractExplorer abstractExplorer;
    private TreeItem<String> rootNode;

    @Before
    public void setUp() throws Exception {
        abstractExplorer = new AbstractExplorer() {
            @Override
            public void doOnDrag(MouseEvent mouseEvent) {

            }

            @Override
            public void doOnDrop(DragEvent dragEvent) {

            }

            @Override
            public void doOnClick(MouseEvent mouseEvent) {

            }
        };

        rootNode = new TreeItem<String>(HOSTNAME);
    }

    @Test
    public void testGenerateChildNodes() throws Exception {
        abstractExplorer.setRoot(rootNode);

        Exception exception = null;
        try {
            abstractExplorer.generateChildNodes();
        } catch(Exception e){
            exception = e;
        }

        assertNull(exception);
    }

    @Test(expected=Exception.class)
    public void testGenerateChildNodesWithNullRoot() throws Exception {
        abstractExplorer.setRoot(null);
        abstractExplorer.generateChildNodes();
    }

    @After
    public void tearDown() throws Exception {

    }
}