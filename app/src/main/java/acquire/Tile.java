package acquire;

public class Tile implements TileInterface{
    private String space;

    protected Tile(String space) {
        this.space = space;
    }

    protected String getSpace() {
        return space;
    }
}
