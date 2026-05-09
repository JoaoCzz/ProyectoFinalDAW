package com.joaoczz.backend.presentation.controller;

import com.joaoczz.backend.presentation.dto.preview.PreviewResponse;
import com.joaoczz.backend.service.implementation.PreviewService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreviewController {

    @Autowired
    private PreviewService previewService;

    @GetMapping("/api/preview")
    @Operation(summary = "Obtener previsualización de una URL (Open Graph)")
    public ResponseEntity<PreviewResponse> preview(@RequestParam String url) {
        if (url == null || url.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        PreviewResponse response = previewService.fetchPreview(url);
        return ResponseEntity.ok(response);
    }
}
