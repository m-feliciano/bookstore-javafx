package dao;

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

public class ProductDAO {

	public ObservableList<Product> list() {

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

	public void addProduct(Product product) {
		PreparedStatement ps = null;
		try (Connection conn = new ConnectionFactory().getConnection()) {

			ps = conn.prepareStatement("insert into products (name," 
					+ " description, value, isbn) values (?,?,?,?)");
			ps.setString(1, product.getName());
			ps.setString(2, product.getDescription());
			ps.setDouble(3, product.getValue());
			ps.setString(4, product.getIsbn());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
