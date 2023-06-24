package id.co.indivara.library;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.ResponseBody;
import id.co.indivara.library.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    String readerKey = "cmVhZGVyOnJlYWRlcg==";
    String librarianKey = "bGlicmFyaWFuOmxpYnJhcmlhbg==";
    String adminKey = "YWRtaW46YWRtaW4=";

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void findAllBookSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Book Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findBookSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Book Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findBookFailedNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }

    @Test
    public void findBookFailedUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void createBookSuccessTest() throws Exception {
        Book book = Book.builder()
                .bookTitle("Banyak Jalan Menuju Roma")
                .bookAuthor("Pleaides")
                .bookDescription("Veni Vidi Vici")
                .bookCopy(1)
                .bookPages(632)
                .bookPublisher("Roman")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + librarianKey)
                    .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(res -> {
                    ResponseBody<Book> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Book>>() {}
                    );
                    Assertions.assertEquals("Book Created", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());

                    bookService.deleteBook(responseBody.getData().getBookId());
                })
        ;
    }

    @Test
    public void createBookFailedValidationErrorTest() throws Exception {
        Book book = Book.builder().build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Data Not Valid", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNotNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void createBookFailedUnauthorizedTest() throws Exception {
        Book book = Book.builder().build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void createBookFailedForbiddenTest() throws Exception {
        Book book = Book.builder().build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
        ;
    }

    @Test
    public void createBookFailedNoRequestBodyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }

    @Test
    public void updateBookSuccessTest() throws Exception {
        Book book = Book.builder()
                .bookTitle("Diwan Makan Somay")
                .bookDescription("Makan somay sekebon")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", 9)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(res -> {
                    ResponseBody<Book> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Book>>() {}
                    );
                    Assertions.assertEquals("Book Updated", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void updateBookFailedNotFoundTest() throws Exception {
        Book book = Book.builder()
                .bookTitle("Diwan Makan Somay")
                .bookDescription("Makan somay sekebon")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(res -> {
                    ResponseBody<Book> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Book>>() {}
                    );
                    Assertions.assertEquals("No Book Found", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void updateBookFailedUnauthorizedTest() throws Exception {
        Book book = Book.builder()
                .bookTitle("Diwan Makan Somay")
                .bookDescription("Makan somay sekebon")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", 9)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void updateBookFailedForbiddenTest() throws Exception {
        Book book = Book.builder()
                .bookTitle("Diwan Makan Somay")
                .bookDescription("Makan somay sekebon")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", 9)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
        ;
    }

    @Test
    public void updateBookFailedNoRequestBodyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", 9)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }
}