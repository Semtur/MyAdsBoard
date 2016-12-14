package MyAdsBoard;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

enum UserRole {
    ADMIN, USER;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}

@Entity
@Table(name = "UserProfiles")
public class UserProfile {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String email;
    private String password;
    @Column(nullable = false)
    private String name;
    private String phone;
    @Embedded
    private Address address;
    private YearMonth dateOfRegistration;
    @OneToMany(mappedBy = "userProfile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ad> ads;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public UserProfile() {}
    public UserProfile(String email, String password, String name, String phone, Address address, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        ads = new ArrayList<>();
        dateOfRegistration = YearMonth.now();
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public List<Ad> getAds() {
        return ads;
    }
    public void addAd(Ad ad) {
        ads.add(ad);
    }
    public void deleteAd(Ad ad) {
        ads.remove(ad);
    }
    public YearMonth getDateOfRegistration() {
        return dateOfRegistration;
    }
    public UserRole getUserRole() {
        return userRole;
    }
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
