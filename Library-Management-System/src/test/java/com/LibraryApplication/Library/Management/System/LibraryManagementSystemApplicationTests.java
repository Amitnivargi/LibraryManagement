import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class LibraryManagementSystemApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@Autowired
	private ObjectMapper objectMapper;

	private Book book;
	@BeforeEach
	public void setUp() {
		book = new Book(1, "Book Title", "Author", "ISBN", 5);
	}

	@Test
	public void testBorrowBook() throws Exception {
		when(bookService.borrowBook(1, 1)).thenReturn(book);

		mockMvc.perform(post("/api/books/borrow")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(book)))
				.andExpect(status().isOk());
	}

	@Test
	public void testReturnBook() throws Exception {
		when(bookService.returnBook(1)).thenReturn(book);

		mockMvc.perform(post("/api/books/return/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testBookAvailability() throws Exception {
		when(bookService.checkAvailability(1)).thenReturn(true);

		mockMvc.perform(get("/api/books/1/availability"))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
	}
	@Test
	public void testCreateUser() throws Exception {
		User user = new User(1, "John Doe", "johndoe@example.com", "password");

		when(userService.createUser(user)).thenReturn(user);

		mockMvc.perform(post("/api/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateUser() throws Exception {
		User user = new User(1, "Jane Doe", "janedoe@example.com", "password123");

		when(userService.updateUser(1, user)).thenReturn(user);

		mockMvc.perform(put("/api/users/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
	}
}