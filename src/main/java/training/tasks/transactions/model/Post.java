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
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity(name = "post")
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String content;
    @ManyToOne
    @NotNull
    private User author;
    @NotNull
    private Integer likes;
    @OneToMany
    private List<Comment> comments;
    @NotNull
    private LocalDateTime posted;

    public Post(String content, User author, Integer likes, LocalDateTime posted) {
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.posted = posted;
    }

    public static class Builder {
        private Post post;
        private PostDto dto;

        private Builder(PostDto dto) {
            post = new Post();
            this.dto = dto;
        }

        public static Builder takeFrom(PostDto dto) {
            return new Builder(dto);
        }

        public Builder id() {
            if (dto.getId() != null) {
                post.setId(dto.getId());
            }
            return this;
        }

        public Builder content() {
            if (dto.getContent() != null) {
                post.setContent(dto.getContent());
            }
            return this;
        }

        public Builder author() {
            if (dto.getAuthor() != null) {
                post.setAuthor(User.Builder.takeFrom(dto.getAuthor())
                        .id()
                        .name()
                        .email()
                        .andTurnToUser());
            }
            return this;
        }

        public Builder likes() {
            if (dto.getLikes() != null) {
                post.setLikes(dto.getLikes());
            }
            return this;
        }

        public Builder comments() {
            if (dto.getComments() != null) {
                post.setComments(dto.getComments().stream()
                        .map(dto -> Comment.Builder.takeFrom(dto)
                                .id()
                                .content()
                                .author()
                                .likes()
                                .commented()
                                .andTurnToComment())
                        .collect(Collectors.toList()));
            }
            return this;
        }

        public Builder posted() {
            if (dto.getPosted() != null) {
                post.setPosted(dto.getPosted());
            }
            return this;
        }

        public Builder all() {
            return id()
                    .content()
                    .author()
                    .likes()
                    .comments()
                    .posted();
        }

        public Post andTurnToPost() {
            return post;
        }
    }

}
