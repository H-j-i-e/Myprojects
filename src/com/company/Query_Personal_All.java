package com.company;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;
import java.util.Vector;

class ButtonEditor extends DefaultCellEditor {
    protected JButton button=new JButton();
    private String label=null;
    private boolean isPushed;

    public ButtonEditor(JTextField textField) {
        super(textField);
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {

        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            JOptionPane.showMessageDialog(button, label + ": Ouch!");
            // System.out.println(label + ": Ouch!");
        }
        isPushed = false;
        return new String(label);
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

class MyButtonRender implements TableCellRenderer {
    public JPanel jPanel;
    public JButton jButton;
    public MyButtonRender(){
        initJPanel();
        initButton();
        jPanel.add(jButton);
    }
    public void initJPanel(){
        jPanel=new JPanel();
        jPanel.setLayout(null);
        jPanel.setVisible(true);
    }
    public void initButton(){
        jButton=new JButton("编辑");
        jButton.setBounds(35,1,40,30);

    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        jButton.setPreferredSize(new Dimension(table.getColumnModel().getColumn(column).getPreferredWidth(),20));
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "test");
            }
        });
        return jButton;
    }
}


class Query_Personal_All implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JFrame frame1=new JFrame("查询全部基本信息结果");
            frame1.setSize(1700,850);
            frame1.setLayout(new FlowLayout());
            frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame1.setVisible(true);

            ResultSet rs=null;
            final DefaultTableModel model1;
            JScrollPane jsp=null;
            String sql="select distinct * from stu_infos inner join Class_infos on stu_infos.Class=Class_infos.Class inner join Depart on stu_infos.SC=Depart.SC left join Changes on stu_infos.Id=Changes.Id left join C on Changes.Change_no=C.Change_no left join Reward on stu_infos.Id=Reward.Id left join R on Reward.Levels=R.Levels left join Punishment on stu_infos.Id=Punishment.Id left join P on Punishment.Levels=P.Levels";
            String[] cname=new String[]{"编号","学号","姓名","性别","班级编号","班级名称","院系编号","院系名称","出生日期","籍贯","学籍变更代号","学籍变更情况","奖励项目代号","奖励情况","惩罚情况代号","惩罚情况"};
            Vector studentVector=new Vector();
            model1=new DefaultTableModel(cname,40);
            JTable table=new JTable(model1);
            jsp=new JScrollPane(table){
                public Dimension getPreferredSize(){
                    return new Dimension(1600,500);
                }
            };
            jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            frame1.add(jsp);
            jsp.setBounds(0,0,1550,500);

            /*我们利用JTable所提供的getTableColumnModel()方法取得TableColumnModel对象,再由TableColumnModel类所提供的getColumn()方
             *法取得TableColumn对象,TableColumn类可针对表格中的每一行做具体的设置,例如设置字段的宽度,某行的标头,设置输入较复杂的
             *数据类型等等.在这里,我们利用TableColumn类所提供的setCellRender()方法,将JButton作为第一列的默认编辑组件.
             */
            int c=table.getRowCount();
            for(int i=1;i<c;i++) {
                table.setRowHeight(30);
            }
            JTextField textf=new JTextField(10);
            //table.getColumnModel().getColumn(0).setCellRenderer(new MyButtonRender());
            //table.getColumnModel().getColumn(0).setCellEditor(new ButtonEditor(textf));

            try {
                rs = Conn_db.stat.executeQuery(sql);

                int num=0,x=0;
                int numb=1;
                while(rs.next()) {
                    JButton button = new JButton("修改");
                    String id = rs.getString("stu_infos.Id");
                    String name = rs.getString("stu_infos.Name");
                    String sex = rs.getString("stu_infos.Sex");
                    String class_no = rs.getString("stu_infos.Class");
                    String class_name = rs.getString("Class_infos.Class_name");
                    String sc = rs.getString("stu_infos.SC");
                    String sc_name = rs.getString("Depart.Depart_name");
                    String birth = String.valueOf(rs.getDate("Birthday"));
                    String address = rs.getString("Address");
                    String change_name = change_name = rs.getString("C.Description");
                    int change_no = rs.getInt("Changes.Change_no");
                    if (change_no == 0) {
                        change_name = "在读";
                    }
                    int r_no = rs.getInt("Reward.Levels");
                    String r_name = rs.getString("R.Description");
                    if(r_no==0){
                        r_name="无";
                    }
                    int p_no = rs.getInt("Punishment.Levels");
                    String p_name = rs.getString("P.Description");
                    if(p_no==0){
                        p_name="无";
                    }
                    Object[] str = new Object[]{numb, id, name, sex, class_no, class_name, sc, sc_name, birth, address, change_no, change_name, r_no, r_name, p_no, p_name};
                    x++;
                    numb++;
                    model1.insertRow(num++, str);
                }
            }catch (Exception e20){
                e20.printStackTrace();
            }
                JButton b=new JButton("结束浏览");
                JButton btt=new JButton("刷新");
                frame1.add(b);
                frame1.add(btt);
                b.setBounds(600,520,120,50);
                btt.setBounds(730,520,120,50);
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame1.setVisible(false);
                    }
                });
                Font font=new Font("宋体", Font.BOLD,16);

                JLabel label1=new JLabel("编号");label1.setFont(font);
                JTextField text1=new JTextField(25);text1.setFont(font);
                JLabel label2=new JLabel("学号");label2.setFont(font);
                JTextField text2=new JTextField(25);text2.setFont(font);
                JLabel label3=new JLabel("姓名");label3.setFont(font);
                JTextField text3=new JTextField(25);text3.setFont(font);
                JLabel label4=new JLabel("性别");label4.setFont(font);
                JComboBox cb1=new JComboBox();
                cb1.addItem("请选择性别");
                cb1.addItem("男");
                cb1.addItem("女");

                JLabel label5=new JLabel("班级编号");label5.setFont(font);
                JTextField text5=new JTextField(25);text5.setFont(font);
                JLabel label6=new JLabel("院系编号");label6.setFont(font);
                JTextField text6=new JTextField(25);text6.setFont(font);
                JLabel label7=new JLabel("出生日期");label7.setFont(font);
                JTextField text7=new JTextField(25);text7.setFont(font);
                JLabel label8=new JLabel("籍贯");label8.setFont(font);
                JTextField text8=new JTextField(25);text8.setFont(font);

                JLabel label9=new JLabel("学籍变更");label9.setFont(font);
                JComboBox cb2=new JComboBox();//学籍变更选框
                cb2.addItem("代码 类型");
                cb2.addItem("0 在读");
                cb2.addItem("1 休学");
                cb2.addItem("2 复学");
                cb2.addItem("3 退学");
                cb2.addItem("4 毕业");
                cb2.addItem("5 转系");

                JLabel label10=new JLabel("获奖选项");label10.setFont(font);
                JComboBox cb3=new JComboBox();//获奖情况选框
                cb3.addItem("代码 类型");
                cb3.addItem("0 无");
                cb3.addItem("1 校一等奖学金");
                cb3.addItem("2 校二等奖学金");
                cb3.addItem("3 校三等奖学金");
                cb3.addItem("4 系一等奖学金");
                cb3.addItem("5 系二等奖学金");
                cb3.addItem("6 系三等奖学金");
                cb3.addItem("7 校特等奖学金");

                JLabel label11=new JLabel("处罚类型");label11.setFont(font);
                JComboBox cb4=new JComboBox();//学籍变更选框
                cb4.addItem("代码 类型");
                cb4.addItem("0 无");
                cb4.addItem("1 严重警告");
                cb4.addItem("2 记过");
                cb4.addItem("3 记大过");
                cb4.addItem("4 开除");
                cb4.addItem("5 警告");

                JButton bt=new JButton("确认修改");

                frame1.setLayout(null);
                frame1.add(label1);frame1.add(text1);
                frame1.add(label2);frame1.add(text2);
                frame1.add(label3);frame1.add(text3);
                frame1.add(label4);frame1.add(cb1);
                frame1.add(label5);frame1.add(text5);
                frame1.add(label6);frame1.add(text6);
                frame1.add(label7);frame1.add(text7);
                frame1.add(label8);frame1.add(text8);

                frame1.add(label9);frame1.add(cb2);
                frame1.add(label10);frame1.add(cb3);
                frame1.add(label11);frame1.add(cb4);
                frame1.add(bt);

                label1.setBounds(200,610,80,40); //编号
                text1.setBounds(270,610,200,40);
                label2.setBounds(500,610,80,40);//学号
                text2.setBounds(570,610,200,40);
                label3.setBounds(800,610,80,40);//姓名
                text3.setBounds(870,610,200,40);
                label4.setBounds(1100,610,200,40);//性别
                cb1.setBounds(1170,610,200,40);

                label5.setBounds(200,660,80,40); //班级编号
                text5.setBounds(270,660,200,40);
                label6.setBounds(500,660,80,40);//院系编号
                text6.setBounds(570,660,200,40);
                label7.setBounds(800,660,80,40);//出生日期
                text7.setBounds(870,660,200,40);
                label8.setBounds(1100,660,200,40);//籍贯
                text8.setBounds(1170,660,200,40);

                label9.setBounds(200,710,200,40);//学籍
                cb2.setBounds(270,710,200,40);
                label10.setBounds(500,710,200,40);//奖励
                cb3.setBounds(570,710,200,40);
                label11.setBounds(800,710,200,40);//处罚
                cb4.setBounds(870,710,200,40);
                bt.setBounds(1180,710,200,40);  //确认修改按钮

                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e){
                        int row=((JTable)e.getSource()).rowAtPoint(e.getPoint());//获取行的位置
                        String c00=table.getValueAt(row,0).toString();//编号
                        String c1=table.getValueAt(row,1).toString();//学号
                        String c2=table.getValueAt(row,2).toString();//姓名
                        String c3=table.getValueAt(row,3).toString();//性别///
                        String c4=table.getValueAt(row,4).toString();//班级编号
                        String c5=table.getValueAt(row,5).toString();//班名////
                        String c6=table.getValueAt(row,6).toString();//院系编号
                        String c7=table.getValueAt(row,7).toString();//院名称///
                        String c8=table.getValueAt(row,8).toString();//出生
                        String c9=table.getValueAt(row,9).toString();//籍贯
                        String c10=table.getValueAt(row,10).toString();//学籍变更代号
                        String c11=table.getValueAt(row,11).toString();//学籍变更情况
                        String c12=table.getValueAt(row,12).toString();//奖励项目代号
                        String c13=table.getValueAt(row,13).toString();//奖励情况
                        String c14=table.getValueAt(row,14).toString();//处罚代号
                        String c15=table.getValueAt(row,15).toString();//处罚情况

                        text1.setText(c00);
                        text2.setText(c1);
                        text3.setText(c2);
                        text5.setText(c4);
                        text6.setText(c6);
                        text7.setText(c8);
                        text8.setText(c9);
                    }
                });
                //bt是确认修改按钮
                bt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //首先获取下拉框的内容
                        String it1 = (String) cb1.getSelectedItem(); //性别
                        String it2 = (String) cb2.getSelectedItem();//学籍
                        String[] i2 = it2.split(" ");
                        String it3 = (String) cb3.getSelectedItem();//奖励
                        String[] i3 = it3.split(" ");
                        String it4 = (String) cb4.getSelectedItem();//处罚
                        String[] i4 = it4.split(" ");
                        //获取各个文本框内容
                        String id = text2.getText();//学号
                        String name = text3.getText();//姓名
                        String c_no = text5.getText();//班级编号
                        String s_no = text6.getText();//院系编号
                        String bir = text7.getText();//出生日期
                        Date date=new Date();
                        Date c_date=new Date();
                        String bir2=null;
                        String curren_date=null;
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            date=sdf.parse(bir);
                            bir2=sdf.format(date);
                            curren_date=sdf.format(c_date);
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                        String add = text8.getText();//籍贯

                        //根据学号修改各表内容
                        int a1 =999,a2=999,a3=999,a4=999;
                        ResultSet r1,r2,r3,r4;
                        try {
                            r1=Conn_db.stat.executeQuery("select count(*) from stu_infos where Id='" +id+"'");
                            while(r1.next()){
                                a1=r1.getInt("count(*)");}
                            r2=Conn_db.stat.executeQuery("select count(*) from Changes where Id='"+id+"'");
                            while(r2.next()){
                                a2=r2.getInt("count(*)");}
                            r3=Conn_db.stat.executeQuery("select count(*) from Reward where Id='"+id+"'");
                            while(r3.next()){
                                a3=r3.getInt("count(*)");}
                            r4=Conn_db.stat.executeQuery("select count(*) from Punishment where Id='"+id+"'");
                            while(r4.next()){
                            a4=r4.getInt("count(*)");}

                            if(a1!=0){
                                String sql1 = "delete from stu_infos where Id='" + id+"'";
                                Conn_db.stat.execute(sql1);
                                Conn_db.stat.execute("insert into stu_infos values('"+id+"','"+name+"','"+it1+"','"+c_no+"','"+s_no+"','"+bir2+"','"+add+"')");
                            }
                            a2++;
                                Conn_db.stat.execute("update Changes set Id='"+id+"', Change_no="+i2[0]+",Rec_time='"+curren_date+"' where Number='"+a2+"'");

                            a3++;
                                Conn_db.stat.execute("update Reward set Id='"+id+"', Levels="+i3[0]+",Rec_time='"+curren_date+"' where Number='"+a3+"'");

                            a4++;
                                Conn_db.stat.execute("update Punishment set Id='"+id+"',Levels="+i4[0]+",Rec_time='"+curren_date+"',Enable='是'  where Number='"+a4+"'");

                            JOptionPane.showMessageDialog(null,"修改成功！");
                        }catch(Exception e20){
                            e20.printStackTrace();
                        }


                    }
                });
                //btt是刷新按钮
                btt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for(int i=0;i<table.getRowCount();i++)
                            for(int j=0;j<table.getColumnCount();j++){
                                table.setValueAt("",i,j);
                            }
                        try {
                            ResultSet rs = Conn_db.stat.executeQuery(sql);

                            int num=0,x=0;
                            while(rs.next()) {
                                String id = rs.getString("stu_infos.Id");
                                String name = rs.getString("stu_infos.Name");
                                String sex = rs.getString("stu_infos.Sex");
                                String class_no = rs.getString("stu_infos.Class");
                                String class_name = rs.getString("Class_infos.Class_name");
                                String sc = rs.getString("stu_infos.SC");
                                String sc_name = rs.getString("Depart.Depart_name");
                                String birth = String.valueOf(rs.getDate("Birthday"));
                                String address = rs.getString("Address");
                                String change_name = change_name = rs.getString("C.Description");
                                int change_no = rs.getInt("Changes.Change_no");
                                if (change_no == 0) {
                                    change_name = "在读";
                                }
                                int r_no = rs.getInt("Reward.Levels");
                                String r_name = rs.getString("R.Description");
                                if(r_no==0){
                                    r_name="无";
                                }
                                int p_no = rs.getInt("Punishment.Levels");
                                String p_name = rs.getString("P.Description");
                                if(p_no==0){
                                    p_name="无";
                                }
                                Object[] str = new Object[]{x, id, name, sex, class_no, class_name, sc, sc_name, birth, address, change_no, change_name, r_no, r_name, p_no, p_name};
                                x++;
                                model1.insertRow(num++, str);
                            }
                        }catch (Exception e20){
                            e20.printStackTrace();
                        }
                    }
                });


}}
