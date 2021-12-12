module bookstore.javafx {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires book.oo;
	
	opens application to javafx.graphics, javafx.fxml;
}
