import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
//import java.awt.Image;

class RectObj implements Collidable
{
    private Rectangle rect;
    private Color color;
    private double width;
    private double height;
    private double x;
    private double y;
    private double yV;
    //Image image=Toolkit.getDefaultToolkit().getImage("FL.png");
    public RectObj(Point2D.Double center, double width, double height, Color color, double yVValue)
    {
        this.color=color;
        this.width=width;
        this.height=height;
        x=center.getX();
        y=center.getY();
        yV=yVValue;
        rect=new Rectangle((int)(x-width),(int)(y-height),(int)width*2,(int)height*2);
    }
    public void calcXY()
    {

    	 y-=yV;
    	 yV-=.01;
         //if (y>DrawingPanel.LOWER_BOUNDS+100) {y=0;}
         //if (y<-100) {y=DrawingPanel.LOWER_BOUNDS+100;}
    	 if (y>DrawingPanel.LOWER_BOUNDS+100) 
    	 {yV*=-1; y-=yV;}
    }
    public void draw(Graphics2D g2)
    {
           // g2.drawImage(image,(int)(x-width),-100,100,900,null);
        rect=new Rectangle((int)(x-width),(int)(y-height),(int)width*2,(int)height*2);
        g2.setColor(color);                  
        g2.fill(rect);
        
    }
    
    public boolean isInside(Point2D.Double point)
    {
        return (Math.abs(x-point.getX())<width)&&(Math.abs(y-point.getY())<height);
    }
    public boolean isInside(double x2,double y2)
    {
        return (Math.abs(x-x2)<width)&&(Math.abs(y-y2)<height);
    }

    boolean isOnBorder(Point2D.Double point)
    {        
        return (Math.abs(Math.abs(x-point.getX())-width)<=width/4)||(Math.abs(Math.abs(y-point.getY())-height)<=height/4);//used to say radius instead of width?
    }
    
    double getXL()
    {
        return width;
    }
    double getYL()
    {
        return height;
    }
    public double getRadius()
    {
    	return (width+height)/2;
    }
    public double getY()
    {return y;}
    public double getX()
    {return x;}
    public double getYV()
    {return yV;}
    public int getAttackPower()
    {
    	return 0;
    }
	@Override
	public double getWidth() {
		return width;
	}
	@Override
	public double getHeight() {
		return height;
	}
    
}

