package fr.epsi.widgets.explorer.filetree.remote;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;
import fr.epsi.service.command.CommandService;
import fr.epsi.service.command.commands.LsCommand;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

public class RemoteRootFileTreeItem extends TreeItem<String> {

    public RemoteRootFileTreeItem(String value) {
        super(value);

        attachExpantionListener();
    }

    private void attachExpantionListener() {
        expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasExpanded, Boolean isExpanded) {
                if (isExpanded) {
                    generateChildren();
                }
            }
        });
    }

    public void generateChildren() {
        if (isSocketConnect()) {
            LsCommand lsCommand = createLsCommand();

            CommandService commandService = MainController.getCommandService();
            commandService.getCommandQueue().offer(lsCommand);

            while (!lsCommand.isExecuted()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            FileDTO[] files = (FileDTO[]) lsCommand.getResponse();
            getChildren().clear();
            for (FileDTO file : files) {
                getChildren().add(new RemoteFileTreeItem(file));
            }
        }
    }

    public boolean isSocketConnect() {
        return MainController.getConnectionService().getSocket() != null && MainController.getConnectionService().getSocket().isConnected();
    }

    public LsCommand createLsCommand() {
        return new LsCommand();
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
