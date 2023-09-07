package bo.custom.impl;

import bo.custom.StudentBO;
import dao.DAOFactory;
import dao.custom.StudentDAO;
import dto.StudentDTO;
import entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO<StudentDTO> {


    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STUDENT);

    @Override
    public List<StudentDTO> getAllStudents() {

        List<Student> students = studentDAO.getAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student : students) {

            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setStudent_id(student.getStudent_id());
            studentDTO.setName(student.getName());
            studentDTO.setAddress(student.getAddress());
            studentDTO.setContact(student.getContact());
            studentDTO.setGender(student.getGender());
            studentDTO.setDob(student.getDob());

            studentDTOS.add(studentDTO);
        }

        return studentDTOS;

    }

    @Override
    public boolean addStudent(StudentDTO entity) {
        Student student = new Student();
        student.setStudent_id(entity.getStudent_id());
        student.setName(entity.getName());
        student.setAddress(entity.getAddress());
        student.setContact(entity.getContact());
        student.setGender(entity.getGender());
        student.setDob(entity.getDob());


        return studentDAO.add(student);
    }

    @Override
    public boolean updateStudent(StudentDTO entity) {
        Student student = new Student();
        student.setStudent_id(entity.getStudent_id());
        student.setName(entity.getName());
        student.setAddress(entity.getAddress());
        student.setContact(entity.getContact());
        student.setGender(entity.getGender());
        student.setDob(entity.getDob());
        return studentDAO.update(student);
    }

    @Override
    public String generateNewStudentID() {
        return studentDAO.generateNewID();
    }

    @Override
    public boolean deleteStudent(String id) {
        return studentDAO.delete(id);
    }

    @Override
    public StudentDTO searchStudent(String id) {

        Student student = studentDAO.search(id);
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudent_id(student.getStudent_id());
        studentDTO.setName(student.getName());
        studentDTO.setAddress(student.getAddress());
        studentDTO.setContact(student.getContact());
        studentDTO.setGender(student.getGender());
        studentDTO.setDob(student.getDob());
        return studentDTO;
    }

    @Override
    public List<StudentDTO> getUnpaidStudents() {
        List<Student> students = studentDAO.getUnpaidStudents();
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student : students) {

            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setStudent_id(student.getStudent_id());
            studentDTO.setName(student.getName());
            studentDTO.setAddress(student.getAddress());
            studentDTO.setContact(student.getContact());
            studentDTO.setGender(student.getGender());
            studentDTO.setDob(student.getDob());

            studentDTOS.add(studentDTO);
        }

        return studentDTOS;
    }
}
