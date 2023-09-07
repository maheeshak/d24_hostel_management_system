package dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationTM {
    private String res_id;
    private String student_id;
    private String date;
    private String room_type;
    private String status;

}
