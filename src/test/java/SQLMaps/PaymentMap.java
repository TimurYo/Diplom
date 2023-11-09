package SQLMaps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMap {
    String id;
    String amount;
    String created;
    String status;
    String transaction_id;
}

