package sv.edu.udb.www.beans;

/**
 *
 * @author Diego Lemus
 */
public class Employee extends Entity{
    private int idEmployee;
    private String lastName;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Constructores
    public Employee(){
        super();
        this.idEmployee = 0;
        this.lastName = "";
        this.company = null;
    }
    
    public Employee(int idEmployee, String name, String lastName, String email, String password){
        super(name, email, password);
        this.idEmployee = idEmployee;
        this.lastName = lastName;
        this.company = null;
    }
    
    public Employee(int idEmployee, String name, String lastName, String email, String password, Company company){
        super(name, email, password);
        this.idEmployee = idEmployee;
        this.lastName = lastName;
        this.company = company;
    }
}
