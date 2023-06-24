package id.co.indivara.library;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.library.entities.Reader;
import id.co.indivara.library.entities.ResponseBody;
import id.co.indivara.library.services.ReaderService;
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
public class ReaderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReaderService readerService;

    String readerKey = "cmVhZGVyOnJlYWRlcg==";
    String librarianKey = "bGlicmFyaWFuOmxpYnJhcmlhbg==";
    String adminKey = "YWRtaW46YWRtaW4=";

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void findAllReaderSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/readers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Reader Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findReaderSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/readers/{id}", 5)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Reader Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findReaderFailedNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/readers/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }

    @Test
    public void findReaderFailedUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/readers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void createReaderSuccessTest() throws Exception {
        Reader reader = Reader.builder()
                .readerName("Budi")
                .readerAddress("Jalan Doang Jadian Enggak")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/readers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey)
                        .content(objectMapper.writeValueAsString(reader)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(res -> {
                    ResponseBody<Reader> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Reader>>() {}
                    );
                    Assertions.assertEquals("Reader Created", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());

                    readerService.deleteReader(responseBody.getData().getReaderId());
                })
        ;
    }

    @Test
    public void createReaderFailedValidationErrorTest() throws Exception {
        Reader reader = Reader.builder()
                .readerName("Budi")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/readers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey)
                        .content(objectMapper.writeValueAsString(reader)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(res -> {
                    ResponseBody<Reader> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Reader>>() {}
                    );
                    Assertions.assertEquals("Data Not Valid", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNotNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void createReaderFailedUnauthorizedTest() throws Exception {
        Reader reader = Reader.builder()
                .readerName("Budi")
                .readerAddress("Jalan Doang Jadian Enggak")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/readers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reader)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void createReaderFailedForbiddenTest() throws Exception {
        Reader reader = Reader.builder()
                .readerName("Budi")
                .readerAddress("Jalan Doang Jadian Enggak")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/readers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey)
                        .content(objectMapper.writeValueAsString(reader)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
        ;
    }

    @Test
    public void createReaderFailedNoRequestBodyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/readers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }

    @Test
    public void updateReaderSuccessTest() throws Exception {
        Reader reader = Reader.builder()
                .readerName("Chiko Hakles")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/readers/{id}", 5)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey)
                        .content(objectMapper.writeValueAsString(reader)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(res -> {
                    ResponseBody<Reader> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Reader>>() {}
                    );
                    Assertions.assertEquals("Reader Updated", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void updateReaderFailedNotFoundTest() throws Exception {
        Reader reader = Reader.builder()
                .readerName("Chiko Hakles")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/readers/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey)
                        .content(objectMapper.writeValueAsString(reader)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }

    @Test
    public void updateReaderFailedUnauthorizedTest() throws Exception {
        Reader reader = Reader.builder()
                .readerName("Chiko Hakles")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/readers/{id}", 5)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reader)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void updateReaderFailedForbiddenTest() throws Exception {
        Reader reader = Reader.builder()
                .readerName("Chiko Hakles")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/readers/{id}", 5)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey)
                        .content(objectMapper.writeValueAsString(reader)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
        ;
    }

    @Test
    public void updateReaderFailedNoRequestBodyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/readers/{id}", 5)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + librarianKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }
}
