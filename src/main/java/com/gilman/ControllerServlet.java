package com.gilman;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControllerServlet
 */
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllerServlet() {
		super();

		// Test the database connection
		bookDAO = new BookDAO();
		bookDAO.connect();
		bookDAO.disconnect();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo(); // get the relative path
		if (action.equals("/new")) { // match relative path /new
			addBook(request, response);
		} else {
			listBooks(request, response); // default display list of books
		}
	}

	private void addBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get dispatcher for specific page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/BookForm.html");
		// send request and response objects to specific page to be utilized
		dispatcher.forward(request, response);
	}

	private void listBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set attribute that can be accessed by JSTL on JSP
		ArrayList<Book> books = this.bookDAO.listAllBooks();
		request.setAttribute("book_list", books);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/BookList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		if (action.equals("/insert")) {
			insertBook(request, response);
		}
	}

	private void insertBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// handle Book Form inputs and update list of books
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		float price = Float.parseFloat(request.getParameter("price"));
		Book newBook = new Book(title, author, price);
		bookDAO.insertBook(newBook);

		response.sendRedirect("list");
	}

}
