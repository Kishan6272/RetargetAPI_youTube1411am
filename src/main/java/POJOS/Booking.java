package POJOS;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;

    private BookingDates bookingDates;
    private String additionalneeds;
}
