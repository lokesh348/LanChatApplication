import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;



public class ChatPage extends JFrame {
    public static final String MESSAGE_FORMAT = "message||{receiver}::{sender}::{message}";
    public static final String LIST_FORMAT = "namelist||{onlinelist}";
    private JTextField massagebox;
    public JTextArea textArea1;


    private JTextArea textArea2;
    private JPanel rootpanel;
    private JList chatMembersListObj;
    public JLabel labelname;
    private JButton SANDButton;
    // public    JTextArea textArea3;
    public DefaultListModel<String> clientNamesModel;

    public DefaultListModel<String> getClientNamesModel() {
        return clientNamesModel;
    }

    private List<Client> clientNamesArrayList = new ArrayList<>();


    private Client currentlySelectedClient = null;
    private final Socket serverCon;
    public String currentSelectedId;
    public String currentUserName;

    private Map<String, String> incomingMessagesMap;

    public ChatPage(Socket serverCon, Map<String, String> incomingMessagesMap) {
        this.serverCon = serverCon;
        this.incomingMessagesMap = incomingMessagesMap;
        this.setContentPane(rootpanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        // clientNamesArrayList.add(new Client("mohit"));
        //  clientNamesArrayList.add(new Client("pawan"));

        this.clientNamesModel = new DefaultListModel<>();
        chatMembersListObj.setModel(clientNamesModel);
        refreshList();
//        chatMembersListObj.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                int firstIndex = e.getFirstIndex();
//
//                currentlySelectedClient = clientNamesArrayList.get(firstIndex);
//
//            }
//        });

        SANDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentSelectedId == null) {
                    return;
                }
                String sanderNameWithId = getTitle();
                String[] sanderNameId = sanderNameWithId.split("::");
                String senderId = sanderNameId[1];
                String massageboxtext = massagebox.getText();
                textArea2.setText("your message : " + massageboxtext);
                String payload = MESSAGE_FORMAT.replace("{receiver}", currentSelectedId).replace("{sender}", senderId).replace("{message}", massageboxtext);
                Utility.send(serverCon, payload);
                massagebox.setText(" ");

            }
        });

        chatMembersListObj.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String clientid = (String) chatMembersListObj.getSelectedValue();
                int selectedIndex = chatMembersListObj.getSelectedIndex();
                //System.out.println(clientid);
                String[] splitclientid = clientid.split("::");
                currentUserName = splitclientid[0];
                System.out.println("destination current client  name" + currentUserName);
                // textArea3.setText( "Client Name:"+currentUserName);
                //  client_Name.setText("Client Name :" + currentUserName);
                currentSelectedId = splitclientid[1];
                System.out.println(currentSelectedId);
                String messageHistroy = incomingMessagesMap.get(currentSelectedId);
                System.out.println("message hisotry" + messageHistroy);
                textArea1.setText(messageHistroy);
                labelname.setText(currentUserName);


            }
        });
        //textArea1.setEditable(false);
        //textArea1.setSize(25,50);

        // JScrollPane scroll = new JScrollPane(textArea1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //      rootpanel.add(scroll);

    addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closing the window");
                if (serverCon != null) {
                    try {
                        serverCon.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

    }

    public void addItemToList() {

    }

    private void refreshList() {
        for (Client client : clientNamesArrayList) {
            clientNamesModel.addElement(client.getName());

        }
    }


    public JPanel getRootpanel() {
        return rootpanel;
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        //   $$$setupUI$$$();
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
        rootpanel = new JPanel();
        rootpanel.setLayout(new GridLayoutManager(8, 4, new Insets(0, 0, 0, 0), -1, -1));
        rootpanel.setForeground(new Color(-8025683));
        rootpanel.setPreferredSize(new Dimension(1000, 500));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootpanel.add(panel1, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        SANDButton = new JButton();
        SANDButton.setText("SAND");
        panel1.add(SANDButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textArea1 = new JTextArea();
        // JScrollPane scroll = new JScrollPane (textArea1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // rootpanel.add(scroll);

        textArea1.setBackground(new Color(-1));
        textArea1.setCaretColor(new Color(-15263977));
        textArea1.setDisabledTextColor(new Color(-12696182));
        Font textArea1Font = this.$$$getFont$$$(null, -1, 22, textArea1.getFont());
        if (textArea1Font != null) textArea1.setFont(textArea1Font);
        textArea1.setForeground(new Color(-16446460));
        textArea1.setLineWrap(true);
        textArea1.setText("");
		textArea1.setEditable(false);
		 

        rootpanel.add(textArea1, new GridConstraints(2, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setFocusTraversalPolicyProvider(false);
        label1.setFocusable(false);
        Font label1Font = this.$$$getFont$$$("Blackoak Std", Font.BOLD, 26, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-13704396));
        label1.setText("ONLINE");
        rootpanel.add(label1, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textArea2 = new JTextArea();
		textArea2.setEditable(false);
        Font textArea2Font = this.$$$getFont$$$(null, -1, 22, textArea2.getFont());
        if (textArea2Font != null) textArea2.setFont(textArea2Font);
        rootpanel.add(textArea2, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 144), null, 0, false));
        massagebox = new JTextField();
        Font massageboxFont = this.$$$getFont$$$(null, -1, 28, massagebox.getFont());
        if (massageboxFont != null) massagebox.setFont(massageboxFont);
        rootpanel.add(massagebox, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 50), null, 0, false));
        chatMembersListObj = new JList();
        chatMembersListObj.setDragEnabled(true);
        Font chatMembersListObjFont = this.$$$getFont$$$(null, -1, 36, chatMembersListObj.getFont());
        if (chatMembersListObjFont != null) chatMembersListObj.setFont(chatMembersListObjFont);
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        chatMembersListObj.setModel(defaultListModel1);
        chatMembersListObj.setSelectionMode(0);
        rootpanel.add(chatMembersListObj, new GridConstraints(2, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        labelname = new JLabel();
        Font labelnameFont = this.$$$getFont$$$(null, -1, 24, labelname.getFont());
        if (labelnameFont != null) labelname.setFont(labelnameFont);
        labelname.setHorizontalAlignment(2);
        labelname.setSize(400, 30);
        labelname.setHorizontalTextPosition(2);
        labelname.setIconTextGap(10);
        labelname.setText(" ");
        labelname.setVerticalAlignment(0);
        labelname.setVerticalTextPosition(0);
        rootpanel.add(labelname, new GridConstraints(0, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(50, 50), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootpanel.add(panel2, new GridConstraints(0, 1, 8, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootpanel.add(panel3, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootpanel.add(panel4, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
        return rootpanel;
    }

}


class Client {
    String name;
    String uuid;

    public Client(String name) {
        this.name = name;
        this.uuid = uuid;

    }
    //public  void setuuid(){
    //  uuid = String.valueOf(UUID.randomUUID());
    //   }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



