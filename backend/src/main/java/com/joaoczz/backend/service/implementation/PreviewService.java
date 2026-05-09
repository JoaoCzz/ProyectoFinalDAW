package com.joaoczz.backend.service.implementation;

import com.joaoczz.backend.presentation.dto.preview.PreviewResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Duration;

@Service
public class PreviewService {

    private static final Logger logger = LoggerFactory.getLogger(PreviewService.class);

    public PreviewResponse fetchPreview(String url) {
        try {
            if (url == null || url.trim().isEmpty()) {
                return new PreviewResponse();
            }

            String originalUrl = url;
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }

            logger.info("Fetching preview for URL: {}", url);

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (compatible; PreviewBot/1.0)")
                    .timeout((int) Duration.ofSeconds(10).toMillis())
                    .get();

            String title = firstMeta(doc, "og:title");
            if (title == null || title.isEmpty()) title = doc.title();

            String description = firstMeta(doc, "og:description");
            if (description == null || description.isEmpty()) description = firstMeta(doc, "description");

            String image = firstMeta(doc, "og:image");
            if (image == null || image.isEmpty()) {
                Element icon = doc.selectFirst("link[rel~=(?i)^(icon|shortcut icon)$]");
                if (icon != null) image = icon.attr("href");
            }

            // Resolve relative image URLs
            try {
                URI uri = URI.create(url);
                if (image != null && image.startsWith("/")) {
                    image = uri.getScheme() + "://" + uri.getHost() + image;
                }
            } catch (Exception e) {
                // ignore
            }

            String siteName = firstMeta(doc, "og:site_name");

            String embedHtml = null;
            String audioPreviewUrl = null;
            String provider = null;

            // Provider-specific quick handling
            try {
                URI uri = URI.create(url);
                String host = uri.getHost() == null ? "" : uri.getHost().toLowerCase();
                if (host.contains("spotify.com") && url.contains("/track/")) {
                    provider = "spotify";
                    String[] parts = url.split("/track/");
                    if (parts.length > 1) {
                        String id = parts[1].split("[?&]")[0];
                        embedHtml = "<iframe src=\"https://open.spotify.com/embed/track/" + id + "\" width=\"300\" height=\"80\" frameborder=\"0\" allow=\"encrypted-media\"></iframe>";
                    }
                }
            } catch (Exception ex) {
                logger.warn("Error parsing provider for url {}: {}", url, ex.getMessage());
            }

            PreviewResponse resp = new PreviewResponse();
            resp.setTitle(title);
            resp.setDescription(description);
            resp.setImage(image);
            resp.setEmbedHtml(embedHtml);
            resp.setAudioPreviewUrl(audioPreviewUrl);
            resp.setSiteName(siteName);
            resp.setUrl(originalUrl);
            resp.setProvider(provider);

            return resp;
        } catch (Exception ex) {
            logger.error("Failed to fetch preview for url {}: {}", url, ex.getMessage());
            return new PreviewResponse(null, null, null, null, null, null, url, null);
        }
    }

    private String firstMeta(Document doc, String key) {
        Element el = doc.selectFirst("meta[property=\"" + key + "\"]");
        if (el != null) return el.attr("content");
        el = doc.selectFirst("meta[name=\"" + key + "\"]");
        if (el != null) return el.attr("content");
        return null;
    }
}
