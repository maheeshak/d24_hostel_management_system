package dto;

import entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDTO {

    private String student_id;
    private String name;
    private String address;
    private String contact;
    private String gender;
    private Date dob;
    private List<ReservationDTO> reservationList = new ArrayList<>();

}
