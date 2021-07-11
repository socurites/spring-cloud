package socurites.cloud.web.dto;

import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {
  private String orderId;
  private String productId;
  private Integer qty;
  private Integer unitPrice;
  private Integer totalPrice;
  private LocalDateTime createdDate;
}
