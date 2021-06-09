package by.tms.swager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private long id;
    private long petId;
    private int quantity;
    private LocalDateTime shipDate;
    private OrderStatus status;
    private boolean complete;
}
