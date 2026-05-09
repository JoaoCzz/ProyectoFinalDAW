package com.joaoczz.backend.service.implementation;

import com.joaoczz.backend.persistence.entity.ArtistEntity;
import com.joaoczz.backend.persistence.entity.GenreEntity;
import com.joaoczz.backend.persistence.entity.PostEntity;
import com.joaoczz.backend.persistence.entity.UserEntity;
import com.joaoczz.backend.persistence.repository.ArtistRepository;
import com.joaoczz.backend.persistence.repository.GenreRepository;
import com.joaoczz.backend.persistence.repository.LikePostRepository;
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
    @Autowired
    private LikePostRepository likePostRepository;

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

        return toResponse(postRepository.save(post), user.getId());
    }

    @Override
    public PostResponse getById(Long id, String username) {
        return toResponse(findOrThrow(id), resolveCurrentUserId(username));
    }

    @Override
    public Page<PostResponse> getAll(Pageable pageable, String username) {
        Long currentUserId = resolveCurrentUserId(username);
        return postRepository.findAll(pageable).map(post -> toResponse(post, currentUserId));
    }

    @Override
    public Page<PostResponse> search(String query, Pageable pageable, String username) {
        Long currentUserId = resolveCurrentUserId(username);
        return postRepository.search(query, pageable).map(post -> toResponse(post, currentUserId));
    }

    @Override
    public List<PostResponse> getByUser(Long userId, String username) {
        Long currentUserId = resolveCurrentUserId(username);
        return postRepository.findByUserId(userId).stream()
                .map(post -> toResponse(post, currentUserId))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getByArtist(Long artistId, String username) {
        Long currentUserId = resolveCurrentUserId(username);
        return postRepository.findByArtistId(artistId).stream()
                .map(post -> toResponse(post, currentUserId))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PostResponse> getByGenre(Long genreId, Pageable pageable, String username) {
        Long currentUserId = resolveCurrentUserId(username);
        return postRepository.findByGenreId(genreId, pageable)
                .map(post -> toResponse(post, currentUserId));
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
        return toResponse(postRepository.save(post), post.getUser().getId());
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

    private PostResponse toResponse(PostEntity post, Long currentUserId) {
        if (post == null) {
            return null;
        }

        String artistName = "Artista Independiente";
        if (post.getArtist() != null) {
            try {
                artistName = post.getArtist().getName();
            } catch (Exception ignored) {
            }
        }

        int likesCount = 0;
        try {
            likesCount = post.getLikes() != null ? post.getLikes().size() : 0;
        } catch (Exception ignored) {
        }

        int commentsCount = 0;
        try {
            commentsCount = post.getComments() != null ? post.getComments().size() : 0;
        } catch (Exception ignored) {
        }

        boolean likedByCurrentUser = false;
        if (currentUserId != null && post.getId() != null) {
            likedByCurrentUser = likePostRepository.existsByUserIdAndPostId(currentUserId, post.getId());
        }

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getMusicUrl(),
                post.isSelfPromotion(),
                post.getCreatedAt(),
                post.getUser() != null ? post.getUser().getId() : null,
                post.getUser() != null ? post.getUser().getUsername() : "Usuario desconocido",
                post.getArtist() != null ? post.getArtist().getId() : null,
                artistName,
                post.getGenre() != null ? post.getGenre().getId() : null,
                post.getGenre() != null ? post.getGenre().getName() : "Sin Género",
                likesCount,
                commentsCount,
                likedByCurrentUser
        );
    }

    private Long resolveCurrentUserId(String username) {
        if (username == null || username.isBlank()) {
            return null;
        }

        return userRepository.findUserEntityByUsername(username)
                .map(UserEntity::getId)
                .orElse(null);
    }
}
