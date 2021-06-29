package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ViewInvoices {

	public static ObservableList<PastInvoices> invoices = FXCollections.observableArrayList();

	public static TableView<PastInvoices> table;

	@SuppressWarnings("unchecked")
	public VBox getPage() {

		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(20, 20, 20, 20));

		GridPane grid = new GridPane();
		grid.getStyleClass().add("border");
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(12);
		grid.setHgap(10);
		vbox.getChildren().add(grid);

		TableColumn<PastInvoices, String> nameColumn = new TableColumn<>("Invoice");
		nameColumn.setMinWidth(430);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));

		TableColumn<PastInvoices, String> dateColumn = new TableColumn<>("Date Created");
		dateColumn.setMinWidth(200);
		dateColumn.setStyle("-fx-alignment: center");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

		table = new TableView<>();
		table.getColumns().addAll(nameColumn, dateColumn);
		table.setMinWidth(630);
		table.setPlaceholder(new Label("No Invoices."));
		GridPane.setConstraints(table, 0, 0);
		table.setItems(invoices);
		invoices.addListener((ListChangeListener.Change<? extends PastInvoices> e) -> {
			table.setItems(invoices);
		});

		Button openButton = new Button("   Open Invoice   ");
		openButton.setDisable(true);
		GridPane.setConstraints(openButton, 0, 1);
		openButton.setOnAction(e -> {
			try {
				File file = new File("invoices/" + table.getSelectionModel().getSelectedItem().getFileName());
				Desktop desktop = Desktop.getDesktop();
				desktop.open(file);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// If the table is not focused (not selected) then disable openButton
		table.focusedProperty().addListener((obs, old, n) -> {
			// Make sure to check if the user is trying to use the open button. If they
			// are, don't disable yet.
			if (!table.isFocused() && !openButton.isFocused()) {
				openButton.setDisable(true);
				table.getSelectionModel().clearSelection();
			}
		});

		// When a row is selected, enable the delete button
		table.getSelectionModel().selectedItemProperty().addListener((obs, old, n) -> {
			if (n != null) {
				openButton.setDisable(false);
			}
		});

		grid.getChildren().addAll(table, openButton);

		return vbox;

	}

}
