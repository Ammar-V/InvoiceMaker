package application;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Invoice Maker");

		VBox loginBox = new VBox(10);
		loginBox.setPadding(new Insets(20));

		Label label1 = new Label("Username:");
		loginBox.getChildren().add(label1);

		TextField username = new TextField();
		username.setPromptText("Username");
		username.setMinWidth(150);
		username.setMaxWidth(150);
		loginBox.getChildren().add(username);

		Label label2 = new Label("Password:");
		loginBox.getChildren().add(label2);

		PasswordField password = new PasswordField();
		password.setPromptText("Password");
		password.setMinWidth(150);
		password.setMaxWidth(150);
		loginBox.getChildren().add(password);

		Button check;
		boolean exists = new File("info.txt").exists();
		if (exists) {
			check = new Button("  Log In  ");
		} else {
			check = new Button("  Sign In  ");
		}
		loginBox.getChildren().add(check);

		Scene loginScene = new Scene(loginBox, 250, 250);
		primaryStage.setScene(loginScene);
		// Icon mad by surang on www.flaticon.com
		primaryStage.getIcons().add(new Image("file:icon.png"));
		primaryStage.show();

		check.setOnAction(a -> {

			if (Cred.check(username.getText(), password.getText()) || !exists) {
				try {

					// Save login info if this is the first time
					if (!exists) {
						Cred.save(username.getText(), password.getText());
					}
					// Load business info
					ManageData manage = new ManageData();
					manage.load();
					manage.loadContacts();
					manage.loadShippers();
					manage.loadReceivers();
					manage.loadInvoices();

					BorderPane root = new BorderPane();

					CreateInvoice create = new CreateInvoice();
					AddContact addContact = new AddContact();
					AddShipper addShipper = new AddShipper();
					AddReceiver addReceiver = new AddReceiver();
					EditInfo edit = new EditInfo();
					ViewInvoices viewInvoices = new ViewInvoices();
					ChangeCred change = new ChangeCred();

					TabPane tabPane = new TabPane();
					tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

					Tab tab1 = new Tab("    Create Invoice    ", create.getPage(primaryStage));
					tabPane.getTabs().add(tab1);

					Tab tab2 = new Tab("    Brokers    ", addContact.getPage());
					tabPane.getTabs().add(tab2);

					Tab tab3 = new Tab("    Shippers    ", addShipper.getPage());
					tabPane.getTabs().add(tab3);

					Tab tab4 = new Tab("    Receivers    ", addReceiver.getPage());
					tabPane.getTabs().add(tab4);

					Tab tab5 = new Tab("    Edit Info    ", edit.getPage(primaryStage));
					tabPane.getTabs().add(tab5);

					Tab tab6 = new Tab("    Past Invoices    ", viewInvoices.getPage());
					tabPane.getTabs().add(tab6);

					Tab tab7 = new Tab("    Credentials    ", change.getPage());
					tabPane.getTabs().add(tab7);

					root.setCenter(tabPane);
					Scene scene = new Scene(root, 721, 750);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.close();
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Incorrect Username/Password.");
				alert.show();
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}