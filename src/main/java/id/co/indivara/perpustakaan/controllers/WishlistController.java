package id.co.indivara.perpustakaan.controllers;

import id.co.indivara.perpustakaan.entities.Wishlist;
import id.co.indivara.perpustakaan.entities.ResponseBody;
import id.co.indivara.perpustakaan.entities.WishlistDTO;
import id.co.indivara.perpustakaan.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/wishlists")
    ResponseEntity<ResponseBody<Object>> findAllWishlist() {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Wishlist Found")
                        .data(wishlistService.findAllWishlist())
                        .build()
        );
    }

    @GetMapping("/wishlists/{id}")
    ResponseEntity<ResponseBody<Object>> findWishlistById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message(("Wishlist Found"))
                        .data(wishlistService.findWishlistById(id))
                        .build()
        );
    }

    @PostMapping("/wishlists")
    ResponseEntity<ResponseBody<Object>> saveWishlist(@Nullable @RequestBody WishlistDTO wishlistDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseBody.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Wishlist Created")
                        .data(wishlistService.saveWishlist(wishlistDTO))
                        .build()
        );
    }

    @PutMapping("/wishlists/{id}")
    ResponseEntity<ResponseBody<Object>> updateWishlist(@PathVariable(name = "id") Integer id, @Nullable @RequestBody Wishlist wishlist) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ResponseBody.builder()
                        .status(HttpStatus.ACCEPTED.value())
                        .message(("Wishlist Updated"))
                        .data(wishlistService.updateWishlist(id, wishlist))
                        .build()
        );
    }

    @DeleteMapping("/wishlists/{id}")
    ResponseEntity<ResponseBody<Object>> deleteBook(@PathVariable(name = "id") Integer id) {
        wishlistService.deleteWishlist(id);
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message(("Wishlist Deleted"))
                        .build()
        );
    }
}
