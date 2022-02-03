
package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mastery.java.task.dto.Gender.FEMALE;
import static com.mastery.java.task.dto.Gender.MALE;


@Component
public class EmployeeDao {


    @Autowired
    JdbcTemplate jdbc;

    public Employee findId(Integer id) {
        String query = "SELECT * FROM employee WHERE employee_id = ?";
        final Employee[] employee = new Employee[1];
        jdbc.execute(query, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    employee[0] = getResultEmployee(resultSet);
                }
                return null;
            }
        });
        return employee[0];
    }


    public List<Employee> findAll() {
        String query = "SELECT * FROM employee ";
        List<Employee> list = new ArrayList<>();
        jdbc.execute(query, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException {
                ResultSet resultSet = ps.executeQuery();
                Employee employee = null;
                while (resultSet.next()) {
                    list.add(getResultEmployee(resultSet));
                }
                return ps.execute();
            }
        });
        return list;
    }


    public void deleteEmployee(Integer id) {
        String query = "DELETE FROM employee WHERE employee_id = ?";
        jdbc.execute(query, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
                return ps.execute();
            }
        });
        System.out.println("Employee deleted successfully");
    }


    public void newEmployee(Employee newEmployee) {
        String query = "INSERT INTO employee (employee_id, first_name, last_name, department_id, job_title, gender, date_of_birth) " +
                "VALUES ( ?, ?, ?, ?, ?, ?)";
        jdbc.execute(query, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException {
                setParametersForEmployee(newEmployee, ps);
                return ps.execute();
            }
        });
        System.out.println("Employee generated successfully");
    }


    public void updateEmployee(Employee employee, Integer id) {
        String query = "UPDATE employee SET employee_id = ?,  first_name = ?, last_name = ?, department_id = ?, job_title = ?, " +
                "gender = ?, date_of_birth = ? WHERE employee_id = ?";
        jdbc.execute(query, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException {
                setParametersForEmployee(employee, ps);
                ps.setInt(7, employee.getEmployeeId());
                return ps.execute();
            }
        });
        System.out.println("Employee updated successfully");
    }


    private Employee getResultEmployee(ResultSet resultSet) throws SQLException {
        Employee employee;
        Integer employeeId = resultSet.getInt(1);
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        Integer departmentId = resultSet.getInt(4);
        String jobTitle = resultSet.getString(5);
        String gender = resultSet.getString(6);
        Gender genderEnum = convertGenderToEnum(gender);
        LocalDate dateOfBirth = resultSet.getDate(7).toLocalDate();

        employee = new Employee(employeeId, firstName, lastName, departmentId, jobTitle, genderEnum, dateOfBirth);
        return employee;
    }

    private Gender convertGenderToEnum(String gender) {
        Gender genderEnum = null;
        if (gender.equals("Male")) {
            genderEnum = MALE;
        } else if (gender.equals("Female")) {
            genderEnum = FEMALE;
        }
        return genderEnum;
    }


    private void setParametersForEmployee(Employee employee, PreparedStatement selectStatement) throws SQLException {
        Gender genderEnum = employee.getGender();
        String stringGender = "";
        if (genderEnum == MALE) {
            stringGender = "Male";
        } else if (genderEnum == FEMALE) {
            stringGender = "Female";
        }
        selectStatement.setInt(1,employee.getEmployeeId());
        selectStatement.setString(2, employee.getFirstName());
        selectStatement.setString(3, employee.getLastName());
        selectStatement.setInt(4, employee.getDepartmentId());
        selectStatement.setString(5, employee.getJobTitle());
        selectStatement.setString(6, stringGender);
        selectStatement.setDate(7, java.sql.Date.valueOf(employee.getDateOfBirth()));
    }
}


