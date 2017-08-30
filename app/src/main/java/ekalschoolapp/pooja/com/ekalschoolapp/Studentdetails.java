package ekalschoolapp.pooja.com.ekalschoolapp;

/**
 * Created by Puja on 2/10/2017.
 */
public class Studentdetails {

    //name and address string
    private String name;
    private String rollno;
    private int marks;
    private int attendance;
    boolean selected = false;

    public Studentdetails() {
      /*Blank default constructor essential for Firebase*/
    }

    public Studentdetails(String name, String rollno){
        this.name = name;
        this.rollno = rollno;
    }

    public Studentdetails(String name, String rollno, int marks, int attendance){
        this.name = name;
        this.rollno = rollno;
        this.marks = marks;
        this.attendance = attendance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() { return rollno;  }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public int getMarks(){  return marks; }

    public void setMarks(int marks){  this.marks = marks; }

    public int getAttendance(){  return attendance; }

    public void setAttendance(int attendance){  this.attendance = attendance; }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
