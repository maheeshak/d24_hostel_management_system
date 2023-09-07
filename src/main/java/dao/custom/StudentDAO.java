package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.Student;

import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {
    List<Student> getUnpaidStudents();
}
