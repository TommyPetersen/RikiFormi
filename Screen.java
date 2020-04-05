package RikiFormi;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Screen extends Frame{

    Image image = null;
    int W = 0, H = 0;

    Insets in;

    Color background;

    Screen(int W, int H){
	this.W = W; this.H = H;
	setSize(W, H);
	setUndecorated(false);
	setTitle("RikiFormi");
	setVisible(true);

	this.addWindowListener(
			       new WindowAdapter(){
				   public void windowClosing(WindowEvent e){
				       System.exit(0);
				   }
			       }
			       );

	image = createImage(W, H);
	initScreen();
	in = getInsets();
	setSize(W + in.left + in.right, H + in.top + in.bottom);
	getGraphics().drawImage(image, in.left, in.top, null);
	setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	int[] myPixels = new int[16 * 16];
	Image thecursorimage= createImage(new MemoryImageSource(16, 16, myPixels, 0, 16));
	Cursor hidden_cursor= (getToolkit()).createCustomCursor(thecursorimage,
								new Point(0, 0),
								"invisiblecursor");
	setCursor(hidden_cursor);
	background = Color.lightGray;

	repaint();
    }

    protected void initScreen(){
	Graphics sg = image.getGraphics();
	sg.setClip(0, 0, W, H);
	sg.setColor(background);
	sg.fillRect(0, 0, W, H);
	sg.dispose();
    }

    public Image createImage(){
	return createImage(W, H);
    }

    public void update(Graphics g){
	paint(g);
    }

    public void paint(Graphics g){
	try{
	    g.translate(in.left, in.top);
	    g.drawImage(image, 0,0, null);
	} catch(Exception e){}
    }

    public void drawRaster(RasterPoint[][] raster) throws Exception{
	image = createImage(W, H);  // image is off screen for double buffering
	Graphics ig = image.getGraphics();

	for (int i = 0; i < W; i++){
	    for (int j = 0; j < H; j++){
		ig.setColor((raster[i][j]).fadedColor);
		ig.drawLine(i, H - j - 1, i, H - j - 1); //Reversed on y-axis
	    }
	}
	
	update(getGraphics());
	Thread.sleep(1); //Delay added because of SocketExceptions
    }

    public void drawDecorations(){
	image = createImage(W, H);  // image is off screen for double buffering
	Graphics ig = image.getGraphics();

	// Crosshair:
	ig.setColor(Color.cyan);
	ig.drawLine(W / 2 - 10, H / 2, W / 2 + 10, H / 2);
	ig.drawLine(W / 2, H / 2 - 10, W / 2, H / 2 + 10);

	update(getGraphics());
    }

    public void paintImage(Image _image){
	image = _image;
	update(getGraphics());
    }
}
