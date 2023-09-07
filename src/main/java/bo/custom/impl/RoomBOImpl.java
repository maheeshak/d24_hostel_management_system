package bo.custom.impl;

import bo.custom.RoomBO;
import dao.DAOFactory;
import dao.custom.RoomDAO;
import dto.RoomDTO;
import dto.StudentDTO;
import entity.Room;
import entity.Student;

import java.util.ArrayList;
import java.util.List;

public class RoomBOImpl implements RoomBO<RoomDTO> {

    RoomDAO roomDAO = (RoomDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ROOM);

    @Override
    public List<RoomDTO> getAllRooms() {

        List<Room> rooms = roomDAO.getAll();
        List<RoomDTO> roomDTOS = new ArrayList<>();

        for (Room room : rooms) {

            RoomDTO roomDTO = new RoomDTO();

            roomDTO.setRoom_id(room.getRoom_id());
            roomDTO.setType(room.getType());
            roomDTO.setKey_money(room.getKey_money());
            roomDTO.setQty(room.getQty());
            roomDTOS.add(roomDTO);
        }

        return roomDTOS;
    }

    @Override
    public boolean addRoom(RoomDTO entity) {
        Room room = new Room();
        room.setRoom_id(entity.getRoom_id());
        room.setType(entity.getType());
        room.setKey_money(entity.getKey_money());
        room.setQty(entity.getQty());
        return roomDAO.add(room);
    }

    @Override
    public boolean updateRoom(RoomDTO entity) {
        Room room = new Room();
        room.setRoom_id(entity.getRoom_id());
        room.setType(entity.getType());
        room.setKey_money(entity.getKey_money());
        room.setQty(entity.getQty());
        return roomDAO.update(room);
    }

    @Override
    public String generateNewRoomID() {
        return roomDAO.generateNewID();
    }

    @Override
    public boolean deleteRoom(String id) {
        return roomDAO.delete(id);
    }

    @Override
    public RoomDTO searchRoom(String id) {

        Room room = roomDAO.search(id);

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoom_id(room.getRoom_id());
        roomDTO.setType(room.getType());
        roomDTO.setKey_money(room.getKey_money());
        roomDTO.setQty(room.getQty());

        return roomDTO;


    }
}
