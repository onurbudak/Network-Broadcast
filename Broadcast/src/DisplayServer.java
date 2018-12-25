import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

public class DisplayServer {
	

    public static void main(String[] args) throws IOException {


        Dimension[] nonStandardResolutions = new Dimension[]{
                WebcamResolution.PAL.getSize(),
               // WebcamResolution.HD720.getSize(),
                new Dimension(200, 100),
                new Dimension(100, 50),

        };

        Webcam webcam;
        webcam = Webcam.getDefault();
        webcam.setCustomViewSizes(nonStandardResolutions);
       // webcam.setViewSize(WebcamResolution.HD720.getSize());
        webcam.open();



        System.out.println("CAM ACILDI");
        ServerSocket server = null;

        server = new ServerSocket(9877);
        System.out.println("SOKET HAZIR");


        ServerSocket finalServer = server;
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

                Socket socket1 = null;
                try {
                    socket1 = finalServer.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(" client baglandý. ");




                BufferedImage bm = webcam.getImage();
                ObjectOutputStream dout = null;

                try {
                    dout = new ObjectOutputStream(socket1.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ImageIcon im = new ImageIcon(bm);





                while (socket1.isConnected()) {



                    bm = webcam.getImage();
                    im = new ImageIcon(bm);

                    try {
                        dout.writeObject(im);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //   l.setIcon(im);
                    try {
                        dout.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }




            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {

                Socket socket1 = null;
                try {
                    socket1 = finalServer.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("  client baglandý ");


      /*  try {
            server = new ServerSocket(9876);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

                BufferedImage bm = webcam.getImage();
                ObjectOutputStream dout = null;

                try {
                    dout = new ObjectOutputStream(socket1.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ImageIcon im = new ImageIcon(bm);






                while (socket1.isConnected()) {



                    bm = webcam.getImage();
                    im = new ImageIcon(bm);

                    try {
                        dout.writeObject(im);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //   l.setIcon(im);
                    try {
                        dout.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }




            }
        });





        thread1.start();
        thread2.start();
    }


    
    
    public  void  serverStream (){


        JFrame frame = new JFrame("SERVER");
        frame.setSize(640, 360);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);


        JLabel l = new JLabel();
        l.setSize(640, 360);
        l.setVisible(true);


        frame.add(l);
        frame.setVisible(true);


    }

}
