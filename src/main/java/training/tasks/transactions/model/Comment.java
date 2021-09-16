package training.tasks.transactions.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import training.tasks.transactions.model.dto.CommentDto;
import training.tasks.transactions.model.dto.PostDto;
import training.tasks.transactions.model.dto.UserDto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity(name = "comment")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String content;
    @ManyToOne
    @NotNull
    private User author;
    @ManyToOne
    @NotNull
    private Post post;
    @NotNull
    private Integer likes;
    @NotNull
    private LocalDateTime commented;

    public Comment(String content, User author, Post post, Integer likes, LocalDateTime commented) {
        this.content = content;
        this.author = author;
        this.post = post;
        this.likes = likes;
        this.commented = commented;
    }

    public static class Builder {
        private Comment comment;
        private CommentDto dto;

        private Builder(CommentDto dto) {
            this.dto = dto;
            comment = new Comment();
        }

        public static Builder takeFrom(CommentDto dto) {
            return new Builder(dto);
        }

        public Builder id() {
            if (dto.getId() != null) {
                comment.setId(dto.getId());
            }
            return this;
        }

        public Builder content() {
            if (dto.getContent() != null) {
                comment.setContent(dto.getContent());
            }
            return this;
        }

        public Builder author() {
            if (dto.getAuthor() != null) {
                comment.setAuthor(User.Builder.takeFrom(dto.getAuthor())
                        .id()
                        .name()
                        .email()
                        .andTurnToUser());
            }
            return this;
        }

        public Builder post() {
            if (dto.getPost() != null) {
                comment.setPost(Post.Builder.takeFrom(dto.getPost())
                        .id()
                        .andTurnToPost());
            }
            return this;
        }

        public Builder likes() {
            if (dto.getLikes() != null) {
                comment.setLikes(dto.getLikes());
            }
            return this;
        }

        public Builder commented() {
            if (dto.getCommented() != null) {
                comment.setCommented(dto.getCommented());
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

        public Comment andTurnToComment() {
            return comment;
        }
    }
}
