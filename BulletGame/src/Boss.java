import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Boss extends Enemy {
	double wander=.4;
	long wait=0;
	static public Image bossImg=Toolkit.getDefaultToolkit().getImage("bosscloud.png");
	static public Image angryBossImg=Toolkit.getDefaultToolkit().getImage("angrybosscloud.png");
	static public Image mootBossImg=Toolkit.getDefaultToolkit().getImage("mootbosscloud.png");
	static public Image nozImg=Toolkit.getDefaultToolkit().getImage("bossN2i guess.png");
	static public Graphic[] exs= {new Graphic(0,0),new Graphic(0,0),new Graphic(0,0)};
	double yradius=0;
	Image img[];
	int[] attacks;
	int whichAttack=0;
	int yOffset=0;
	int xOffset=0;
	int points=0;
	public Boss(Point2D.Double center, double width, int ID)
	{
		super(center,width);
		switch (ID) {
    	case 0:
		    hp=20;
		    attacks=new int[11];//simple attacker
		    attacks[0]=105;
		    fireFrequency=50;
		    for (int i=0;i<5;i++)
		    {
		    	attacks[i]=-1;
		    }
		    for (int i=5;i<10;i+=2)
		    {
		    	attacks[i]=1;//spread shots with super
		    	attacks[i+1]=2;
		    }
		    img=new Image[attacks.length];
		    for(int i=0;i<5;i++)
		    {
		    	img[i]=mootBossImg;
		    }
		    for(int i=5;i<11;i++)
		    {
		    	img[i]=bossImg;
		    }
		    yOffset=20;
		    xOffset=3;
    	break;
    	case 1:
		    hp=20;
		    attacks=new int[20];
		    for (int i=0;i<20;i+=2)
		    {
		    	attacks[i]=1;//spread shots with super
		    	attacks[i+1]=2;
		    }
		    attacks[19]=101;
		    fireFrequency=50;
		    img=new Image[attacks.length];
		    for(int i=0;i<20;i++)
		    {
		    	img[i]=nozImg;
		    }
		    yOffset=-5;
		    xOffset=6;
    	break;
    	case 2:
		    hp=40;
		    attacks=new int[16];
		    for (int i=0;i<15;i+=3)
		    {
		    	//System.out.println(i);
		    	attacks[i]=3;//'bomb the alley'
		    	attacks[i+1]=5;
		    	attacks[i+2]=4;
		    }
		    attacks[15]=105;
		    fireFrequency=50;
		    img=new Image[attacks.length];
		    for(int i=0;i<img.length;i++)
		    {
		    	img[i]=nozImg;
		    }
		    yOffset=20;
		    xOffset=3;
    	break;
    	case 3:
		    hp=40;
		    attacks=new int[28];
		    for (int i=0;i<27;i+=3)
		    {
		    	//System.out.println(i);
		    	attacks[i]=2;
		    	attacks[i+1]=1;//flail projectiles
		    	attacks[i+2]=102;
		    }
		    attacks[27]=105;
		    fireFrequency=50;
		    img=new Image[attacks.length];
		    for(int i=0;i<20;i++)
		    {
		    	img[i]=bossImg;
		    }
		    yOffset=20;
		    xOffset=3;
    	break;
		}
		yradius=100;
		points=hp*4;
	}


	@Override
	void draw(Graphics2D g2) {
		if(hp>0) {
	        g2.drawString(Integer.toString((int)hp), (int)(x-150),(int)(y));
			if (yradius==radius)
			{
				yradius=(radius*((double)(img[0].getHeight(null))/(double)(img[0].getWidth(null))));
			}
			if(img[whichAttack]!=null)
		       {
		           g2.drawImage(img[whichAttack],(int)(x-radius),(int)(y-(yradius)),(int)(radius*2),2*(int)yradius,null);
		       }
		       else
		       {
		            Ellipse2D.Double ell2=new Ellipse2D.Double(x-(radius/2),y-yradius/2,radius,yradius);  
		            g2.fill(ell2); 
		       }
		}
		else
		{
			int expRad=50;
			if(img[0]==Boss.bossImg||img[0]==Boss.mootBossImg)
				g2.drawImage(Boss.angryBossImg,(int)(x-radius),(int)(y-yradius),(int)(radius*2),2*(int)yradius,null);
			else
				g2.drawImage(Boss.angryBossImg,(int)(x-radius),(int)(y-yradius),(int)(radius*2),2*(int)yradius,null);
			if((DrawingPanel.tickCounter+40)%120==0)
			{
				exs[0].setVisible(true);
				exs[0].setPlace(x-radius-expRad+(Math.random()*radius*2), y-yradius-expRad+(Math.random()*yradius*2), expRad*2, 2*expRad);
			}
			if((DrawingPanel.tickCounter+80)%120==0)
			{
				exs[1].setVisible(true);
				exs[1].setPlace(x-radius-expRad+(Math.random()*radius*2), y-yradius-expRad+(Math.random()*yradius*2), expRad*2, 2*expRad);
			}
			if(DrawingPanel.tickCounter%120==0)
			{			
				exs[2].setVisible(true);
				exs[2].setPlace(x-radius-expRad+(Math.random()*radius*2), y-yradius-expRad+(Math.random()*yradius*2), expRad*2, 2*expRad);
			}
			exs[0].draw(g2);
			exs[1].draw(g2);
			exs[2].draw(g2);		
		}
	}
		
	

	@Override
	public void calcXY() {
		if (wait<DrawingPanel.tickCounter&&hp>0)
		{
			x+=wander;
			if (x<50||x>600)
			{
				wander*=-1;
				x+=wander*2;
				wait=DrawingPanel.tickCounter+500;
			}
			
		}
		
	}

	@Override
	public boolean isInside(Double point) {
		
		return (Math.abs(x-point.getX())<radius)&&(Math.abs(y-point.getY())<yradius);
	}
	public boolean isInside(double x2,double y2)
    {
        return (Math.abs(x-x2)<radius)&&(Math.abs(y-y2)<yradius);
    }


	public boolean isTouching(Proj proj) {
		return (proj.getX()+proj.getRadius()>x-radius&&proj.getX()-proj.getRadius()<x+radius&&proj.getY()>y-yradius&&proj.getY()<y+yradius);
	}
	
	public boolean die()
	{
		if(fireFrequency!=0)
		{wander=DrawingPanel.tickCounter+300;}
		fireFrequency=0;
		return (wander<DrawingPanel.tickCounter);
	}
	
	
	
	public <Type extends Collidable> void getHit(Type by, boolean contact)
    {
    	hp-=by.getAttackPower();
    	//if(hp==10||hp==9)
    	//{
    	//	img=angryBossImg;
    	//}
    }
	public int getAttackPower() {
		return 2;
	}
	public int getPoints() {
		return points;
	}
	public double getHeight()
	{return yradius;}
	public int getAttackType()
	{
		whichAttack++;
		if (whichAttack>attacks.length-1)
		{whichAttack=0;}
		return attacks[whichAttack];
	}
	public double attackFromWhereY()
	{
		return y+yOffset;
	}
	public double attackFromWhereX()
	{
		return x+xOffset;
	}
}