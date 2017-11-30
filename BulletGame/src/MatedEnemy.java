import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;

class MatedEnemy extends Enemy
{
	private Rectangle rect;
    private RectObj matedShape;
    //double x2;
    double roam=1;
    private double roamC;
    private Color color;
    private int fireType=0;
    public MatedEnemy(Color color, RectObj matedShape, double r,double speed)
    {
        super(new Point2D.Double(0,0),r);
        this.color=color;
        this.matedShape=matedShape;
        this.color=color;   
        roamC=speed;//~.01
        x=-999;
        fireFrequency=100;
        fireType=8;
    }
    public void calcXY()
    {
        y=matedShape.getY()-matedShape.getYL()-radius;
        x=matedShape.getX()+(matedShape.getXL()*roam);
        //x2=matedShape.getStaticY()-Player.scrollX+(matedShape.getXL()*roam);
        if (roam<-1)
        {
            roamC=Math.abs(roamC);
        }
        else if (roam>1)
        {
            roamC=-1*Math.abs(roamC);
        }
        
        roam+=roamC;
    }
    public void draw(Graphics2D g2)
    {
        //calcXY(scrollX);        
        
        rect=new Rectangle((int)(getX()-radius),(int)(getY()-radius),(int)radius*2,(int)radius*2);
        g2.setColor(color);
        g2.fill(rect);
        g2.drawImage(Graphic.faceImg,(int)(x-radius),(int)(y-radius),(int)radius*2,(int)radius*2,null);
        
    }
    
    public boolean isInside(Point2D.Double point)
    {
        return (Math.abs(x-point.getX())<radius)&&(Math.abs(y-point.getY())<radius);
    }                 
    public boolean isInside(double x2,double y2)
    {
        return (Math.abs(x-x2)<radius)&&(Math.abs(y-y2)<radius);
    }
    
    public RectObj getMate()
    {
        return matedShape;
    }
    public int getAttackType()
    {
    	return fireType;
    }
    public double getYV()
    {
    	return matedShape.getYV();
    }

}

