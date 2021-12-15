module bookstore.javafx {
	requires javafx.graphics;
	requires javafx.controls;
	requires bookstore.backend;

	opens br.com.feliciano.bookstore.view.application to javafx.graphics, javafx.fxml;
}
