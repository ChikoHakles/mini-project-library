package id.co.indivara.library;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.library.entities.ResponseBody;
import id.co.indivara.library.services.BorrowService;
import id.co.indivara.library.services.HistoryService;
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
public class HistoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private ReturnService returnService;

    @Autowired
    private HistoryService historyService;

    String readerKey = "cmVhZGVyOnJlYWRlcg==";
    String librarianKey = "bGlicmFyaWFuOmxpYnJhcmlhbg==";
    String adminKey = "YWRtaW46YWRtaW4=";

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void findAllhistoryFailedUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/history")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void findAllhistoryByReaderSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/readers/{id}/history", 5)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("History Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findAllhistoryByReaderFailedHistoryNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/readers/{id}/history", 6)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("No History Found", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findAllhistoryByReaderFailedReaderNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/readers/{id}/history", 0)
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
    public void findAllhistoryByBookSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}/history", 8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("History Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findAllhistoryByBookFailedHistoryNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}/history", 7)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("No History Found", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findAllhistoryByBookFailedBookNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}/history", 0)
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
}
