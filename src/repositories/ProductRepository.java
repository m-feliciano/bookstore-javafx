package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.io.demo.bookstore.entities.Author;
import src.io.demo.bookstore.entities.PrintedBook;
import src.io.demo.bookstore.intefaces.Product;
import util.ConnectionFactory;

public class ProductRepository {

	public ObservableList<Product> list() {
//		Author author = new Author("Rodrigo Turini","rodrigo.turini@caelum.com.br","123.456.789.10");
//		Author author2 = new Author();
//		author2.setName("Paulo Silveira");
//		author2.setEmail("paulo.silveira@caelum.com.br");
//		author2.setCpf("123.456.789.10");
//		
//		Book book = new PrintedBook("Java 8 Prático","Novos recursos da linguagem",
//				59.90,"978-85-66250-46-6", author);
//		Book book2 = new PrintedBook("Desbravando a O.O.", "Book de Java e O.O",
//				59.90,"321-54-67890-11-2", author);		
//		Book book3 = new PrintedBook("Lógica de Programação", 
//				"Crie seus primeiros programas", 59.90, "978-85-66250-22-0", author2);

//		return FXCollections.observableArrayList(book, book2, book3);
		
		ObservableList<Product> products = observableArrayList();
		Connection conn = new ConnectionFactory().getConnection();
		String sql = "select * from tb_products";

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				PrintedBook book = new PrintedBook(new Author());
				book.setName(resultSet.getString("name"));
				book.setDescription(resultSet.getString("description"));
				book.setValue(resultSet.getDouble("value"));
				book.setIsbn(resultSet.getString("isbn"));
				products.add(book);
			}
		} catch (SQLException e1) {
			throw new RuntimeException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return products;
	}

	private ObservableList<Product> observableArrayList() {
		return FXCollections.observableArrayList();
	}

}
