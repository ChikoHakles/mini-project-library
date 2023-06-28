package id.co.indivara.library;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import id.co.indivara.library.entities.*;
import id.co.indivara.library.services.BookService;
import id.co.indivara.library.services.BorrowService;
import id.co.indivara.library.services.ReturnService;
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
public class BorrowControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private ReturnService returnService;

    @Autowired
    private BookService bookService;

    //siapkan credential untuk 3 jenis akun, reader, librarian, dan admin
    String readerKey = "cmVhZGVyOnJlYWRlcg==";
    String librarianKey = "bGlicmFyaWFuOmxpYnJhcmlhbg==";
    String adminKey = "YWRtaW46YWRtaW4=";

    //object mapper untuk membaca format json dan mengubah nya jadi string jika diperlukan
    ObjectMapper objectMapper = new ObjectMapper();

    /* yang dilakukan disini adalah menyiapkan request dengan type json dan credential Authorization dengan key yg telah ada.
     * setelah itu, mengecek response apakah status sesuai, data sesuai, dan error sesuai untuk setiap case.*/
    @Test
    public void findAllBorrowSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/borrows")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Borrow Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findBorrowByIdSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/borrows")
                        .param("uuid", "52561536-8050-46fb-a478-31e32cd30280")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Borrow Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findBorrowByCodeSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/borrows")
                        .param("code", "B22062023YZW")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Borrow Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findBorrowFailedNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/borrows")
                        .param("code", "B210922ACB")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("No Borrow Found", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findBorrowFailedUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/borrows")
                        .param("code", "B210922ACB")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void findBorrowUnreturnedByBookSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}/unreturned", 5)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Unreturned Book Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findBorrowUnreturnedByBookFailedBookNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}/unreturned", 0)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("No Book Found", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findBorrowUnreturnedByReaderSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/readers/{id}/unreturned", 4)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Unreturned Book Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findBorrowUnreturnedByReaderFailedReaderNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/readers/{id}/unreturned", 0)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("No Reader Found", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void createBorrowSuccessTest() throws Exception {
        Book book = bookService.saveBook(Book.builder()
                .bookTitle("Banyak Jalan Menuju Roma")
                .bookAuthor("Pleaides")
                .bookDescription("Veni Vidi Vici")
                .bookCopy(1)
                .bookPages(632)
                .bookPublisher("Roman")
                .build()
        );

        objectMapper.registerModule(new JavaTimeModule());
        BorrowDTO borrow = BorrowDTO.builder()
                .bookId(book.getBookId())
                .readerId(5)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrows")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey)
                        .content(objectMapper.writeValueAsString(borrow)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(res -> {
                    ResponseBody<Borrow> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Borrow>>() {}
                    );
                    Assertions.assertEquals("Borrow Created", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());

                    borrowService.deleteBorrowByCode(responseBody.getData().getBorrowCode());
                    bookService.deleteBook(book.getBookId());
                })
        ;
    }

    @Test
    public void createBorrowFailedUnauthorizedTest() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        BorrowDTO borrow = BorrowDTO.builder()
                .bookId(9)
                .readerId(5)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrows")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrow)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void createBorrowFailedForbiddenTest() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        BorrowDTO borrow = BorrowDTO.builder()
                .bookId(9)
                .readerId(5)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrows")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + adminKey)
                        .content(objectMapper.writeValueAsString(borrow)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
        ;
    }

    @Test
    public void createBorrowFailedNoRequestBodyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrows")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }

    @Test
    public void createBorrowFailedBookNotFoundTest() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        BorrowDTO borrow = BorrowDTO.builder()
                .bookId(0)
                .readerId(5)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrows")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey)
                        .content(objectMapper.writeValueAsString(borrow)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(res -> {
                    ResponseBody<Borrow> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Borrow>>() {}
                    );
                    Assertions.assertEquals("No Book Found", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void createBorrowFailedReaderNotFoundTest() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        BorrowDTO borrow = BorrowDTO.builder()
                .bookId(9)
                .readerId(0)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrows")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey)
                        .content(objectMapper.writeValueAsString(borrow)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(res -> {
                    ResponseBody<Borrow> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Borrow>>() {}
                    );
                    Assertions.assertEquals("No Reader Found", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }
}
