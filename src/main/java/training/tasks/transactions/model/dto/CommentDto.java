package training.tasks.transactions.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training.tasks.transactions.model.Comment;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {
    private Long id;
    private String content;
    private UserDto author;
    private PostDto post;
    private Integer likes;
    private LocalDateTime commented;

    public static class Builder {
        private Comment comment;
        private CommentDto dto;

        private Builder(Comment comment) {
            this.comment = comment;
            dto = new CommentDto();
        }

        public static Builder takeFrom(Comment comment) {
            return new Builder(comment);
        }

        public Builder id() {
            if (comment.getId() != null) {
                dto.setId(comment.getId());
            }
            return this;
        }

        public Builder content() {
            if (comment.getContent() != null) {
                dto.setContent(comment.getContent());
            }
            return this;
        }

        public Builder author() {
            if (comment.getAuthor() != null) {
                dto.setAuthor(UserDto.Builder.takeFrom(comment.getAuthor())
                        .id()
                        .name()
                        .email()
                        .andTurnToDto());
            }
            return this;
        }

        public Builder post() {
            if (comment.getPost() != null) {
                dto.setPost(PostDto.Builder.takeFrom(comment.getPost())
                        .id()
                        .andTurnToDto());
            }
            return this;
        }

        public Builder likes() {
            if (comment.getLikes() != null) {
                dto.setLikes(comment.getLikes());
            }
            return this;
        }

        public Builder commented() {
            if (comment.getCommented() != null) {
                dto.setCommented(comment.getCommented());
            }
            return this;
        }

        public Builder all() {
            return id()
                    .content()
                    .author()
                    .post()
                    .likes()
                    .commented();
        }

        public CommentDto andTurnToDto() {
            return dto;
        }
    }
}
