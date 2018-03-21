package sample.daffodil.sample2;

/**
 * Created by DAFFODIL-29 on 3/14/2018.
 */

public class ConfHall {
    String Book_id, Ministry, Department, State, no_of_persons, City, Conf_Name, Date, Start_time, End_time, Conf_id;

    public ConfHall(String Book_id, String Ministry, String Department, String State, String no_of_persons, String City, String Conf_Name, String Date, String Start_time, String End_time, String Conf_id) {
        super();
        this.Book_id = Book_id;
        this.Ministry = Ministry;
        this.Department = Department;
        this.State = State;
        this.no_of_persons = no_of_persons;
        this.City = City;
        this.Conf_Name = Conf_Name;
        this.Date = Date;
        this.Start_time = Start_time;
        this.End_time = End_time;
        this.Conf_id = Conf_id;
    }

    public String getBook_id() {
        return Book_id;
    }

    public void setBook_id(String book_id) {
        this.Book_id = book_id;
    }

    public String getMinistry() {
        return Ministry;
    }

    public void setMinistry(String ministry) {
        this.Ministry = ministry;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        this.Department = department;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        this.State = state;
    }

    public String getNo_of_persons() {
        return no_of_persons;
    }

    public void setNo_of_persons(String no_of_persons) {
        this.no_of_persons = no_of_persons;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public String getConf_Name() {
        return Conf_Name;
    }

    public void setConf_Name(String conf_Name) {
        this.Conf_Name = conf_Name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getStart_time() {
        return Start_time;
    }

    public void setStart_time(String start_time) {
        this.Start_time = start_time;
    }

    public String getEnd_time() {
        return End_time;
    }

    public void setEnd_time(String end_time) {
        this.End_time = end_time;
    }

    public String getConf_id() {
        return Conf_id;
    }

    public void setConf_id(String conf_id) {
        this.Conf_id = conf_id;
    }
}
