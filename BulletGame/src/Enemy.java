import java.awt.geom.Point2D;
import java.awt.Graphics2D;

public abstract class Enemy implements Collidable
{
    protected double x;
    protected double y;
    protected double radius;
    protected int hp=1;
    protected int fireFrequency=0;
    
    public Enemy(Point2D.Double center, double radius)
    {
        x=center.getX();
        y=center.getY();
        this.radius=radius;

        
      
    }
    
    public abstract void calcXY();
    
    public Point2D.Double getCenter()
    {
        return new Point2D.Double(x,y);
    }
    
    public double getXL()
    {
        return radius;
    }
    public double getYL()
    {
        return radius;
    }
    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
    public double getXV()
    {
        return 0;
    }
    public double getYV()
    {
        return 0;
    }
    public int getFireFrequency()
    {
    	return fireFrequency;
    }
    public int getPoints()
    {
    	return (int)radius;
    }
    public int getAttackType()
    {
    	return 0;
    }
    
    public void move(double x, double y)
    {
        this.x+=x;
        this.y+=y;
    }
    
    public void goTo(double x, double y)
    {
        this.x=x;
        this.y=y;
    }
    
    public void goToX(double x)
    {
        this.x=x;
    }
    
    public void setRadius(double r)
    {
        radius=r;
    }
    
    public double getWidth()
    {
    	return radius;
    }
    public double getHeight()
    {
    	return radius;
    }
    
    public int getAttackPower()
    {
    	return 1;
    }
    
    public boolean isAlive()
    {
    	return (hp>0);
    }
    
    public RectObj getMate()
    {
    	return null;
    }
    public abstract boolean isInside(Point2D.Double point);
    //abstract boolean isOnBorder(Point2D.Double point);
    abstract void draw(Graphics2D g2); 
    
    public int getInteractionType()//I guess 1 means hurt.
    {
    	return 1;
    }
    
    public <Type extends Collidable> void getHit(Type by, boolean contact)
    {
    	hp-=by.getAttackPower();
    }
    
    public double attackFromWhereY()
	{
		return y;
	}
	public double attackFromWhereX()
	{
		return x;
	}
    
}

