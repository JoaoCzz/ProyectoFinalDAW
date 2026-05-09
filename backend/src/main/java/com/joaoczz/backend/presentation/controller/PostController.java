package com.joaoczz.backend.presentation.controller;

import com.joaoczz.backend.presentation.dto.post.PostRequest;
import com.joaoczz.backend.presentation.dto.post.PostResponse;
import com.joaoczz.backend.service.interfaces.IPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@Tag(name = "Posts", description = "Gestión de publicaciones musicales")
public class PostController {

    @Autowired
    private IPostService postService;

    @PostMapping
    @Operation(summary = "Crear publicación", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<PostResponse> create(
            @RequestBody @Valid PostRequest request,
            Authentication authentication) {
        return new ResponseEntity<>(postService.create(request, authentication.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener publicación por ID")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(postService.getById(id, authentication != null ? authentication.getName() : null));
    }

    @GetMapping
    @Operation(summary = "Listar todas las publicaciones (paginado)")
    public ResponseEntity<Page<PostResponse>> getAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Authentication authentication) {
        return ResponseEntity.ok(postService.getAll(pageable, authentication != null ? authentication.getName() : null));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar publicaciones por título, descripción, artista o género")
    public ResponseEntity<Page<PostResponse>> search(
            @RequestParam String q,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Authentication authentication) {
        return ResponseEntity.ok(postService.search(q, pageable, authentication != null ? authentication.getName() : null));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Publicaciones de un usuario")
    public ResponseEntity<List<PostResponse>> getByUser(@PathVariable Long userId, Authentication authentication) {
        return ResponseEntity.ok(postService.getByUser(userId, authentication != null ? authentication.getName() : null));
    }

    @GetMapping("/artist/{artistId}")
    @Operation(summary = "Publicaciones de un artista")
    public ResponseEntity<List<PostResponse>> getByArtist(@PathVariable Long artistId, Authentication authentication) {
        return ResponseEntity.ok(postService.getByArtist(artistId, authentication != null ? authentication.getName() : null));
    }

    @GetMapping("/genre/{genreId}")
    @Operation(summary = "Publicaciones de un género")
    public ResponseEntity<Page<PostResponse>> getByGenre(
            @PathVariable Long genreId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Authentication authentication) {
        return ResponseEntity.ok(postService.getByGenre(genreId, pageable, authentication != null ? authentication.getName() : null));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar publicación propia", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<PostResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid PostRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(postService.update(id, request, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar publicación propia", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication) {
        postService.delete(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
