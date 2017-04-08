package MyAdsBoard.entity;

public enum Seller {
    OWNER, ENTITY;

    @Override
    public String toString() {
        return "" + name();
    }
}
