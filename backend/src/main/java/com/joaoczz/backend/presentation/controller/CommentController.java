package com.joaoczz.backend.presentation.controller;

import com.joaoczz.backend.presentation.dto.comment.CommentRequest;
import com.joaoczz.backend.presentation.dto.comment.CommentResponse;
import com.joaoczz.backend.service.interfaces.ICommentService;
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

@RestController
@RequestMapping("/comments")
@Tag(name = "Comments", description = "Gestión de comentarios")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @PostMapping
    @Operation(summary = "Crear comentario", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CommentResponse> create(
            @RequestBody @Valid CommentRequest request,
            Authentication authentication) {
        return new ResponseEntity<>(commentService.create(request, authentication.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "Comentarios de una publicación (paginado)")
    public ResponseEntity<Page<CommentResponse>> getByPost(
            @PathVariable Long postId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(commentService.getByPost(postId, pageable));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar comentario propio", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication) {
        commentService.delete(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
