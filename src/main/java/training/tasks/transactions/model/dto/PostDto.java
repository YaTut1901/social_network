package training.tasks.transactions.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training.tasks.transactions.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {
    private Long id;
    private String content;
    private UserDto author;
    private Integer likes;
    private List<CommentDto> comments;
    private LocalDateTime posted;

    //TODO: Test for builders
    public static class Builder {
        private Post post;
        private PostDto dto;

        private Builder(Post post) {
            this.post = post;
            dto = new PostDto();
        }

        public static Builder takeFrom(Post post) {
            return new Builder(post);
        }

        public Builder id() {
            if (post.getId() != null) {
                dto.setId(post.getId());
            }
            return this;
        }

        public Builder content() {
            if (post.getContent() != null) {
                dto.setContent(post.getContent());
            }
            return this;
        }

        public Builder author() {
            if (post.getAuthor() != null) {
                dto.setAuthor(UserDto.Builder.takeFrom(post.getAuthor())
                        .id()
                        .name()
                        .email()
                        .andTurnToDto());
            }
            return this;
        }

        public Builder likes() {
            if (post.getLikes() != null) {
                dto.setLikes(post.getLikes());
            }
            return this;
        }

        public Builder comments() {
            if (post.getComments() != null) {
                dto.setComments(post.getComments().stream()
                        .map(comment -> CommentDto.Builder.takeFrom(comment)
                                .id()
                                .content()
                                .author()
                                .likes()
                                .commented()
                                .andTurnToDto())
                        .collect(Collectors.toList()));
            }
            return this;
        }

        public Builder posted() {
            if (post.getPosted() != null) {
                dto.setPosted(post.getPosted());
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

        public PostDto andTurnToDto() {
            return dto;
        }
    }
}
