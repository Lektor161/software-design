package drawer;

public interface DrawingApi {
    public abstract long getDrawingAreaSize();

    public abstract void drawPoint(Coord coord, double radius);

    public abstract void drawLine(Coord coord1, Coord coord2);

    public abstract void drawCircle(Coord coord, double radius);


    public abstract void paint();
}
