package fr.epsi.widgets.transfer;

import fr.epsi.dto.TransferDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class TransferQueue extends TableView {

    ObservableList<TransferDTO> transferDTOs = FXCollections.observableArrayList();

    public TransferQueue() {
        generateColumns();

        setItems(transferDTOs);
    }

    private void generateColumns() {
        // Colonne Fichier
        TableColumn fileColumn = new TableColumn("Fichier");
        fileColumn.setCellValueFactory(new PropertyValueFactory<TransferDTO, String>("file"));

        // Colonne Progression
        TableColumn progressColumn = new TableColumn("Progression");
        progressColumn.setCellValueFactory(new PropertyValueFactory<TransferDTO, String>("progress"));
        progressColumn.setCellFactory(ProgressBarTableCell.<TransferDTO> forTableColumn());

        // Colonne Débit
        TableColumn bandwidthColumn = new TableColumn("Débit");
        bandwidthColumn.setCellValueFactory(new PropertyValueFactory<TransferDTO, String>("bandwidth"));

        // Colonne Taille
        TableColumn sizeColumn = new TableColumn("Taille");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<TransferDTO, String>("size"));

        // Colonne Destination
        TableColumn destinationColumn = new TableColumn("Destination");
        destinationColumn.setCellValueFactory(new PropertyValueFactory<TransferDTO, String>("destination"));

        getColumns().addAll(fileColumn, progressColumn, bandwidthColumn, sizeColumn, destinationColumn);
    }

    public ObservableList<TransferDTO> getTransferDTOs() {
        return transferDTOs;
    }
}
