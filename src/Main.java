
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;




  class HuffmanNode { 
  
    int data; 
    int val; 
    String binary;
  
    HuffmanNode left; 
    HuffmanNode right; 
    } 
  
 

class MyComparator implements Comparator<HuffmanNode> { 
    public int compare(HuffmanNode x, HuffmanNode y) 
    { 
  
        return x.data - y.data; 
    } 
} 

public class Main {

	public static void main(String[] args) throws IOException {
		
             
              BufferedImage myImage;
              File Image =new File("C:\\Users\\tranv\\Downloads\\test.jpg");
              myImage = ImageIO.read(Image);
              GrafixTools GT = new GrafixTools(myImage);
              int[][] rgbArray= GT.getRGBArray();
              PixelArray pixelArray = new PixelArray("Core RGB matrix",rgbArray,GT.imageWidth,GT.imageHeight);
              
              
                  System.out.print("Hit enter to continue");
                  try {
                        System.in.read();
                  } catch (Exception e) {
                          }
                      pixelArray.setVisible(false);
                      clearScreen();
        
     //------------------------------------------ chuyen doi kenh mau -----------------------------------------------------------------------   
               int[][] redArray= GT.getRedArray();
            PixelArray rApx =  new PixelArray("Original RED matrix",redArray,GT.imageWidth,GT.imageHeight);
               int[][] greenArray= GT.getGreenArray();
             PixelArray grApx = new PixelArray("Original GREEN matrix",greenArray,GT.imageWidth,GT.imageHeight);
               int[][] blueArray= GT.getBlueArray();
            PixelArray blApx =   new PixelArray("Original BLUE matrix",blueArray,GT.imageWidth,GT.imageHeight);    

            
                        System.out.print("Hit enter to continue");
                  try {
                        System.in.read();
                  } catch (Exception e) {
                          }
                      rApx.setVisible(false);   
                      grApx.setVisible(false);   
                      blApx.setVisible(false);   
                       clearScreen();
            
            
               int[][] Y_array = GT.convertRGBtoY(redArray, greenArray, blueArray);
          PixelArray YApx   =  new PixelArray("Y Color matrix",Y_array,GT.imageWidth,GT.imageHeight);                    
               int[][] Cb_array =GT.convertRGBtoCb(redArray, greenArray, blueArray);
          PixelArray CbApx  =  new PixelArray("Cb Color matrix",Cb_array,GT.imageWidth,GT.imageHeight);                  
               int[][] Cr_array =GT.convertRGBtoCr(redArray, greenArray, blueArray);
          PixelArray  CrApx =  new PixelArray("Cr Color matrix",Cr_array,GT.imageWidth,GT.imageHeight);    
               
          System.out.print("Hit enter to continue");
                  try {
                        System.in.read();
                  } catch (Exception e) {
                          }
                      YApx.setVisible(false);   
                      CbApx.setVisible(false);   
                     CrApx.setVisible(false);   
                      clearScreen();
                      


   //--------------------------------  KET THUC chuyen doi kenh mau --------------------------------------------------------------------------------------------
               
               
   //--------------------------------  Bien doi cousin roi rac --------------------------------------------------------------------------------------------
               

         
               
               
              int[][] CbDCTCompress =  DCTcompress(Cb_array, GT );
              int[][] CrDCTCompress =  DCTcompress(Cr_array, GT );
              
             
              
             
             PixelArray CbCompxD =  new PixelArray("Cb DCT matrix",CbDCTCompress,GT.imageWidth,GT.imageHeight); 
             PixelArray CrCompxD =  new PixelArray("Cr DCT matrix",CrDCTCompress,GT.imageWidth,GT.imageHeight);  
            
            
                  System.out.print("Hit enter to continue");
                  try {
                        System.in.read();
                  } catch (Exception e) {
                          }
                      CrCompxD.setVisible(false);   
                      CbCompxD.setVisible(false);   
                       clearScreen();
                       
//   ---------------------------------- KET THUC bien doi cousin roi rac -------------------------------------------------------------------------------------
              
// ------------------------------------ Luong tu hoa --------------------------------------------------------------------------------------------------------
              int[][] CbCompressQ =  quantumCompress(CbDCTCompress, GT );
              int[][] CrCompressQ =  quantumCompress(CrDCTCompress, GT );  
              
             
              
             PixelArray CrCompxQ =  new PixelArray("Cr quantum matrix",CrCompressQ,GT.imageWidth,GT.imageHeight);  
             PixelArray CbCompxQ =  new PixelArray("Cb quantum matrix",CbCompressQ,GT.imageWidth,GT.imageHeight); 
             
              System.out.print("Hit enter to continue");
                  try {
                        System.in.read();
                  } catch (Exception e) {
                          }
                   CrCompxQ.setVisible(false);   
                     clearScreen();
//             
// -------------------------------------- KET THUC qua trinh luong tu ----------------------------------------------------------------------------------------
              
              
// --------------------------------------- chay zigzag tung khoi block 8x8 -------------------------------------------------------------------------------              
//             
             int[] CbCompressArr =  convert2Dto1D(CbCompressQ);
             int[] CrCompressArr =  convert2Dto1D(CrCompressQ);
             
             
             
             
             System.out.println("Cb array after zigzag running");
             
               for(int i =0; i< CbCompressArr.length; i++){
                   System.out.print(CbCompressArr[i]+",");
                   if( ((i+1)%64)==0 ) System.out.println();
               }
             System.out.print("Hit enter to continue");
                  try {
                        System.in.read();
                  } catch (Exception e) {
                          }
                 clearScreen();  
                CbCompxQ.setVisible(false);  
                
// ---------------------------------------- KET THUC chay zigzag tung khoi ----------------------------------------------------------------------------     

// ----------------------------------------- nen du lieu khong mat mat thong tin -----------------------------------------------------------------------
             //----------------------------- DPCM cho DC va day Y ------------------------------------------------------------------------
              int[] CbDPCM = DPCMcompressBL( CbCompressArr);
              
              System.out.println("Cb array after DPCM");
              //------------  show to console -------------------------------------
               for(int i =0; i< CbDPCM.length; i++){
                   System.out.print(CbDPCM[i]+",");
                   if( ((i+1)%64)==0 ) System.out.println();
               }
               //----------------------------------------------------
              
               
               
               System.out.println("Hit enter to continue");
                  try {
                        System.in.read();
                  } catch (Exception e) {
                          }
                 clearScreen();  
               
             
                
              int[] CrDPCM = DPCMcompressBL( CrCompressArr);
              
             
              
             
              
              
               
             
             
              
              
           //------------------------KET THUC DPCM --------------------------------------------------------------------
              
 // --------------------------------------run length encoding cho AC -------------------------------------------------
            int[] CbRLE = RunLength_Encoding.encode(CbDPCM);
            int[] CrRLE = RunLength_Encoding.encode(CrDPCM);
           
            
            
              System.out.println("Cb array after RLE");
             //------------  show to console -------------------------------------
               for(int i =0; i< CbRLE.length; i++){
                   System.out.print(CbRLE[i]+",");
                   if( ((i+1)%50)==0 ) System.out.println();
               }
              
               System.out.println("Hit enter to continue");
                  try {
                        System.in.read();
                  } catch (Exception e) {           }
                clearScreen();  

                
 //--------------------------------------KET THUC run length encoding ------------------------------------------------------------------------
            
 //------------------------------------------Hien thi bang huffman ------------------------------------------------------------------------           
                    System.out.println("Bang ma huffman cho Cr");
                       int[] value =  value( CrRLE,  CrRLE.length);
                       int[] freq =  Freq( CrRLE,  CrRLE.length); 
                        showBinary(value, freq);
                        
                         //------------  show to console -------------------------------------
              
                             
                             System.out.println("Hit enter to continue");
                               try {
                                   System.in.read();
                             } catch (Exception e) {
                                   }
                            clearScreen();
                
                          //----------------------------------------------------   
                              
                
 //---------------------- KET THUC chuoi huffman   --------------------------------------------------------------------
 //---------------------------------------------------KET THUC qua trinh ma hoa ----------------------------------------------------------------
                        
           
 //-----------------------------------------------  DECOMPRESS  ----------------------------------------------------------------------                      
  //---------------------------------------------------Run length decoding------------------------------------------             
                     int[] DeCbRLE = RunLength_Encoding.decode(CbRLE);
                     int[] DeCrRLE = RunLength_Encoding.decode(CrRLE);       
                           
                     
//-------------------------------- DeDPCM -------------------------------------------------                     
               int[] DeCbDPCM = DeDPCMcompressBL( DeCbRLE);
               int[] DeCrDPCM = DeDPCMcompressBL( DeCrRLE);
               
             
               
        
      //       int[][] newY_Arr = convertYto2D(DeDPCMcompressY(YDPCM));
          
 //------------------------------------De zigzag--------------------------------------------
               int[][] DeCbCompressArr = conver1Dto2D(DeCbDPCM);
               int[][] DeCrCompressArr = conver1Dto2D(DeCrDPCM);
               
 //-----------------------------------------De quantum ---------------------------------------------------------------
        
                    int[][] CbDecompressQ = deQuantumCompress(DeCbCompressArr, GT);
                 PixelArray CbQpx = new PixelArray("New Cb DCT matrix",CbDecompressQ,GT.imageWidth,GT.imageHeight); 
                    int[][] CrDecompressQ = deQuantumCompress(DeCrCompressArr, GT);
                 CbCompxD.setVisible(true); 
               
                   
               
               System.out.println("Hit enter to continue");
                  try {
                        System.in.read();
                  } catch (Exception e) {
                          }
                 clearScreen(); 
                 CbQpx.setVisible(false);
                 CbCompxD.setVisible(false);
                 
                 
                 int[][] CbDeDCTArr = deDCTCompress(CbDecompressQ,GT);
                 int[][] CrDeDCTArr = deDCTCompress(CrDecompressQ,GT);
                 
              PixelArray CbDCTpx = new PixelArray("New Cb matrix",CbDeDCTArr,GT.imageWidth,GT.imageHeight); 
                CbApx.setVisible(true);
                
               
               System.out.println("Hit enter to continue");
                  try {
                        System.in.read();
                  } catch (Exception e) {
                          }
                 clearScreen(); 
                 CbApx.setVisible(false);
                 CbDCTpx.setVisible(false);
                 
                 
                 
                  
                 
                  
               int[][] convertB = GT.convertYCbCrtoB(Y_array, CbDeDCTArr, CrDeDCTArr);
               int[][] convertR = GT.convertYCbCrtoR(Y_array, CbDeDCTArr, CrDeDCTArr);
               int[][] convertG = GT.convertYCbCrtoG(Y_array, CbDeDCTArr, CrDeDCTArr);

               PixelArray newRApx =  new PixelArray("new RED matrix",convertR,GT.imageWidth,GT.imageHeight);
               rApx.setVisible(true);  
               
               
               
               System.out.println("Hit enter to continue");
                  try {
                        System.in.read();
                  } catch (Exception e) {
                          }
                 clearScreen(); 
               rApx.setVisible(false);  
               newRApx.setVisible(false);
               
               
               
           
              int[] convertedRGB = GT.convertRGBtoArray(convertR, convertG, convertB);



             new ImageWindow(convertedRGB);
   
                  
                  
                  
                  
               System.out.println("Hit enter");
        try {
            System.in.read();
        } catch (Exception e) {
        }
        System.exit(0);       
               
               
      
	}
        
        
        
