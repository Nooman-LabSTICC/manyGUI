/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import deloream.DeloreamMainFrame;
import energyMemory.energyMemGUI.MainEnergyMemoryFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import source.header_decoder.Decoder;
import util.AboutFrame;
import util.MPSoCConfig;

/**
 *
 * @author Marcelo
 */
public final class MainFrame extends javax.swing.JFrame {

    private JPanel nocPanel;
    private JPanel mainPanel;
    private JPanel peripheralPanel;
    private MPSoCInformation mPSoCInformation;
    private boolean run;
    private CheckpointController checkControl;
    private ServiceFilter serviceFilter;
    private FilterForm filter;
    private TaskMappingFrame taskMappingFrame;
    private MPSoCConfig mpsocConfig;
    private int routerToPrintLinkUsage;
    private boolean linkUsage;
    private Image image_icon;
    
    private LinkedList<Integer> unfinished_packet_list;
    /**
     * Creates new form MainFrame
     * 
     * ARRUMAR TASK MAPPING FRAM
     */
    
    public MainFrame(String args[]) {
        this.setLocation(100, 20);
        //URL url = this.getClass().getResource("/icon/gaph_logo.png");
        URL url = this.getClass().getResource("/icon/openpiton.png");   
        this.image_icon = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(image_icon);
        //this.setTitle("MPSoC Debugger [OpenPiton-v1.2]");
        this.setTitle("ManyGUI [OpenPiton-v1.2]");
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        taskMappingFrame = null;
        mpsocConfig = null;
        nocPanel = new JPanel();
        nocPanel.setBackground(Color.blue);
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        peripheralPanel = new JPanel();
        initComponents();
        simulTime.setText("0 ms");
        simulTick.setText("0 ticks");
        frequencyLabel.setText("0 MHz");
        resetCurrentPacketTable();
        simSpeedControlBar.setValue(98);
        stopButton.setEnabled(false);
        nextButton.setEnabled(false);
        //deloreamMenuItem.setEnabled(false);
        playButton.setEnabled(false);
        goButton.setEnabled(false);
        printRouterTotalLinkUsageMenuItem.setVisible(false);
        currentPacketTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //initMPSoC();
        if (args.length > 0){
            if (!new File(args[0]).exists()){
                JOptionPane.showMessageDialog(this, "platform.cfg NOT FOUND: \n"+args[0], "", JOptionPane.ERROR_MESSAGE);
            }
            configProcess(args[0]);
        }
        
        percentLabel.setVisible(false);

    }

