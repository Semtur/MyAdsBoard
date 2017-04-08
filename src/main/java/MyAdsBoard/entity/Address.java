package MyAdsBoard.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String state;
    private String city;
    private String street;

    public Address() {}
    public Address(String state, String city, String street) {
        this.state = state;
        this.city = city;
        this.street = street;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!state.equals(address.state)) return false;
        if (!city.equals(address.city)) return false;
        return street != null ? street.equals(address.street) : address.street == null;

    }
   @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(state).append(", ").append(city);
        if (street != null)
            stringBuilder.append(",").append(street);
        return stringBuilder.toString();
    }
}
