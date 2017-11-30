
import java.awt.Color;
//import java.awt.Font;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.geom.Line2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

class Player implements Collidable
{
	public static final int DEFAULT_HP=5;
    Point2D.Double center;
    double baseRadius;
    double xradius;
    double yradius;
    Color color;
    double yVelocity=0;
    double xVelocity=0;
    double scrollXV=0;
    Player inactive;
    boolean touching=false;
    
    boolean crouching=false;
    int hp=DEFAULT_HP;
    double vulnerabilityTimer=0;
    boolean ded=false;//ded==dead
    double sanic=1;
    Image image=Toolkit.getDefaultToolkit().getImage("naturally.png");
    
    
    public Player(Point2D.Double center)
    {
        this.center=center;
        this.xradius=10;
        this.yradius=20;       
        baseRadius=25;//no longer needed
    }
    
    public void calcMove(boolean space)
    {
    	
            move(xVelocity,(-1*yVelocity)+DrawingPanel.WIND_EFFECT);
            
            if (center.getY()>DrawingPanel.LOWER_BOUNDS)
            {
                takeDamage(1);
            }
            else if (center.getY()<DrawingPanel.UPPER_BOUNDS)
            {
                takeDamage(1);
            }
            else if (center.getX()>DrawingPanel.RIGHT_BOUNDS)
            {
                takeDamage(1);
            }
            else if (center.getX()<DrawingPanel.LEFT_BOUNDS)
            {
                takeDamage(1);
            }
            xVelocity*=.9;
            yVelocity*=.9;
            
    }    
    public void moveX(double xdirection, double ydirection, boolean shift)
    {                       
    	if (ydirection>0&&touching) {touching=false;move(0,-2);}
        xVelocity+=xdirection/6;
        if (shift)
        {
            xVelocity+=xdirection/12;
        }
        
        if (xVelocity>35||xVelocity<-35)
        {            
            xVelocity*=.97;
            //xVelocity/=10;
        }
        else if ((xVelocity>25||xVelocity<-25)&&!shift)
        {
            xVelocity*=.97;
            //xVelocity/=10;
        }
        yVelocity+=ydirection/6;
        if (shift)
        {
            yVelocity+=ydirection/12;
        }
        
        if (yVelocity>35||yVelocity<-35)
        {            
            yVelocity*=.97;
            //xVelocity/=10;
        }
        else if ((yVelocity>25||yVelocity<-25)&&!shift)
        {
            yVelocity*=.97;
            //xVelocity/=10;
        }
        
    }        
    public void jump()
    {
       //boost function probably
    }    
    /*public void bounce(int behavior)//0 is bounce, 1 is tangible, 2 is intangible
    {   switch (behavior) {
            case 0: 
                yVelocity=20*sanic;       
                touching=false;
                break;
            case 1:
                whenTouchingGround(true,center.getY()+yradius);
        }    }    */
    public void whenTouchingGround(boolean touching, double groundHeight)
    {       
       
        if (touching)
        {           
            this.touching=true; 
            yVelocity=0;
            goTo(center.getX(),groundHeight-yradius+DrawingPanel.WIND_EFFECT);
        }
    }
    
    void draw(Graphics2D g2)
    {                
    	g2.setColor(Color.BLACK);
    	 g2.drawString(Integer.toString((int)hp), (int)center.getX(),(int)center.getY());
        //System.out.println(vulnerabilityTimer);
    	if(!isDead()) {
    			if(vulnerabilityTimer>DrawingPanel.tickCounter) {
	        	if(DrawingPanel.tickCounter%2==0)
	            {
	                g2.setColor(Color.BLACK);
	            }
	            else
	            {
	            	g2.setColor(Color.GRAY);
	            }}
	        	//g2.drawString("ANIME", (int)(center.getX()-xradius),(int)(center.getY()+yradius-5));//and then Siiva's Clannad music comes on. Because of course it does.
	        	g2.drawString("A", (int)(center.getX()-xradius),(int)(center.getY()+yradius-5));
	        	//Rectangle rect=new Rectangle((int)(center.getX()-xradius),(int)(center.getY()-yradius),(int)xradius*2,(int)yradius*2);
	        	//g2.draw(rect);4
	        	g2.setColor(Color.BLACK);
	            
	//            if(!vulnerable)
	//            {
	//                Rectangle rect6=new Rectangle((int)(center.getX()-(xradius/2)),(int)(center.getY()+(yradius/2)),(int)xradius*2/3,(int)yradius/4);
	//                Line2D.Double l2=new Line2D.Double((center.getX()-(xradius*3/4)),(center.getY()-(yradius*1/5)),center.getX()-xradius/5,center.getY());
	//                Line2D.Double l3=new Line2D.Double(center.getX()+xradius*4/5,(center.getY()-(yradius*1/5)),(center.getX()+(xradius/4)),center.getY());
	//            
	//                g2.setColor(Color.BLACK);
	//                g2.fill(rect6);
	//                g2.draw(l2);
	//                g2.draw(l3);
	//            }
	        	g2.draw(new Rectangle((int)(center.getX()-xradius),(int)(center.getY()-yradius),(int)xradius*2,(int)yradius*2));
    	}
    }
    
            
    public void setSpeed(int speed)
    {
        yVelocity=speed;
    }    
    public void setYRadius(double newR)
    {
        yradius=newR;        
    }    
    public void changeRadius(double delta)
    {
        yradius+=delta;
        xradius+=delta;
    }   
    public boolean isDead()
    {
        return ded;
    }
    public void setDed(boolean ded)
    {
        this.ded=ded;
    }
    public void resetRadii()
    {
        yradius=30+(10*hp);;
        xradius=baseRadius;
    }   
    public double getHeight()
    {
        return yradius;
    }
    public double getUpV()
    {
        return yVelocity;
    }
    public double getXV()
    {
        return xVelocity;
    }
    public double getWidth()
    {
        return xradius;
    }
    public int getPowerUpLevel()
    {
        return hp;
    }
    public double getY()
    {
        return center.getY();
    }
    public double getX()
    {
        return center.getX();
    }
    public double getRadius()
    {
    	return xradius;
    }
    public int getAttackPower()
    {
    	return 1;//Is contact power this time
    }
    public void move(double x, double y)
    {
        center=new Point2D.Double(center.getX()+x,center.getY()+y);
    }    
    public void goTo(double x, double y)
    {
        center=new Point2D.Double(x,y);
    }
    
