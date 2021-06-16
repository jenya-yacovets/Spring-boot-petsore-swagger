package by.tms.swager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private long petId;

    @NotEmpty
    private int quantity;

    @NotEmpty
    private LocalDateTime shipDate;

    @NotEmpty
    private OrderStatus status;

    @NotEmpty
    private boolean complete;
}
