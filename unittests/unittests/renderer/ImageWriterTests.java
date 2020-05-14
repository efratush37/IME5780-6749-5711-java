package unittests.renderer;

import org.junit.Test;
import renderer.ImageWriter;

import java.awt.*;

/**
 * Test for the image writer class
 *
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class ImageWriterTests {
    @Test
    public void ImageWiterWriteToImageTest() {
        ImageWriter IMwr=new ImageWriter("ImageWriterTest", 1600, 1000, 800,500);
        int NX=IMwr.getNx();
        int NY=IMwr.getNy();
        for (int i=0; i<NX; i++){
            for (int j=0; j<NY; j++){
                if (i % 50 == 0 || j % 50 == 0) {
                    IMwr.writePixel(i, j, Color.WHITE);
                } else {
                    IMwr.writePixel(i, j, Color.BLACK);
                }
            }
        }
        IMwr.writeToImage();
    }





}