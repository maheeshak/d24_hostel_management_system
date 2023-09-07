package dto;

import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
    @Id
    private String id;
    private String password;
    private String passwordHint;

}
