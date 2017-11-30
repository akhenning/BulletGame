import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Graphic {
	public static Image expImgs[]= {Toolkit.getDefaultToolkit().getImage("explosion_1.png"), Toolkit.getDefaultToolkit().getImage("explosion_2.png"),
			Toolkit.getDefaultToolkit().getImage("explosion_3.png"), Toolkit.getDefaultToolkit().getImage("explosion_4.png"),
			Toolkit.getDefaultToolkit().getImage("explosion_5.png"), Toolkit.getDefaultToolkit().getImage("explosion_6.png"),
			Toolkit.getDefaultToolkit().getImage("explosion_7.png"), Toolkit.getDefaultToolkit().getImage("explosion_8.png")};
	public static Image explosion=Toolkit.getDefaultToolkit().getImage("explosion.png");
	public static Image screwyou=Toolkit.getDefaultToolkit().getImage("sigh.png");
	public static Image faceImg=Toolkit.getDefaultToolkit().getImage("enemyface.png");
	public static Image faceImg2=Toolkit.getDefaultToolkit().getImage("enemyface2.png");
	public static Image jungleBack=Toolkit.getDefaultToolkit().getImage("background1.png");
	public static Image cloudBack=Toolkit.getDefaultToolkit().getImage("background2.png");
	public static Image spaceBack=Toolkit.getDefaultToolkit().getImage("background3.jpg");
	public static Image greyButton=Toolkit.getDefaultToolkit().getImage("button.png");
	public static Image selectedButton=Toolkit.getDefaultToolkit().getImage("buttonpressed.png");
	public static Image splashScreen=Toolkit.getDefaultToolkit().getImage("blackScreen.png");
	public static Image paused=Toolkit.getDefaultToolkit().getImage("pausescreen2.png");
	public static Image[] spaceInvader= {Toolkit.getDefaultToolkit().getImage("spaceInvader1.png"),Toolkit.getDefaultToolkit().getImage("spaceInvader2.png")};
	public static Image potatoImg=Toolkit.getDefaultToolkit().getImage("normalpotato.png");
	public Color color=Color.BLACK;
	public static Font[] fonts= {new Font("IMPACT", Font.PLAIN, 40),new Font("IMPACT", Font.PLAIN, 70)};
	private int x=0;
	private double y=0;
	private int height=0;
	private int width=0;
	private long time=0;
	private Image img;
	private int type;
	private String[] text;
	private int num=0;
	private int actionID=-1;
	private boolean visible=true;
	private int font=0;

	
	public Graphic(int graphic,int alternate)
	{
		type=graphic;
		switch(graphic) {
		case 0://explosion
			//img=image;
			break;
		case 1://silly, or general decoration
			switch(alternate) {
			case 0: img=screwyou;
			break;
			case 1: img=splashScreen;
			break;
			case 2: img=paused;
			break;
			}
			break;
		case 2://background tile
			switch(alternate) {
			case 0: img=jungleBack;
			break;
			case 1: img=cloudBack;
			break;
			case 2: img=spaceBack;
			break;
			}
			width=DrawingPanel.RIGHT_BOUNDS;
			height=(int)(((double)img.getHeight(null))/((double)img.getWidth(null))*((double)width));//I'd like to get rid of this, but for
			if(height!=width)
				height-=2;
			break;
		}
	}
	public Graphic(int graphic, String text,int font,int id)//for UI elements
	{
		actionID=id;
		type=-1;
		this.text=new String[1];
		this.text[0]=text;
		switch(graphic) {
		case 0:
			img=greyButton;
			if(text.length()<7)
				width=120;//text.length()*25;
			else
				width=text.length()*20;
			height=70;
			break;
		}
		this.font=font;
	}
	
	public Graphic(String text,boolean oneWordPerLine)
	{
		type=-1;
		if(oneWordPerLine)
		{this.text=text.split(" ");}
		else
		{
			this.text= new String[1];
			this.text[0]=text;
		}
		height=fonts[font].getSize();
	}
	public Graphic(String text1,String text2,int font,Color color)
	{
		this.color=color;
		type=-1;
		text=new String[2];
		text[0]=text1;
		text[1]=text2;
		this.font=font;
		width=1;
		height=fonts[font].getSize();
	}
	public void draw(Graphics2D g2)
	{
		
		if(visible) {
			g2.setFont(fonts[font]);
			g2.setColor(color);
			if(type==0)
			{
				if(time-70>DrawingPanel.tickCounter)
				{g2.drawImage(expImgs[0],x,(int)y,width,height,null);}
				else if(time-60>DrawingPanel.tickCounter)
				{g2.drawImage(expImgs[1],x,(int)y,width,height,null);}
				else if(time-50>DrawingPanel.tickCounter)
				{g2.drawImage(expImgs[2],x,(int)y,width,height,null);}
				else if(time-40>DrawingPanel.tickCounter)
				{g2.drawImage(expImgs[3],x,(int)y,width,height,null);}
				else if(time-30>DrawingPanel.tickCounter)
				{g2.drawImage(expImgs[4],x,(int)y,width,height,null);}
				else if(time-20>DrawingPanel.tickCounter)
				{g2.drawImage(expImgs[5],x,(int)y,width,height,null);}
				else if(time-10>DrawingPanel.tickCounter)
				{g2.drawImage(expImgs[6],x,(int)y,width,height,null);}
				else if(time>DrawingPanel.tickCounter)
				{g2.drawImage(expImgs[7],x,(int)y,width,height,null);}
				else
				{setVisible(false);}
			}
			else if (type==-1)
			{
				
				if(img!=null) {
					g2.drawImage(img,x-15,(int)y-((height*2)/3),width,height,null);
				}
				for (int i=0;i<text.length;i++)
				{
					g2.drawString(text[i], x,(int)y+(i*height));//I think x and y refer to bottom corner
				}
			}
			else
			{
				g2.drawImage(img,x,(int)y,width,height,null);
			}
			if(type!=0&&height==width&&img!=null)
			{
				if(width>600&&width!=DrawingPanel.RIGHT_BOUNDS)
					width=DrawingPanel.RIGHT_BOUNDS;
				height=(int)((((double)img.getHeight(null))/((double)img.getWidth(null)))*((double)width));
				height-=2;
			}	
		}
	}
	public void draw(Graphics2D g2,int v)
	{
		
		if(type!=-1)
		{
			System.out.println("invalid type/incorrect draw function called.");
		}
		else
		{
			if(img!=null) {
				g2.drawImage(selectedButton,x-15,(int)y-((height*2)/3),width,height,null);
			}
			g2.setColor(color);
			g2.setFont(fonts[font]);
			for (int i=0;i<text.length;i++)
			{
				g2.drawString(text[i], x,(int)y+(i*40));
			}
		}
	}
	public void drawWithNum(Graphics2D g2)
	{
		if(img!=null) {
			g2.drawImage(img,x-15,(int)y-((height*2)/3),width,height,null);
		}
		for (int i=0;i<text.length;i++)
		{
			g2.drawString(text[i]+num, x,(int)y+(i*height));
		}
	}
	public void setPlace(double x, double y, double xwidth, double yheight)//xwidth doubles as a 'font' key if a string
	{
		this.x=(int)x;
		this.y=y;
		height=(int)yheight;
		width=(int)xwidth;
		time=DrawingPanel.tickCounter+85;//should be ~20 more than the first time frames change
	}
	public void setPlace(double x, double y)
	{
		this.x=(int)x;
		this.y=(int)y;
	}
	public void setActionID(int i)
	{
		actionID=i;
	}
	public int getActionID()
	{
		return actionID;
	}
	public void calcXY()
	{
		y+=DrawingPanel.WIND_EFFECT;

		if(y>DrawingPanel.LOWER_BOUNDS&&type==2)
		{
			y-=(height*2);
		}
	}
	public void updateText(String s)
	{
		text[0]=s;
	}
	public void setNum(int i)
	{
		num=i;
	}
	
	public void tileUp()
	{
		if(height!=width)
		{
			y-=height;
		}
	}
	public boolean isAvailable()
	{
		return (time<DrawingPanel.tickCounter||type!=0);
	}
	public String getText()
	{
		if(text==null) {
			if(type==0)
				return "explosion";
			if(type==1)
				return "image";
			if(type==2)
				return "background";
			return " "+type;
		}
		return text[0];
	}
	public int getNum()
	{return num;}
	public double getY()
	{
		return y;
	}
	public void setVisible(boolean visible)
	{
		this.visible=visible;
	}
	public void switchVisible()
	{
		visible=(!visible);
	}
	public void setColor(Color c)
	{color=c;}
	
	
	
	/** ID LIST
	 * 0 exit game
	 * 1 start game
	 * 2 return to title
	 * 3 reduce 1 fire rate
	 * 4 set 2 to tri shot
	 * 5 set 1 to curved shot
	 * 6 set 1 to wave shot
	 */
}
