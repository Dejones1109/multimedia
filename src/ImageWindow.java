import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
* ImageWindow: A class (part of lib)  it dispalys an image from a vector of pixel values

*/
class ImageWindow extends Frame {

    Image image;
    int width=680, height=680;
    int imageArray_[] = new int[width * height];
    int locationwidth=10, locationheight=10;
    int windowwidth=800, windowheight=800;
    
    int imagepositionx=10/2, imagepositiony=10/2;

    public ImageWindow(int[] imageArray) throws IOException {
        super("DCT Window");
        imageArray_ = imageArray;
        setLocation(locationwidth, locationheight);			//move(100,100);
        setSize(windowwidth, windowheight);		//resize(700,300);

        System.out.println("Calculating and displaying image..");
        image = this.createImage(new MemoryImageSource(width, height, imageArray_, 0, width));
         
        ImageTools.write("test.jpg", imageArray, 680, 680, BufferedImage.TYPE_INT_RGB, true);
        
        this.show();
        
    }

    public void paint(Graphics g) {
        
        g.drawImage(image, imagepositionx, imagepositiony, this);
    }
    
 
    
    
}