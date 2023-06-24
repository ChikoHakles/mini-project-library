package id.co.indivara.library;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.library.entities.ResponseBody;
import id.co.indivara.library.entities.Wishlist;
import id.co.indivara.library.entities.WishlistDTO;
import id.co.indivara.library.services.WishlistService;
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
public class WishlistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WishlistService wishlistService;

    String readerKey = "cmVhZGVyOnJlYWRlcg==";
    String librarianKey = "bGlicmFyaWFuOmxpYnJhcmlhbg==";
    String adminKey = "YWRtaW46YWRtaW4=";

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void findAllWishlistSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/wishlists")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Wishlist Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findWishlistSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/wishlists/{id}", 4)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Wishlist Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findWishlistFailedNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/wishlists/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }

    @Test
    public void findWishlistFailedUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/wishlists/{id}", 4)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void findWishlistByReaderSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/readers/{id}/wishlists", 2)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Wishlist Found", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void findWishlistByReaderFailedReaderNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/readers/{id}/wishlists", 0)
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
    public void createWishlistSuccessTest() throws Exception {
        WishlistDTO wishlist = WishlistDTO.builder()
                .bookId(9)
                .readerId(5)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/wishlists")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey)
                        .content(objectMapper.writeValueAsString(wishlist)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(res -> {
                    ResponseBody<Wishlist> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Wishlist>>() {}
                    );
                    Assertions.assertEquals("Wishlist Created", responseBody.getMessage());
                    Assertions.assertNotNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());

                    wishlistService.deleteWishlist(responseBody.getData().getWishlistId());
                })
        ;
    }

    @Test
    public void createWishlistFailedUnauthorizedTest() throws Exception {
        WishlistDTO wishlist = WishlistDTO.builder()
                .bookId(9)
                .readerId(5)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/wishlists")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wishlist)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void createWishlistFailedNoRequestBodyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/wishlists")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }

    @Test
    public void createWishlistFailedBookNotFoundTest() throws Exception {
        WishlistDTO wishlist = WishlistDTO.builder()
                .bookId(0)
                .readerId(5)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/wishlists")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey)
                        .content(objectMapper.writeValueAsString(wishlist)))
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
    public void createWishlistFailedReaderNotFoundTest() throws Exception {
        WishlistDTO wishlist = WishlistDTO.builder()
                .bookId(9)
                .readerId(0)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/wishlists")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey)
                        .content(objectMapper.writeValueAsString(wishlist)))
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
    public void createWishlistFailedSameWishlistTest() throws Exception {
        WishlistDTO wishlist = WishlistDTO.builder()
                .bookId(7)
                .readerId(3)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/wishlists")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey)
                        .content(objectMapper.writeValueAsString(wishlist)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Wishlist for this book and reader have had set", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void deleteWishlistSuccessTest() throws Exception {
        WishlistDTO wishlist = WishlistDTO.builder()
                .bookId(6)
                .readerId(3)
                .build();
        Wishlist createdWishlist = wishlistService.saveWishlist(wishlist);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/wishlists/{id}", createdWishlist.getWishlistId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey)
                        .content(objectMapper.writeValueAsString(wishlist)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(res -> {
                    ResponseBody<Object> responseBody = objectMapper.readValue(
                            res.getResponse().getContentAsString(),
                            new TypeReference<ResponseBody<Object>>() {}
                    );
                    Assertions.assertEquals("Wishlist Deleted", responseBody.getMessage());
                    Assertions.assertNull(responseBody.getData());
                    Assertions.assertNull(responseBody.getErrors());
                })
        ;
    }

    @Test
    public void deleteWishlistFailedUnauthorizedTest() throws Exception {
        WishlistDTO wishlist = WishlistDTO.builder()
                .bookId(6)
                .readerId(3)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/wishlists/{id}", 4)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wishlist)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;
    }

    @Test
    public void deleteWishlistFailedNoFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/wishlists/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + readerKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }
}