    public boolean isInside(Point2D.Double point)
    {
        return (Math.abs(center.getX()-point.getX())<xradius)&&(Math.abs(center.getY()-point.getY())<yradius);
    }
    public boolean isInside(double x2,double y2)
    {
        return (Math.abs(center.getX()-x2)<xradius)&&(Math.abs(center.getY()-y2)<yradius);
    }
    
    public <T extends Collidable> boolean isOnTopOfNextFrame(T on)
    {
        Point2D.Double center2=new Point2D.Double(center.getX()+xVelocity,center.getY()-yVelocity+1);        
        return(yVelocity<=0)&&((on.isInside(new Point2D.Double(center2.getX()+xradius,center2.getY()+yradius)))||(on.isInside(new Point2D.Double(center2.getX()-xradius,center2.getY()+yradius))));
        
    }        
    public <T extends Collidable> boolean isHitNextFrame(T on)
    {
        Point2D.Double center2=new Point2D.Double(center.getX()+xVelocity,center.getY()-yVelocity+DrawingPanel.WIND_EFFECT);        
        return (on.isInside(new Point2D.Double(center2.getX()+xradius+1,center2.getY()+(yradius*3/4))))||(on.isInside(new Point2D.Double(center2.getX()-xradius-1,center2.getY()+(yradius*.75))))||(on.isInside(new Point2D.Double(center2.getX()-xradius-1,center2.getY()-yradius)))||(on.isInside(new Point2D.Double(center2.getX()+xradius+1,center2.getY()-yradius)));
    }
    public <T extends Collidable> boolean isHit(T on)
    {      
        return (on.isInside(center.getX()+xradius,center.getY()+yradius))||(on.isInside(center.getX()-xradius,center.getY()+yradius))||(on.isInside(center.getX()-xradius,center.getY()-yradius))||(on.isInside(center.getX()+xradius,center.getY()-yradius));
    }
    public void hitWall(double objX, double objY, double xLength,double yLength, RectObj object)
    {
        if(objX-xLength>center.getX()&&objY+yLength>center.getY()-yradius+1)
        {            
            goTo(objX-xLength-xradius,center.getY());
            xVelocity=-.5;
            
        }
        else if (objX+xLength<center.getX()&&objY+yLength>center.getY()-yradius+1)
        {            
            goTo(objX+xLength+xradius,center.getY());
            xVelocity=.5;//used to be .4
        }
        else if (objY+yLength<center.getY())
        {           
            goTo(center.getX(),objY+yLength+yradius);
            yVelocity=0;
        }   
        else if (objY+yLength>center.getY())
        {           
            goTo(center.getX(),objY-yLength-yradius);
            yVelocity=0;
        }   
    }
    
    public void getPowerUp(int identity)
    {        
        if(identity==hp)
        {
            identity++;
        }
        if(hp<identity)
        {
            hp=identity;
            yradius=30+(10*hp);
            xradius=baseRadius;
        }
        if(identity==2&&identity==hp-1)
        {
            hp=12;
            sanic=10;
            yradius=30+(10*hp);
            xradius=baseRadius;
        }
        
    }
    public void refresh()
    {
    	hp=DEFAULT_HP;
    	ded=false;
    }
    public boolean takeDamage(int type)
    {       
        if(vulnerabilityTimer<DrawingPanel.tickCounter)
        {   
        	hp-=type;
        	if(hp<0)
            {
                ded=true;
                hp=0;
            }
        	vulnerabilityTimer=DrawingPanel.tickCounter+100;
//            if(type==0||type==1)
//            {
//                vulnerabilityTimer=DrawingPanel.tickCounter+100;
//                hp--;     
////                if(hp<0)
////                {
////                    ded=true;
////                    hp=0;
////                }
//            }
//            else if (type==3)
//            {
//                //Originally something that warped the player
//            }
            return true;//attack landed
        }
        return false;//attack missed/invincible
    }
    public void setHP(int hp)
    {this.hp=hp;}
}

