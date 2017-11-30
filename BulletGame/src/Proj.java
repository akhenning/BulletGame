
import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Proj implements Collidable

{          
	protected double hp=1;
	protected long remainingTime;
	protected double radius=20;
	private int type;
    private double yRadRatio=1;
    private double radChange;
    private double curve;
    private double yV=0;
    private double xV;
    private double x;
    private double y;
    private int power=1;
    private int maxProjSize=9999;;
    public static Image fireImg=Toolkit.getDefaultToolkit().getImage("fire.png");
    public static Image vineImg=Toolkit.getDefaultToolkit().getImage("vine.png");
    public static Image boomImg=Toolkit.getDefaultToolkit().getImage("boom.png");
    public static Image smugImg=Toolkit.getDefaultToolkit().getImage("smugu.png");
    public static Image smugImg2=Toolkit.getDefaultToolkit().getImage("smug2.png");
    private Image img;
    public static Player player;
    public Proj(int type, double angle, double speed, double curve, int offset, int lifetime, double radChange)//lifetime == -1 means increased power
    {
    	this.radChange=radChange;
    	if (lifetime==-1)
    	{
    		power++;
    		remainingTime=Long.MAX_VALUE;
    	}
    	else if (lifetime!=0)
    	{
    		remainingTime=DrawingPanel.tickCounter+lifetime;
    	}
    	else
    		remainingTime=Long.MAX_VALUE;
    	if(player==null)
    	{System.out.println("STATIC VARIABLE player HAS NOT BEEN INITIALIZED IN PlayerProj CLASS");}
    	y=player.getY();
        x=player.getX()+offset;
    	switch (type) {
    	case 0:
    		radius=15;
	    	//staticX=player.getX()+Player.scrollX;
		this.type=type;   
    	break;
    	case 1:
    		radius=20;
	        img=fireImg;
	        break;
    	case 2:
    		radius=20;
    		img=vineImg;
    		break;
    	case 3:
    		hp++;
    		img=boomImg;
    		break;
		case 4:
			
			break;
    	}
        yV=speed;
        xV=player.getXV()/2+angle;
        this.curve=curve;
        if(img!=null)
        {
        	yRadRatio=(double)(img.getHeight(null))/(double)(img.getWidth(null));
        }
    }
    public Proj(Enemy shooter, int type, double absSpeed, double angle, double speed, double curve, int offset, int lifetime, double radChange)
    {
    	this.type=type;
    	this.radChange=radChange;
    	if (lifetime!=0)
    	{
    		remainingTime=DrawingPanel.tickCounter+lifetime;
    	}
    	else
    		remainingTime=Long.MAX_VALUE;
    	if(shooter==null)
    	{System.out.println("Trying to fire from nonexistant enemy. Somehow.");}
    	y=shooter.attackFromWhereY();
        x=shooter.attackFromWhereX();
        yV=speed;
        xV=angle;
        boolean goodIdea=true;
        if(goodIdea)
        {
        	yV+=shooter.getYV();
        	xV+=shooter.getXV()/2;
        }
        if(absSpeed!=0)
        {
        	double speedProp=Math.sqrt(absSpeed/((xV*xV)+(yV*yV)));//TODO this might cause a bit of slowdown
        	xV*=speedProp;
        	yV*=speedProp;
        }
        this.curve=curve;
        switch (type) {
        case 0://Default fireball
        	radius=8;
        	img=fireImg;
        	break;
        case 1://larger fireball
        	radius=20;
        	img=vineImg;
        	break;
        case 2://also... a larger fireball
        	radius=20;
        	img=fireImg;
        	break;
        case 5://basketball falling shot
        	radius=20;
        	yV=1;
        	img=vineImg;
        	break;
        case 101://good homing
        	radius=30;
        	img=fireImg;
        	break;
        case 102://hard homing
        	radius=30;
        	img=fireImg;
        	break;
        case 103://gatling homing
        	radius=30;
        	img=fireImg;
        	break;
        case 104://good homing
        	radius=30;
        	img=vineImg;
        	break;
        case 105://bomb
        	radius=60;
        	img=smugImg;
        	hp=-30;
        	maxProjSize=600;
        	break;
        }
        if(img!=null)
        {
        	yRadRatio=(double)(img.getHeight(null))/(double)(img.getWidth(null));
        }
    }
    
    public void calcXY()
    {
    	if(DrawingPanel.tickCounter>remainingTime)
    		{hp=0;
    		return;}
    	switch (type) {
    	case 101:
    		xV+=(player.getX()-x)/5000;
    		yV-=(player.getY()-y)/5000;
    		if(xV>2)
    		{xV=2;}
    		if(yV<-2)
    		{yV=-2;}
    		if(xV<-2)
    		{xV=-2;}
    		if(yV>2)
    		{yV=2;}
    		break;
    	case 102:
    		xV=(player.getX()-x)/100;//Run up to then hesitate,really hard to deal with
    		yV=-(player.getY()-y)/100;
    		break;
    	case 103:
    		xV+=(player.getX()-x)/6000;//Probably sections off areas.
    		yV-=(player.getY()-y)/6000;
    		
    		break;
    	case 104:
    		xV+=(player.getX()-x)/60000;//good very slow homing
    		yV-=(player.getY()-y)/60000;
    		break;
    	case 5:
    		yV-=.01;
    		break;
    	}
    	if(type<5||type==105) {
	        xV+=curve;
	        if(type==105&&y>DrawingPanel.LOWER_BOUNDS-40)
	        {
	        	if(img==smugImg)
	        	{
		        	yV=0;
		        	//y-=1;
		        	img=Graphic.explosion;
		        	yRadRatio=1;

	        	}
	        	radius+=(maxProjSize-radius)/40+2;
	        }
    	}
    	x+=xV;
		y-=yV;
		radius+=radChange;
        if(y>1000)
        {
            hp=0;
        }
        else if(x>DrawingPanel.RIGHT_BOUNDS||x<DrawingPanel.LEFT_BOUNDS||y<DrawingPanel.UPPER_BOUNDS||y>DrawingPanel.LOWER_BOUNDS)
        {
            hp=0;
        }
        if(radius>maxProjSize)
        {
        	hp=0;
        }
        if(radChange<0&&radius<10)
        {
        	hp=0;
        }
        
    }
    public void draw(Graphics2D g2)
    {
    	//System.out.println(boomImg);
    	if(yRadRatio==1&&img!=null)
    	{
    		yRadRatio=(double)(img.getHeight(null))/(double)(img.getWidth(null));
    	}
       if(img!=null)
       {
           g2.drawImage(img,(int)(x-radius),(int)(y-(radius*yRadRatio)),(int)(radius*2),(int)(radius*yRadRatio*2),null);//1.4 
       }
       else
       {
       	g2.setColor(Color.BLACK);
            Ellipse2D.Double ell2=new Ellipse2D.Double(x-(radius/2),y-radius/2,radius,radius);  
            g2.fill(ell2); 
        }
//       if(radius==0) {
//    	   radius=20;
//       }
    }
    
    public void hitEnemy(int damage)
    {
    	if(damage==0)
    	{hp=0;}
    	else {hp-=damage;}
    }
    
    public boolean isInside(Point2D.Double point)
    {
    
  		return (Math.abs(x-point.getX())<radius)&&(Math.abs(y-point.getY())<radius*yRadRatio);
    }      
    public boolean isInside(double x2,double y2)
    {
    
  		return (Math.abs(x-x2)<radius)&&(Math.abs(y-y2)<radius*yRadRatio);
    }                 
    
        
    public <T extends Collidable> boolean isTouching(T on)//Still need to check that this works.
    {       
    	
    	if(img==Graphic.explosion)
    	{return (Point2D.distanceSq(x, y, on.getX(), on.getY())<(radius*radius/2));}
    	else
    		return (Math.abs(on.getX()-x)<on.getWidth()+radius)&&(Math.abs(on.getY()-y)<on.getHeight()+(radius*yRadRatio));
//    	else if(radius<35&&yRadRatio<2)
//    		return ((on.isInside(new Point2D.Double(x+radius,y))))||(on.isInside(new Point2D.Double(x,y+(radius*yRadRatio))));
//    	else if (radius<70)
//    		return ((on.isInside(new Point2D.Double(x+radius,y)))||(on.isInside(new Point2D.Double(x,y-(radius*yRadRatio)))||(on.isInside(new Point2D.Double(x-radius,y)))));
//    	else
//    		return (Math.abs(on.getX()-x)<on.getWidth()+radius)&&(Math.abs(on.getY()-y)<on.getHeight()+(radius*yRadRatio));
    		//return ((on.isInside(new Point2D.Double(x+radius,y)))||(on.isInside(new Point2D.Double(x+radius/2,y-(radius*yRadRatio)))||(on.isInside(new Point2D.Double(x-radius/2,y-(radius*yRadRatio))))||(on.isInside(new Point2D.Double(x,y-(radius*yRadRatio))))||(on.isInside(new Point2D.Double(x-radius,y)))));
    }    
    public boolean isTouching(RectObj r)
    {
    	
    	return  ((r.isInside(new Point2D.Double(x+radius,y+(radius*yRadRatio)))))||(r.isInside(new Point2D.Double(x-radius,y+(radius*yRadRatio))));
    }
    
    public boolean isAlive()
    {

    	return (hp>0||hp<-20);
    }
    public double getRadius()
    {return radius;}
    public double getWidth()
    {return radius;}
    public double getHeight()
    {return radius;}
    public double getX()
    {
    	return x;
    }
    public double getY()
    {
    	return y;
    }
    public int getAttackPower()
    {
    	return power;
    }
    public static void setPlayer(Player p)
    {
    	player=p;
    }
}


