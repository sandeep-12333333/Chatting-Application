import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.util.*;
import java.text.*;
import java.io.*;


public class client implements ActionListener {
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();//show message one by one
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    client(){
        ImageIcon i1 = new ImageIcon((ClassLoader.getSystemResource("icons/3.png")));
        Image i2 = i1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(66, 14, 96));
        p1.setBounds(0,0,450,70);//show background panel
        p1.setLayout(null);
        f.add(p1);

            }
        });

        ImageIcon i4 = new ImageIcon((ClassLoader.getSystemResource("icons/2.png")));
        Image i5 = i4.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile= new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        JLabel name = new JLabel("Debasish");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN SERIF", Font.BOLD,18));
        p1.add(name);

        JLabel status = new JLabel("Active now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN SERIF", Font.BOLD,14));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(66,14,96));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.BOLD, 16));

        f.add(send);

        f.setSize(450,700);
        f.setLocation(800, 50);
        f.setUndecorated(true);//for hide max and min header
        f.getContentPane().setBackground(Color.white);

        f.setVisible(true);//show frame

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String out = text.getText();
            JPanel p2 = formatLabel(out);
            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15)); //15 space btw two message
            a1.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(out);
            text.setText("");
            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 185, 211));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }

    public static void main(String[] args) {
        new client();
        try {
            Socket s = new Socket("192.168.56.1",6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true){
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f.validate();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}