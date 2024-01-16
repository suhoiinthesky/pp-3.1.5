package com.Task31533.DTO;

import com.Task31533.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$", message = "Only English characters are allowed")
    private String username;
    @Pattern(regexp = "^[A-Za-z]*$", message = "Only English characters are allowed")
    private String lastName;
    @Min(value = 0, message = "Age must be greater than 0")
    private Integer age;
    @NotEmpty
    private String email;
    @Size(min = 3, message = "Password should be min 4 characters")
    private String password;

    private Set<Role> roles = new HashSet<>();

    @Override
    public String toString() {
        return "UserDTO{" +
               "id=" + id +
               ", name='" + username + '\'' +
               ", lastName='" + lastName + '\'' +
               ", age=" + age +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               ", roles=" + roles.stream().map(Role::getName).toString().substring(5) +
               '}';
    }
}
