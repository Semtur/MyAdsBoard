package MyAdsBoard;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

enum State {
    NEW, USED;

    @Override
    public String toString() {
        return "" + name();
    }
}
enum Seller {
    OWNER, ENTITY;

    @Override
    public String toString() {
        return "" + name();
    }
}

@Entity
@Table(name = "Ads")
public class Ad {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 70)
    private String title;
    private String rubric;
    private String price;
    @Enumerated(EnumType.STRING)
    private State state;
    @Enumerated(EnumType.STRING)
    private Seller seller;
    @Column(length = 4096)
    private String description;
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<Photo> photos;
    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<Message> messages;
    private String address;
    private LocalDate creationDate;


    public Ad() {}
    public Ad(String title, String rubric, String price, State state, Seller seller,
              String description, UserProfile userProfile, String address) {
        this.title = title;
        this.rubric = rubric;
        this.price = price;
        this.state = state;
        this.seller = seller;
        this.description = description;
        this.userProfile = userProfile;
        this.address = address;
        creationDate = LocalDate.now();
        photos = new ArrayList<>();
        messages = new ArrayList<>();

    }

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String head) {
        this.title = head;
    }
    public String getRubricName() {
        int index = rubric.indexOf("=>");
        return rubric.substring(index + 3);
    }
    public String getRubric() {
        return rubric;
    }
    public void setRubric(String rubric) {
        this.rubric = rubric;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
    public Seller getSeller() {
        return seller;
    }
    public void setSeller(Seller sellerType) {
        this.seller = seller;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Photo> getPhotos() {
        return photos;
    }
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
    public UserProfile getUser() {
        return userProfile;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public void updateCreationDate() {
        creationDate = LocalDate.now();
    }
    public List<Message> getMessages() {
        return messages;
    }
    public void addMessage(Message message) {
        messages.add(message);
    }
    public void deleteMessage(Message message) {
        messages.remove(message);
    }
}
