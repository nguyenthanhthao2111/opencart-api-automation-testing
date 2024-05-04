package api.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
    private String store;
    private String warning;
    private String option;
    private String email;
    private String firstname;
    private String lastname;
    private String telephone;
    private String address_1;
    private String city;
}
