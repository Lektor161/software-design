package drawer;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DrawingApiAwt extends Frame implements DrawingApi {
//    private final List<Ellipse2D.Double> circles = new ArrayList<Ellipse2D.Double>();
//    private final List<Line2D.Double> lines = new ArrayList<>();

    private final List<Consumer<Graphics2D>> tasks = new ArrayList<>();

    private final int screenSize;

    public DrawingApiAwt(int screenSize) {
        this.screenSize = screenSize;
    }

    @Override
    public void paint(final Graphics graphics) {
        final Graphics2D graphics2D = (Graphics2D) graphics;
        tasks.forEach(task -> task.accept(graphics2D));
    }

    @Override
    public long getDrawingAreaSize() {
        return screenSize;
    }

    @Override
    public void drawCircle(Coord coord, final double radius) {
        tasks.add(g -> g.draw(new Ellipse2D.Double(coord.getX() - radius, coord.getY() - radius, 2 * radius, 2 * radius)));
    }

    @Override
    public void drawPoint(Coord coord, double radius) {
        tasks.add(g -> g.fill(new Ellipse2D.Double(coord.getX() - radius, coord.getY() - radius, 2 * radius, 2 * radius)));
    }

    @Override
    public void drawLine(Coord coord1, Coord coord2) {
        tasks.add(g -> g.draw(new Line2D.Double(coord1.getX(), coord1.getY(), coord2.getX(), coord2.getY())));
    }

    @Override
    public void paint() {
        addWindowListener(new WindowAdapter() {
            /**
             * on close
             */
            @Override
            public void windowClosing(final WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(screenSize, screenSize);
        setVisible(true);
    }
}
