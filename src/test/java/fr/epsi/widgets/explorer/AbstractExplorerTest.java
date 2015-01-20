package fr.epsi.widgets.explorer;

import javafx.scene.control.TreeItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import org.junit.After;
import org.junit.Before;

public class AbstractExplorerTest {

    public static final String HOSTNAME = "test-computer";

    private AbstractExplorer abstractExplorer;
    private TreeItem<String> rootNode;

    @Before
    public void setUp() throws Exception {
        abstractExplorer = new AbstractExplorer() {
            @Override
            public void doOnFileDrag(MouseEvent mouseEvent) {

            }

            @Override
            public void doOnFileDrop(DragEvent dragEvent) {

            }

            @Override
            public void initializeNodes() {

            }
        };

        rootNode = new TreeItem<String>(HOSTNAME);
    }

    @After
    public void tearDown() throws Exception {

    }
}