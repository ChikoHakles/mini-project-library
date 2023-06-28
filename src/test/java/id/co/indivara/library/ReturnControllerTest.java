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
public class ReturnControllerTest {
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
    public void findAllReturnSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/returns")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Return Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findReturnSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/returns/{code}", "R24062023VID")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Return Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findReturnFailedNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/returns/{code}", "R20052023KJA")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("No Return Found", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findReturnFailedUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/returns/{code}", "R20052023KJA")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void findReturnByBorrowSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/returns/{code}", "B24062023SWW")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Return Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findReturnByBorrowFailedBorrowNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/returns/{code}", "B20012023WWK")
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
    public void createReturnSuccessTest() throws Exception {
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

        Borrow createdBorrow = borrowService.saveBorrow(borrow);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/returns")
                        .param("borrowCode", createdBorrow.getBorrowCode())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(res -> {
                    ResponseBody<Return> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Return>>() {}
                    );
                    Assertions.assertEquals("Return Created", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());

                    returnService.deleteReturnByCode(responseBody.getData().getReturnCode());
                    borrowService.deleteBorrowByCode(responseBody.getData().getBorrow().getBorrowCode());
                    bookService.deleteBook(book.getBookId());
                })
        ;
    }

    @Test
    public void createReturnFailedUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/returns")
                        .param("borrowCode", "B22062023MEC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void createReturnFailedForbiddenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/returns")
                        .param("borrowCode", "B22062023MEC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
        ;
    }

    @Test
    public void createReturnFailedBorrowNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/returns")
                        .param("borrowCode", "B30092023EGC")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey))
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
    public void createReturnFailedBorrowHaveHadReturnedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/returns")
                        .param("borrowCode", "B22062023YEL")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("This Borrow has returned!", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }
}
