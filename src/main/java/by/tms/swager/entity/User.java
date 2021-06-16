package by.tms.swager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true)
    @NotEmpty
    @Size(min = 4, max = 20)
    @NotBlank
    private String username;

    @NotEmpty
    @NotBlank
    private String firstName;

    @NotEmpty
    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotEmpty
    @NotBlank
    @Size(min = 7, max = 35)
    private String password;

    @NotEmpty
    @NotBlank
    private String phone;

    @NotEmpty
    private int userStatus;
}
