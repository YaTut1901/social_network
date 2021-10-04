package training.tasks.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.tasks.transactions.model.Post;
import training.tasks.transactions.model.dto.PostDto;
import training.tasks.transactions.service.PostService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("posts")
public class PostController {
    @Autowired
    private PostService service;

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> get(@PathVariable Long id) {
        Post post = service.getOne(id);
        return new ResponseEntity<>(PostDto.Builder.takeFrom(post)
                .id()
                .content()
                .author()
                .comments()
                .posted()
                .likes()
                .andTurnToDto(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAll() {
        return new ResponseEntity<>(service.getAll().stream()
                .map(post -> PostDto.Builder.takeFrom(post)
                        .all()
                        .andTurnToDto())
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDto> save(@RequestBody PostDto dto) {
        Post post = service.save(Post.Builder.takeFrom(dto)
                .all()
                .andTurnToPost());
        return new ResponseEntity(PostDto.Builder.takeFrom(post)
                .all()
                .andTurnToDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
