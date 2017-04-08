package MyAdsBoard.entity;

public enum State {
    NEW, USED;

    @Override
    public String toString() {
        return "" + name();
    }
}
