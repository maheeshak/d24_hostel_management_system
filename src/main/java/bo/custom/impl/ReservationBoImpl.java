package bo.custom.impl;

import bo.custom.ReservationBO;
import dao.DAOFactory;
import dao.custom.ReservationDAO;
import dto.ReservationDTO;
import dto.RoomDTO;
import dto.StudentDTO;
import entity.Reservation;
import entity.Room;
import entity.Student;

import java.util.ArrayList;
import java.util.List;

public class ReservationBoImpl implements ReservationBO<ReservationDTO> {

    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RESERVATION);

    @Override
    public List<ReservationDTO> getAllReservations() {

        List<Reservation> reservations = reservationDAO.getAll();

        List<ReservationDTO> reservationDTOS = new ArrayList<>();


        for (Reservation reservation : reservations) {

            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setRes_id(reservation.getRes_id());
            reservationDTO.setDate(reservation.getDate());
            reservationDTO.setStatus(reservation.getStatus());

            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setStudent_id(reservation.getStudent().getStudent_id());
            studentDTO.setName(reservation.getStudent().getName());
            studentDTO.setAddress(reservation.getStudent().getAddress());
            studentDTO.setContact(reservation.getStudent().getContact());
            studentDTO.setDob(reservation.getStudent().getDob());
            studentDTO.setGender(reservation.getStudent().getGender());

            reservationDTO.setStudentDTO(studentDTO);

            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setRoom_id(reservation.getRoom().getRoom_id());
            roomDTO.setType(reservation.getRoom().getType());
            roomDTO.setKey_money(reservation.getRoom().getKey_money());
            roomDTO.setQty(reservation.getRoom().getQty());

            reservationDTO.setRoomDTO(roomDTO);

            reservationDTOS.add(reservationDTO);

        }
        return reservationDTOS;

    }

    @Override
    public boolean addReservation(ReservationDTO entity) {

        Reservation reservation = new Reservation();
        reservation.setRes_id(entity.getRes_id());

        Room room = new Room();
        room.setRoom_id(entity.getRoomDTO().getRoom_id());
        room.setType(entity.getRoomDTO().getType());
        room.setKey_money(entity.getRoomDTO().getKey_money());
        room.setQty(entity.getRoomDTO().getQty());
        reservation.setRoom(room);

        reservation.setStatus(entity.getStatus());
        reservation.setDate(entity.getDate());

        Student student = new Student();
        student.setStudent_id(entity.getStudentDTO().getStudent_id());
        student.setName(entity.getStudentDTO().getName());
        student.setAddress(entity.getStudentDTO().getAddress());
        student.setContact(entity.getStudentDTO().getContact());
        student.setDob(entity.getStudentDTO().getDob());
        student.setGender(entity.getStudentDTO().getGender());
        reservation.setStudent(student);

        return reservationDAO.add(reservation);
    }

    @Override
    public boolean updateReservation(ReservationDTO entity) {
        Reservation reservation = new Reservation();
        reservation.setRes_id(entity.getRes_id());

        Room room = new Room();
        room.setRoom_id(entity.getRoomDTO().getRoom_id());
        room.setType(entity.getRoomDTO().getType());
        room.setKey_money(entity.getRoomDTO().getKey_money());
        room.setQty(entity.getRoomDTO().getQty());
        reservation.setRoom(room);

        reservation.setStatus(entity.getStatus());
        reservation.setDate(entity.getDate());

        Student student = new Student();
        student.setStudent_id(entity.getStudentDTO().getStudent_id());
        student.setName(entity.getStudentDTO().getName());
        student.setAddress(entity.getStudentDTO().getAddress());
        student.setContact(entity.getStudentDTO().getContact());
        student.setDob(entity.getStudentDTO().getDob());
        student.setGender(entity.getStudentDTO().getGender());
        reservation.setStudent(student);

        return reservationDAO.update(reservation);

    }

    @Override
    public String generateNewReservationID() {
        return reservationDAO.generateNewID();
    }

    @Override
    public boolean deleteReservation(String id) {

        return reservationDAO.delete(id);


    }

    @Override
    public ReservationDTO searchReservation(String id) {
        Reservation reservation = reservationDAO.search(id);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setRes_id(reservation.getRes_id());
        reservationDTO.setStatus(reservation.getStatus());
        reservationDTO.setDate(reservation.getDate());



        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudent_id(reservation.getStudent().getStudent_id());
        studentDTO.setName(reservation.getStudent().getName());
        studentDTO.setAddress(reservation.getStudent().getAddress());
        studentDTO.setContact(reservation.getStudent().getContact());
        studentDTO.setDob(reservation.getStudent().getDob());
        studentDTO.setGender(reservation.getStudent().getGender());

        reservationDTO.setStudentDTO(studentDTO);

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoom_id(reservation.getRoom().getRoom_id());
        roomDTO.setType(reservation.getRoom().getType());
        roomDTO.setKey_money(reservation.getRoom().getKey_money());
        roomDTO.setQty(reservation.getRoom().getQty());

        reservationDTO.setRoomDTO(roomDTO);

        return reservationDTO;
    }
}
