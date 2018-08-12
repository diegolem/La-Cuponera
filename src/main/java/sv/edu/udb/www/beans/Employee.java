package sv.edu.udb.www.beans;

/**
 *
 * @author Diego Lemus
 */
public class Employee {
    private int idEmployee;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // Constructores
    public Employee(){
        this.idEmployee = 0;
        this.name = "";
        this.lastName = "";
        this.email = "";
        this.password = "";     
        this.company = null;
    }
    
    public Employee(int idEmployee, String name, String lastName, String email, String password){
        this.idEmployee = idEmployee;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.company = null;
    }
    
    public Employee(int idEmployee, String name, String lastName, String email, String password, Company company){
        this.idEmployee = idEmployee;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.company = company;
    }
}
