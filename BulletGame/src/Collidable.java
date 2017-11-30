import java.awt.geom.Point2D;

public interface Collidable {
	public boolean isInside(Point2D.Double point);
	public boolean isInside(double x2, double y2);
	public double getX();
	public double getY();
	public double getWidth();
	public double getHeight();
	public int getAttackPower();
}