        public static void clearScreen() {  
         
   try {
    Robot pressbot = new Robot();
    pressbot.keyPress(17); // Holds CTRL key.
    pressbot.keyPress(76); // Holds L key.
    pressbot.keyRelease(17); // Releases CTRL key.
    pressbot.keyRelease(76); // Releases L key.
 } catch (AWTException ex) {
       System.out.println("damn");
  }
}  
        
        public static int[][] conver1Dto2D(int[] input){
            int[][] result = new int[680][680];
            int temprs[] = new int[680*680];
            int startpos, xpos, ypos;             
            int tempArr[] = new int[64];
            int  temp1d[] = new int[64];
            
            
            for (int i =0; i < ((680*680)/64); i++){
                startpos = i*64;
                for (int j=0; j< 64; j++){
                    tempArr[j] = input[j+startpos];
                    
                }
           
                temp1d = DeZigzag(tempArr);
                
                 for (int jj=0; jj< 64; jj++){
                    temprs[jj+startpos] = temp1d[jj];
                 
                }
              
            }
            
              int count1 = 0;
             for(int i =0; i< 680/8; i++){
                for(int j = 0; j< 680/8; j++){
                 xpos = j * 8;
                 ypos = i * 8;
                
                     for (int a = 0; a < 8; a++) {
                        for (int b = 0; b < 8; b++) {
                            result[a+xpos][b+ypos] = temprs[count1];
                            count1++;
                        }
                     }
                     
                }
             }
            
            return result;
        }
        
        
        
        
        public static int[] convert2Dto1D(int[][] input){
            int[] result = new int[680*680];
             int[] temp = new int[64];
            int[] temp1 = new int[64];
           
            int count1 = 0;
        int xpos;
        int ypos;
            
            for(int i =0; i< 680/8; i++){
                for(int j = 0; j< 680/8; j++){
                    
                xpos = j * 8;
                ypos = i * 8;
                int count = 0;
                
                for (int a = 0; a < 8; a++) {
                    for (int b = 0; b < 8; b++) {
                   temp[count] =  input [xpos+a][ypos+b];
                   count++;
                    }}
                
                temp1 = Zig_Zag(temp);
                
                for (int a=0; a< 64; a++){
                    result[count1]=temp[a];
                 //   System.out.print(result[count1]+",");
                 count1++;
                }
               //     System.out.println();
                }
            }
            
            return result;
        }

        
        
