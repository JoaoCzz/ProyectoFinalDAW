package com.joaoczz.backend.service.implementation;

import com.joaoczz.backend.persistence.entity.ArtistEntity;
import com.joaoczz.backend.persistence.entity.GenreEntity;
import com.joaoczz.backend.persistence.entity.PostEntity;
import com.joaoczz.backend.persistence.entity.UserEntity;
import com.joaoczz.backend.persistence.repository.ArtistRepository;
import com.joaoczz.backend.persistence.repository.GenreRepository;
import com.joaoczz.backend.persistence.repository.PostRepository;
import com.joaoczz.backend.persistence.repository.UserRepository;
import com.joaoczz.backend.presentation.advice.ResourceNotFoundException;
import com.joaoczz.backend.presentation.dto.post.PostRequest;
import com.joaoczz.backend.presentation.dto.post.PostResponse;
import com.joaoczz.backend.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public PostResponse create(PostRequest request, String username) {
        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        ArtistEntity artist = null;
        if (request.artistId() != null && request.artistId() > 0) {
            artist = artistRepository.findById(request.artistId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con id: " + request.artistId()));
        }
        
        GenreEntity genre = genreRepository.findById(request.genreId())
                .orElseThrow(() -> new ResourceNotFoundException("Género no encontrado con id: " + request.genreId()));

        PostEntity post = PostEntity.builder()
                .title(request.title())
                .description(request.description())
                .musicUrl(request.musicUrl())
                .isSelfPromotion(request.isSelfPromotion())
                .user(user)
                .artist(artist)
                .genre(genre)
                .createdAt(LocalDateTime.now())
                .build();

        return toResponse(postRepository.save(post));
    }

    @Override
    public PostResponse getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    public Page<PostResponse> getAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public Page<PostResponse> search(String query, Pageable pageable) {
        return postRepository.search(query, pageable).map(this::toResponse);
    }

    @Override
    public List<PostResponse> getByUser(Long userId) {
        return postRepository.findByUserId(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getByArtist(Long artistId) {
        return postRepository.findByArtistId(artistId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getByGenre(Long genreId) {
        return postRepository.findByGenreId(genreId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public PostResponse update(Long id, PostRequest request, String username) {
        PostEntity post = findOrThrow(id);
        if (!post.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("No tienes permiso para editar esta publicación");
        }
        
        ArtistEntity artist = null;
        if (request.artistId() != null && request.artistId() > 0) {
            artist = artistRepository.findById(request.artistId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con id: " + request.artistId()));
        }
        
        GenreEntity genre = genreRepository.findById(request.genreId())
                .orElseThrow(() -> new ResourceNotFoundException("Género no encontrado con id: " + request.genreId()));

        post.setTitle(request.title());
        post.setDescription(request.description());
        post.setMusicUrl(request.musicUrl());
        post.setSelfPromotion(request.isSelfPromotion());
        post.setArtist(artist);
        post.setGenre(genre);
        return toResponse(postRepository.save(post));
    }

    @Override
    public void delete(Long id, String username) {
        PostEntity post = findOrThrow(id);
        if (!post.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("No tienes permiso para eliminar esta publicación");
        }
        postRepository.deleteById(id);
    }

    private PostEntity findOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicación no encontrada con id: " + id));
    }

    private PostResponse toResponse(PostEntity p) {
        if (p == null) return null;
        
        String artistName = "Artista Independiente";
        if (p.getArtist() != null) {
            try {
                artistName = p.getArtist().getName();
            } catch (Exception e) {
                // Si hay error de lazy loading o similar, fallback
            }
        }

        int likesCount = 0;
        try {
            likesCount = p.getLikes() != null ? p.getLikes().size() : 0;
        } catch (Exception e) {}

        int commentsCount = 0;
        try {
            commentsCount = p.getComments() != null ? p.getComments().size() : 0;
        } catch (Exception e) {}

        return new PostResponse(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getMusicUrl(),
                p.isSelfPromotion(),
                p.getCreatedAt(),
                p.getUser() != null ? p.getUser().getId() : null,
                p.getUser() != null ? p.getUser().getUsername() : "Usuario desconocido",
                p.getArtist() != null ? p.getArtist().getId() : null,
                artistName,
                p.getGenre() != null ? p.getGenre().getId() : null,
                p.getGenre() != null ? p.getGenre().getName() : "Sin Género",
                likesCount,
                commentsCount
        );
    }
}
