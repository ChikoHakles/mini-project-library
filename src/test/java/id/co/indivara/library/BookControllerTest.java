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

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void findAllBookSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic cmVhZGVyOnJlYWRlcg=="))
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
                        .header("Authorization", "Basic cmVhZGVyOnJlYWRlcg=="))
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
    public void findBookNoBookFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic cmVhZGVyOnJlYWRlcg=="))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }

    @Test
    public void findBookUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void createBookSuccess() throws Exception {
        Book book = new Book();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Book Created", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }
}