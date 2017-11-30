import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class MobileEnemy extends Enemy
{
	private Image img[];
	private int animationSpeed=0;
	private double yRadRatio=1;
    private double yV=0;
    private double xV=0;
    private int power=1;
    private int type;
    private int fireType=105;
    private long behavior=-1;
    private double yHover=100;
    private double points=10;
    @SuppressWarnings("unused")
	private int currImage=0;
	
	public MobileEnemy(Double center, double radius, int type2) {
		super(center, radius);
		this.type=type2;
		hp=(int)(radius/30);
		if(hp==0) hp++;
		switch(type) {
		case 0://I believe first element in img[] is right-facing.
			img=new Image[] {Graphic.potatoImg,Graphic.potatoImg};//slow homing
			fireFrequency=150;
			fireType=6;
			points=radius*1.25;
			break;
		case 1:
			img=Graphic.spaceInvader;//quickly follows at set y value
			animationSpeed=100;
			fireFrequency=150;
			fireType=7;
			points=radius*1;
			break;
		case 2://Please don't initialize at y>400 or something
			img=new Image[] {Graphic.faceImg2,Graphic.faceImg};//quickly follows a bit above player.
			fireFrequency=150;
			fireType=7;
			hp=2;
			points=radius*1.5;
			break;
		case 3:
			img=new Image[] {Graphic.faceImg2,Graphic.faceImg};//Dives at player alarmingly
			fireFrequency=0;
			fireType=0;
			hp*=4;
			behavior=DrawingPanel.tickCounter+200;
			this.type=1;
			power=2;
			points=radius;
		}
	}

	@Override
	public void calcXY() {
		switch(type) {
		case 0:
			xV+=(DrawingPanel.player.getX()-x)/60000;//geometric slow homing
			yV-=(DrawingPanel.player.getY()-y)/60000;
			break;
		case 1:
			if(behavior!=-1&&behavior<DrawingPanel.tickCounter)
			{
				if(behavior==DrawingPanel.tickCounter-30)
				{
					currImage=1;
				}
				if(yHover==100)
					yHover=DrawingPanel.player.getY();
				else 
				{
					yHover=100;
					currImage=0;
				}
				behavior=DrawingPanel.tickCounter+400;
			}
			yV*=.99;//TODO change for funny effects
			xV*=.99;
			yV-=((yHover-y))/6000;//quick follow on X value only
			if(Math.abs(DrawingPanel.player.getX()-x)>radius*2)
			{
				if(DrawingPanel.player.getX()<x)
				{
					xV+=(DrawingPanel.player.getX()+(radius*2)-x)/1500;//doesn't float directly above player
				}
				else
				{
					xV+=(DrawingPanel.player.getX()-(radius*2)-x)/1500;
				}
			}
			if(xV>3)
			{xV=3;}
			if(xV<-3)
			{xV=-3;}
			break;
		case 2:
			yV*=.99;//change for funny effects
			xV*=.99;
			yV-=((DrawingPanel.player.getY()/2-radius)-y)/2000;//yV=-((DrawingPanel.player.getY()/2-radius)-y)/50;//hovers ~200px above player, decreasing as player rises
//			if(y<radius)
//			{
//				yV=-.1;
//			}
			if(Math.abs(DrawingPanel.player.getX()-x)>radius*2)
			{
				if(DrawingPanel.player.getX()<x)
				{
					xV+=(DrawingPanel.player.getX()+(radius*2)-x)/1500;//doesn't float directly above player
				}
				else
				{
					xV+=(DrawingPanel.player.getX()-(radius*2)-x)/1500;
				}
			}
			if(xV>3)
			{xV=3;}
			if(xV<-3)
			{xV=-3;}
			break;
		}
		if(y>DrawingPanel.LOWER_BOUNDS-radius&&yV<0)
		{
			y+=yV;
			yV=0;
		}
		if(y<radius&&yV>0)
		{
			y-=yV;
			yV=0;
		}
		if(x<DrawingPanel.LEFT_BOUNDS-radius)
			xV=.5;
		if(x>DrawingPanel.RIGHT_BOUNDS+radius)
			xV=-.5;
		x+=xV;
		y-=yV;
		//TODO universal wallblocking
	}

	@Override
	 public boolean isInside(Point2D.Double point)
    {
        return (Math.abs(x-point.getX())<radius*.6)&&(Math.abs(y-point.getY())<radius*yRadRatio*.6);
    }       
	public boolean isInside(double x2,double y2)
    {
        return (Math.abs(x-x2)<radius*.8)&&(Math.abs(y-y2)<radius*yRadRatio*.8);
    }

	@Override
	void draw(Graphics2D g2) {
		if(yRadRatio==1&&img[0]!=null)
    	{
    		yRadRatio=(double)(img[0].getHeight(null))/(double)(img[0].getWidth(null));
    	}
       if(img[0]!=null)
       {
    	   if(animationSpeed!=0)
    	   {
    		   if(DrawingPanel.tickCounter%animationSpeed==0)
    		   {
    			   currImage++;
    			   if(currImage>=img.length)
    				   currImage=0;
    		   }
    	   }
    	   else if(type<3) {
	    	   if(DrawingPanel.player.getX()>x)
	    		   currImage=0;
	    	   else
	    		   currImage=1;
    	   }
    	   g2.setColor(Color.BLACK);
    	   g2.drawImage(img[currImage],(int)(x-radius),(int)(y-(radius*yRadRatio)),(int)(radius*2),(int)(radius*yRadRatio*2),null);
    	   
       }
       else
       {
    	    g2.setColor(Color.BLACK);
            Ellipse2D.Double ell2=new Ellipse2D.Double(x-(radius/2),y-radius/2,radius,radius);  
            g2.fill(ell2); 
       }	
       g2.drawString(Integer.toString(hp), (int)x,(int)(y-radius));
       //g2.draw(new Rectangle((int)(x-radius),(int)(y-(yRadRatio*radius)),(int)radius*2,(int)(2*radius*yRadRatio)));
	}
	public int getPoints()
    {
    	return (int)points;
    }
	public int getAttackPower()
    {
    	return power;
    }
	public int getAttackType()
	{
		return fireType;
	}
	public double getHeight()
    {
        return radius*yRadRatio;
    }
	public double getXV()
    {
        return xV;
    }
    public double getYV()
    {
        return yV;
    }
}
