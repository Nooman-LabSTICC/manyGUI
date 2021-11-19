package source;

import componentes.UJPanelImagem;
import java.awt.Color;
import java.awt.Cursor;
import java.text.DecimalFormat;
import javax.swing.SwingConstants;
import util.MPSoCConfig;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marcelo
 */
public class Roteador extends javax.swing.JPanel {

    private int router_address;
    private MPSoCConfig mPSoCConfig;
    private MainFrame mainFrame;
    private int peripheralPosition;
    private boolean needReset;

    public Roteador(MainFrame mainFrame, int router_address, MPSoCConfig mPSoCConfig, int peripheralPosition) {
        this.mainFrame = mainFrame;
        this.router_address = router_address;
        this.mPSoCConfig = mPSoCConfig;
        this.peripheralPosition = peripheralPosition;
        this.needReset = true;
        initComponents();
        
        routerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        router.setCursor(new Cursor(Cursor.HAND_CURSOR));
        routerLabel.setForeground(Color.BLUE.darker());
        routerLabel.setText(this.mPSoCConfig.XYAdressToXYLabel(this.router_address));
        //removeCornersArrows();
        routerLabel.setToolTipText("XY router address");
        noc1_label.setToolTipText("NoC 1");
        noc2_label.setToolTipText("NoC 2");
        noc3_label.setToolTipText("NoC 3");
        l15_label.setToolTipText("L1.5 interface with router: -UP arrows are inputs to router -DOWN arrows are output from router]");
        l2_label.setToolTipText("L2 interface with router: -UP arrows are inputs to router -DOWN arrows are output from router]");

        
        south_NoC1_Label.setToolTipText("Link utilization percentage");
        south_NoC2_Label.setToolTipText("Link utilization percentage");
        south_NoC3_Label.setToolTipText("Link utilization percentage");
        north_NoC1_Label.setToolTipText("Link utilization percentage");
        north_NoC2_Label.setToolTipText("Link utilization percentage");
        north_NoC3_Label.setToolTipText("Link utilization percentage");
        weast_NoC1_Label.setToolTipText("Link utilization percentage");
        weast_NoC2_Label.setToolTipText("Link utilization percentage");
        weast_NoC3_Label.setToolTipText("Link utilization percentage");
        east_NoC1_Label.setToolTipText("Link utilization percentage");
        east_NoC2_Label.setToolTipText("Link utilization percentage");
        east_NoC3_Label.setToolTipText("Link utilization percentage");
        local_NoC1_Label.setToolTipText("Link utilization percentage");
        local_NoC2_Label.setToolTipText("Link utilization percentage");
        local_NoC3_Label.setToolTipText("Link utilization percentage");
        
        
        local_NoC1_out.setToolTipText("NoC1: local router output");
        local_NoC2_out.setToolTipText("NoC2: local router output");
        local_NoC3_out.setToolTipText("NoC3: local router output");
        
        local_NoC1_in.setToolTipText("NoC1: local router input");
        local_NoC2_in.setToolTipText("NoC2: local router input");
        local_NoC3_in.setToolTipText("NoC3: local router input");
        
        out_east_NoC1.setToolTipText("NoC1");
        out_east_NoC2.setToolTipText("NoC2");
        out_east_NoC3.setToolTipText("NoC3");
        
        out_weast_NoC1.setToolTipText("NoC1");
        out_weast_NoC2.setToolTipText("NoC2");
        out_weast_NoC3.setToolTipText("NoC3");
        
        out_south_NoC1.setToolTipText("NoC1");
        out_south_NoC2.setToolTipText("NoC2");
        out_south_NoC3.setToolTipText("NoC3");
        
        out_north_NoC1.setToolTipText("NoC1");
        out_north_NoC2.setToolTipText("NoC2");
        out_north_NoC3.setToolTipText("NoC3");
        
        in_east_NoC1.setToolTipText("NoC1");
        in_east_NoC2.setToolTipText("NoC2");
        in_east_NoC3.setToolTipText("NoC3");
        
        in_weast_NoC1.setToolTipText("NoC1");
        in_weast_NoC2.setToolTipText("NoC2");
        in_weast_NoC3.setToolTipText("NoC3");
        
        in_south_NoC1.setToolTipText("NoC1");
        in_south_NoC2.setToolTipText("NoC2");
        in_south_NoC3.setToolTipText("NoC3");
        
        in_north_NoC1.setToolTipText("NoC1");
        in_north_NoC2.setToolTipText("NoC2");
        in_north_NoC3.setToolTipText("NoC3");
        
        router.setImagem("/images/Router.png");
        /*switch(type){
            case MPSoCConfig.SLAVE:
                router.setImagem("/images/Router.png");
                break;
            case MPSoCConfig.CLUSTER_MASTER:
                //router.setImagem("/images/Router_cluster_master.png");
                break;
            case MPSoCConfig.GLOBAL_MASTER:
                //router.setImagem("/images/Router_master.png");
                router.setImagem("/images/Router.png");
                break;
        }*/
        router.repaint();
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        router = new componentes.UJPanelImagem();
        north_NoC2_Label = new javax.swing.JLabel();
        south_NoC2_Label = new javax.swing.JLabel();
        south_NoC1_Label = new javax.swing.JLabel();
        weast_NoC2_Label = new javax.swing.JLabel();
        weast_NoC1_Label = new javax.swing.JLabel();
        north_NoC1_Label = new javax.swing.JLabel();
        east_NoC1_Label = new javax.swing.JLabel();
        east_NoC3_Label = new javax.swing.JLabel();
        local_NoC1_out = new componentes.UJPanelImagem();
        local_NoC1_in = new componentes.UJPanelImagem();
        local_NoC1_Label = new javax.swing.JLabel();
        weast_NoC3_Label = new javax.swing.JLabel();
        routerLabel = new javax.swing.JLabel();
        north_NoC3_Label = new javax.swing.JLabel();
        south_NoC3_Label = new javax.swing.JLabel();
        east_NoC2_Label = new javax.swing.JLabel();
        local_NoC2_out = new componentes.UJPanelImagem();
        local_NoC2_in = new componentes.UJPanelImagem();
        local_NoC3_out = new componentes.UJPanelImagem();
        local_NoC3_in = new componentes.UJPanelImagem();
        local_NoC3_Label = new javax.swing.JLabel();
        local_NoC2_Label = new javax.swing.JLabel();
        noc3_label = new javax.swing.JLabel();
        l2_label = new javax.swing.JLabel();
        noc2_label = new javax.swing.JLabel();
        noc1_label = new javax.swing.JLabel();
        l15_label = new javax.swing.JLabel();
        out_north_NoC2 = new componentes.UJPanelImagem();
        in_north_NoC2 = new componentes.UJPanelImagem();
        out_weast_NoC1 = new componentes.UJPanelImagem();
        in_weast_NoC1 = new componentes.UJPanelImagem();
        in_east_NoC2 = new componentes.UJPanelImagem();
        out_east_NoC2 = new componentes.UJPanelImagem();
        in_south_NoC1 = new componentes.UJPanelImagem();
        out_south_NoC1 = new componentes.UJPanelImagem();
        in_south_NoC2 = new componentes.UJPanelImagem();
        out_south_NoC2 = new componentes.UJPanelImagem();
        in_east_NoC1 = new componentes.UJPanelImagem();
        out_east_NoC1 = new componentes.UJPanelImagem();
        out_north_NoC1 = new componentes.UJPanelImagem();
        in_north_NoC1 = new componentes.UJPanelImagem();
        in_weast_NoC3 = new componentes.UJPanelImagem();
        out_weast_NoC3 = new componentes.UJPanelImagem();
        in_south_NoC3 = new componentes.UJPanelImagem();
        out_south_NoC3 = new componentes.UJPanelImagem();
        in_north_NoC3 = new componentes.UJPanelImagem();
        out_north_NoC3 = new componentes.UJPanelImagem();
        in_weast_NoC2 = new componentes.UJPanelImagem();
        out_weast_NoC2 = new componentes.UJPanelImagem();
        out_east_NoC3 = new componentes.UJPanelImagem();
        in_east_NoC3 = new componentes.UJPanelImagem();

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(170, 170));
        setMinimumSize(new java.awt.Dimension(170, 170));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(170, 170));
        setLayout(null);

        router.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        router.setMinimumSize(new java.awt.Dimension(300, 300));
        router.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                routerMouseReleased(evt);
            }
        });
        router.setLayout(null);

        north_NoC2_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        north_NoC2_Label.setText("00");
        router.add(north_NoC2_Label);
        north_NoC2_Label.setBounds(72, -2, 20, 20);

        south_NoC2_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        south_NoC2_Label.setText("00");
        router.add(south_NoC2_Label);
        south_NoC2_Label.setBounds(55, 112, 20, 20);

        south_NoC1_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        south_NoC1_Label.setText("00");
        router.add(south_NoC1_Label);
        south_NoC1_Label.setBounds(11, 112, 20, 20);

        weast_NoC2_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        weast_NoC2_Label.setText("00");
        router.add(weast_NoC2_Label);
        weast_NoC2_Label.setBounds(1, 50, 20, 20);

        weast_NoC1_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        weast_NoC1_Label.setText("00");
        router.add(weast_NoC1_Label);
        weast_NoC1_Label.setBounds(1, 6, 25, 20);

        north_NoC1_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        north_NoC1_Label.setText("00");
        router.add(north_NoC1_Label);
        north_NoC1_Label.setBounds(29, -2, 20, 20);

        east_NoC1_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        east_NoC1_Label.setText("00");
        router.add(east_NoC1_Label);
        east_NoC1_Label.setBounds(116, 24, 12, 20);

        east_NoC3_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        east_NoC3_Label.setText("00");
        east_NoC3_Label.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        router.add(east_NoC3_Label);
        east_NoC3_Label.setBounds(116, 113, 12, 10);

        local_NoC1_out.setBorder(null);

        javax.swing.GroupLayout local_NoC1_outLayout = new javax.swing.GroupLayout(local_NoC1_out);
        local_NoC1_out.setLayout(local_NoC1_outLayout);
        local_NoC1_outLayout.setHorizontalGroup(
            local_NoC1_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        local_NoC1_outLayout.setVerticalGroup(
            local_NoC1_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        router.add(local_NoC1_out);
        local_NoC1_out.setBounds(78, 42, 20, 20);

        local_NoC1_in.setBackground(java.awt.Color.pink);
        local_NoC1_in.setBorder(null);

        javax.swing.GroupLayout local_NoC1_inLayout = new javax.swing.GroupLayout(local_NoC1_in);
        local_NoC1_in.setLayout(local_NoC1_inLayout);
        local_NoC1_inLayout.setHorizontalGroup(
            local_NoC1_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        local_NoC1_inLayout.setVerticalGroup(
            local_NoC1_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        router.add(local_NoC1_in);
        local_NoC1_in.setBounds(40, 42, 20, 20);

        local_NoC1_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        local_NoC1_Label.setText("00");
        router.add(local_NoC1_Label);
        local_NoC1_Label.setBounds(60, 45, 25, 13);

        weast_NoC3_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        weast_NoC3_Label.setText("00");
        router.add(weast_NoC3_Label);
        weast_NoC3_Label.setBounds(1, 92, 25, 20);

        routerLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        routerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        routerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        routerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                routerLabelMouseReleased(evt);
            }
        });
        router.add(routerLabel);
        routerLabel.setBounds(45, 10, 40, 20);

        north_NoC3_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        north_NoC3_Label.setText("00");
        router.add(north_NoC3_Label);
        north_NoC3_Label.setBounds(115, -2, 20, 20);

        south_NoC3_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        south_NoC3_Label.setText("00");
        router.add(south_NoC3_Label);
        south_NoC3_Label.setBounds(93, 112, 20, 20);

        east_NoC2_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        east_NoC2_Label.setText("00");
        router.add(east_NoC2_Label);
        east_NoC2_Label.setBounds(116, 67, 12, 20);

        local_NoC2_out.setBackground(java.awt.Color.pink);
        local_NoC2_out.setBorder(null);

        javax.swing.GroupLayout local_NoC2_outLayout = new javax.swing.GroupLayout(local_NoC2_out);
        local_NoC2_out.setLayout(local_NoC2_outLayout);
        local_NoC2_outLayout.setHorizontalGroup(
            local_NoC2_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        local_NoC2_outLayout.setVerticalGroup(
            local_NoC2_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        router.add(local_NoC2_out);
        local_NoC2_out.setBounds(40, 62, 20, 20);

        local_NoC2_in.setBorder(null);

        javax.swing.GroupLayout local_NoC2_inLayout = new javax.swing.GroupLayout(local_NoC2_in);
        local_NoC2_in.setLayout(local_NoC2_inLayout);
        local_NoC2_inLayout.setHorizontalGroup(
            local_NoC2_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        local_NoC2_inLayout.setVerticalGroup(
            local_NoC2_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        router.add(local_NoC2_in);
        local_NoC2_in.setBounds(78, 62, 20, 20);

        local_NoC3_out.setBorder(null);

        javax.swing.GroupLayout local_NoC3_outLayout = new javax.swing.GroupLayout(local_NoC3_out);
        local_NoC3_out.setLayout(local_NoC3_outLayout);
        local_NoC3_outLayout.setHorizontalGroup(
            local_NoC3_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        local_NoC3_outLayout.setVerticalGroup(
            local_NoC3_outLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        router.add(local_NoC3_out);
        local_NoC3_out.setBounds(78, 82, 20, 20);

        local_NoC3_in.setBackground(java.awt.Color.pink);
        local_NoC3_in.setBorder(null);

        javax.swing.GroupLayout local_NoC3_inLayout = new javax.swing.GroupLayout(local_NoC3_in);
        local_NoC3_in.setLayout(local_NoC3_inLayout);
        local_NoC3_inLayout.setHorizontalGroup(
            local_NoC3_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        local_NoC3_inLayout.setVerticalGroup(
            local_NoC3_inLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        router.add(local_NoC3_in);
        local_NoC3_in.setBounds(40, 82, 20, 20);

        local_NoC3_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        local_NoC3_Label.setText("00");
        router.add(local_NoC3_Label);
        local_NoC3_Label.setBounds(98, 65, 25, 13);

        local_NoC2_Label.setFont(new java.awt.Font("Andale Mono", 0, 10)); // NOI18N
        local_NoC2_Label.setText("00");
        router.add(local_NoC2_Label);
        local_NoC2_Label.setBounds(60, 85, 25, 13);

        noc3_label.setFont(new java.awt.Font("Andale Mono", 1, 10)); // NOI18N
        noc3_label.setText("N3");
        router.add(noc3_label);
        noc3_label.setBounds(24, 85, 20, 13);

        l2_label.setFont(new java.awt.Font("Andale Mono", 1, 10)); // NOI18N
        l2_label.setText("L2");
        router.add(l2_label);
        l2_label.setBounds(80, 30, 20, 10);

        noc2_label.setFont(new java.awt.Font("Andale Mono", 1, 10)); // NOI18N
        noc2_label.setText("N2");
        router.add(noc2_label);
        noc2_label.setBounds(24, 65, 20, 13);

        noc1_label.setFont(new java.awt.Font("Andale Mono", 1, 10)); // NOI18N
        noc1_label.setText("N1");
        router.add(noc1_label);
        noc1_label.setBounds(24, 45, 20, 13);

        l15_label.setFont(new java.awt.Font("Andale Mono", 1, 10)); // NOI18N
        l15_label.setText("L1.5");
        router.add(l15_label);
        l15_label.setBounds(37, 30, 30, 13);

        add(router);
        router.setBounds(30, 30, 130, 130);

        out_north_NoC2.setBorder(null);

        javax.swing.GroupLayout out_north_NoC2Layout = new javax.swing.GroupLayout(out_north_NoC2);
        out_north_NoC2.setLayout(out_north_NoC2Layout);
        out_north_NoC2Layout.setHorizontalGroup(
            out_north_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        out_north_NoC2Layout.setVerticalGroup(
            out_north_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(out_north_NoC2);
        out_north_NoC2.setBounds(86, 0, 6, 30);

        in_north_NoC2.setBorder(null);

        javax.swing.GroupLayout in_north_NoC2Layout = new javax.swing.GroupLayout(in_north_NoC2);
        in_north_NoC2.setLayout(in_north_NoC2Layout);
        in_north_NoC2Layout.setHorizontalGroup(
            in_north_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        in_north_NoC2Layout.setVerticalGroup(
            in_north_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(in_north_NoC2);
        in_north_NoC2.setBounds(97, 0, 20, 30);

        out_weast_NoC1.setBorder(null);

        javax.swing.GroupLayout out_weast_NoC1Layout = new javax.swing.GroupLayout(out_weast_NoC1);
        out_weast_NoC1.setLayout(out_weast_NoC1Layout);
        out_weast_NoC1Layout.setHorizontalGroup(
            out_weast_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        out_weast_NoC1Layout.setVerticalGroup(
            out_weast_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );

        add(out_weast_NoC1);
        out_weast_NoC1.setBounds(0, 61, 30, 6);

        in_weast_NoC1.setBorder(null);

        javax.swing.GroupLayout in_weast_NoC1Layout = new javax.swing.GroupLayout(in_weast_NoC1);
        in_weast_NoC1.setLayout(in_weast_NoC1Layout);
        in_weast_NoC1Layout.setHorizontalGroup(
            in_weast_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        in_weast_NoC1Layout.setVerticalGroup(
            in_weast_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        add(in_weast_NoC1);
        in_weast_NoC1.setBounds(0, 36, 30, 20);

        in_east_NoC2.setBorder(null);

        javax.swing.GroupLayout in_east_NoC2Layout = new javax.swing.GroupLayout(in_east_NoC2);
        in_east_NoC2.setLayout(in_east_NoC2Layout);
        in_east_NoC2Layout.setHorizontalGroup(
            in_east_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        in_east_NoC2Layout.setVerticalGroup(
            in_east_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        add(in_east_NoC2);
        in_east_NoC2.setBounds(160, 97, 33, 20);

        out_east_NoC2.setBorder(null);

        javax.swing.GroupLayout out_east_NoC2Layout = new javax.swing.GroupLayout(out_east_NoC2);
        out_east_NoC2.setLayout(out_east_NoC2Layout);
        out_east_NoC2Layout.setHorizontalGroup(
            out_east_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        out_east_NoC2Layout.setVerticalGroup(
            out_east_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );

        add(out_east_NoC2);
        out_east_NoC2.setBounds(160, 86, 33, 6);

        in_south_NoC1.setBorder(null);

        javax.swing.GroupLayout in_south_NoC1Layout = new javax.swing.GroupLayout(in_south_NoC1);
        in_south_NoC1.setLayout(in_south_NoC1Layout);
        in_south_NoC1Layout.setHorizontalGroup(
            in_south_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        in_south_NoC1Layout.setVerticalGroup(
            in_south_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(in_south_NoC1);
        in_south_NoC1.setBounds(36, 160, 20, 30);

        out_south_NoC1.setBorder(null);

        javax.swing.GroupLayout out_south_NoC1Layout = new javax.swing.GroupLayout(out_south_NoC1);
        out_south_NoC1.setLayout(out_south_NoC1Layout);
        out_south_NoC1Layout.setHorizontalGroup(
            out_south_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        out_south_NoC1Layout.setVerticalGroup(
            out_south_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(out_south_NoC1);
        out_south_NoC1.setBounds(61, 160, 6, 30);

        in_south_NoC2.setBorder(null);

        javax.swing.GroupLayout in_south_NoC2Layout = new javax.swing.GroupLayout(in_south_NoC2);
        in_south_NoC2.setLayout(in_south_NoC2Layout);
        in_south_NoC2Layout.setHorizontalGroup(
            in_south_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        in_south_NoC2Layout.setVerticalGroup(
            in_south_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(in_south_NoC2);
        in_south_NoC2.setBounds(79, 160, 20, 30);

        out_south_NoC2.setBorder(null);

        javax.swing.GroupLayout out_south_NoC2Layout = new javax.swing.GroupLayout(out_south_NoC2);
        out_south_NoC2.setLayout(out_south_NoC2Layout);
        out_south_NoC2Layout.setHorizontalGroup(
            out_south_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        out_south_NoC2Layout.setVerticalGroup(
            out_south_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(out_south_NoC2);
        out_south_NoC2.setBounds(104, 160, 6, 30);

        in_east_NoC1.setBorder(null);

        javax.swing.GroupLayout in_east_NoC1Layout = new javax.swing.GroupLayout(in_east_NoC1);
        in_east_NoC1.setLayout(in_east_NoC1Layout);
        in_east_NoC1Layout.setHorizontalGroup(
            in_east_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        in_east_NoC1Layout.setVerticalGroup(
            in_east_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        add(in_east_NoC1);
        in_east_NoC1.setBounds(160, 54, 33, 20);

        out_east_NoC1.setBorder(null);

        javax.swing.GroupLayout out_east_NoC1Layout = new javax.swing.GroupLayout(out_east_NoC1);
        out_east_NoC1.setLayout(out_east_NoC1Layout);
        out_east_NoC1Layout.setHorizontalGroup(
            out_east_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        out_east_NoC1Layout.setVerticalGroup(
            out_east_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );

        add(out_east_NoC1);
        out_east_NoC1.setBounds(160, 43, 33, 6);

        out_north_NoC1.setBorder(null);

        javax.swing.GroupLayout out_north_NoC1Layout = new javax.swing.GroupLayout(out_north_NoC1);
        out_north_NoC1.setLayout(out_north_NoC1Layout);
        out_north_NoC1Layout.setHorizontalGroup(
            out_north_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        out_north_NoC1Layout.setVerticalGroup(
            out_north_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(out_north_NoC1);
        out_north_NoC1.setBounds(43, 0, 6, 30);

        in_north_NoC1.setBorder(null);

        javax.swing.GroupLayout in_north_NoC1Layout = new javax.swing.GroupLayout(in_north_NoC1);
        in_north_NoC1.setLayout(in_north_NoC1Layout);
        in_north_NoC1Layout.setHorizontalGroup(
            in_north_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        in_north_NoC1Layout.setVerticalGroup(
            in_north_NoC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(in_north_NoC1);
        in_north_NoC1.setBounds(54, 0, 20, 30);

        in_weast_NoC3.setBorder(null);

        javax.swing.GroupLayout in_weast_NoC3Layout = new javax.swing.GroupLayout(in_weast_NoC3);
        in_weast_NoC3.setLayout(in_weast_NoC3Layout);
        in_weast_NoC3Layout.setHorizontalGroup(
            in_weast_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        in_weast_NoC3Layout.setVerticalGroup(
            in_weast_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        add(in_weast_NoC3);
        in_weast_NoC3.setBounds(0, 122, 30, 20);

        out_weast_NoC3.setBorder(null);

        javax.swing.GroupLayout out_weast_NoC3Layout = new javax.swing.GroupLayout(out_weast_NoC3);
        out_weast_NoC3.setLayout(out_weast_NoC3Layout);
        out_weast_NoC3Layout.setHorizontalGroup(
            out_weast_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        out_weast_NoC3Layout.setVerticalGroup(
            out_weast_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );

        add(out_weast_NoC3);
        out_weast_NoC3.setBounds(0, 147, 30, 6);

        in_south_NoC3.setBorder(null);

        javax.swing.GroupLayout in_south_NoC3Layout = new javax.swing.GroupLayout(in_south_NoC3);
        in_south_NoC3.setLayout(in_south_NoC3Layout);
        in_south_NoC3Layout.setHorizontalGroup(
            in_south_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        in_south_NoC3Layout.setVerticalGroup(
            in_south_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(in_south_NoC3);
        in_south_NoC3.setBounds(122, 160, 20, 30);

        out_south_NoC3.setBorder(null);

        javax.swing.GroupLayout out_south_NoC3Layout = new javax.swing.GroupLayout(out_south_NoC3);
        out_south_NoC3.setLayout(out_south_NoC3Layout);
        out_south_NoC3Layout.setHorizontalGroup(
            out_south_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        out_south_NoC3Layout.setVerticalGroup(
            out_south_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(out_south_NoC3);
        out_south_NoC3.setBounds(147, 160, 6, 30);

        in_north_NoC3.setBorder(null);

        javax.swing.GroupLayout in_north_NoC3Layout = new javax.swing.GroupLayout(in_north_NoC3);
        in_north_NoC3.setLayout(in_north_NoC3Layout);
        in_north_NoC3Layout.setHorizontalGroup(
            in_north_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        in_north_NoC3Layout.setVerticalGroup(
            in_north_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(in_north_NoC3);
        in_north_NoC3.setBounds(140, 0, 20, 30);

        out_north_NoC3.setBorder(null);

        javax.swing.GroupLayout out_north_NoC3Layout = new javax.swing.GroupLayout(out_north_NoC3);
        out_north_NoC3.setLayout(out_north_NoC3Layout);
        out_north_NoC3Layout.setHorizontalGroup(
            out_north_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        out_north_NoC3Layout.setVerticalGroup(
            out_north_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        add(out_north_NoC3);
        out_north_NoC3.setBounds(129, 0, 6, 30);

        in_weast_NoC2.setBorder(null);

        javax.swing.GroupLayout in_weast_NoC2Layout = new javax.swing.GroupLayout(in_weast_NoC2);
        in_weast_NoC2.setLayout(in_weast_NoC2Layout);
        in_weast_NoC2Layout.setHorizontalGroup(
            in_weast_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        in_weast_NoC2Layout.setVerticalGroup(
            in_weast_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        add(in_weast_NoC2);
        in_weast_NoC2.setBounds(0, 79, 30, 20);

        out_weast_NoC2.setBorder(null);

        javax.swing.GroupLayout out_weast_NoC2Layout = new javax.swing.GroupLayout(out_weast_NoC2);
        out_weast_NoC2.setLayout(out_weast_NoC2Layout);
        out_weast_NoC2Layout.setHorizontalGroup(
            out_weast_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        out_weast_NoC2Layout.setVerticalGroup(
            out_weast_NoC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );

        add(out_weast_NoC2);
        out_weast_NoC2.setBounds(0, 104, 30, 6);

        out_east_NoC3.setBorder(null);

        javax.swing.GroupLayout out_east_NoC3Layout = new javax.swing.GroupLayout(out_east_NoC3);
        out_east_NoC3.setLayout(out_east_NoC3Layout);
        out_east_NoC3Layout.setHorizontalGroup(
            out_east_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        out_east_NoC3Layout.setVerticalGroup(
            out_east_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );

        add(out_east_NoC3);
        out_east_NoC3.setBounds(160, 129, 30, 6);

        in_east_NoC3.setBorder(null);

        javax.swing.GroupLayout in_east_NoC3Layout = new javax.swing.GroupLayout(in_east_NoC3);
        in_east_NoC3.setLayout(in_east_NoC3Layout);
        in_east_NoC3Layout.setHorizontalGroup(
            in_east_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        in_east_NoC3Layout.setVerticalGroup(
            in_east_NoC3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        add(in_east_NoC3);
        in_east_NoC3.setBounds(160, 140, 30, 20);
    }// </editor-fold>//GEN-END:initComponents

    private void routerLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_routerLabelMouseReleased
        mainFrame.routerClicked(router_address);
    }//GEN-LAST:event_routerLabelMouseReleased

    private void routerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_routerMouseReleased
        mainFrame.routerClicked(router_address);
    }//GEN-LAST:event_routerMouseReleased

    public void updateThroughput(int port, float value){
        
        if (value >= 100) value = 99.99f;
        
        //String text = new DecimalFormat("00.00").format((float)value);
        String text = new DecimalFormat("00").format((float)value);
        //text+="%";
        
        switch (port) {
            case MPSoCConfig.EAST1:
                east_NoC1_Label.setText(text);
                break;
            case MPSoCConfig.EAST2:
                east_NoC2_Label.setText(text);
                break;
            case MPSoCConfig.EAST3:
                east_NoC2_Label.setText(text);
                break;
            case MPSoCConfig.NORTH1:
                north_NoC1_Label.setText(text);
                break;
            case MPSoCConfig.NORTH2:
                north_NoC2_Label.setText(text);
                break;
            case MPSoCConfig.NORTH3:
                north_NoC3_Label.setText(text);
                break;
            case MPSoCConfig.WEST1:
                weast_NoC1_Label.setText(text);
                break;
            case MPSoCConfig.WEST2:
                weast_NoC2_Label.setText(text);
                break;
            case MPSoCConfig.WEST3:
                weast_NoC3_Label.setText(text);
                break;
            case MPSoCConfig.SOUTH1:
                south_NoC1_Label.setText(text);
                break;
            case MPSoCConfig.SOUTH2:
                south_NoC2_Label.setText(text);
                break;
            case MPSoCConfig.SOUTH3:
                south_NoC3_Label.setText(text);
                break;
            case MPSoCConfig.LOCAL1:
                local_NoC1_Label.setText(text);
                break;
            case MPSoCConfig.LOCAL2:
                local_NoC2_Label.setText(text);
                break;
            case MPSoCConfig.LOCAL3:
                local_NoC3_Label.setText(text);
                break;
        }
        
    }
    
    public void resetArrows() {

        int x_dimension = mPSoCConfig.getX_dimension();
        int y_dimension = mPSoCConfig.getY_dimension();
        
        int x_master = mPSoCConfig.getManagerPosition_x();
        int y_master = mPSoCConfig.getManagerPosition_y();
        
        int x = router_address >> 8;
        int y = router_address & 0xFF;
        
        needReset = false;
            
        //L1.5
        local_NoC1_in.setImagem("/images/local_in_L15.png");
        local_NoC1_in.repaint();
        local_NoC2_out.setImagem("/images/local_out_L15.png");
        local_NoC2_out.repaint();
        local_NoC3_in.setImagem("/images/local_in_L15.png");
        local_NoC3_in.repaint();
        
        //L2
        local_NoC1_out.setImagem("/images/local_out_L2.png");
        local_NoC1_out.repaint();
        local_NoC2_in.setImagem("/images/local_in_L2.png");
        local_NoC2_in.repaint();
        local_NoC3_out.setImagem("/images/local_out_L2.png");
        local_NoC3_out.repaint();
        

        if (x != 0 || peripheralPosition == MPSoCConfig.PERIPH_POS_WEST) {//reset left
           
            out_weast_NoC1.setImagem("/images/undirected_h.png");
            out_weast_NoC1.repaint();
            in_weast_NoC1.setImagem("/images/right.png");
            in_weast_NoC1.repaint();
            out_weast_NoC2.setImagem("/images/undirected_h.png");
            out_weast_NoC2.repaint();
            in_weast_NoC2.setImagem("/images/right.png");
            in_weast_NoC2.repaint();
            out_weast_NoC3.setImagem("/images/undirected_h.png");
            out_weast_NoC3.repaint();
            in_weast_NoC3.setImagem("/images/right.png");
            in_weast_NoC3.repaint();
        }
        
        if (x != x_dimension - 1 || peripheralPosition == MPSoCConfig.PERIPH_POS_EAST) {//reset right
            in_east_NoC2.setImagem("/images/left.png");
            in_east_NoC2.repaint();
            out_east_NoC2.setImagem("/images/undirected_h.png");
            out_east_NoC2.repaint();
            in_east_NoC1.setImagem("/images/left.png");
            in_east_NoC1.repaint();
            out_east_NoC1.setImagem("/images/undirected_h.png");
            out_east_NoC1.repaint();
            in_east_NoC3.setImagem("/images/left.png");
            in_east_NoC3.repaint();
            out_east_NoC3.setImagem("/images/undirected_h.png");
            out_east_NoC3.repaint();
        }
        
        if (y != y_dimension - 1 || peripheralPosition == MPSoCConfig.PERIPH_POS_SOUTH) { //reset down
            
            in_south_NoC1.setImagem("/images/up.png");
            in_south_NoC1.repaint();
            out_south_NoC1.setImagem("/images/undirected_v.png");
            out_south_NoC1.repaint();
            in_south_NoC2.setImagem("/images/up.png");
            in_south_NoC2.repaint();
            out_south_NoC2.setImagem("/images/undirected_v.png");
            out_south_NoC2.repaint();
            in_south_NoC3.setImagem("/images/up.png");
            in_south_NoC3.repaint();
            out_south_NoC3.setImagem("/images/undirected_v.png");
            out_south_NoC3.repaint();
        }
        
        if (y != 0 || peripheralPosition == MPSoCConfig.PERIPH_POS_NORTH) { //reset up
            out_north_NoC2.setImagem("/images/undirected_v.png");
            out_north_NoC2.repaint();
            in_north_NoC2.setImagem("/images/down.png");
            in_north_NoC2.repaint();
            out_north_NoC1.setImagem("/images/undirected_v.png");
            out_north_NoC1.repaint();
            in_north_NoC1.setImagem("/images/down.png");
            in_north_NoC1.repaint();
            out_north_NoC3.setImagem("/images/undirected_v.png");
            out_north_NoC3.repaint();
            in_north_NoC3.setImagem("/images/down.png");
            in_north_NoC3.repaint();
        }
        
        
        //Reset labels
        if (x == 0 && peripheralPosition != MPSoCConfig.PERIPH_POS_WEST) {
            weast_NoC1_Label.setText("");
            weast_NoC2_Label.setText("");
            weast_NoC3_Label.setText("");
        }
        
        if (x == x_dimension - 1 && peripheralPosition != MPSoCConfig.PERIPH_POS_EAST) {
            east_NoC1_Label.setText("");
            east_NoC2_Label.setText("");
            east_NoC3_Label.setText("");
        }
        
        if (y == y_dimension - 1 && peripheralPosition != MPSoCConfig.PERIPH_POS_SOUTH) {
            south_NoC1_Label.setText("");
            south_NoC2_Label.setText("");
            south_NoC3_Label.setText("");
        }
        
        if (y == 0  && peripheralPosition != MPSoCConfig.PERIPH_POS_NORTH) {
            north_NoC1_Label.setText("");
            north_NoC2_Label.setText("");
            north_NoC3_Label.setText("");
        }
        


    }

    public void paintArrow(int arrow) {

        UJPanelImagem panel = null;

        needReset = true;
        
        switch (arrow) {
            case MPSoCConfig.EAST_IN_NOC1:
                panel = in_east_NoC1;
                panel.setImagem("/images/red_left.png");
                break;
            case MPSoCConfig.EAST_IN_NOC2:
                panel = in_east_NoC2;
                panel.setImagem("/images/red_left.png");
                break;
            case MPSoCConfig.EAST_IN_NOC3:
                panel = in_east_NoC3;
                panel.setImagem("/images/red_left.png");
                break;
            case MPSoCConfig.EAST_OUT_NOC1:
                panel = out_east_NoC1;
                panel.setImagem("/images/red_undirected_h.png");
                break;
            case MPSoCConfig.EAST_OUT_NOC2:
                panel = out_east_NoC2;
                panel.setImagem("/images/red_undirected_h.png");
                break;
            case MPSoCConfig.EAST_OUT_NOC3:
                panel = out_east_NoC3;
                panel.setImagem("/images/red_undirected_h.png");
                break;
            case MPSoCConfig.NORTH_IN_NOC1:
                panel = in_north_NoC1;
                panel.setImagem("/images/red_down.png");
                break;
            case MPSoCConfig.NORTH_IN_NOC2:
                panel = in_north_NoC2;
                panel.setImagem("/images/red_down.png");
                break;
            case MPSoCConfig.NORTH_IN_NOC3:
                panel = in_north_NoC3;
                panel.setImagem("/images/red_down.png");
                break;
            case MPSoCConfig.NORTH_OUT_NOC1:
                panel = out_north_NoC1;
                panel.setImagem("/images/red_undirected_v.png");
                break;
            case MPSoCConfig.NORTH_OUT_NOC2:
                panel = out_north_NoC2;
                panel.setImagem("/images/red_undirected_v.png");
                break;
            case MPSoCConfig.NORTH_OUT_NOC3:
                panel = out_north_NoC3;
                panel.setImagem("/images/red_undirected_v.png");
                break;
            case MPSoCConfig.WEAST_IN_NOC1:
                panel = in_weast_NoC1;
                panel.setImagem("/images/red_right.png");
                break;
            case MPSoCConfig.WEAST_IN_NOC2:
                panel = in_weast_NoC2;
                panel.setImagem("/images/red_right.png");
                break;
            case MPSoCConfig.WEAST_IN_NOC3:
                panel = in_weast_NoC3;
                panel.setImagem("/images/red_right.png");
                break;
            case MPSoCConfig.WEAST_OUT_NOC1:
                panel = out_weast_NoC1;
                panel.setImagem("/images/red_undirected_h.png");
                break;
            case MPSoCConfig.WEAST_OUT_NOC2:
                panel = out_weast_NoC2;
                panel.setImagem("/images/red_undirected_h.png");
                break;
            case MPSoCConfig.WEAST_OUT_NOC3:
                panel = out_weast_NoC3;
                panel.setImagem("/images/red_undirected_h.png");
                break;
            case MPSoCConfig.SOUTH_IN_NOC1:
                panel = in_south_NoC1;
                panel.setImagem("/images/red_up.png");
                break;
            case MPSoCConfig.SOUTH_IN_NOC2:
                panel = in_south_NoC2;
                panel.setImagem("/images/red_up.png");
                break;
            case MPSoCConfig.SOUTH_IN_NOC3:
                panel = in_south_NoC3;
                panel.setImagem("/images/red_up.png");
                break;
            case MPSoCConfig.SOUTH_OUT_NOC1:
                panel = out_south_NoC1;
                panel.setImagem("/images/red_undirected_v.png");
                break;
            case MPSoCConfig.SOUTH_OUT_NOC2:
                panel = out_south_NoC2;
                panel.setImagem("/images/red_undirected_v.png");
                break;
            case MPSoCConfig.SOUTH_OUT_NOC3:
                panel = out_south_NoC3;
                panel.setImagem("/images/red_undirected_v.png");
                break;
            //L1.5
            case MPSoCConfig.LOCAL_IN_NOC1:
                panel = local_NoC1_in;
                panel.setImagem("/images/red_local_in_L15.png");
                break;
            case MPSoCConfig.LOCAL_OUT_NOC2:
                panel = local_NoC2_out;
                panel.setImagem("/images/red_local_out_L15.png");
                break;
            case MPSoCConfig.LOCAL_IN_NOC3:
                panel = local_NoC3_in;
                panel.setImagem("/images/red_local_in_L15.png");
                break;
                
           //L2
            case MPSoCConfig.LOCAL_OUT_NOC1:
                panel = local_NoC1_out;
                panel.setImagem("/images/red_local_out_L2.png");
                break;
            case MPSoCConfig.LOCAL_IN_NOC2:
                panel = local_NoC2_in;
                panel.setImagem("/images/red_local_in_L2.png");
                break;
            case MPSoCConfig.LOCAL_OUT_NOC3:
                panel = local_NoC3_out;
                panel.setImagem("/images/red_local_out_L2.png");
                break;
            
            default:
                needReset = false;
                break;
        }

        panel.repaint();

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel east_NoC1_Label;
    private javax.swing.JLabel east_NoC2_Label;
    private javax.swing.JLabel east_NoC3_Label;
    private componentes.UJPanelImagem in_east_NoC1;
    private componentes.UJPanelImagem in_east_NoC2;
    private componentes.UJPanelImagem in_east_NoC3;
    private componentes.UJPanelImagem in_north_NoC1;
    private componentes.UJPanelImagem in_north_NoC2;
    private componentes.UJPanelImagem in_north_NoC3;
    private componentes.UJPanelImagem in_south_NoC1;
    private componentes.UJPanelImagem in_south_NoC2;
    private componentes.UJPanelImagem in_south_NoC3;
    private componentes.UJPanelImagem in_weast_NoC1;
    private componentes.UJPanelImagem in_weast_NoC2;
    private componentes.UJPanelImagem in_weast_NoC3;
    private javax.swing.JLabel l15_label;
    private javax.swing.JLabel l2_label;
    private javax.swing.JLabel local_NoC1_Label;
    private componentes.UJPanelImagem local_NoC1_in;
    private componentes.UJPanelImagem local_NoC1_out;
    private javax.swing.JLabel local_NoC2_Label;
    private componentes.UJPanelImagem local_NoC2_in;
    private componentes.UJPanelImagem local_NoC2_out;
    private javax.swing.JLabel local_NoC3_Label;
    private componentes.UJPanelImagem local_NoC3_in;
    private componentes.UJPanelImagem local_NoC3_out;
    private javax.swing.JLabel noc1_label;
    private javax.swing.JLabel noc2_label;
    private javax.swing.JLabel noc3_label;
    private javax.swing.JLabel north_NoC1_Label;
    private javax.swing.JLabel north_NoC2_Label;
    private javax.swing.JLabel north_NoC3_Label;
    private componentes.UJPanelImagem out_east_NoC1;
    private componentes.UJPanelImagem out_east_NoC2;
    private componentes.UJPanelImagem out_east_NoC3;
    private componentes.UJPanelImagem out_north_NoC1;
    private componentes.UJPanelImagem out_north_NoC2;
    private componentes.UJPanelImagem out_north_NoC3;
    private componentes.UJPanelImagem out_south_NoC1;
    private componentes.UJPanelImagem out_south_NoC2;
    private componentes.UJPanelImagem out_south_NoC3;
    private componentes.UJPanelImagem out_weast_NoC1;
    private componentes.UJPanelImagem out_weast_NoC2;
    private componentes.UJPanelImagem out_weast_NoC3;
    private componentes.UJPanelImagem router;
    private javax.swing.JLabel routerLabel;
    private javax.swing.JLabel south_NoC1_Label;
    private javax.swing.JLabel south_NoC2_Label;
    private javax.swing.JLabel south_NoC3_Label;
    private javax.swing.JLabel weast_NoC1_Label;
    private javax.swing.JLabel weast_NoC2_Label;
    private javax.swing.JLabel weast_NoC3_Label;
    // End of variables declaration//GEN-END:variables

    public int getRouter_address() {
        return router_address;
    }

    public boolean isNeedReset() {
        return needReset;
    }

    public int getPeripheralPosition() {
        return peripheralPosition;
    }
    

}
