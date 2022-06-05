package com.gilman;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// Class to define object that will connect and query the database for Books
public class BookDAO {
	// first part of URL is always the same - last part is database name
	private String jdbcURL = "jdbc:mysql://localhost:3306/<DATABASE_NAME>";
	private String jdbcUsername = "<USERNAME>";
	private String jdbcPassword = "<PASSWORD>";
	private Connection jdbcConnection;

	public void connect() {
		try {
			// only connect to database if connection already exists or is closed
			if (jdbcConnection == null || jdbcConnection.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
				System.out.println("MySQL Connection Established");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			// only disconnect from database when there is an established connection
			if (jdbcConnection != null && !jdbcConnection.isClosed()) {
				jdbcConnection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Book> listAllBooks() {
		this.connect();
		ArrayList<Book> bookList = new ArrayList<>();

		try {
			// SQL Statement object used to execute SQL queries
			Statement statement = jdbcConnection.createStatement();
			// Result set stores query results in rows
			// contains methods to access and iterate through result records
			ResultSet resultSet = statement.executeQuery("SELECT * FROM book");

			while (resultSet.next()) { // will end when no records left to be read
				String title = resultSet.getString("title"); // name matches table field
				String author = resultSet.getString("author");
				float price = resultSet.getFloat("price");

				Book newBook = new Book(title, author, price);
				bookList.add(newBook);
			}

			// cleanup
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.disconnect();

		return bookList;
	}

	public boolean insertBook(Book book) {
		this.connect();
		boolean rowInserted = false;
		String sql = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";
		try {
			// Prepared Statement allows values (?s) to be replaced at runtime
			PreparedStatement statement = jdbcConnection.prepareStatement(sql);
			// parameters: position of (?) , value to replace with
			statement.setString(1, book.getTitle()); // set the value in the SQL statement
			statement.setString(2, book.getAuthor());
			statement.setFloat(3, book.getPrice());

			// executes the Prepared Statement (successful if value returned > 0)
			rowInserted = statement.executeUpdate() > 0;
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.disconnect();
		return rowInserted;
	}
}
