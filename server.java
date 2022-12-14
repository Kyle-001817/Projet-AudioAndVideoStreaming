package server;

import java.io.File;
     import java.io.FileInputStream;
     import java.io.ObjectInputStream;
     import java.io.ObjectOutputStream;
     import java.net.ServerSocket;
     import java.net.Socket;
     import javax.swing.JOptionPane;


     public class server
     {

  private Socket soc;
  private ServerSocket serSoc;
  private ObjectInputStream ois;

  server()
 {
    try {
        System.out.println("En Attente...");
        serSoc = new ServerSocket(4576);
        while (true)
        {
            soc = serSoc.accept();
            ois = new ObjectInputStream(soc.getInputStream());
            String str = (String) ois.readObject();
            checkStatus(str);
        }
       } 
    catch (Exception e)
    {
        e.printStackTrace();
    }
  }

   private void checkStatus(String str)
  {
    try
    {
        if (str.equals("StreamingVideo"))
        {
            String file = (String) ois.readObject();
            process(file);
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }

  }

   private void process(String file)
  {
    try
    {
        System.out.println("Connected");
        File file2 = new File("E:\\ITU\\S3\\Mr Naina\\VideoStreaming\\Nouveau dossier\\1-Mety Socket\\" + file);

        if (file2.exists() == false) 
        {
            JOptionPane.showMessageDialog(null,"Fichier Inexistant, Sorry!!!");
        }

        FileInputStream fis = new FileInputStream(file2);
        byte[] b = new byte[fis.available()];
        fis.read(b);
        fis.close();

        Socket socket = new Socket("localhost",4576);
        ObjectOutputStream oos = new ObjectOutputStream(
                socket.getOutputStream());
        oos.writeObject("REP");
        oos.writeObject(b);
        oos.writeObject(file2.getName());

     }
      catch (Exception e)
     {
        e.printStackTrace();
    }

  }

    public static void main(String args[])
    {
        new server();
    }
}