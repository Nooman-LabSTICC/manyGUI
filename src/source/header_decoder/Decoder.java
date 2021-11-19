package source.header_decoder;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.math.BigInteger;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.UIManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ruaro
 */
public class Decoder extends javax.swing.JFrame {
  
    LinkedList<MessageType> messages;
    
    private static final int FLIT1 = 1;
    private static final int FLIT2 = 2;
    private static final int FLIT3 = 3;
      
     
    public Decoder(String headerHex) {
        initComponents();
        headerValueTextField.setText(headerHex);
        labelChipId.setText("");
        labelXpos.setText("");
        labelYpos.setText("");
        labelFBITS.setText("");
        labelPayloadLenght.setText("");
        labelMessaegType.setText("");
        labelMSHR.setText("");
        labelReserved.setText("");
        
        messages = new LinkedList<>();
        
        messages.add(new MessageType("RESERVED",            0, "reserved"));
        messages.add(new MessageType("LOAD REQ",            31, "load request"));
        messages.add(new MessageType("PREFETCH REQ",        1, "prefetch request, unused"));
        messages.add(new MessageType("STORE REQ",           2, "store request"));
        messages.add(new MessageType("BLK STORE REQ",       3, "block-store request, unused"));
        messages.add(new MessageType("BLKINIT STORE REQ",   4, "block-store init request, unused"));
        messages.add(new MessageType("CAS REQ",             5, "compare and swap request"));
        messages.add(new MessageType("CAS P1 REQ",          6, "first phase of a CAS request, only used within L2"));
        messages.add(new MessageType("CAS P2Y REQ",         7, "second phase of a CAS request if the comparison returns true, only used within L2"));
        messages.add(new MessageType("CAS P2N REQ",         8, "second phase of a CAS request if the comparison returns false, only used within L2"));
        messages.add(new MessageType("SWAP REQ",            9, "atomic swap request"));
        messages.add(new MessageType("SWAP P1 REQ",         10, "first phase of a swap request"));
        messages.add(new MessageType("SWAP P2 REQ",         11, "second phase of a swap request"));
        messages.add(new MessageType("WB REQ",              12, "write-back request"));
        messages.add(new MessageType("WBGUARD REQ",         13, "write-back guard request"));
        messages.add(new MessageType("NC LOAD REQ",         14, "non-cacheable load request"));
        messages.add(new MessageType("NC STORE REQ",        15, "non-cacheable store request"));
        messages.add(new MessageType("INTERRUPT FWD",       32, "interrupt forward"));
        messages.add(new MessageType("LOAD FWD",            16, "downgrade request due to a load request"));
        messages.add(new MessageType("STORE FWD",           17, "downgrade request due to a store request"));
        messages.add(new MessageType("INV FWD",             18, "invalidate request"));
        messages.add(new MessageType("LOAD MEM",            19, "load request to memory"));
        messages.add(new MessageType("STORE MEM",           20, "store request to memory"));
        messages.add(new MessageType("LOAD FWDACK",         21, "acknowledgement of a LOAD FWD request"));
        messages.add(new MessageType("STORE FWDACK",        22, "acknowledgement of a STORE FWD request"));
        messages.add(new MessageType("INV FWDACK",          23, "acknowledgement of a INV FWD request"));
        messages.add(new MessageType("LOAD MEM ACK",        24, "acknowledgement of a LOAD MEM request"));
        messages.add(new MessageType("STORE MEM ACK",       25, "acknowledgement of a STORE MEM request"));
        messages.add(new MessageType("NC LOAD MEM ACK",     26, "acknowledgement of a NC LOAD REQ request, unused"));
        messages.add(new MessageType("NC STORE MEM ACK",    27, "acknowledgement of a NC STORE REQ request"));
        messages.add(new MessageType("NODATA ACK",          28, "acknowledgement to L1.5 without data"));
        messages.add(new MessageType("DATA ACK",            29, "acknowledgement to L1.5 with data"));
        messages.add(new MessageType("ERROR",               30, "error message, unused"));
        messages.add(new MessageType("INTERRUPT",           33, "interrupt"));
        messages.add(new MessageType("L2 LINE FLUSH REQ",   34, "flush a specific L2 cache line, used only within L2"));
        messages.add(new MessageType("L2 DIS FLUSH REQ",    35, "displacement of a L2 cache line, used only within L2"));
    
                
        //Set frame at center
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        decode(FLIT1);
    
    }
    
