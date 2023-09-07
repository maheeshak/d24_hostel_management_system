package bo.custom.impl;

import bo.custom.UserBO;
import dao.DAOFactory;
import dao.custom.UserDAO;
import dto.StudentDTO;
import dto.UserDTO;
import entity.Student;
import entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO<UserDTO> {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);


    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userDAO.getAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {

            UserDTO userDTO = new UserDTO(user.getId(), user.getPassword(), user.getPasswordHint());
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    @Override
    public boolean addUser(UserDTO userDTO) {
        User user = new User(userDTO.getId(), userDTO.getPassword(), userDTO.getPasswordHint());
        return userDAO.add(user);
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        User user = new User(userDTO.getId(), userDTO.getPassword(), userDTO.getPasswordHint());
        return userDAO.update(user);
    }

    @Override
    public String generateNewUserID() {
        return null;
    }

    @Override
    public boolean deleteUser(String id) {
        return userDAO.delete(id);
    }

    @Override
    public UserDTO searchUser(String id) {
        User user = userDAO.search(id);
        if (user == null) {
            return null;
        } else {
            return new UserDTO(user.getId(), user.getPassword(), user.getPasswordHint());
        }
    }

    @Override
    public boolean validUser(UserDTO userDTO) {
        return userDAO.valid(new User(userDTO.getId(), userDTO.getPassword(), userDTO.getPasswordHint()));
    }
}
