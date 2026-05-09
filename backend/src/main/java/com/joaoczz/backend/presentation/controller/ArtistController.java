package com.joaoczz.backend.presentation.controller;

import com.joaoczz.backend.presentation.dto.artist.ArtistRequest;
import com.joaoczz.backend.presentation.dto.artist.ArtistResponse;
import com.joaoczz.backend.service.interfaces.IArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private IArtistService artistService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArtistResponse> create(@RequestBody @Valid ArtistRequest request) {
        return new ResponseEntity<>(artistService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getAll() {
        return ResponseEntity.ok(artistService.getAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArtistResponse> update(@PathVariable Long id, @RequestBody @Valid ArtistRequest request) {
        return ResponseEntity.ok(artistService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        artistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
