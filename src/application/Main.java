package application;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import repositories.ProductRepository;
import src.io.demo.bookstore.intefaces.Product;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Group group = new Group();
			Scene scene = new Scene(group, 690, 510);

			ObservableList<Product> products = new ProductRepository().list();

			TableView tableView = new TableView<>(products);

			TableColumn nameColumn = new TableColumn("Name");
			nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
			nameColumn.setMinWidth(180);

			TableColumn descColumn = new TableColumn("Description");
			descColumn.setMinWidth(230);
			descColumn.setCellValueFactory(new PropertyValueFactory("description"));

			TableColumn valColumn = new TableColumn("Value");
			valColumn.setMinWidth(60);
			valColumn.setCellValueFactory(new PropertyValueFactory("value"));

			TableColumn isbnColumn = new TableColumn("ISBN");
			isbnColumn.setMinWidth(180);
			isbnColumn.setCellValueFactory(new PropertyValueFactory("isbn"));

			tableView.getColumns().addAll(nameColumn, descColumn, valColumn, isbnColumn);

			VBox vbox = new VBox(tableView);
			vbox.setPadding(new Insets(70, 0, 0, 10));

			Label label = new Label("List of books");
			label.setFont(Font.font("Lucida Grande", FontPosture.REGULAR, 30));
			label.setPadding(new Insets(20, 0, 10, 10));

			Button button = new Button("Export CSV");
			button.setLayoutX(575);
			button.setLayoutY(25);
			button.setOnAction(event -> exportToCSV(products));

			group.getChildren().addAll(label, vbox, button);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Java FX Bookstore system");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void exportToCSV(ObservableList<Product> products) {
		PrintStream ps = null;
		try {
			ps = new PrintStream("product.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ps.println("Name, Description, Value, ISBN");

		for (Product product : products) {
			ps.println(String.format("%s, %s, %s, %s", 
					product.getName(), 
					product.getDescription(), 
					product.getValue(),
					product.getIsbn()));
		}
		ps.close();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
