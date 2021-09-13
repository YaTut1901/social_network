package training.tasks.transactions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training.tasks.transactions.model.dto.UserDto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 20)
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    private String bio;
    private String telegram;
    private String otherContacts;
    @ManyToMany
    private List<User> subscriptions;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, String bio, String telegram, String otherContacts, List<User> subscriptions) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.telegram = telegram;
        this.otherContacts = otherContacts;
        this.subscriptions = subscriptions;
    }

    public static class Builder {
        private User user;
        private UserDto dto;

        private Builder(UserDto dto) {
            this.dto = dto;
            user = new User();
        }

        public static Builder takeFrom(UserDto dto) {
            return new Builder(dto);
        }

        public Builder id() {
            if (dto.getId() != null) {
                user.setId(dto.getId());
            }
            return this;
        }

        public Builder name() {
            if (dto.getName() != null) {
                user.setName(dto.getName());
            }
            return this;
        }

        public Builder email() {
            if (dto.getEmail() != null) {
                user.setEmail(dto.getEmail());
            }
            return this;
        }

        public Builder bio() {
            if (dto.getBio() != null) {
                user.setBio(dto.getBio());
            }
            return this;
        }

        public Builder telegram() {
            if (dto.getTelegram() != null) {
                user.setTelegram(dto.getTelegram());
            }
            return this;
        }

        public Builder otherContacts() {
            if (dto.getOtherContacts() != null) {
                user.setOtherContacts(dto.getOtherContacts());
            }
            return this;
        }

        public Builder subscriptions() {
            if (dto.getSubscriptions() != null) {
                user.setSubscriptions(dto.getSubscriptions().stream()
                        .map(user -> User.Builder.takeFrom(user)
                                .id()
                                .name()
                                .email()
                                .andTurnToUser())
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

        public User andTurnToUser() {
            return user;
        }
    }
}
