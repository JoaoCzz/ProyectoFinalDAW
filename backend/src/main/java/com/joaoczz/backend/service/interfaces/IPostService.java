package com.joaoczz.backend.service.interfaces;

import com.joaoczz.backend.presentation.dto.post.PostRequest;
import com.joaoczz.backend.presentation.dto.post.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPostService {
    PostResponse create(PostRequest request, String username);
    PostResponse getById(Long id);
    Page<PostResponse> getAll(Pageable pageable);
    Page<PostResponse> search(String query, Pageable pageable);
    List<PostResponse> getByUser(Long userId);
    List<PostResponse> getByArtist(Long artistId);
    List<PostResponse> getByGenre(Long genreId);
    PostResponse update(Long id, PostRequest request, String username);
    void delete(Long id, String username);
}
