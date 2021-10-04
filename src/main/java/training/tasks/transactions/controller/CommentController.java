package training.tasks.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.tasks.transactions.model.Comment;
import training.tasks.transactions.model.dto.CommentDto;
import training.tasks.transactions.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService service;

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> get(@PathVariable Long id) {
        Comment comment = service.getOne(id);
        return new ResponseEntity<>(CommentDto.Builder.takeFrom(comment)
                .id()
                .content()
                .author()
                .post()
                .likes()
                .andTurnToDto(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentDto> save(@RequestBody CommentDto dto) {
        Comment comment = service.save(Comment.Builder.takeFrom(dto)
                .all()
                .andTurnToComment());
        return new ResponseEntity<>(CommentDto.Builder.takeFrom(comment)
                .all()
                .andTurnToDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
