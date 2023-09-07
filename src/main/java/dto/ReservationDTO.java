package dto;

import entity.Room;
import entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationDTO {
    private String res_id;
    private Date date;
    private String status;
    private StudentDTO studentDTO;
    private RoomDTO roomDTO;

}
