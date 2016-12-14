package MyAdsBoard;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "Photos")
public class Photo {
    @Id
    @GeneratedValue
    private Long id;
    @Lob
    private byte[] photo;
    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    public Photo() {}
    public Photo(byte[] photo, Ad ad) {
        long l ;
        this.photo = photo;
        this.ad = ad;
    }

    public Long getId() {
        return id;
    }
    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    public Ad getAd() {
        return ad;
    }
    public void setAd(Ad ad) {
        this.ad = ad;
    }
}
