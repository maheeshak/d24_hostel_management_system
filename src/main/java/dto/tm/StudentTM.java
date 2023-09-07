package dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentTM {

    private String student_id;
    private String name;
    private String address;
    private String contact;
    private String gender;
    private Date dob;

}