         public static int[][] DCTcompress(int[][] inputArray,  GrafixTools GT){
        int i = 0;
        int j = 0;
        int a = 0;
        int b = 0;
        int x = 0;
        int y = 0;
      
        int BlocSize = 8;
        

        int xpos;
        int ypos;
               
         DCT dctTrans = new DCT();
        float dctArray1[][] = new float[BlocSize][BlocSize];
        float[][] dctArray2 = new float[BlocSize][BlocSize];
        

        int reconstImage1[][] = new int[GT.imageWidth][GT.imageHeight];
        
               for (i = 0; i < GT.imageWidth / BlocSize; i++) {
            for (j = 0; j < GT.imageHeight / BlocSize; j++) {
                // Read in a BLOCK_SIZE x BLOCK_SIZE block, starting at (i * BLOCK_SIZE) -> BLOCK_SIZE;
                xpos = i * BlocSize;
                ypos = j * BlocSize;
				
                for (a = 0; a < BlocSize; a++) {
                    for (b = 0; b < BlocSize; b++) {
					// loading the 8x8 block (matrix dctArray) from the image array (outputArray)
                        dctArray1[a][b] = (float)  inputArray[xpos + a][ypos + b];
                    }
                }

                dctArray2 = dctTrans.slow_fdct(dctArray1);


                
                for (a = 0; a < BlocSize; a++) {
                    for (b = 0; b < BlocSize; b++) {
                        reconstImage1[xpos + a][ypos + b] = (int) dctArray2[a][b];
                    }
                }
                
                

            }
        }
                 
        
        return reconstImage1 ;
        
        }
        
         
         public static int[][] quantumCompress(int[][] inputArray,  GrafixTools GT){
        int i = 0;
        int j = 0;
        int a = 0;
        int b = 0;
        int x = 0;
        int y = 0;
      
        int BlocSize = 8;
        

        int xpos;
        int ypos;
               
         DCT dctTrans = new DCT();
        float dctArray1[][] = new float[BlocSize][BlocSize];
        double[][] dctArray3 = new double[BlocSize][BlocSize];

        int reconstImage1[][] = new int[GT.imageWidth][GT.imageHeight];
        
               for (i = 0; i < GT.imageWidth / BlocSize; i++) {
            for (j = 0; j < GT.imageHeight / BlocSize; j++) {
                // Read in a BLOCK_SIZE x BLOCK_SIZE block, starting at (i * BLOCK_SIZE) -> BLOCK_SIZE;
                xpos = i * BlocSize;
                ypos = j * BlocSize;
				
                for (a = 0; a < BlocSize; a++) {
                    for (b = 0; b < BlocSize; b++) {
					// loading the 8x8 block (matrix dctArray) from the image array (outputArray)
                        dctArray1[a][b] = (float)  inputArray[xpos + a][ypos + b];
                    }
                }

              dctArray3 = CompressionTools.quantitizeBloc(dctArray1,  BlocSize);
     
                for (a = 0; a < BlocSize; a++) {
                    for (b = 0; b < BlocSize; b++) {
                        reconstImage1[xpos + a][ypos + b] = (int) dctArray3[a][b];
                    }
                }
            }
        }

        return reconstImage1 ;
        
        }
         
         
        public static int[][] deQuantumCompress(int[][] inputArray,  GrafixTools GT){
         int i = 0;
        int j = 0;
        int a = 0;
        int b = 0;
        int x = 0;
        int y = 0;
        int counter = 0;
        int BlocSize = 8;
        int Quality =1;

        int xpos;
        int ypos;
        
         float dctArray1[][] = new float[BlocSize][BlocSize];
        double [][] dctArray2 = new double[BlocSize][BlocSize];
        
  
        
        int reconstImage[][] = new int[GT.imageWidth][GT.imageHeight];
        int reconstImage1[][] = new int[GT.imageWidth][GT.imageHeight];
        
         DCT dctTrans = new DCT();
           for (i = 0; i < GT.imageWidth/BlocSize; i++) {
            for (j = 0; j < GT.imageHeight/BlocSize; j++) {
                // Read in a BLOCK_SIZExBLOCK_SIZE block, starting at (i * BLOCK_SIZE) -> BLOCK_SIZE;
				// Reading an 8x8 block from the reconstructed image matrix
                xpos = i * BlocSize;
                ypos = j * BlocSize;

                for (a = 0; a < BlocSize; a++) {
                    for (b = 0; b < BlocSize; b++) {
					// filling the 8x8 matrix dctArray2 from the reconstImage matrix:
                        dctArray1[a][b] = inputArray[xpos + a][ypos + b];
                    }
                }
                
                // Run the dequantitizer
                // Operate the dequantization operation of the dctTrans object, which takes as parameter a 2D table (dctArray2) and a boolean variable
                
                
                dctArray2 = CompressionTools.dequantitizeImage(dctArray1,  BlocSize);

                for (a = 0; a < BlocSize; a++) {
                    for (b = 0; b < BlocSize; b++) {
						// remplir la nouvelle image reconstruite a partir du bloc 8x8 (dctArray4)
                        reconstImage[xpos + a][ypos + b] =(int) dctArray2[a][b];
                    }
                }
               
            }
        }
   return reconstImage;
}
          
