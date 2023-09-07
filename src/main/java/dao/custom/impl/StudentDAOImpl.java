package dao.custom.impl;

import dao.custom.StudentDAO;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public List<Student> getAll() {

        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM Student");
        nativeQuery.addEntity(Student.class);
        List<Student> customers = nativeQuery.list();


        transaction.commit();
        session.close();

        return customers;

    }

    @Override
    public boolean add(Student student) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(student);


        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(Student student) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(student);


        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public String generateNewID() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT student_id FROM Student ORDER BY student_id DESC");
        query.setMaxResults(1);
        List results = query.list();

        transaction.commit();
        session.close();

        return (results.size() == 0) ? null : (String) results.get(0);
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        Student student = new Student();
        student.setStudent_id(id);
        session.remove(student);


        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public Student search(String id) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();


        Student student = session.get(Student.class, id);


        transaction.commit();
        session.close();

        return student;
    }

    @Override
    public List<Student> getUnpaidStudents() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery nativeQuery = session.createNativeQuery("SELECT DISTINCT * FROM student s JOIN reservation r on s.student_id = r.student_student_id WHERE r.status='un-paid'");
        nativeQuery.addEntity(Student.class);
        List<Student> customers = nativeQuery.list();


        transaction.commit();
        session.close();

        return customers;
    }
}
