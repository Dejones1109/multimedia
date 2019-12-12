import java.awt.AWTException;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


/*
* ImageTools: a class library created by Fahmi Ncibi, it contains communs functions/methods used for image processing/clustering/features extraction
* It allows you to
* - Get color matrices data (red, blue, green, alpha)
* - Construct image from color matrices data
* - Read image file
* - Create image file
* - Show image in a custom window
* - Change image properties
* - Display arrays in graphical window (datatable)
* - ...etc.
* Date: August 28, 2014
* Published: July 1, 2017
*/
public class ImageTools {
	private static BufferedImage myImage; // The subclass BufferedImage  describes an image with an accessible image data buffer
	static final int RED_COLOR_MAP=1; // flag to say that we will choose the red color plan
	static final int GREEN_COLOR_MAP=2; // flag to say we will choose the green color plan
	final static int BLUE_COLOR_MAP=3; // flag to say we will choose the blue color plan
	private static int[][] pixelArray=null; // Matrix that will contain the samples
	private static int imageWidth=320; // width of the image
	private static int imageHeight=240; // height of the image
	private static int[][] redPixels = null; // Pixel matrix of the red plane
	private static int[][] greenPixels = null; // Pixel matrix of the red plane
	private static int[][] bluePixels = null; // Pixel matrix of the red plane
	private static Image myImageObject=null;

	public ImageTools(){
		super();
	}

	public ImageTools(String fileName){
		super();
		File imageFile;
		try {
			imageFile=new File(fileName);
			myImage=ImageIO.read(imageFile);
		} catch (IOException e) {
			System.out.println("Error reading the image");
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Error reading file", "Error",JOptionPane.ERROR_MESSAGE); // display an error message
			e.printStackTrace();
		}
		// Load in an image
		myImageObject = Toolkit.getDefaultToolkit().getImage("test.jpg");

        // Make sure it gets loaded
        MediaTracker tracker = new MediaTracker(null);
        tracker.addImage(myImageObject, 0);
        System.out.println("Initialized tracker.");
        try {
            System.out.println("Waiting for tracker...");
            tracker.waitForID(0);
        } catch (Exception e) {
            return;
        }
        System.out.println("Loaded image.");
	}


	// 2nd constructor that takes as parameter a BufferedIamge objer
	public ImageTools(BufferedImage image){
		this.myImage=image;
		this.imageWidth=image.getWidth();
		this.imageHeight=image.getHeight();
	}

	public static int getImageWidth(){
		return myImage.getWidth();
	}
	public static int getImageHeight(){
		return myImage.getHeight();
	}

	/*
	 *  Method that returns the corresponding matrix to the JPEG image file "imagFile":
	 *  It uses a 'BufferedImage' object to retrieve the pixelArray RGB matrix from the image
	 */

	public static int[][] getArrayFromImage(String fileName){
                 File imageFile;
                 imageFile=new File(fileName);
            
		BufferedImage myImage=null; // the object that allows us to manipulate the image
		int[][] array=null;
		try{
	        myImage=ImageIO.read(imageFile); //The BufferedImage subclass describes an image with an accessible image data buffer.
	        imageHeight=myImage.getHeight(); // the height of the image from the BufferedImage object
	        imageWidth=myImage.getWidth(); // the width of the image from the BufferedImage object
	        array=new int[imageWidth][imageHeight]; // matrix that will contain the RGB matrix of the image
	        for(int i=0;i<imageWidth;i++){
	            for(int j=0;j<imageHeight;j++){
	            	array[i][j]=myImage.getRGB(i, j); // Retrieves the RGB component of the image from the BufferedImage object
	            }
	        }
		}
		catch(Exception ee){
			ee.printStackTrace();
			System.err.println("Error reading image file");
		}
		return array;
	}

