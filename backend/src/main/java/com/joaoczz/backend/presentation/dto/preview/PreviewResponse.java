package com.joaoczz.backend.presentation.dto.preview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreviewResponse {
    private String title;
    private String description;
    private String image;
    private String embedHtml;
    private String audioPreviewUrl;
    private String siteName;
    private String url;
    private String provider;
}