    String getMessageName(int value){
        for (MessageType message : messages) {
            if (message.getValue() == value){
                return message.getMessageName();
            }
        }
        
        return value+" Unknown Value";
    }
    
    String getMessageDescription(int value){
        for (MessageType message : messages) {
            if (message.getValue() == value){
                return message.getDescription();
            }
        }
        
        return "Wrong Message Value";
    }

    private void decode(int flit){
        try { //800000008084C008
            if (headerValueTextField.getText().toString().length() > 16){
                System.out.println("Error: Input <"+headerValueTextField.getText().toString()+"> has a length > 16");
                throw new Exception("Error: Input <"+headerValueTextField.getText().toString()+"> has a length > 16");
            }
            BigInteger input = new BigInteger(headerValueTextField.getText().toString(), 16);
            String result = input.toString(2);
            
            int pad =   64 - result.length();
            for (int i = 0; i < pad; i++) {
                result = "0" + result;
            }
            
            //System.out.println(result);
            
            //System.out.println(result.substring(63, 64));
            
            int INVERTER = 63;
            int start_index, end_index;
            String parcial_value;
            
            switch(flit){
                case FLIT1:
                    
                    jLabel1.setText("ChipID:");
                    start_index = INVERTER - 63;
                    end_index   = INVERTER - 50;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelChipId.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));

                    jLabel2.setText("Xpos:");
                    start_index = INVERTER - 49;
                    end_index   = INVERTER - 42;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelXpos.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));

                    jLabel3.setText("Ypos:");
                    start_index = INVERTER - 41;
                    end_index   = INVERTER - 34;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelYpos.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));

                    jLabel4.setText("FBITS:");
                    start_index = INVERTER - 33;
                    end_index   = INVERTER - 30;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelFBITS.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));

                    jLabel5.setText("PayloadLengh:");
                    start_index = INVERTER - 29;
                    end_index   = INVERTER - 22;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelPayloadLenght.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));

                    jLabel6.setText("Message Type:");
                    start_index = INVERTER - 21;
                    end_index   = INVERTER - 14;
                    parcial_value = result.substring(start_index, ++end_index);
                    int msg_value = Integer.parseInt(parcial_value, 2);
                    labelMessaegType.setText(getMessageName(msg_value));
                    labelMessaegType.setToolTipText(getMessageDescription(msg_value));

                    jLabel7.setText("MSHR/TAG:");
                    start_index = INVERTER - 13;
                    end_index   = INVERTER - 6;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelMSHR.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));

                    jLabel8.setText("Reserved:");
                    start_index = INVERTER - 5;
                    end_index   = INVERTER - 0;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelReserved.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));
                    break;
               
                case FLIT2:
                    
                    jLabel1.setText("Address:");
                    start_index = INVERTER - 63;
                    end_index   = INVERTER - 16;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelChipId.setText(String.valueOf(Long.toHexString(Long.parseLong(parcial_value, 2))));
                    
                    jLabel2.setText("Options:");
                    start_index = INVERTER - 15;
                    end_index   = INVERTER - 0;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelXpos.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));
                    
                    
                    jLabel3.setText("");
                    jLabel4.setText("");
                    jLabel5.setText("");
                    jLabel6.setText("");
                    jLabel7.setText("");
                    jLabel8.setText("");
                    labelYpos.setText("");
                    labelFBITS.setText("");
                    labelPayloadLenght.setText("");
                    labelMessaegType.setText("");
                    labelMSHR.setText("");
                    labelReserved.setText("");
                    
                    break;
                    
                case FLIT3:
                    
                    jLabel1.setText("Src ChipID:");
                    start_index = INVERTER - 63;
                    end_index   = INVERTER - 50;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelChipId.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));
                    
                    jLabel2.setText("Src Xpos:");
                    start_index = INVERTER - 49;
                    end_index   = INVERTER - 42;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelXpos.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));

                    jLabel3.setText("Src Ypos:");
                    start_index = INVERTER - 41;
                    end_index   = INVERTER - 34;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelYpos.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));
                    
                    jLabel4.setText("Src FBITS:");
                    start_index = INVERTER - 33;
                    end_index   = INVERTER - 30;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelFBITS.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));

                    jLabel5.setText("Src Option:");
                    start_index = INVERTER - 29;
                    end_index   = INVERTER - 0;
                    parcial_value = result.substring(start_index, ++end_index);
                    labelPayloadLenght.setText(String.valueOf(Integer.parseInt(parcial_value, 2)));
                    
                    
                    jLabel6.setText("");
                    jLabel7.setText("");
                    jLabel8.setText("");
                    labelMessaegType.setText("");
                    labelMSHR.setText("");
                    labelReserved.setText("");
                    
                    break;
                    
            }
            
        } catch (Exception e) {
            labelChipId.setText("wrong input format");
            labelXpos.setText("wrong input format");
            labelYpos.setText("wrong input format");
            labelFBITS.setText("wrong input format");
            labelPayloadLenght.setText("wrong input format");
            labelMessaegType.setText("wrong input format");
            labelMSHR.setText("wrong input format");
            labelReserved.setText("wrong input format");
            //e.printStackTrace();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerValueTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelChipId = new javax.swing.JLabel();
        labelXpos = new javax.swing.JLabel();
        labelYpos = new javax.swing.JLabel();
        labelFBITS = new javax.swing.JLabel();
        labelPayloadLenght = new javax.swing.JLabel();
        labelMessaegType = new javax.swing.JLabel();
        labelMSHR = new javax.swing.JLabel();
        labelReserved = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jComboBoxFlitType = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.Color.white);
        setResizable(false);

        headerValueTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headerValueTextFieldActionPerformed(evt);
            }
        });
        headerValueTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                KeyReleased(evt);
            }
        });

        jLabel1.setText("ChipID:");

        jLabel2.setText("Xpos:");

        jLabel3.setText("Ypos:");

        jLabel4.setText("FBITS:");

        jLabel5.setText("PayloadLengh:");

        jLabel6.setText("Message Type:");

        jLabel7.setText("MSHR/TAG:");

        jLabel8.setText("Reserved:");

        labelChipId.setText("jLabel9");

        labelXpos.setText("jLabel9");

        labelYpos.setText("jLabel9");

        labelFBITS.setText("jLabel9");

        labelPayloadLenght.setText("jLabel9");

        labelMessaegType.setText("jLabel9");

        labelMSHR.setText("jLabel9");

        labelReserved.setText("jLabel9");

        jLabel9.setText("Header (hex):");

        jComboBoxFlitType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FLIT1", "FLIT2", "FLIT3"}));
        jComboBoxFlitType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFlitTypeActionPerformed(evt);
            }
        });

        jLabel10.setText("Flit Type");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(headerValueTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2))
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelChipId, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelXpos, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelYpos, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelFBITS, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelPayloadLenght, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMessaegType, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMSHR, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelReserved, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 112, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxFlitType, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(headerValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(labelChipId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelXpos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelYpos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(labelFBITS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelPayloadLenght))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(labelMessaegType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(labelMSHR))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelReserved))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxFlitType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void headerValueTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_headerValueTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_headerValueTextFieldActionPerformed

    private void KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeyReleased
        int flit = jComboBoxFlitType.getSelectedIndex() + 1; //+1 because index start in 0
        decode(flit);
    }//GEN-LAST:event_KeyReleased

    private void jComboBoxFlitTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFlitTypeActionPerformed
        int flit = jComboBoxFlitType.getSelectedIndex() + 1; //+1 because index start in 0
        decode(flit);
    }//GEN-LAST:event_jComboBoxFlitTypeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField headerValueTextField;
    private javax.swing.JComboBox<String> jComboBoxFlitType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelChipId;
    private javax.swing.JLabel labelFBITS;
    private javax.swing.JLabel labelMSHR;
    private javax.swing.JLabel labelMessaegType;
    private javax.swing.JLabel labelPayloadLenght;
    private javax.swing.JLabel labelReserved;
    private javax.swing.JLabel labelXpos;
    private javax.swing.JLabel labelYpos;
    // End of variables declaration//GEN-END:variables
}
