package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "shelter")
public class Shelter {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String Address;
    private String workTime;
    private String securityContactDetails;

    public Shelter(Long id,String name, String address, String workTime, String securityContactDetails) {
        this.id = id;
        this.name = name;
        this.Address = address;
        this.workTime = workTime;
        this.securityContactDetails = securityContactDetails;
    }

    public Shelter() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getSecurityContactDetails() {
        return securityContactDetails;
    }

    public void setSecurityContactDetails(String securityContactDetails) {
        this.securityContactDetails = securityContactDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(id, shelter.id) && Objects.equals(name, shelter.name) && Objects.equals(Address, shelter.Address) && Objects.equals(workTime, shelter.workTime) && Objects.equals(securityContactDetails, shelter.securityContactDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, Address, workTime, securityContactDetails);
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "name='" + name + '\'' +
                ", Address='" + Address + '\'' +
                ", workTime='" + workTime + '\'' +
                ", securityContactDetails='" + securityContactDetails + '\'' +
                '}';
    }
}
