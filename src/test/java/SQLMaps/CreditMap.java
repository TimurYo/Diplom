package SQLMaps;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreditMap {
    String id;
    String bankId;
    String created;
    String status;
}
