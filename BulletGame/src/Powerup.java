
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;

class Powerup implements Collidable
{
    Color color;
    int type;
    int exploding=-1;
    int radius;
    double x;
    double y;
    
    public <Type extends Collidable> Powerup(Type source)
    {
    	x=source.getX();
    	y=source.getY();
    	radius=15;
        type=(int)(Math.random()*AttackInfo.NUMBER_AVALIABLE_PROJS);//change depending on # of avaliable items or liklihood
    }
    public void calcXY()    
    {
        y+=DrawingPanel.WIND_EFFECT;
    }
    public void draw(Graphics2D g2)
    {   
    	if(DrawingPanel.tickCounter%32<16) color=Color.BLUE;
    	else color=Color.CYAN;
    	
    	g2.setColor(color);
    	Ellipse2D.Double ell=new Ellipse2D.Double(x-(radius/2),y-radius/2,radius,radius);  
        g2.fill(ell); 
    }
    
    public boolean isInside(Point2D.Double point)
    {
        return (Math.abs(x-point.getX())<radius)&&(Math.abs(y-point.getY())<radius);
    }         
    public boolean isInside(double x,double y)
    {
        return (Math.abs(this.x-x)<radius)&&(Math.abs(this.y-y)<radius);
    }       
    
    public void getHit(Player player, boolean x)
    {      
        if(exploding==-1)
        {   
            player.getPowerUp(type);        
            exploding=1;}
    }
    
    public int interactionType()
    {
        return 2;
    }
    
    public void recalculateType(int old)//NOTE: THIS WILL BREAK PROGRAM IF 0 AVALIABLE PROJS
	{
    	while (type==old)
    		type=(int)(Math.random()*AttackInfo.NUMBER_AVALIABLE_PROJS);
	}
    
    public void appear(Enemy source)
    {
        y=source.getY();
        x=source.getX();
    }
	@Override
	public double getX() {
		return x;
	}
	@Override
	public double getY() {
		return y;
	}
	@Override
	public double getWidth() {
		return radius;
	}
	@Override
	public double getHeight() {
		return radius;
	}
	@Override
	public int getAttackPower() {
		return type;
	}
}