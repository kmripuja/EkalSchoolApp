package ekalschoolapp.pooja.com.ekalschoolapp;

/**
 * Created by Puja on 2/19/2017.
 */

public class User {

    public String name;
    public String email;
    public String photourl;
    public String village;
    public String state;
    public int phone;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public User( String name, String email, String url, String village, String state, int phone) {
        this.name = name;
        this.email = email;
        this.photourl = url;
        this.village = village;
        this.phone = phone;
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhotourl(String url) {
        this.photourl = url;
    }
    public void setVillage(String village) {
        this.village = village;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setPhone(int phone) {this.phone = phone; }

    public String getName() {
        return  name;
    }
    public String getPhotourl() {
        return  photourl;
    }
    public String getVillage() {
        return  village;
    }
    public String getState() {
        return  state;
    }
    public String getemail() {
        return  email;
    }
    public int getPhone() {
        return  phone;
    }


}
