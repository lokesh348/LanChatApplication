import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.*;


public class ChatProject extends JFrame {

    public static final String PIPE_DELIMETER = "\\|\\|";
    private JPanel panel1;
    private JTextField textField1;
    private JButton LOGINButton;

    private static ChatPage chatFrame;

    public static final String MESSAGE_FORMAT = "message||{receiver}::{sender}::{message}";
    public static final String LIST_FORMAT = "namelist||{onlinelist}";

    Map<String, String> storeAllMessage = new HashMap<>() ;

    public ChatProject() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);






        LOGINButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                if (text is empty) {
//                    show jdialg
//                    return;
//                }
                if (textField1.getText().length() <= 0) {
                    showMessageDilogpane();
                    return;
                } else {
                    setVisible(false);

                    // chatFrame.labelname.setText(textField1.getText());
                    String clientName = textField1.getText();
                    String uuid = UUID.randomUUID().toString();
                    clientName = clientName + "::" + uuid;
                    System.out.println("this is client"+ clientName);
                    connetToServer(clientName);

                }
            }
        });
    }

    public void showMessageDilogpane() {
        JOptionPane.showMessageDialog(this, "please Enter you Name...");
    }

    // "lokesh kumar verma"
    // prefix -> l, lo, lok, loke, lokesh, lokesh k,........
    //a, ma, rma, erma, verma......

    //lokesh kumar vermaabcd
    //abcdlokesh kumar ....

    //delimeter
    //separator
    //lokesh kumar === lokes, h kumar
    // first = lokesh
    //

    //append, prepend
    //

    //message format
    //namelist||aman::38423,jitender::4234,rakesh::34234

    // a ---- c,    name = c , id = 1234, hello world

    //message||id::hello world

    public void connetToServer(String clientName) {
        try {
            Socket serverCon = new Socket("localhost", 3333);
            Utility.send(serverCon, clientName);
            chatFrame = new ChatPage(serverCon, storeAllMessage);
            chatFrame.setSize(1000, 500);
            chatFrame.setVisible(true);
            chatFrame.setTitle(clientName);
//            JScrollPane scroll = new JScrollPane(chatFrame.textArea1);
//            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//            chatFrame.add(scroll);

            Runnable runnable = () -> {
                try {
                    while (true) {
                        System.out.println("inside client recieve......");
                        String payload = Utility.recieve(serverCon);
                        System.out.println(payload);
                        if (payload != null && !payload.isEmpty()) {
                            System.out.println("inside payload check");

                            String prefix = payload.split(PIPE_DELIMETER)[0];
                            System.out.println( " this is prefix "+ prefix);
                            String suffix = payload.split(PIPE_DELIMETER)[1];
                            System.out.println("this is suffix"+ suffix);

                            if ("namelist".equals(prefix)) {
                               // System.out.println("inside namelist variable");
                                String[] split = suffix.split(",");
                                if (split.length > 0) {
                                    DefaultListModel<String> clientNamesModel = chatFrame.getClientNamesModel();

                                   clientNamesModel.removeAllElements();
                                    for (String nameWithId : split) {
                                        if(nameWithId.equals(clientName)){
                                            continue;
                                        }
                                        else {
                                            String[] splitNameId = nameWithId.split("::");
                                            String ClientName1 = splitNameId[0];
                                            String ClientId = splitNameId[1];
                                         //   addNameIdMap.put(ClientId,ClientName1);
                                          //  String onlineclientname = addNameIdMap.get(ClientId);
                                          //  System.out.println("online client name " + onlineclientname);
                                           //clientNamesModel.removeAllElements();
                                            clientNamesModel.addElement(nameWithId);
                                        }
                                    }
                                }
                            }
                            else if ("message".equals(prefix)) {
                                String receiverId = suffix.split("::")[0];
                                String senderId = suffix.split("::")[1];
                                String message = suffix.split("::")[2];
                                System.out.println( "suffix  end  "+message);

                                String existingValue = storeAllMessage.get(senderId);
                                System.out.println("chatprejct receiverId " + receiverId);
                                String updatedValue = (existingValue == null ? "" : existingValue) + "\n" + message;
                                storeAllMessage.put(senderId, updatedValue);
                                System.out.println("upadated value message "+ updatedValue);
                                if(senderId.equals(chatFrame.currentSelectedId)){


                                    chatFrame.textArea1.setText("Client Message"+updatedValue);

                                }

//                               for(Map.Entry<String,String> currentmessage : storeAllMessage.entrySet()){
//                                   System.out.println(currentmessage.getValue());
//                                  //chatFrame.textArea1.setText( "Client Message :"+currentmessage.getValue());
//
//                                }


                                // chatFrame.textArea1.setText("client message : " + message);


                            }
                        }
                        //Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println("client is unable to connect to server...");
            JOptionPane.showMessageDialog(this, "client is unable to connect to server...");
            System.exit(-1);
        }
    }


    public static void main(String[] args) {
        ChatProject p1 = new ChatProject();
        p1.setSize(500, 400);
        p1.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 5, new Insets(0, 0, 0, 0), -1, -1));
        Font panel1Font = this.$$$getFont$$$(null, -1, 28, panel1.getFont());
        if (panel1Font != null) panel1.setFont(panel1Font);
        panel1.setPreferredSize(new Dimension(1000, 500));
        textField1 = new JTextField();
        Font textField1Font = this.$$$getFont$$$("Consolas", Font.BOLD, 36, textField1.getFont());
        if (textField1Font != null) textField1.setFont(textField1Font);
        textField1.setText("");
        panel1.add(textField1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 50), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(1, 4, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(1, 3, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        LOGINButton = new JButton();
        LOGINButton.setText("LOGIN");
        LOGINButton.setVerticalAlignment(0);
        LOGINButton.setVerticalTextPosition(0);
        panel1.add(LOGINButton, new GridConstraints(2, 2, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(50, 50), new Dimension(50, 50), new Dimension(150, 50), 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 36, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16446460));
        label1.setText("Full_Name\n");
        panel1.add(label1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }


}
class Utility{
    public static void send(Socket serverSocket, String message) {
        try {
            OutputStream outputStream = serverSocket.getOutputStream();
            DataOutputStream dout = new DataOutputStream(outputStream);
            dout.writeUTF(message);
            dout.flush();
        } catch (Exception e) {
            System.out.println("ERROR in send - " + e.getMessage());
        }
    }


    public static String recieve(Socket serverSocket) {
        try {
            InputStream inputStream = serverSocket.getInputStream();
            DataInputStream din = new DataInputStream(inputStream);
            return din.readUTF();
        } catch (Exception e) {
            System.out.println("ERROR in send - " + e.getMessage());
        }
        return "";
    }

}




