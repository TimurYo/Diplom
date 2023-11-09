package SQLMaps;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderMap {
    String id;
    String created;
    String creditId;
    String paymentId;
}
