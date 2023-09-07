package dto.tm;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTM {
    @Id
    private String id;
    private String password;
    private String passwordHint;

}
