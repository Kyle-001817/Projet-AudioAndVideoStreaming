package client;

import server.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.media.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.StackPane;
import javafx.beans.property.*;
import javafx.stage.Screen;
import javafx.beans.binding.*;
import javax.print.attribute.standard.*;
import javax.swing.*;
import javax.swing.border.*;


  public class client extends JFrame
  {
    clientreceive cr;
    JFXPanel p;

public void init()
{
    clientreceive cr = new clientreceive();
}

public client()
{
    getContentPane().setLayout(null);
    getContentPane().setBackground(Color.WHITE);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JLabel l = new JLabel("enter file name");
    l.setBounds(50, 30, 100, 50);
    getContentPane().add(l);

    final JTextField t = new JTextField();
    t.setBounds(160, 45, 100, 30);
    getContentPane().add(t);

    JButton b = new JButton("Acceder");
    b.setBounds(145, 100, 100, 30);
    getContentPane().add(b);

    b.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent arg0)
        {
            sendREQ(t.getText());

        }
    });

    p = new JFXPanel();
    p.setBorder(new LineBorder(new Color(0, 0, 0), 1));
    p.setBounds(0, 200, 509, 317);
    getContentPane().add(p);

    setSize(600,600);
    setVisible(true);
}

protected void sendREQ(String path)
{
    try {
        Socket soc = new Socket("localhost", 4576);
        ObjectOutputStream oos = new ObjectOutputStream(
                soc.getOutputStream());
        oos.writeObject("StreamingVideo");
        oos.writeObject(path);
        System.out.println(path);

        JFXPanel VFXPanel = new JFXPanel();
        File video_source = new File(path);
        Media media = new Media(video_source.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        MediaView viewer = new MediaView(player);

        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        javafx.geometry.Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        viewer.setX((screen.getWidth() - p.getWidth()) / 2);
        viewer.setY((screen.getHeight() - p.getHeight()) / 2);

        DoubleProperty width = viewer.fitWidthProperty();
        DoubleProperty height = viewer.fitHeightProperty();
        width.bind(Bindings.selectDouble(viewer.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(viewer.sceneProperty(), "height"));
        viewer.setPreserveRatio(true);
        root.getChildren().add(viewer);
        VFXPanel.setScene(scene);
        player.play();
        p.setLayout(new BorderLayout());
        p.add(VFXPanel, BorderLayout.CENTER);

    }
    catch (UnknownHostException e)
    {

        e.printStackTrace();
    }
    catch (IOException e)
    {

        e.printStackTrace();
    }

}

public static void main(String args[])
{
    new client();
}
}