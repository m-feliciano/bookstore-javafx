package br.com.feliciano.bookstore.view.application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Optional;

import br.com.feliciano.bookstore.controller.ProductController;
import br.com.feliciano.bookstore.intefaces.Product;
import br.com.feliciano.bookstore.services.Exporter;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Group group = new Group();

			Scene scene = new Scene(group, 690, 510);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			ObservableList<Product> products = new ProductController().list();

			TableView<Product> tableView = new TableView<>(products);
			TableColumn<Product, String> nameColumn = criateColumn("Name", 180, "name");
			TableColumn<Product, String> descColumn = criateColumn("Description", 230, "description");
			TableColumn<Product, String> valColumn = criateColumn("Price", 60, "price");
			TableColumn<Product, String> isbnColumn = criateColumn("ISBN", 180, "isbn");

			tableView.getColumns().addAll(Arrays.asList(nameColumn, descColumn, valColumn, isbnColumn));

			VBox vbox = new VBox(tableView);
			vbox.setId("vbox");

			Label label = new Label("List of books");
			label.setId("label-list");

			BigDecimal totalValue = getTotalPriceOf(products);

			Label labelFooter = new Label(String.format("You have R$%.2f in stock, with a total of %d products.",
					totalValue, products.size()));

			labelFooter.setId("label-footer");

			Button button = new Button("Export CSV");

			Label progress = new Label();
			progress.setId("label-progress");

			button.setOnAction(event -> {
				Task<Void> task = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						sleepFor(10000);
						exportToCSV(products);
						return null;
					}
				};
				task.setOnRunning(e -> progress.setText("exporting..."));
				task.setOnSucceeded(e -> progress.setText("sucess!"));
				new Thread(task).start();
			});
			group.getChildren().addAll(label, vbox, button, progress, labelFooter);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Java FX Bookstore system");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BigDecimal getTotalPriceOf(ObservableList<Product> products) {
		Optional<BigDecimal> totalValue = products.stream().map(Product::getPrice).reduce(BigDecimal::add);
		return totalValue.isPresent() ? totalValue.get().setScale(2, RoundingMode.HALF_UP) : null;
	}

	private void exportToCSV(ObservableList<Product> products) {
		new Exporter().toCSV(products);
	}

	private void sleepFor(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			System.out.println("oops, there was an error\\r\\n: " + e);
		}
	}

	private TableColumn<Product, String> criateColumn(String title, int width, String attribute) {
		TableColumn<Product, String> column = new TableColumn<>(title);
		column.setMinWidth(width);
		column.setCellValueFactory(new PropertyValueFactory<>(attribute));
		return column;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
