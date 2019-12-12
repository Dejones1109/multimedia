
/*
* CompressionTools: a class created by Fahmi Ncibi, it contains commun methods used during compression process (Quantization, compression, decompression)
* Date: August 28, 2014
* Published: July 1, 2017
*/
public class CompressionTools {
	private int imageWidth,imageHeight;
        private static int quality = 1;
        private static int[][] quantum ={
            {   16  ,  11  ,  10  ,  16  ,  24  ,  40   ,  51   ,  61 },
            {   12  ,  12  ,  14  ,  19  ,  26  ,  58   ,  60   ,  55 },
            {   14  ,  13  ,  16  ,  24  ,  40  ,  57   ,  69   ,  56 },
            {   14  ,  17  ,  22  ,  29  ,  51  ,  87   ,  80   ,  62 },
            {   18  ,  22  ,  37  ,  56  ,  68  ,  109  ,  103  ,  77 },
            {   24  ,  35  ,  55  ,  64  ,  81  ,  104  ,  113  ,  92 },
            {   49  ,  64  ,  78  ,  87  ,  103 ,  121  ,  120  ,  101 },
            {   72  ,  92  ,  95  ,  98  ,  112 ,  100  ,  103  ,  99 }};
        
        
	public CompressionTools(int width, int height){
		this.imageWidth=width;
		this.imageHeight=height;
	}
	/*
	 * Method that quantifies a block goes into input
	 */
	public static double[][] quantitizeBloc(float[][] input,int N)
    {
        
        int i;
        int j;
        float q;
        double[][] result=new double[N][N];
	// Calculation of the Quantization Levels Matrix
        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
            {
            	q=input[i][j]/quantum[i][j];
            	result[i][j]=(int)(Math.round(q));
                
            }
            
        }
        return result;
    }

	/*
	 * Method that performs the compression
	 */
	 public static int[] compressImage(int[] QDCT, int imageWidth, int imageHeight)
	    {
	        int i = 0;
	        int j = 0;
	        int k = 0;
	        int temp = 0;
	        int curPos = 0;
	        int runCounter = 0;
	        int imageLength = imageWidth*imageHeight;

	        int pixel[] = new int[imageWidth*imageHeight];

	        while((i<imageLength))
	        {
	            temp = QDCT[i];

	            while((i < imageLength) && (temp == QDCT[i]))
	            {
	                runCounter++;
	                i++;
	            }

	            if (runCounter > 4)
	            {
	                pixel[j] = 255;
	                j++;
	                pixel[j] = temp;
	                j++;
	                pixel[j] = runCounter;
	                j++;
	            }
	            else
	            {
	                for (k=0; k<runCounter; k++)
	                {
	                    pixel[j] = temp;
	                    j++;
	                }
	            }

	            runCounter = 0;
	            //i++;
	        }

	        return pixel;
	    }

	 /**
     * This method determines the runs in the input data, decodes it
     * and then returnss the corrected matrix. It is used to decode the data
     * from the compressImage method. Huffman encoding, Adaptive Huffman or
     * Arithmetic will give much better compression.
     *
     * @param DCT Compressed DCT int array (Expands to whole image).
     * @returns The decompressed one dimensional array.
     */
    public static int[] decompressImage(int[] DCT, int width, int height)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        int temp = 0;
        int imageLength = width*height;
        System.out.println("width*height="+width*height);
        int pixel[] = new int[width*height];

        while (i < imageLength)
        {
            temp = DCT[i];

            if (k < imageLength)
            {
                if (temp == 255)
                {
                    i++;
                    int value = DCT[i];
                    i++;
                    int length = DCT[i];

                    for(j=0; j<length; j++)
                    {
                        pixel[k] = value;
                        k++;
                   //     System.out.println("k="+k);
                    }
                }

                else
                {
                    pixel[k] = temp;
                    k++;
                }
            }
            i++;
        }

        
        for (int a = 0; a < 80; a++)
        {
            System.out.print(pixel[a] + " ");
        }
        System.out.println();
        for (int a = 0; a < 80; a++)
        {
            System.out.print(DCT[a] + " ");
        }
        System.out.println();
        return pixel;
    }

    /**
     * This method reads in DCT codes  dequanitizes them
     * and places them in the correct location. The codes are stored in the
     * zigzag format so they need to be redirected to a N * N block through
     * simple table lookup. After dequantitization the data needs to be
     * run through an inverse DCT.
     *
     * @param dctArray2 8x8 Array of quantitized image data
     * @param zigzag Boolean switch to enable/disable zigzag path.
     * @returns outputData A N * N array of de-quantitized data
     *
     */
    public static double[][] dequantitizeImage(float[][] dctArray2, int N)
    {
        int i = 0;
        int j = 0;
        int a = 0;
        int b = 0;
        int row;
        int col;
        double[][] outputData = new double[N][N];

        double result;

            for (i=0; i<8; i++)
            {
                for (j=0; j<8; j++)
                {
                    result = dctArray2[i][j] * quantum[i][j];
                    outputData[i][j] = result;
                }
            }


        return outputData;
    }

    /*
     * Method that calculates the quantization matrix
     */
    public static int[][] initQuantum( int blocSize){
    	int[][] q=new int[blocSize][blocSize];
    	 int i;
         int j;
 	// Calculation of the Quantization Levels Matrix
         for (i = 0; i < blocSize; i++)
         {
             for (j = 0; j < blocSize; j++)
             {
             	q[i][j]=1 + ((1 + i + j) * quality);
             }
         }
         return q;
    }
}