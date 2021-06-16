package by.tms.swager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @NotEmpty
    private Category category;

    @NotEmpty
    @NotBlank
    private String name;

    @ManyToMany
    @NotEmpty
    private List<Tag> tags;

    @NotEmpty
    private StatusPet status;
}