	/*
	 * Method that constructs an image from an RGB pixel matrix:
	 * We used the "setRGB ()" method of the BufferedImage object, it has as parameter: the RGB pixel matrix and the positions (in x and y)
	 */
	public static Image writeImageFromArray(int[][] pixelArray,String fileName){
		BufferedImage myReconstImage=new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_BYTE_GRAY); // the reconstructed image, grayscale image thanks to the flag "BufferedImage.TYPE_BYTE_GRAY"
			try{
	        for(int y=0;y<imageHeight;y++){
	            for(int x=0;x<imageWidth;x++){
	            	myReconstImage.setRGB(x, y, pixelArray[x][y]);   //  Fill the image using the setRGB method from the RGB matrix
	            }
	        }
	         File outputfile = new File(fileName); //Creating a new file that will contain the reconstructed image
	            ImageIO.write(myReconstImage, "jpg", outputfile); // write the image to the file
        }
        catch(Exception ee){
            ee.printStackTrace();
        }
		return myReconstImage; // return the reconstructed image
	}

	/*
	 * Method that returns the red, green, or blue color map matrix, depending on the 'colorMap' variable:
	 * 		if colorMap = RED_COLOR_MAP (1) => redraws the matrix of the red plane
     *  	if colorMap = BLUE_COLOR_MAP (2) => returns the blue plan matrix
     *  	if colorMap = GREEN_COLOR_MAP (3) => returns the green plan matrix
	 */
	public static int[][] getSingleColorMapArray(File imageFile,int colorMap){
		int[][] array=null;
		try{
	        BufferedImage myImage=ImageIO.read(imageFile); //The BufferedImage subclass describes an image with an accessible image data buffer.
	        imageHeight=myImage.getHeight(); // the height of the image from the BufferedImage object
	        imageWidth=myImage.getWidth(); // the width of the image from the BufferedImage object
	        array=new int[imageWidth][imageHeight]; // matrix that will contain the RGB matrix of the image
	        redPixels=new int[imageWidth][imageHeight]; //matrix of the red plane of the image
	        greenPixels=new int[imageWidth][imageHeight]; // matrix of the green plane of the image
	        bluePixels=new int[imageWidth][imageHeight]; //  matrix of the blue plane of the image
	        for(int i=0;i<imageWidth;i++){
	            for(int j=0;j<imageHeight;j++){
	            	array[i][j]=myImage.getRGB(i, j);                   // Retrieves the RGB component of the image from the BufferedImage object
	                redPixels[i][j]=  getRed(array[i][j]);                    // Retrieves the red plane of the image
	                greenPixels[i][j] = getGreen(array[i][j]);             // Get the green plane of the image
	                bluePixels[i][j] = getBlue(array[i][j]);               // Get the blue shot of the image
	            }
	        }
		}
        catch(Exception ee){
            ee.printStackTrace();
        }
        /*
         *  the returned matrix depends on the 'colorMap' input variable,
         *  	if colorMap = RED_COLOR_MAP (1) => redraws the matrix of the red plane
         *  	if colorMap = BLUE_COLOR_MAP (2) => returns the blue plan matrix
         *  	if colorMap = GREEN_COLOR_MAP (3) => returns the green plan matrix
         */
        if(colorMap==1)
			return redPixels; // return the matrix of the red plane
		else if(colorMap==2)
			return greenPixels; // return the green plan matrix
		else if(colorMap==3)
			return bluePixels; // return the green plan matrix
		else
			return null;
	}

	/*
	 * Method that returns a matrix composed by the samples of the three colors (Red, Green and Blue),
         * The pixels are organized in the following order: Red Green Blue in three consecutive boxes
	 */

	public static int[][] getFullColorMatrix(File imageFile){
		BufferedImage image=null; // the object that allows us to manipulate the image
		int[][] fullPixelArray=null;
		try{
			image=ImageIO.read(imageFile); //The BufferedImage subclass describes an image with an accessible image data buffer.
			int w=image.getWidth();
			int h=image.getHeight();
	        redPixels=  getSingleColorMapArray(imageFile,RED_COLOR_MAP);                    // Retrieves the red plane of the image
	        greenPixels = getSingleColorMapArray(imageFile,GREEN_COLOR_MAP);             // Get the green plane of the image
	        bluePixels = getSingleColorMapArray(imageFile,BLUE_COLOR_MAP);               // Get the blue shot of the image
	        fullPixelArray=new int[w][h];
	        for(int i=0;i<w;i++){
	        	for(int j=0;j<h;j++){
	        		 /* We gather the three components (Red, Green, Blue) in the same matrix respectively:
                                  * for example: fullPixelArray [0] [0] = redPixels [0] [0]
                                  * fullPixelArray [0] [1] = greenPixels [0] [0]
                                  * fullPixelArray [0] [2] = bluePixels [0] [0]
	                 * 				 ...
	                 */
	        		fullPixelArray[i][3*j]=redPixels[i][j]; // Get the Red component and put it in their position in the global matrix
	                fullPixelArray[i][3*j+1]=bluePixels[i][j]; // Get the blue component and put it in their position in the global matrix
	                fullPixelArray[i][3*j+2]=greenPixels[i][j]; // Retrieve the Green component and put it in their position in the global matrix
	        	}
	        }

		}
		catch(Exception ee){
			ee.printStackTrace();
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Error reading file", "Error",JOptionPane.ERROR_MESSAGE); // display an error message
			System.err.println("Error reading image file");
		}
		return fullPixelArray;
	}

	/* Method that reads an image file and returns an RGB pixel matrix in hexadecimal format */
	public static String[][] getHexArrayFromImage(File imageFile){
		BufferedImage myImage=null; // the object that allows us to manipulate the image
		String[][] pixelHexArray=null;
		try{
	        myImage=ImageIO.read(imageFile); //The BufferedImage subclass describes an image with an accessible image data buffer.
	        imageHeight=myImage.getHeight(); // the height of the image from the BufferedImage object
	        imageWidth=myImage.getWidth(); // the width of the image from the BufferedImage object
	        pixelHexArray=new String[imageWidth][imageHeight]; // matrix that will contain the RGB matrix of the image
	        for(int i=0;i<imageWidth;i++){
	            for(int j=0;j<imageHeight;j++){
	            	pixelHexArray[i][j]="#"+Integer.toHexString(myImage.getRGB(i,j)).substring(2); // Retrieves the RGB component of the image from the BufferedImage object and converts it to hexadecimal (a string)
	            }
	        }
		}
		catch(Exception ee){
			ee.printStackTrace();
			System.err.println("Error reading image file");
		}
		return pixelHexArray;
	}

	/*
	 * Method that builds an image from the three matrices of the red, green and blue color planes, currently being edited
	 */
	public Image writeImageFrom3ColorMap(int[][] redColorMap,int[][] greenColorMap,int[][] blueColorMap, File fileName){
		/* In edition */
		return null;

	}

	/*
	 * Method that reads an image file and returns a 1D array contains the pixels of the image
	 */
	public static double[] read(String fileName){
		double[] pixelArrayDouble=null; // double precision real type array that will contain pixel values ​​in dual 32-bit format
		int w,h; // w: width, h: height of the image
		int index=0; // compteur
		try {
			File imageFile=new File(fileName); // read the image file
			BufferedImage image=ImageIO.read(imageFile); // assigning the file to the BufferedImage object

			w=image.getWidth(); // we recover width of the image
			h=image.getHeight(); // we get the height of the image
			pixelArrayDouble=new double[w*h];
			for(int i=0;i<w;i++){
	            for(int j=0;j<h;j++){
	                pixelArrayDouble[index]=image.getRGB(i, j); // Retrieves the RGB component of the image from the BufferedImage object
	                index++;
	            }
	        }
			// en cas d'erreur de chargement du fichier image
		} catch (IOException e) {
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Error reading file", "Error",JOptionPane.ERROR_MESSAGE); // display an error message
			e.printStackTrace();
		}
		return pixelArrayDouble;
	}

	/*
	 * Method that reads an image file and returns an array of integers contains pixels in 32-bit integer format
	 */
	public static int[] read(String fileName,Object objetNull){ // overloading the return type of a method requires changing the input parameter
		int[] pixelArrayInt = null; // integer array that will contain pixel values ​​in 32-bit integer format
		int w,h; // w: width, h: height of the image
		int index=0;
		try {
			File imageFile=new File(fileName);// read the image file
			BufferedImage image=ImageIO.read(imageFile); //

			w=image.getWidth(); // we recover width of the image
			h=image.getHeight(); // we get the height of the image
			pixelArrayInt=new int[w*h];
			for(int i=0;i<w;i++){
	            for(int j=0;j<h;j++){
	                pixelArrayInt[index]=image.getRGB(i, j); // Retrieves the RGB component of the image from the BufferedImage object
	                index++;
	            }
	        }
			// if there is an error loading the image file
		} catch (IOException e) {
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Error reading file", "Error",JOptionPane.ERROR_MESSAGE); // display an error message
			System.out.println("Error reading file");
			e.printStackTrace();
		}
		return pixelArrayInt;
	}

	/*
	 * method that write an image file from a pixel array encoded in integer 32 bits format 
	 * the imageType parameter is an integer corresponding to the type of the image we are aiming to build it.
         * constants proposed by BufferedImage object: TYPE_4BYTE_ABGR, TYPE_BYTE_GRAY, TYPE_INT_ARGB , ...
	 */
	public static void write(String imageName, int[] pixel1DArray, int width, int height, int imageType,boolean rgbFormat){
		BufferedImage myReconstImage=new BufferedImage(width, height,imageType); //the reconstructed image, image in gray level thanks to the flag "BufferedImage.TYPE_BYTE_GRAY"
		int index=0;
		int[][] array=new int[width][height];
		try{
			if(rgbFormat){
		        for(int x=0;x<width;x++){
		            for(int y=0;y<height;y++){
		            	array[x][y]=pixel1DArray[index];
		            	myReconstImage.setRGB(x, y, array[x][y]);   //  Fill the image using the setRGB method from the RGB matrix
		            	index++;
		            }
		        }
			}
		         File outputfile = new File(imageName); // Creating a new file that will contain the reconstructed image
		            ImageIO.write(myReconstImage, "jpg", outputfile); // write the image to the file
			}
		catch(Exception ee){
        ee.printStackTrace();
    }
   //new PixelArray("Array",array,320,240);
	}

	/*
	 * Method that converts a 1D array to a 2D array
	 */
	private static int[][] convert1DTo2DArray(int[] pixel1DArray, int w, int h) {
		int index=0;
		int[][] array=new int[w][h];
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				array[i][j]=pixel1DArray[index];
				index++;
			}
		}
		return array;
	}

	public static int getRed(int pixel){return((pixel >> 16) & 0xff);}

	public static int getGreen(int pixel) {return((pixel >> 8) & 0xff);}

	public static int getBlue(int pixel) { return(pixel & 0xff);}

	public static int getAlpha(int pixel){ return (pixel >>> 24) & 0xff;}

	/**
     * This method converts a single array 0..255 range pixel matrix to a
     * java-compatible array of 32 bit integers. This method can take a long
     * time to complete so a reporting feature has been added. The arrays
     * should be the same dimensions as imageWidth and imageHeight. This array
     * can then be converted to an image with: <br>
     * newimage = comp.createImage(new MemoryImageSource(imageWidth,imageHeight,imageArray,0,imageWidth));
     * @param Array The pixel values
     * @param log Enable/Disable progress reporting.
     * @returns array A one dimensional image array.
     */
    public static int[] convertGrayToArray(int[][] R)
    {
        int array[] = new int[imageWidth * imageHeight];

        int x = 0;
        int y = 0;
        int index = 0;

        int G[][] = new int[imageWidth][imageHeight];
        int B[][] = new int[imageWidth][imageHeight];
        int alpha[][]=new int[imageWidth][imageHeight];

        G = R;
        B = R;
        alpha=R;


    	for (x=0; x<imageWidth; x++)
    	{
    	    for (y=0; y<imageHeight; y++)
    	    {
                array[index] = -16777216 | (R[x][y] << 16) | (G[x][y] << 8) | B[x][y];
        	    index++;
    	    }
    	}

        return array;
    }

	/*
	 * Method that displays the elements of a matrix in the java console
	 */
	static void display2DArray(int[][] matrix,
			int imageWidth, int imageHeight) {
		for(int i=0;i<imageWidth;i++){
			for(int j=0;j<imageHeight;j++){
				System.out.print(" | "+matrix[i][j]);
			}
			System.out.println();
		}
	}

	/**
     * This method returns an array[][] of 8-bit red values. The dimensions are
     * the array bounds ex. (320 x 200).
     * @returns An array[][] of red pixel values
     */
    public int[][] getRedArray()
    {
        int values[] = new int[imageWidth * imageHeight];
    	PixelGrabber grabber = new PixelGrabber(myImageObject.getSource(), 0, 0, imageWidth, imageHeight, values, 0, imageWidth);

    	try
    	{
    	    if(grabber.grabPixels() != true)
    	    {
    		    try
    		    {
    		        throw new AWTException("Grabber returned false: " + grabber.status());
    		    }
    		    catch (Exception e) {};
    		}
    	}
    	catch (InterruptedException e) {};

    	int r[][] = new int[imageWidth][ imageHeight];

  		int index = 0;
    	for (int y = 0; y < imageHeight; ++y)
    	{
    	    for (int x = 0; x < imageWidth; ++x)
    	    {
    	    	r[x][y] = ((values[index] & 0x00ff0000) >> 16);
        		index++;
    	    }
    	}

    	return r;
    }

    /**
     * This method returns an array[][] of 8-bit green values. The dimensions are
     * the array bounds ex. (320 x 200).
     * @returns An array[][] of green pixel values.
     */
    public int[][] getGreenArray()
    {
        int values[] = new int[imageWidth * imageHeight];
    	PixelGrabber grabber = new PixelGrabber(myImageObject.getSource(), 0, 0, imageWidth, imageHeight, values, 0, imageWidth);

    	try
    	{
    	    if(grabber.grabPixels() != true)
    	    {
    	        try
    	        {
    		        throw new AWTException("Grabber returned false: " + grabber.status());
    		    }
    		    catch (Exception e) {};
    		}
    	}
    	catch (InterruptedException e) {};

    	int g[][] = new int[imageWidth][imageHeight];

  		int index = 0;
    	for (int y = 0; y < imageHeight; ++y)
    	{
    	    for (int x = 0; x < imageWidth; ++x)
    	    {
        		g[x][y] = ((values[index] & 0x0000ff00) >> 8);
        		++index;
    	    }
    	}

    	return g;
    }

    /**
     * This method returns an array[][] of 8-bit blue values. The dimensions are
     * the array bounds, ex. (320 x 200).
     * @returns An array[][] of blue pixel values.
     */
    public int[][] getBlueArray()
    {
        int values[] = new int[imageWidth * imageHeight];
    	PixelGrabber grabber = new PixelGrabber(myImageObject.getSource(), 0, 0, imageWidth, imageHeight, values, 0, imageWidth);

    	try
    	{
    	    if(grabber.grabPixels() != true)
    	    {
    	        try
    	        {
    		        throw new AWTException("Grabber returned false: " + grabber.status());
    		    }
    		    catch (Exception e) {};
    		}
    	}
    	catch (InterruptedException e) {};

    	int b[][] = new int[imageWidth][imageHeight];

  		int index = 0;
    	for (int y = 0; y < imageHeight; ++y)
    	{
    	    for (int x = 0; x < imageWidth; ++x)
    	    {
        		b[x][y] = (values[index] & 0x000000ff);
        		++index;
    	    }
    	}

    	return b;
    }
}