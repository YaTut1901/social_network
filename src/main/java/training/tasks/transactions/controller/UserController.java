package training.tasks.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.tasks.transactions.model.User;
import training.tasks.transactions.util.InputSanitizator;
import training.tasks.transactions.model.dto.UserDto;
import training.tasks.transactions.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private InputSanitizator sanitizator;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        User user = service.getOne(id);
        return new ResponseEntity<>(UserDto.Builder.takeFrom(user)
                .id()
                .name()
                .email()
                .andTurnToDto(), HttpStatus.OK);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<UserDto> getDetails(@PathVariable Long id) {
        User user = service.getOne(id);
        return new ResponseEntity<>(UserDto.Builder.takeFrom(user)
                .all()
                .andTurnToDto(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return new ResponseEntity<>(service.getAll().stream()
                .map(user -> UserDto.Builder.takeFrom(user)
                        .id()
                        .name()
                        .email()
                        .andTurnToDto())
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody UserDto dto) {
        sanitizator.checkUserInfo(dto);
        User user = service.save(User.Builder.takeFrom(dto)
                .all()
                .andTurnToUser());
        return new ResponseEntity(UserDto.Builder.takeFrom(user)
                .all()
                .andTurnToDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{followerId}/follow/{userId}")
    public ResponseEntity<UserDto> follow(@PathVariable Long followerId, @PathVariable Long userId) {
        return new ResponseEntity(UserDto.Builder.takeFrom(service.followUser(followerId, userId))
                .all()
                .andTurnToDto(), HttpStatus.OK);
    }
}
