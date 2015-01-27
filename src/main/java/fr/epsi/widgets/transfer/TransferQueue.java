package fr.epsi.widgets.transfer;

import fr.epsi.service.transfer.thread.TransferThread;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class TransferQueue extends TableView<TransferThread> {

    ObservableList<TransferThread> transferThreads = FXCollections.observableArrayList();

    public TransferQueue() {
        generateColumns();
        generateContextMenu();

        setItems(transferThreads);
    }

    private void generateColumns() {
        // Colonne Fichier
        TableColumn fileColumn = new TableColumn("Fichier");
        fileColumn.setCellValueFactory(new PropertyValueFactory<TransferThread, String>("file"));

        // Colonne Progression
        TableColumn progressColumn = new TableColumn("Progression");
        progressColumn.setCellValueFactory(new PropertyValueFactory<TransferThread, Double>("progress"));
        progressColumn.setCellFactory(ProgressBarTableCell.<TransferThread> forTableColumn());

        // Colonne Débit
        TableColumn bandwidthColumn = new TableColumn("Débit");
        bandwidthColumn.setCellValueFactory(new PropertyValueFactory<TransferThread, String>("bandwidth"));

        // Colonne Taille
        TableColumn sizeColumn = new TableColumn("Taille");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<TransferThread, String>("size"));

        // Colonne Destination
        TableColumn destinationColumn = new TableColumn("Destination");
        destinationColumn.setCellValueFactory(new PropertyValueFactory<TransferThread, String>("destination"));

        getColumns().addAll(fileColumn, progressColumn, bandwidthColumn, sizeColumn, destinationColumn);
    }

    private void generateContextMenu() {
        MenuItem cancelMenuItem = new MenuItem("Annuler");
        cancelMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TransferThread transferThread = getSelectionModel().getSelectedItem();
                if (transferThread != null) {
                    transferThread.interrupt();
                    getItems().remove(transferThread);
                }
            }
        });

        setContextMenu(new ContextMenu(cancelMenuItem));
    }

    public ObservableList<TransferThread> getTransferThreads() {
        return transferThreads;
    }
}