    public boolean initMPSoC() {
        
        if (mpsocConfig == null) return false;

        try {
            mPSoCInformation = new MPSoCInformation(mpsocConfig);
            run = false;
            simulTime.setText("0 ms");
            frequencyLabel.setText(mpsocConfig.getFrequencyInMHz()+" MHz");
            simulTick.setText("0 ticks");
            checkControl = new CheckpointController(this, mpsocConfig.getClockPeriodInNs(), mpsocConfig.getThrougphputMonWindow());
            unfinished_packet_list = new LinkedList<>();
            serviceFilter = new ServiceFilter(mpsocConfig, image_icon);
            filter = new FilterForm(mpsocConfig, image_icon);
            createNoCPanel();
            resetAllArrows();
            nextButton.setEnabled(true);
            //deloreamMenuItem.setEnabled(true);
            playButton.setEnabled(true);
            stopButton.setEnabled(false);
            goButton.setEnabled(false);
            resetCurrentPacketTable();
            if (taskMappingFrame != null) {
                taskMappingFrame.setInformation(mpsocConfig, mPSoCInformation);
            }
            routerToPrintLinkUsage = -1;
            return true;
            //JOptionPane.showMessageDialog(this, "The project has been successfully loaded!");

        } catch (Exception ex) {
            stopButton.setEnabled(false);
            nextButton.setEnabled(false);
            //deloreamMenuItem.setEnabled(false);
            playButton.setEnabled(false);
            goButton.setEnabled(false);
            nocPanel.removeAll();
            nocPanel.repaint();
            mPSoCInformation = null;
            JOptionPane.showMessageDialog(this, "Output router file not created yet. TRY AGAIN!!!", "Attention", JOptionPane.WARNING_MESSAGE);
            return false;
            
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        panelControl = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        currentPacketTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        simSpeedControlBar = new javax.swing.JSlider();
        simulTime = new javax.swing.JLabel();
        simulTick = new javax.swing.JLabel();
        percentLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        nextButton = new javax.swing.JButton();
        playButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        goButton = new javax.swing.JButton();
        goTextField = new javax.swing.JTextField();
        frequencyLabel = new javax.swing.JLabel();
        scrollPanelNoC = new javax.swing.JScrollPane(mainPanel,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        resetSimulation = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        printRouterTotalLinkUsageMenuItem = new javax.swing.JMenuItem();
        deloreamMenuItem = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        taskMappingMenuItem = new javax.swing.JMenuItem();
        jMenuItemEnergyMemoryProf = new javax.swing.JMenuItem();
        serviceListMenuItem = new javax.swing.JMenuItem();
        taskListMenuItem = new javax.swing.JMenuItem();
        messageLogMenuItem = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mainServiceFilter = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        packetFormatMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(700, 200));

        panelControl.setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Current Packet Information"));

        currentPacketTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Current", "Target", "Service", "Size"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        currentPacketTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                currentPacketTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(currentPacketTable);
        if (currentPacketTable.getColumnModel().getColumnCount() > 0) {
            currentPacketTable.getColumnModel().getColumn(2).setMinWidth(200);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
        );

        panelControl.add(jPanel1);
        jPanel1.setBounds(710, 0, 450, 90);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Speed Control"));
        jPanel2.setLayout(null);

        simSpeedControlBar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                simSpeedControlBarStateChanged(evt);
            }
        });
        simSpeedControlBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                simSpeedControlBarMouseReleased(evt);
            }
        });
        jPanel2.add(simSpeedControlBar);
        simSpeedControlBar.setBounds(0, 30, 230, 40);

        simulTime.setText("as");
        simulTime.setToolTipText("");
        jPanel2.add(simulTime);
        simulTime.setBounds(10, 60, 99, 30);

        simulTick.setText("as");
        simulTick.setToolTipText("");
        jPanel2.add(simulTick);
        simulTick.setBounds(120, 60, 99, 30);

        percentLabel.setFont(new java.awt.Font("Ubuntu", 0, 13)); // NOI18N
        percentLabel.setText("jLabel2");
        jPanel2.add(percentLabel);
        percentLabel.setBounds(190, 10, 44, 16);

        panelControl.add(jPanel2);
        jPanel2.setBounds(230, 0, 250, 90);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Simulation Control"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nextButton.setFont(nextButton.getFont().deriveFont(nextButton.getFont().getStyle() | java.awt.Font.BOLD, nextButton.getFont().getSize()-3));
        nextButton.setText(">||");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        jPanel3.add(nextButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 60, 48));

        playButton.setFont(playButton.getFont().deriveFont(playButton.getFont().getStyle() | java.awt.Font.BOLD, playButton.getFont().getSize()-1));
        playButton.setText(">");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });
        jPanel3.add(playButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 55, 48));

        stopButton.setFont(stopButton.getFont().deriveFont(stopButton.getFont().getStyle() | java.awt.Font.BOLD, stopButton.getFont().getSize()-1));
        stopButton.setText("STOP");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });
        jPanel3.add(stopButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 31, 70, 48));

        panelControl.add(jPanel3);
        jPanel3.setBounds(0, 0, 230, 90);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Back To", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel4.setLayout(null);

        jLabel1.setText("Time in Ticks");
        jPanel4.add(jLabel1);
        jLabel1.setBounds(6, 16, 129, 17);

        goButton.setText("Go");
        goButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goButtonActionPerformed(evt);
            }
        });
        jPanel4.add(goButton);
        goButton.setBounds(166, 36, 48, 30);
        jPanel4.add(goTextField);
        goTextField.setBounds(6, 37, 154, 30);

        frequencyLabel.setText("asa");
        jPanel4.add(frequencyLabel);
        frequencyLabel.setBounds(140, 10, 80, 20);

        panelControl.add(jPanel4);
        jPanel4.setBounds(480, 0, 230, 90);

        scrollPanelNoC.setBackground(new java.awt.Color(255, 255, 255));
        scrollPanelNoC.getVerticalScrollBar().setUnitIncrement(10);
        scrollPanelNoC.getHorizontalScrollBar().setUnitIncrement(10);

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("New Debugging");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setText("Save Project");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveMenuItem);

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setText("Open Project");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(openMenuItem);

        deleteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        deleteMenuItem.setText("Delete Project");
        deleteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(deleteMenuItem);

        resetSimulation.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        resetSimulation.setText("Reset Simulation");
        resetSimulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetSimulationActionPerformed(evt);
            }
        });
        jMenu1.add(resetSimulation);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Reset Graphical Path");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Exit");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu4.setText("Edit");

        jMenuItem5.setText("Platform Setup");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        printRouterTotalLinkUsageMenuItem.setText("Print Router Total Link Usage");
        printRouterTotalLinkUsageMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printRouterTotalLinkUsageMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(printRouterTotalLinkUsageMenuItem);

        jMenuBar1.add(jMenu4);

        deloreamMenuItem.setText("Tools");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Communication Overview");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        deloreamMenuItem.add(jMenuItem2);

        taskMappingMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        taskMappingMenuItem.setText("Task Mapping Overview");
        taskMappingMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taskMappingMenuItemActionPerformed(evt);
            }
        });
        deloreamMenuItem.add(taskMappingMenuItem);

        jMenuItemEnergyMemoryProf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemEnergyMemoryProf.setText("Energy and Memory Profiler");
        jMenuItemEnergyMemoryProf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEnergyMemoryProfActionPerformed(evt);
            }
        });
        deloreamMenuItem.add(jMenuItemEnergyMemoryProf);

        serviceListMenuItem.setText("Services List");
        serviceListMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceListMenuItemActionPerformed(evt);
            }
        });
        deloreamMenuItem.add(serviceListMenuItem);

        taskListMenuItem.setText("Task List");
        taskListMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taskListMenuItemActionPerformed(evt);
            }
        });
        deloreamMenuItem.add(taskListMenuItem);

        messageLogMenuItem.setText("Message Log");
        messageLogMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageLogMenuItemActionPerformed(evt);
            }
        });
        deloreamMenuItem.add(messageLogMenuItem);

        jMenuItem7.setText("Deloream");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        deloreamMenuItem.add(jMenuItem7);

        jMenuBar1.add(deloreamMenuItem);

        jMenu3.setText("Filters");

        mainServiceFilter.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        mainServiceFilter.setText("Service and PE filter");
        mainServiceFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainServiceFilterActionPerformed(evt);
            }
        });
        jMenu3.add(mainServiceFilter);

        jMenuBar1.add(jMenu3);

        jMenu5.setText("Help");

        jMenuItem3.setText("About");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem3);

        packetFormatMenuItem.setText("Packet Format");
        packetFormatMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                packetFormatMenuItemActionPerformed(evt);
            }
        });
        jMenu5.add(packetFormatMenuItem);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelControl, javax.swing.GroupLayout.DEFAULT_SIZE, 1164, Short.MAX_VALUE)
            .addComponent(scrollPanelNoC)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollPanelNoC, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelControl, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        run = false;
        stopButton.setEnabled(false);
        nextButton.setEnabled(true);
        playButton.setEnabled(true);
        goButton.setEnabled(true);
    }//GEN-LAST:event_stopButtonActionPerformed

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        stopButton.setEnabled(true);
        nextButton.setEnabled(false);
        playButton.setEnabled(false);
        goButton.setEnabled(false);
        run = true;

        new Thread() {
            @Override
            public void run() {
                int time;
                while (run) {
                    if (simSpeedControlBar.getValue() == 0) 
                        continue;
                    
                    if (simSpeedControlBar.getValue() > 98){
                        time = nextPacket(false, 0);
                        unfinished_packet_list.clear();
                    } else
                        time = nextPacket(true, 0);
                    
                    if (time == -1){
                        try { /*sleep(1000);*/ } catch (Exception ex) {}
                        continue;
                    }
                    simulTime.setText(new DecimalFormat("0.00000").format(((float) (time * mpsocConfig.getClockPeriodInNs() / 1000.0f / 1000.0f))) + " ms");
                    simulTick.setText(time + " ticks");
                    try {
                        sleep(400 - simSpeedControlBar.getValue() * 4);
                    } catch (Exception ex) {}
                }
            }
        }.start();
    }//GEN-LAST:event_playButtonActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        System.exit(1);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       
        String platformFilepath = "";
        
        JOptionPane.showMessageDialog(this, "Please, inform the platform.cfg file of testcase", "Testcase Debuggin Information", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser chooser = new JFileChooser(System.getenv("HEMPS_PATH"));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Config File", "cfg");
        chooser.addChoosableFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            platformFilepath = chooser.getSelectedFile().getPath();
            //JOptionPane.showMessageDialog(this, "selected path: "+platformFilepath);
        } else {
            chooser.setVisible(false);
            return;
        }
        
        configProcess(platformFilepath);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void simSpeedControlBarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_simSpeedControlBarMouseReleased
    }//GEN-LAST:event_simSpeedControlBarMouseReleased

    private void simSpeedControlBarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_simSpeedControlBarStateChanged
        percentLabel.setText(Integer.toString(simSpeedControlBar.getValue()));
    }//GEN-LAST:event_simSpeedControlBarStateChanged

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
         if (mpsocConfig == null){
            JOptionPane.showMessageDialog(this, "Please, load a debugging before", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
         } 
        new CommunicationOverview(mpsocConfig, mPSoCInformation, image_icon).setVisible(true);
         
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
         if (mpsocConfig == null){
            JOptionPane.showMessageDialog(this, "Please, load a debugging before", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
         }
        new PlatformSetupFrame(mpsocConfig, checkControl, image_icon);
        
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void resetSimulationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetSimulationActionPerformed
         if (mpsocConfig == null){
            JOptionPane.showMessageDialog(this, "Please, load a debugging before", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        run = false;
        try {
            mpsocConfig = new MPSoCConfig(mpsocConfig.getDebugDirPath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (initMPSoC()){
            JOptionPane.showMessageDialog(this, "The project has been successfully loaded!");
        }
        
         
    }//GEN-LAST:event_resetSimulationActionPerformed

    private void goButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goButtonActionPerformed

        int desiredTime = 0;
        try {
            desiredTime = Integer.parseInt(goTextField.getText());
            

            int packetCounter = mPSoCInformation.getPacketCounterByTime(desiredTime);
            
            if (packetCounter == -1){
                throw new IllegalArgumentException();
            }
            
            int ptime = 0;
            mPSoCInformation.initalizePEInformation();
            simulTime.setText("0 ms");
            simulTick.setText("0 ticks");
            checkControl.reset();
            unfinished_packet_list.clear();
            resetAllArrows();
            
            while (packetCounter > 0) {
                ptime = nextPacket((packetCounter < 50), desiredTime);
                if (ptime > desiredTime) break;
                packetCounter--;
            }
            simulTime.setText(new DecimalFormat("0.00000").format(((float) (ptime * mpsocConfig.getClockPeriodInNs() / 1000.0f / 1000.0f))) + " ms");
            simulTick.setText(ptime + " ticks");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter only numbers and valid times!");
        }
        
    }//GEN-LAST:event_goButtonActionPerformed

    private void mainServiceFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainServiceFilterActionPerformed
         if (mpsocConfig == null){
            JOptionPane.showMessageDialog(this, "Please, load a debugging before", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
         } 
        unfinished_packet_list.clear();
        resetAllArrows();
        //serviceFilter.setVisible(true);
        filter.setVisible(true);
         
    }//GEN-LAST:event_mainServiceFilterActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        if (mpsocConfig == null){
            JOptionPane.showMessageDialog(this, "Please, load a debugging before", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        String name = JOptionPane.showInputDialog(this, "Inform the debugging project name");
        
        if (name == null)
            return;
        
        
        while(name.contains(" ")){
            JOptionPane.showMessageDialog(this, "Name contains space", "Error", JOptionPane.ERROR_MESSAGE);
            
            if (name == null)
                return;
            
            name = JOptionPane.showInputDialog(this, "Inform the debugging project name");
        }
        
        try {
            
            String currentDir = System.getProperty("user.dir");
            //JOptionPane.showMessageDialog(this, currentDir+"/projects");
            
            File file = new File(currentDir+"/projects");
            if (!file.exists())
                file.mkdir();
            
            FileWriter fw = new FileWriter(currentDir+"/projects/"+name+".hdf", false);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(mpsocConfig.getDebugDirPath());
            
            bw.close();
            
            this.setTitle(this.getTitle() + ": "+name);

        } catch (IOException ex) {
        }
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        
        String currentDir = System.getProperty("user.dir");
        //JOptionPane.showMessageDialog(this, currentDir+"/projects");
        
        File file = new File(currentDir+"/projects");
       
        //JOptionPane.showMessageDialog(this, new File("").getAbsoluteFile());
        
        if (!file.exists())
            file.mkdir();
        
        String projectPath = "";
        
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setCurrentDirectory(new File(currentDir+"/projects"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("HeMPS Debugging File", "hdf");
        chooser.addChoosableFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            projectPath = chooser.getSelectedFile().getPath();
        } else {
            chooser.setVisible(false);
            return;
        }
        
        
        File f = new File(projectPath);
        
        RandomAccessFile openFile;
        
        if (f.exists()) {
            
            
            
            try {
                openFile = new RandomAccessFile(projectPath, "r");
                
                mpsocConfig = new MPSoCConfig(openFile.readLine());
                
                initMPSoC();
                
                openFile.close();
                
                String title = this.getTitle();
                
                title = title.substring(0, title.indexOf("]")+1);
                
                this.setTitle(title + ": "+mpsocConfig.getTestcasePath());
                
                //ARRUMAR VER IDEIA DE TER UMA PASTA PROJETOS
                
            } catch (Exception ex) {
                Logger.getLogger(ReadTrafficData.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            //System.out.println("ARQUIVO: " + path + " \nNÃO EXISTE");
            JOptionPane.showMessageDialog(null, "File not exists", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void taskMappingMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taskMappingMenuItemActionPerformed
        if (mpsocConfig == null){
            JOptionPane.showMessageDialog(this, "Please, load a debugging before", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        taskMappingFrame = new TaskMappingFrame(mpsocConfig, mPSoCInformation, image_icon);
    }//GEN-LAST:event_taskMappingMenuItemActionPerformed

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        String currentDir = System.getProperty("user.dir");
        
        File file = new File(currentDir+"/projects");
       
        if (!file.exists()){
            JOptionPane.showMessageDialog(this, "There are no projects created");
            return;
        }
        
        String path="";
        String projectName="";
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setCurrentDirectory(new File(currentDir+"/projects"));
        chooser.setApproveButtonText("Delete");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("HeMPS Debugging File", "hdf");
        chooser.addChoosableFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getPath();
            projectName = chooser.getSelectedFile().getName();
        } else {
            chooser.setVisible(false);
            return;
        }
        
        File f = new File(path);
        
        if (f.delete())
            JOptionPane.showMessageDialog(this, "Project "+ projectName+" successfully deleted!");
        else
            JOptionPane.showMessageDialog(this, "Error during the deletion of project: "+ projectName, "Attention", JOptionPane.WARNING_MESSAGE);
        
    }//GEN-LAST:event_deleteMenuItemActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        int time = nextPacket(true, 0);
        if (time != -1){
            simulTime.setText(new DecimalFormat("0.00000").format(((float) (time * mpsocConfig.getClockPeriodInNs() / 1000.0f / 1000.0f))) + " ms");
            simulTick.setText(time + " ticks");
        }
        goButton.setEnabled(true);
    }//GEN-LAST:event_nextButtonActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        if (mpsocConfig == null){
            JOptionPane.showMessageDialog(this, "Please, load a debugging before", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        resetAllArrows();
        unfinished_packet_list.clear();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void serviceListMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceListMenuItemActionPerformed
        if (mpsocConfig == null){
            JOptionPane.showMessageDialog(this, "Please, load a debugging before", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        
        TreeMap<Integer, String> servicesHash = mpsocConfig.getServicesHash();
        JFrame frame = new JFrame("Current Service List");
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Service");
        model.addColumn("Value");
        
        Set<Integer> values = servicesHash.keySet();
        for (Integer key : values) {
            String[] line = new String[2];
            line[0] = servicesHash.get(key);
            line[1] = Integer.toString(key);
            model.addRow(line);
        }
        
        JTable servicesTable = new JTable(model);
        JScrollPane scrollService = new JScrollPane(servicesTable);
        frame.getContentPane().add(scrollService);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(100, 100);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }//GEN-LAST:event_serviceListMenuItemActionPerformed

    private void printRouterTotalLinkUsageMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printRouterTotalLinkUsageMenuItemActionPerformed
        String routerAddress = JOptionPane.showInputDialog(this, "Inform the router address", routerToPrintLinkUsage);
        routerToPrintLinkUsage = Integer.parseInt(routerAddress);
       // System.out.println("Router "+routerToPrintLinkUsage);
        String linkUsageStr = JOptionPane.showInputDialog(this, "Inform the printing tipe: \n1 - Link Usage\n2 - Time", 1);
        int linkUsageInt = Integer.parseInt(linkUsageStr);
        if (linkUsageInt == 1)
            this.linkUsage = true;
        else
            this.linkUsage = false;
    }//GEN-LAST:event_printRouterTotalLinkUsageMenuItemActionPerformed

    private void taskListMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taskListMenuItemActionPerformed
        if (mpsocConfig == null){
            JOptionPane.showMessageDialog(this, "Please, load a debugging before", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        
        TreeMap<Integer, String> servicesHash = mpsocConfig.getTaskNameHash();
        JFrame frame = new JFrame("Current Task List");
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Task Name");
        model.addColumn("ID");
        
        Set<Integer> values = servicesHash.keySet();
        for (Integer key : values) {
            String[] line = new String[2];
            line[0] = servicesHash.get(key);
            line[1] = Integer.toString(key);
            model.addRow(line);
        }
        
        JTable servicesTable = new JTable(model);
        JScrollPane scrollService = new JScrollPane(servicesTable);
        frame.getContentPane().add(scrollService);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(100, 100);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }//GEN-LAST:event_taskListMenuItemActionPerformed

    private void messageLogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageLogMenuItemActionPerformed
       if (mpsocConfig == null){
            JOptionPane.showMessageDialog(this, "Please, load a debugging before", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        showMessageList();
    }//GEN-LAST:event_messageLogMenuItemActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new AboutFrame().setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        if (mpsocConfig == null){
            JOptionPane.showMessageDialog(this, "Please, load a debugging before", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        new DeloreamMainFrame(mpsocConfig.getTestcasePath()).setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void packetFormatMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_packetFormatMenuItemActionPerformed
  

        String infoFormat = "";
        infoFormat += "Packet format has the following fields:\n";
        infoFormat += "Time | Current Router Address | Service | Payload size | Packet bandwidth | Input port | Target Router | Source task <optional> | Target task <optional>\n\n";
        infoFormat += "Field description:\n";
        infoFormat += "*Time*: Integer decimal value, represent the current tick counter that the header entered by the input port of the router\n";
        infoFormat += "*Current Router Address*: An Integer decimal value with the less significative 8 bits (0xFF), representing the Y address, and the bits 8-15 (0xFF00) representing the X address\n";
        infoFormat += "*Service*: Integer decimal value containing the packet service\n";
        infoFormat += "*Payload size*: Integer decimal value with the amount of flits coming after header\n";
        infoFormat += "*Packet bandwidth*: Integer decimal value with the amount of time in clock cicles from the moment that the header enter by the router until the last flit of the packet exits the router\n";
        infoFormat += "*NoC*: Integer decimal value representing the NoC number (1,2,3)\n";
        infoFormat += "*Input port*: Character value (N,S,W,E) representing the input port where the packet entered into the router (0-EAST, 1-WEST, 2-NORTH, 3-SOUTH, 4-LOCAL)\n";
        infoFormat += "*Target Router*: Same format than Current Router address. Has the address of the header, threfore, the target router of the packet\n";
        infoFormat += "\n\nAuthor: Marcelo Ruaro, more info please contact: mceloruaro@gmail.com\n";
        
        JOptionPane.showMessageDialog(this, infoFormat, "", JOptionPane.INFORMATION_MESSAGE);

        
       
    }//GEN-LAST:event_packetFormatMenuItemActionPerformed

    private void currentPacketTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_currentPacketTableMouseReleased
        new Decoder(mPSoCInformation.getCurrentHeader());
    }//GEN-LAST:event_currentPacketTableMouseReleased

    private void jMenuItemEnergyMemoryProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEnergyMemoryProfActionPerformed
        new Thread() {
            @Override
            public void run() {
                MainEnergyMemoryFrame energyEvaluationFrame = null;
                if (mpsocConfig == null){
                    energyEvaluationFrame = new MainEnergyMemoryFrame();
                } else { 
                    energyEvaluationFrame = new MainEnergyMemoryFrame(mpsocConfig.getX_dimension(), mpsocConfig.getY_dimension(), mpsocConfig.getDebugDirPath());
                }
                
                energyEvaluationFrame.setVisible(true);
                
                
                URL url = this.getClass().getResource("/icon/energy_icon.png");
                energyEvaluationFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(url));

                energyEvaluationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                energyEvaluationFrame.pack();
                energyEvaluationFrame.setVisible(true);

            }
        }.start();
    }//GEN-LAST:event_jMenuItemEnergyMemoryProfActionPerformed

    
    private void showMessageList(){
        
        ArrayList<PacketInformation> packetList = mPSoCInformation.getReadTraffic().getAllPackets();
        
        
        DefaultListModel model = new DefaultListModel();
        
        
        for (PacketInformation packetInformation : packetList) {
            
            int port = packetInformation.getInput_port();
            
            String message = null;
            
            if (port == MPSoCConfig.LOCAL1 || port == MPSoCConfig.LOCAL2){
                message = "---->>> "+packetInformation.getRouter_address()+" send message to "+packetInformation.getTarget_router()+" service "+mpsocConfig.getStringServiceName(packetInformation.getService());
            }
            
            if (packetInformation.getRouter_address() == packetInformation.getTarget_router()){
                message = "<<<---- "+packetInformation.getRouter_address()+" receive message with service  "+mpsocConfig.getStringServiceName(packetInformation.getService());
            }
            
            if (message != null){
                model.addElement(message);
            }
        }
        
        
         JList jList = new JList(model);
         
         JScrollPane jscrol = new JScrollPane(jList);
         
         JFrame frame = new JFrame("Message Log");
         frame.getContentPane().add(jscrol);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(100, 100);
        frame.setSize(500, 600);
        frame.setVisible(true);
        
        
    }
    
    void checkpointReached() {
        
        /*new Thread() {
            @Override
            public void run() {*/
             
                float maxThroughputPerWindow = checkControl.getWindowsSizeInNs() / mpsocConfig.getClockPeriodInNs();

                for (int x = 0; x < mpsocConfig.getX_dimension(); x++) {
                    for (int y = 0; y < mpsocConfig.getY_dimension(); y++) {
                        int xyAddr = x << 8 | y;
                        RouterInformation routerInfo = mPSoCInformation.getRouterInformation(xyAddr);
                        updateRouterThroughput(xyAddr, routerInfo, maxThroughputPerWindow);
                    }
                }
                    
/*
            }
        }.start();*/
        
        
    }
    
    public void routerClicked(int router_address){
         new RouterInfoFrame(mPSoCInformation.getRouterInformation(router_address), mpsocConfig, image_icon);
    }
    
    //This is the KEy function
    int nextPacket(boolean repaint, int limitTime) {
        
        PacketInformation packet = mPSoCInformation.getNextPacket(filter, limitTime);

        if (packet == null){
            resetAllArrows();
            return -1;
        }
        
        /*if (mpsocConfig.getStringServiceName(packet.getService()) == null){
            JOptionPane.showMessageDialog(this, "Packet unknowed: service: "+packet.getService(), "", JOptionPane.ERROR_MESSAGE);
        }*/
        //packet.printPacket();
        
        if (!mpsocConfig.getServicesHash().containsKey(packet.getService())) {
            
                        
            String router_address = Integer.toString(packet.getRouter_address());
            
            router_address = mpsocConfig.XYAdressToXYLabel(packet.getRouter_address());
            
            JOptionPane.showMessageDialog(this, "ERROR: Service <" + packet.getService() + "> packet unidentified. \n"
                    + "Time: " + packet.getTime() + " "    
                    + "Router: " + router_address + " "
                        + "Input port: " + MPSoCConfig.getPortString(packet.getInput_port()), "", JOptionPane.ERROR_MESSAGE);
            stopButtonActionPerformed(null);
        }
        
        updateCurrentPacketTable(packet);
        checkControl.setTime(packet.getTime());
        
        if (repaint) {
            int router_address = packet.getRouter_address();
            repaintRouter(getRouterPanel(router_address), packet.getInput_port(), packet.getTarget_router());
        }
        return packet.getTime();
    }

    
    private void updateCurrentPacketTable(PacketInformation packetInfo){
        
        String target = null;
        String source = null;
        
        
        
        if (packetInfo.getTarget_router() == mpsocConfig.getChipset_id()){
            target = "Chipset";
        } else { 
            target = mpsocConfig.XYAdressToXYLabel(packetInfo.getTarget_router());
        }
        
        if (packetInfo.getRouter_address() == mpsocConfig.getChipset_id()){
            source = "Chipset";
        } else {
            source = mpsocConfig.XYAdressToXYLabel(packetInfo.getRouter_address());
        }
        
        String service = mpsocConfig.getStringServiceName(packetInfo.getService());
        
        String size = packetInfo.getSize() + " | "+packetInfo.getBandwidthCycles();
        
        currentPacketTable.setValueAt(source, 0, 0);
        currentPacketTable.setValueAt(target, 0, 1);
        currentPacketTable.setValueAt(service, 0, 2);
        currentPacketTable.setValueAt(size, 0, 3);
    }
    
    private void resetCurrentPacketTable(){
        currentPacketTable.setValueAt("-", 0, 0);
        currentPacketTable.setValueAt("-", 0, 1);
        currentPacketTable.setValueAt("-", 0, 2);
        currentPacketTable.setValueAt("-", 0, 3);
    }
    
    public void createNoCPanel() {

        nocPanel.removeAll();
        peripheralPanel.removeAll();
        mainPanel.removeAll();
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        nocPanel.setLayout(gridBagLayout);

        int pe_type = 0;
        
        cons.gridy = 0;
        for (int y = 0; y < mpsocConfig.getY_dimension(); y++) {
        //for (int y = mpsocConfig.getY_dimension() - 1; y >= 0; y--) {
            cons.gridx = 0;
            for (int x = 0; x < mpsocConfig.getX_dimension(); x++) {
                cons.gridx++;
                pe_type = mpsocConfig.getPEType(x, y);
                int router_addr = (x << 8 | y);
                int peripheralPosition = -1;
                if (pe_type == MPSoCConfig.GLOBAL_MASTER){
                    peripheralPosition = mpsocConfig.getChipset_position();
                }
                nocPanel.add(new Roteador(this, router_addr, mpsocConfig, peripheralPosition), cons);
            }
            cons.gridy++;
        }
        
        nocPanel.revalidate();
        
        
        GridBagLayout gridBagLayoutPeriph = new GridBagLayout();
        GridBagConstraints consPeriph = new GridBagConstraints();
        peripheralPanel.setLayout(gridBagLayoutPeriph);
        
        consPeriph.gridx = 0;
        for (int y = 0; y < mpsocConfig.getY_dimension(); y++) {
            consPeriph.gridy = y;
            if (y==0){
                int peripheral_attached_router = 0; //MAKE THIS DYNAMIC
                peripheralPanel.add(new ChipsetPeripheral(this, peripheral_attached_router, mpsocConfig, MPSoCConfig.PERIPH_POS_WEST), consPeriph);
            } else {
                peripheralPanel.add(new DummyPeripheral(), consPeriph);
            }
            
        }
        
       
        
        GridBagLayout gridBagLayoutMain = new GridBagLayout();
        GridBagConstraints consMain = new GridBagConstraints();
        mainPanel.removeAll();
        consMain.gridx = 0;
        consMain.gridy = 0;
        mainPanel.setLayout(gridBagLayoutMain);
        mainPanel.add(peripheralPanel, consMain);
        consMain.gridx = 1;
        consMain.gridy = 0;
        mainPanel.add(nocPanel, consMain);
        
        mainPanel.revalidate();
        
        
    }
    
    public ChipsetPeripheral getChipsetPeripheralPanel(int router_address){
        ChipsetPeripheral peripheral;
        //Get all components of this panel
        Component[] components = peripheralPanel.getComponents();
        
        for (Component component : components) {
            
            if (component instanceof ChipsetPeripheral){
                peripheral = (ChipsetPeripheral) component;
                if (peripheral.getAttachedRouterAddress() == router_address){
                    return peripheral;
                }
            } 

        }
        return null;
    }

    public Roteador getRouterPanel(int router_address) {
        Roteador router;

        for (int i = 0; i < mpsocConfig.getPENumber(); i++) {

            router = (Roteador) nocPanel.getComponent(i);

            if (router.getRouter_address() == router_address) {

                return router;
            }
        }
        return null;
    }
    
    public void updateRouterThroughput(int router_address, RouterInformation routerInfo, float maxThroughput){
       
        Roteador router = getRouterPanel(router_address);

        int x_dimension = mpsocConfig.getX_dimension();
        int y_dimension = mpsocConfig.getY_dimension();
        
        int x = router_address >> 8;
        int y = router_address & 0xFF;

        
        float portThroughput = 0;

        float portsThroughputCounter = 0;
        
        
        float percent = 100.0f / maxThroughput;
        
        int peripheralPosition = router.getPeripheralPosition();


        if (x != 0 || peripheralPosition == MPSoCConfig.PERIPH_POS_WEST) {//set left

            portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.WEST1) * percent;
            //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.WEST0) * percent;
            router.updateThroughput(MPSoCConfig.WEST1, portThroughput);
            portsThroughputCounter += portThroughput;

            portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.WEST2) * percent;
            //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.WEST1) * percent;
            router.updateThroughput(MPSoCConfig.WEST2, portThroughput);
            portsThroughputCounter += portThroughput;
            
            
            portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.WEST3) * percent;
            //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.WEST1) * percent;
            router.updateThroughput(MPSoCConfig.WEST3, portThroughput);
            portsThroughputCounter += portThroughput;
        }
        if (x != x_dimension - 1 || peripheralPosition == MPSoCConfig.PERIPH_POS_EAST) {//reset right
            portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.EAST1) * percent;
            //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.EAST0) * percent;
            router.updateThroughput(MPSoCConfig.EAST1, portThroughput);
            portsThroughputCounter += portThroughput;

            portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.EAST2) * percent;
            //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.EAST1) * percent;
            router.updateThroughput(MPSoCConfig.EAST2, portThroughput);
            portsThroughputCounter += portThroughput;
            
            portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.EAST3) * percent;
            //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.EAST1) * percent;
            router.updateThroughput(MPSoCConfig.EAST3, portThroughput);
            portsThroughputCounter += portThroughput;
        }
        if (y != y_dimension - 1 || peripheralPosition == MPSoCConfig.PERIPH_POS_SOUTH) { //reset down
            portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.SOUTH1) * percent;
            //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.SOUTH0) * percent;
            router.updateThroughput(MPSoCConfig.SOUTH1, portThroughput);
            portsThroughputCounter += portThroughput;

            portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.SOUTH2) * percent;
            //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.SOUTH1) * percent;
            router.updateThroughput(MPSoCConfig.SOUTH2, portThroughput);
            portsThroughputCounter += portThroughput;
            
            portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.SOUTH3) * percent;
            //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.SOUTH1) * percent;
            router.updateThroughput(MPSoCConfig.SOUTH3, portThroughput);
            portsThroughputCounter += portThroughput;
        }
        if (y != 0 || peripheralPosition == MPSoCConfig.PERIPH_POS_NORTH) { //reset up
            portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.NORTH1) * percent;
            //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.NORTH0) * percent;
            router.updateThroughput(MPSoCConfig.NORTH1, portThroughput);
            portsThroughputCounter += portThroughput;

            portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.NORTH2) * percent;
            //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.NORTH1) * percent;
            router.updateThroughput(MPSoCConfig.NORTH2, portThroughput);
            portsThroughputCounter += portThroughput;
            
            portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.NORTH3) * percent;
            //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.NORTH1) * percent;
            router.updateThroughput(MPSoCConfig.NORTH3, portThroughput);
            portsThroughputCounter += portThroughput;
        }

        portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.LOCAL1) * percent;
        //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.LOCAL0) * percent;
        router.updateThroughput(MPSoCConfig.LOCAL1, portThroughput);
        //portsThroughputCounter += portThroughput;

        portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.LOCAL2) * percent;
        //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.LOCAL1) * percent;
        router.updateThroughput(MPSoCConfig.LOCAL2, portThroughput);
        //portsThroughputCounter += portThroughput;
        
        portThroughput = routerInfo.getPortBandwidthThroughputInCycles(MPSoCConfig.LOCAL3) * percent;
        //portThroughput = routerInfo.getPortThroughputInFlits(MPSoCConfig.LOCAL1) * percent;
        router.updateThroughput(MPSoCConfig.LOCAL3, portThroughput);
        //portsThroughputCounter += portThroughput;

        //Desnecessario
        routerInfo.resetBandwidthThroughput();
        
       
        routerInfo.resetThroughput();
        
        if (router_address == routerToPrintLinkUsage){
            String toPrint = Float.toString(checkControl.getCurrentTimeInMs());
            if (this.linkUsage)
                toPrint = Float.toString(portsThroughputCounter);
            toPrint = toPrint.replace(".", ",");
            System.out.println(toPrint);
        }
        
        
    }
    
    private void printLinkUsageReport( final float linkUsage,  final int routerAddr){
        new Thread() {
            @Override
            public void run() {
                File report = new File(mpsocConfig.getDebugDirPath()+"/report/");
                if (!report.exists())
                    report.mkdir();

                report = new File(mpsocConfig.getDebugDirPath()+"/report/linkUsage.txt");
             
                try {
                    
                    FileWriter fw = new FileWriter(report, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    bw.write(routerAddr +"\t"+linkUsage);
                    bw.newLine();
                    
                    bw.close();

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

               
                
            }
        }.start();
    }
    
    void init_new_path(int target_router){
        
    }
    
    void conclude_path(int target_router){
        
    }
    
    public void repaintRouter(Roteador roteador, int port, int target_router) {

        int neighbor_label = -1;
        int neighbor_arrow = -1;
        int local_arrow = -1;
        boolean local_in = false;
        
        //Comment (without concerns) this line if you want the the packet path keep painted. This works well in Memphis but in OpenPiton not, creating a deadlock in paiting.
        resetAllArrows();

        switch (port) {
            case MPSoCConfig.NORTH1:
            case MPSoCConfig.NORTH2:
            case MPSoCConfig.NORTH3:
                //pintar vizinho de cima
                neighbor_label = mpsocConfig.getVizinho_cima(roteador.getRouter_address());
                break;
            case MPSoCConfig.SOUTH1:
            case MPSoCConfig.SOUTH2:
            case MPSoCConfig.SOUTH3:
                //pintar vizinho de baixo
                neighbor_label = mpsocConfig.getVizinho_baixo(roteador.getRouter_address());
                break;
            case MPSoCConfig.EAST1:
            case MPSoCConfig.EAST2:
            case MPSoCConfig.EAST3:
                //pintar vizinho de direita
                neighbor_label = mpsocConfig.getVizinho_direita(roteador.getRouter_address());
                break;
            case MPSoCConfig.WEST1:
            case MPSoCConfig.WEST2:
            case MPSoCConfig.WEST3:
                //pintar vizinho de esquerda
                neighbor_label = mpsocConfig.getVizinho_esquerda(roteador.getRouter_address());
                break;
            default: 
                break;
        }

        switch (port) {
            case MPSoCConfig.EAST1:
                local_arrow = MPSoCConfig.EAST_IN_NOC1;
                neighbor_arrow = MPSoCConfig.WEAST_OUT_NOC1;
                break;
            case MPSoCConfig.EAST2:
                local_arrow = MPSoCConfig.EAST_IN_NOC2;
                neighbor_arrow = MPSoCConfig.WEAST_OUT_NOC2;
                break;
            case MPSoCConfig.EAST3:
                local_arrow = MPSoCConfig.EAST_IN_NOC3;
                neighbor_arrow = MPSoCConfig.WEAST_OUT_NOC3;
                break;
            case MPSoCConfig.NORTH1:
                local_arrow = MPSoCConfig.NORTH_IN_NOC1;
                neighbor_arrow = MPSoCConfig.SOUTH_OUT_NOC1;
                break;
            case MPSoCConfig.NORTH2:
                local_arrow = MPSoCConfig.NORTH_IN_NOC2;
                neighbor_arrow = MPSoCConfig.SOUTH_OUT_NOC2;
                break;
            case MPSoCConfig.NORTH3:
                local_arrow = MPSoCConfig.NORTH_IN_NOC3;
                neighbor_arrow = MPSoCConfig.SOUTH_OUT_NOC3;
                break;
            case MPSoCConfig.WEST1:
                local_arrow = MPSoCConfig.WEAST_IN_NOC1;
                neighbor_arrow = MPSoCConfig.EAST_OUT_NOC1;
                break;
            case MPSoCConfig.WEST2:
                local_arrow = MPSoCConfig.WEAST_IN_NOC2;
                neighbor_arrow = MPSoCConfig.EAST_OUT_NOC2;
                break;
            case MPSoCConfig.WEST3:
                local_arrow = MPSoCConfig.WEAST_IN_NOC3;
                neighbor_arrow = MPSoCConfig.EAST_OUT_NOC3;
                break;
            case MPSoCConfig.SOUTH1:
                local_arrow = MPSoCConfig.SOUTH_IN_NOC1;
                neighbor_arrow = MPSoCConfig.NORTH_OUT_NOC1;
                break;
            case MPSoCConfig.SOUTH2:
                local_arrow = MPSoCConfig.SOUTH_IN_NOC2;
                neighbor_arrow = MPSoCConfig.NORTH_OUT_NOC2;
                break;
            case MPSoCConfig.SOUTH3:
                local_arrow = MPSoCConfig.SOUTH_IN_NOC3;
                neighbor_arrow = MPSoCConfig.NORTH_OUT_NOC3;
                break;
            case MPSoCConfig.LOCAL1:
                local_arrow = MPSoCConfig.LOCAL_IN_NOC1;
                local_in = true;
                break;
            case MPSoCConfig.LOCAL2:
                local_arrow = MPSoCConfig.LOCAL_IN_NOC2;
                local_in = true;
                break;
            case MPSoCConfig.LOCAL3:
                local_arrow = MPSoCConfig.LOCAL_IN_NOC3;
                local_in = true;
                break;    
        }
        
        //Control the full path color
        if (unfinished_packet_list.isEmpty()){
            resetAllArrows();
        }
        if (local_in){
            unfinished_packet_list.add(target_router);
        }

        

        if (neighbor_label != -1) {
            Roteador neigh_router = getRouterPanel(neighbor_label);
            neigh_router.paintArrow(neighbor_arrow);
        
        } else if (mpsocConfig.isMasterAddress(roteador.getRouter_address())){
            
            //Test if data comes from peripheral
            if (neighbor_arrow != -1){ 
                
                unfinished_packet_list.add(target_router);
           
                ChipsetPeripheral chipset_per = getChipsetPeripheralPanel(roteador.getRouter_address());

                if (chipset_per != null){
                    chipset_per.paintArrow(neighbor_arrow);
                }
            }
            
            // Test if data goes to peripheral.
            if (mpsocConfig.getChipset_id() == target_router){ 
                
                unfinished_packet_list.remove(new Integer(target_router));
                
                //Discover the NoC
                int noc_padd = ((local_arrow+1) / MPSoCConfig.NOC_ID_MULTIPLIER); //Results: MPSoCConfig.NOC1, MPSoCConfig.NOC2, MPSoCConfig.NOC3
                int peripheral_port = -1; //Stores the input port to be painted in the input peripehral
                int local_output_arrow = -1; //Stores the output port to be painted in the Global router
                
                switch(mpsocConfig.getChipset_position()){
                    case MPSoCConfig.PERIPH_POS_EAST:
                        local_output_arrow = MPSoCConfig.EAST_OUT_NOC1;
                        peripheral_port = MPSoCConfig.WEAST_IN_NOC1;
                        break;
                    case MPSoCConfig.PERIPH_POS_WEST:
                        local_output_arrow = MPSoCConfig.WEAST_OUT_NOC1;
                        peripheral_port = MPSoCConfig.EAST_IN_NOC1;
                        break;
                    case MPSoCConfig.PERIPH_POS_NORTH:
                        local_output_arrow = MPSoCConfig.NORTH_OUT_NOC1;
                        peripheral_port = MPSoCConfig.SOUTH_IN_NOC1;
                        break;
                    case MPSoCConfig.PERIPH_POS_SOUTH:
                        local_output_arrow = MPSoCConfig.SOUTH_OUT_NOC1;
                        peripheral_port = MPSoCConfig.NORTH_IN_NOC1;
                        break;
                }
                
                noc_padd = MPSoCConfig.NOC_ID_MULTIPLIER * noc_padd;
                local_output_arrow  += noc_padd;
                peripheral_port     += noc_padd;
                
                roteador.paintArrow(local_output_arrow);
                
                ChipsetPeripheral chipset_per = getChipsetPeripheralPanel(roteador.getRouter_address());

                if (chipset_per != null){
                    chipset_per.paintArrow(peripheral_port);
                }
                
            }
        } 
        
        
        
        roteador.paintArrow(local_arrow);
        
        //Teste if the packets arrived at destination
        if (roteador.getRouter_address() == target_router){
            
            int noc = MPSoCConfig.getNoCfromPort(port);
            switch(noc){
                case MPSoCConfig.NOC1:
                    roteador.paintArrow(MPSoCConfig.LOCAL_OUT_NOC1);
                    break;
                case MPSoCConfig.NOC2:
                    roteador.paintArrow(MPSoCConfig.LOCAL_OUT_NOC2);
                    break;
                case MPSoCConfig.NOC3:
                    roteador.paintArrow(MPSoCConfig.LOCAL_OUT_NOC3);
                    break;
            }
            
            unfinished_packet_list.remove(new Integer(target_router));
            
        }
        
    }
    
    public void resetAllArrows() {
        
        //Reset Routers arrows
        Roteador router;

        for (int i = 0; i < mpsocConfig.getPENumber(); i++) {

            router = (Roteador) nocPanel.getComponent(i);
            
            if (router.isNeedReset())//Melhora o desempenho
                router.resetArrows();
        }
        
        //---------------------------------------------------------
        
        //Reset Perhipehral arrows
        ChipsetPeripheral peripheral;
        //Get all components of this panel
        Component[] components = peripheralPanel.getComponents();
        
        for (Component component : components) {
            
            if (component instanceof ChipsetPeripheral){
                peripheral = (ChipsetPeripheral) component;
                peripheral.resetArrows();
            } 

        }
    }

    private void configProcess(String platformPath) {

        try {

            //System.out.println(platformPath);

            String debugDirPath = platformPath.substring(0, platformPath.indexOf("platform.cfg") - 1);

            //System.out.println(debugDirPath);

            if (!new File(debugDirPath + "/services.cfg").exists()) {
                JOptionPane.showMessageDialog(this, "File services.cfg not found in: " + debugDirPath + "/services.cfg.\n This file must be in the debug folder ", "File not found", JOptionPane.ERROR_MESSAGE);
            }

            mpsocConfig = new MPSoCConfig(debugDirPath);

            String title = this.getTitle();

            title = title.substring(0, title.indexOf("]") + 1);

            this.setTitle(title + ": " + mpsocConfig.getTestcasePath());

            initMPSoC();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please, informs a valid .cfg file", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
    
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame(args).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTable currentPacketTable;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu deloreamMenuItem;
    private javax.swing.JLabel frequencyLabel;
    private javax.swing.JButton goButton;
    private javax.swing.JTextField goTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItemEnergyMemoryProf;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem mainServiceFilter;
    private javax.swing.JMenuItem messageLogMenuItem;
    private javax.swing.JButton nextButton;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem packetFormatMenuItem;
    private javax.swing.JPanel panelControl;
    private javax.swing.JLabel percentLabel;
    private javax.swing.JButton playButton;
    private javax.swing.JMenuItem printRouterTotalLinkUsageMenuItem;
    private javax.swing.JMenuItem resetSimulation;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JScrollPane scrollPanelNoC;
    private javax.swing.JMenuItem serviceListMenuItem;
    private javax.swing.JSlider simSpeedControlBar;
    private javax.swing.JLabel simulTick;
    private javax.swing.JLabel simulTime;
    private javax.swing.JButton stopButton;
    private javax.swing.JMenuItem taskListMenuItem;
    private javax.swing.JMenuItem taskMappingMenuItem;
    // End of variables declaration//GEN-END:variables

    public void setMPSoCConfig(MPSoCConfig nocConfig) {
        this.mpsocConfig = nocConfig;
    }

}
