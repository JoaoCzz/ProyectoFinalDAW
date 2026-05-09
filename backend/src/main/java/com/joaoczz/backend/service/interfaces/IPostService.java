package com.joaoczz.backend.service.interfaces;

import com.joaoczz.backend.presentation.dto.post.PostRequest;
import com.joaoczz.backend.presentation.dto.post.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPostService {
    PostResponse create(PostRequest request, String username);
    PostResponse getById(Long id, String username);
    Page<PostResponse> getAll(Pageable pageable, String username);
    Page<PostResponse> search(String query, Pageable pageable, String username);
    List<PostResponse> getByUser(Long userId, String username);
    List<PostResponse> getByArtist(Long artistId, String username);
    Page<PostResponse> getByGenre(Long genreId, Pageable pageable, String username);
    PostResponse update(Long id, PostRequest request, String username);
    void delete(Long id, String username);
}
