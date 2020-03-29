package com.lim.book.springboot.service;

import com.lim.book.springboot.domain.posts.Posts;
import com.lim.book.springboot.domain.posts.PostsRepository;
import com.lim.book.springboot.web.dto.PostsListResponseDto;
import com.lim.book.springboot.web.dto.PostsResponseDto;
import com.lim.book.springboot.web.dto.PostsSaveRequestDto;
import com.lim.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDTO) {
        Posts posts=postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자 없습니다. id="+id));

        posts.update(requestDTO.getTitle(), requestDTO.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity=postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts=postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+id));

        postsRepository.delete(posts);
    }
}
