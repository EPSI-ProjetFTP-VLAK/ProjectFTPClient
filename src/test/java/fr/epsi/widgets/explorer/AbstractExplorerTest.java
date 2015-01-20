package fr.epsi.widgets.explorer;

import javafx.scene.control.TreeItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import org.junit.After;
import org.junit.Before;

public class AbstractExplorerTest {;

    protected AbstractExplorer explorer;
    protected TreeItem<String> rootNode;

    public AbstractExplorerTest() {
        explorer = new AbstractExplorer() {
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
    }

    public AbstractExplorerTest(AbstractExplorer explorer) {
        this.explorer = explorer;
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
}