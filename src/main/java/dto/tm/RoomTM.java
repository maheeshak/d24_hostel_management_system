package dto.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomTM {

    private String room_id;
    private String type;
    private Double key_money;
    private Integer qty;
}
