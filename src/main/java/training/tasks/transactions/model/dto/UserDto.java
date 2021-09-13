package training.tasks.transactions.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import training.tasks.transactions.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private String telegram;
    private String otherContacts;
    private List<UserDto> subscriptions;

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserDto(Long id, String name, String email, String bio, String telegram, String otherContacts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.telegram = telegram;
        this.otherContacts = otherContacts;
    }

    public static class Builder {
        private User user;
        private UserDto dto;

        private Builder(User user) {
            this.user = user;
            dto = new UserDto();
        }

        public static Builder takeFrom(User user) {
            return new Builder(user);
        }

        public Builder id() {
            if (user.getId() != null) {
                dto.setId(user.getId());
            }
            return this;
        }

        public Builder name() {
            if (user.getName() != null) {
                dto.setName(user.getName());
            }
            return this;
        }

        public Builder email() {
            if (user.getEmail() != null) {
                dto.setEmail(user.getEmail());
            }
            return this;
        }

        public Builder bio() {
            if (user.getBio() != null) {
                dto.setBio(user.getBio());
            }
            return this;
        }

        public Builder telegram() {
            if (user.getTelegram() != null) {
                dto.setTelegram(user.getTelegram());
            }
            return this;
        }

        public Builder otherContacts() {
            if (user.getOtherContacts() != null) {
                dto.setOtherContacts(user.getOtherContacts());
            }
            return this;
        }

        public Builder subscriptions() {
            if (user.getSubscriptions() != null) {
                dto.setSubscriptions(user.getSubscriptions().stream()
                        .map(user -> UserDto.Builder.takeFrom(user)
                                .id()
                                .name()
                                .email()
                                .andTurnToDto())
                        .collect(Collectors.toList()));
            }
            return this;
        }

        public Builder all() {
            return id()
                    .name()
                    .email()
                    .bio()
                    .telegram()
                    .otherContacts()
                    .subscriptions();
        }

        public UserDto andTurnToDto() {
            return dto;
        }
    }
}
