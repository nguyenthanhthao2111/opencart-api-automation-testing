package api.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Product {
    private List<ProductDetails> products;
    private List<Voucher> vouchers;
    private List<Cost> totals;
}