          public static int[][] deDCTCompress(int[][] inputArray,  GrafixTools GT){
         int i = 0;
        int j = 0;
        int a = 0;
        int b = 0;
        int x = 0;
        int y = 0;
        int counter = 0;
        int BlocSize = 8;
        int Quality =1;

        int xpos;
        int ypos;
        
         double dctArray1[][] = new double[BlocSize][BlocSize];
        
        double[][] dctArray3 = new double[BlocSize][BlocSize];
  
        
        int reconstImage[][] = new int[GT.imageWidth][GT.imageHeight];
        int reconstImage1[][] = new int[GT.imageWidth][GT.imageHeight];
        
         DCT dctTrans = new DCT();
           for (i = 0; i < GT.imageWidth/BlocSize; i++) {
            for (j = 0; j < GT.imageHeight/BlocSize; j++) {
                // Read in a BLOCK_SIZExBLOCK_SIZE block, starting at (i * BLOCK_SIZE) -> BLOCK_SIZE;
				// Reading an 8x8 block from the reconstructed image matrix
                xpos = i * BlocSize;
                ypos = j * BlocSize;

                for (a = 0; a < BlocSize; a++) {
                    for (b = 0; b < BlocSize; b++) {
					// filling the 8x8 matrix dctArray2 from the reconstImage matrix:
                        dctArray1[a][b] = inputArray[xpos + a][ypos + b];
                    }
                }
                
                
                dctArray3 = dctTrans.slow_idct(dctArray1);

                // Overwrite with a new reconstructed image
				// Replace the old image recontruite reconstImage with a new image from dctArray4:
                for (a = 0; a < BlocSize; a++) {
                    for (b = 0; b < BlocSize; b++) {
						// remplir la nouvelle image reconstruite a partir du bloc 8x8 (dctArray4)
                        reconstImage[xpos + a][ypos + b] =(int) dctArray3[a][b];
                    }
                }
               
            }
        }
   return reconstImage;
}
        
        
        public static int[] Zig_Zag(int[] input)
{
 int[][] data = new int[8][8];
 int[] zzarr = new int[64];
 int[] result = new int[64];
 int i = 1;
 int j = 1;
 int count = 0 ;
 for (int element = 0; element < 8 * 8; element++)
 {
  data[i - 1][j - 1] = element;
  if ((i + j) % 2 == 0)
  {
   // Even stripes
   if (j < 8)
    j++;
   else
    i+= 2;
   if (i > 1)
    i--;
  }
  else
  {
   // Odd stripes
   if (i < 8)
    i++;
   else
    j+= 2;
   if (j > 1)
    j--;
  }
 }
 
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8 ; b++) {
               zzarr[count] = data[a][b];
               count ++;
            }
        }
        
      for (int x = 0; x< 64; x++){
          result[zzarr[x]] = input[x];
      }  
        
 return result;
}
        
        public static int[] DeZigzag(int[] input){
            int[] result = new int[64];
            int[][] data = new int[8][8];
            int[] zzarr = new int[64];
 
            int i = 1;
             int j = 1;
             int count = 0 ;
             for (int element = 0; element < 8 * 8; element++)
             {
             data[i - 1][j - 1] = element;
             if ((i + j) % 2 == 0)
             {
          // Even stripes
              if (j < 8)
              j++;
              else
              i+= 2;
             if (i > 1)
               i--;
             }
              else
              {
              // Odd stripes
             if (i < 8)
               i++;
              else
              j+= 2;
             if (j > 1)
               j--;
             }
             }
            
             for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8 ; b++) {
               zzarr[count] = data[a][b];
             
               count ++;
            }
        }
             
             for (int x = 0; x< 64; x++){
          result[x] = input[zzarr[x]];
               
                 
      }  
            
            return result;
        }
        
        
        
        public static int[] convertYto1D (int[][] input){
            int[] result = new int[680*680];
            int count = 0;
            for(int i = 0; i< 680; i++){
                for(int j =0; j< 680; j++){
                    result[count]=input[j][i]; 
                  
                    count++;
               
                }
            }
            
          
            
            return result;
        } 
        
        
        public static int[][] convertYto2D (int[] input){  
            int[][] result = new int[680][680];
            int count = 0;
            for(int i = 0; i< 680; i++){
                for(int j =0; j< 680; j++){
                    result[j][i]=input[count]; 
                   count++;
                }
            }
            return result;
        }
        
        
        public static int[] DPCMcompressY(int[] input){
            int[] result = new int[680*680];
            result[0] = input [0];
            for(int i = 1; i< 680*680; i++){
                result[i] = input[i-1] - input[i];
             //   System.out.print(result[i]+",");
             //   if( ((i)% 50)== 0 ) System.out.println();
            }
            
            return result;
        }
        
        public static int[] DPCMcompressBL(int[] input){
            int[] result = new int[680*680];
           
            result[0] = input [0];
            for(int i = 64; i< 680*680; i = i+64){
                
                result[i] = input[i-64] - input[i];
             
            }
            
            return result;
        }
        
        public static int[] DeDPCMcompressY(int[] input){
            int[] result = new int[680*680];
            result[0]= input[0];
            int temp = result[0];
            for(int i = 1; i< 680*680; i++){
                result[i] = temp - input[i];
                temp = result[i];
             //   System.out.print(result[i]+",");
            //    if( ((i)% 50)== 0 ) System.out.println();
            }
            
            
            return result;
        }
        
         public static int[] DeDPCMcompressBL(int[] input){
             
            int[] result = new int[680*680];
            result[0]= input[0];
            int temp = result[0];
           for(int i = 64; i< 680*680; i = i+64){
                result[i] = temp - input[i];
                temp = result[i];
             //   System.out.print(result[i]+",");
            //    if( ((i)% 50)== 0 ) System.out.println();
            }
            
            
            return result;
        }
        
        public static void showBinary(int[] value, int[] freq){
    int n = value.length;
    
     PriorityQueue<HuffmanNode> q  = new PriorityQueue<HuffmanNode>(n, new MyComparator()); 
  
        for (int i = 0; i < n; i++) { 
  
            // creating a Huffman node object 
            // and add it to the priority queue. 
            HuffmanNode hn = new HuffmanNode(); 
  
            hn.val = value[i]; 
            hn.data = freq[i]; 
  
            hn.left = null; 
            hn.right = null; 
  
            // add functions adds 
            // the huffman node to the queue. 
            q.add(hn); 
        } 
  
        // create a root node 
        HuffmanNode root = null; 
  
        // Here we will extract the two minimum value 
        // from the heap each time until 
        // its size reduces to 1, extract until 
        // all the nodes are extracted. 
        while (q.size() > 1) { 
  
            // first min extract. 
            HuffmanNode x = q.peek(); 
            q.poll(); 
  
            // second min extarct. 
            HuffmanNode y = q.peek(); 
            q.poll(); 
  
            // new node f which is equal 
            HuffmanNode f = new HuffmanNode(); 
  
            // to the sum of the frequency of the two nodes 
            // assigning values to the f node. 
            f.data = x.data + y.data; 
            f.val = '-'; 
  
            // first extracted node as left child. 
            f.left = x; 
  
            // second extracted node as the right child. 
            f.right = y; 
  
            // marking the f node as the root node. 
            root = f; 
  
            // add this node to the priority-queue. 
            q.add(f); 
        } 
  
        // print the codes by traversing the tree 
        BinaryCode(root, ""); 
}
        
        
           public static void BinaryCode(HuffmanNode root, String s) 
    { 
  
        // base case; if the left and right are null 
        // then its a leaf node and we print 
        // the code s generated by traversing the tree. 
        if (root.left  == null
            && root.right == null
            ) { 
            root.binary = s;
            
          System.out.println(root.val + ":" + root.binary); 
  
            
  
            return; 
        } 
  
        // if we go to left then add "0" to the code. 
        // if we go to the right add"1" to the code. 
  
        // recursive calls for left and 
        // right sub-tree of the generated tree. 
        BinaryCode(root.left, s + "0"); 
        BinaryCode(root.right, s + "1"); 
    } 
    
    
    
    //---------------------------------------------------------------------------------------
    
   static int[] Freq(int arr[], int n) 
    { 
        Map<Integer, Integer> mp = new HashMap<>(); 
        int i;
        // Traverse through array elements and 
        // count frequencies 
        for ( i= 0; i < n; i++) 
        { 
            if (mp.containsKey(arr[i]))  
            { 
                mp.put(arr[i], mp.get(arr[i]) + 1); 
            }  
            else
            { 
                mp.put(arr[i], 1); 
            } 
        }
        int j= 0;
         
        int[] freq = new int[i];
        
        // Traverse through map and print frequencies 
        for (Map.Entry<Integer, Integer> entry : mp.entrySet()) 
        { 
           // System.out.println(entry.getKey() + " " + entry.getValue()); 
            
            freq[j] = entry.getValue();
            j++;
        } 
        int[] result = new int[j];
        
        for(int a=0; a<j; a++){
            result[a] = freq[a];
        }
        
       return result;
    }  



static int[] value(int arr[], int n) 
    { 
        Map<Integer, Integer> mp = new HashMap<>(); 
        int i;
        // Traverse through array elements and 
        // count frequencies 
        for ( i= 0; i < n; i++) 
        { 
            if (mp.containsKey(arr[i]))  
            { 
                mp.put(arr[i], mp.get(arr[i]) + 1); 
            }  
            else
            { 
                mp.put(arr[i], 1); 
            } 
        }
        int j= 0;
         
        int[] value = new int[i];
        
        // Traverse through map and print frequencies 
        for (Map.Entry<Integer, Integer> entry : mp.entrySet()) 
        { 
           // System.out.println(entry.getKey() + " " + entry.getValue()); 
            
            value[j] = entry.getKey();
            j++;
        } 
        int[] result = new int[j];
        
        for(int a=0; a<j; a++){
            result[a] = value[a];
        }
        
       return result;
    } 
        
        
}

