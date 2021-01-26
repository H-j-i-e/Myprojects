package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

class Change implements ActionListener {
    //编辑学生流动情况表：包括了：转系、休学、复学、退学、毕业

    public void actionPerformed(ActionEvent e){
        JFrame frame1=new JFrame("输入学籍变更情况");
        frame1.setSize(1300,700);
        frame1.setLocationRelativeTo(null);
        frame1.setLayout(null);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setVisible(true);
        Font font=new Font("宋体", Font.BOLD,15);

        JLabel j1=new JLabel("学号");j1.setFont(font);
        JTextField t1=new JTextField(20);
        JButton but1=new JButton("个人信息查询");
        JButton but2=new JButton("查询当前所有");
        frame1.add(j1);
        frame1.add(t1);
        frame1.add(but1);
        frame1.add(but2);
        j1.setBounds(400,20,50,30);
        t1.setBounds(460,20,100,30);
        but1.setBounds(570,20,150,30);
        but2.setBounds(740,20,150,30);

        String[] cname=new String[]{"记录号","学号","记录时间","级别代码","级别奖项","描述"};
        DefaultTableModel model=new DefaultTableModel(cname,40);
        JTable table=new JTable(model);
        JScrollPane jsp=new JScrollPane(table){
            public Dimension getPreferredSize(){return new Dimension(1200,350);}
        };
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frame1.add(jsp);
        jsp.setBounds(30,70,1200,350);
        //获取新成员的数据
        JLabel label1=new JLabel("记录号");label1.setFont(font);
        JLabel label2=new JLabel("学号");label2.setFont(font);
        JLabel label3=new JLabel("变更代码");label3.setFont(font);
        JLabel label4=new JLabel("记录时间");label4.setFont(font);
        JLabel label5=new JLabel("描述");label5.setFont(font);
        JTextField text1=new JTextField(11);text1.setFont(font);
        JTextField text2=new JTextField(11);text2.setFont(font);
        JTextField text3=new JTextField(11);text3.setFont(font);
        JTextField text4=new JTextField(11);text4.setFont(font);
        JTextField text5=new JTextField(100);text5.setFont(font);
        frame1.add(label1);
        frame1.add(text1);
        frame1.add(label2);
        frame1.add(text2);
        frame1.add(label3);
        frame1.add(text3);
        frame1.add(label4);
        frame1.add(text4);
        frame1.add(label5);
        frame1.add(text5);

        label1.setBounds(200,450,80,30);
        text1.setBounds(290,450,150,30);
        label2.setBounds(470,450,80,30);
        text2.setBounds(540,450,150,30);
        label3.setBounds(720,450,80,30);
        text3.setBounds(790,450,150,30);

        label4.setBounds(200,530,80,30);
        text4.setBounds(290,530,150,30);
        label5.setBounds(470,530,80,30);
        text5.setBounds(540,530,280,30);

        JButton b3=new JButton("确认添加");
        b3.setBounds(840,530,120,30);
        frame1.add(b3);
        try{
            //现在要输入信息
            b3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int number = Integer.parseInt(text1.getText());
                    String id = text2.getText();
                    int no = Integer.parseInt(text3.getText());
                    String temp = text4.getText();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
                    Date rec_time = new Date();
                    String rec_=null;
                    try {
                        rec_time=sdf.parse(temp);
                        rec_ = sdf.format(rec_time);
                    } catch (Exception ee2) {
                        ee2.printStackTrace();
                    }
                    String description = text5.getText();
                    try {
                        Conn_db.stat.execute("update Changes set Id='"+id+"',Change_no="+no+",Rec_time='"+rec_+"',Description='"+description+"' where Number="+number);
                        JOptionPane.showMessageDialog(null,"添加成功！");
                    }catch (Exception ee7){
                        ee7.printStackTrace();
                    }
                }
            });
        }catch (Exception ee8){
            ee8.printStackTrace();
        }
        //查询个人学籍变更信息
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs=null;
                String sql11="select distinct * from Changes inner join C on Changes.Change_no=C.Change_no";
                try{
                    int num=0;
                    int g4=0;
                    String target=t1.getText();
                    rs=Conn_db.stat.executeQuery(sql11);
                    while(rs.next()){
                        String id=rs.getString("Id");
                        if(target.equals(id)) {
                            g4=1;
                            int number = Integer.parseInt(rs.getString("Number"));
                            int change_no = rs.getInt("Changes.Change_no");
                            Date rec = rs.getDate("Rec_time");
                            String des = rs.getString("Changes.Description");
                            String des1 = rs.getString("C.Description");
                            Object[] str = new Object[]{number, id, rec, change_no, des1, des};
                            model.insertRow(num++, str);
                        }
                    }
                    if(g4==0){
                        JOptionPane.showMessageDialog(null,"该信息不存在！");
                    }

                }catch (Exception ee9){
                    ee9.printStackTrace();
                }



            }
        });
        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs=null;
                String sql11="select distinct * from Changes inner join C on Changes.Change_no=C.Change_no";
                try{
                    int num=0;
                    rs=Conn_db.stat.executeQuery(sql11);
                    while(rs.next()){
                        int number=Integer.parseInt(rs.getString("Number"));
                        String id=rs.getString("Id");
                        int change_no=rs.getInt("Changes.Change_no");
                        Date rec=rs.getDate("Rec_time");
                        String des=rs.getString("Changes.Description");
                        String des1=rs.getString("C.Description");
                        Object[] str=new Object[]{number,id,rec,change_no,des1,des};
                        model.insertRow(num++,str);
                    }

                }catch (Exception ee9){
                    ee9.printStackTrace();
                }
            }
        });

    }

}
//查询流动情况
class Query_C implements ActionListener{
    public void actionPerformed(ActionEvent e){
        JFrame frame2=new JFrame("查询学籍变更情况");
        frame2.setLayout(new FlowLayout());
        frame2.setSize(1700,800);
        //frame2.setLocationRelativeTo(null);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame2.setVisible(true);

        String[] cname=new String[]{"记录号","学号","记录时间","变更代码","变更类型","描述"};
        DefaultTableModel model=new DefaultTableModel(cname,40);
        JTable table=new JTable(model);
        JScrollPane jsp=new JScrollPane(table){
            public Dimension getPreferredSize(){return new Dimension(1500,500);}
        };
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frame2.add(jsp);

        ResultSet rs=null;
        String sql11="select distinct * from Changes inner join C on Changes.Change_no=C.Change_no";
        try{
            int num=0;
            rs=Conn_db.stat.executeQuery(sql11);
            while(rs.next()){
                int number=Integer.parseInt(rs.getString("Number"));
                String id=rs.getString("Id");
                int change_no=rs.getInt("Changes.Change_no");
                Date rec=rs.getDate("Rec_time");
                String des=rs.getString("Changes.Description");
                String des1=rs.getString("C.Description");
                Object[] str=new Object[]{number,id,rec,change_no,des1,des};
                model.insertRow(num++,str);
            }
            JButton b=new JButton("结束浏览");
            frame2.add(b);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame2.setVisible(false);
                }
            });
        }catch (Exception ee9){
            ee9.printStackTrace();
        }

    }
}
//新增流动情况
class Add_C implements ActionListener{
    public void actionPerformed(ActionEvent e){
        JFrame frame3=new JFrame("添加变更情况");
        frame3.setSize(1300,700);
        frame3.setLocationRelativeTo(null);//设置窗口位置为中心
        frame3.setLayout(null);
        frame3.setVisible(true);
        frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Font font=new Font("宋体", Font.BOLD,15);

        JLabel j1=new JLabel("学号");j1.setFont(font);
        JTextField t1=new JTextField(20);
        JButton but1=new JButton("个人信息查询");
        JButton but2=new JButton("查询当前所有");
        frame3.add(j1);
        frame3.add(t1);
        frame3.add(but1);
        frame3.add(but2);
        j1.setBounds(400,20,50,30);
        t1.setBounds(460,20,100,30);
        but1.setBounds(570,20,150,30);
        but2.setBounds(740,20,150,30);

        String[] cname=new String[]{"记录号","学号","记录时间","级别代码","级别奖项","描述"};
        DefaultTableModel model=new DefaultTableModel(cname,40);
        JTable table=new JTable(model);
        JScrollPane jsp=new JScrollPane(table){
            public Dimension getPreferredSize(){return new Dimension(1200,350);}
        };
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frame3.add(jsp);
        jsp.setBounds(30,70,1200,350);

        //获取新成员的数据
        JLabel label1=new JLabel("记录号"); label1.setFont(font);
        JLabel label2=new JLabel("学号");label2.setFont(font);
        JLabel label4=new JLabel("变更代码");label4.setFont(font);
        JLabel label5=new JLabel("记录时间");label5.setFont(font);
        JLabel label6=new JLabel("描述");label6.setFont(font);

        JTextField text1=new JTextField(11);text1.setFont(font);
        JTextField text2=new JTextField(11);text2.setFont(font);
        JTextField text4=new JTextField(11);text4.setFont(font);
        JTextField text5=new JTextField(11);text5.setFont(font);
        JTextField text6=new JTextField(100);text6.setFont(font);

        frame3.add(label1);
        frame3.add(text1);
        frame3.add(label2);
        frame3.add(text2);
        frame3.add(label4);
        frame3.add(text4);
        frame3.add(label5);
        frame3.add(text5);
        frame3.add(label6);
        frame3.add(text6);

        label1.setBounds(200,450,80,30);
        text1.setBounds(290,450,150,30);
        label2.setBounds(470,450,80,30);
        text2.setBounds(540,450,150,30);
        label4.setBounds(720,450,80,30);
        text4.setBounds(790,450,150,30);

        label5.setBounds(200,530,80,30);
        text5.setBounds(290,530,150,30);
        label6.setBounds(470,530,80,30);
        text6.setBounds(540,530,280,30);

        JButton b3=new JButton("确认添加");
        b3.setBounds(840,530,120,30);
        frame3.add(b3);
        //现在输入添加信息
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int number=Integer.parseInt(text1.getText());
                String id=text2.getText();
                int cno=Integer.parseInt(text4.getText());
                String temp = text5.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
                Date rec_time = new Date();
                String rec_=null;
                try {
                    rec_time=sdf.parse(temp);
                    rec_ = sdf.format(rec_time);
                } catch (Exception ee2) {
                    ee2.printStackTrace();
                }
                String des=text6.getText();
                ResultSet s1=null;
                int n3=0;
                try {
                    s1=Conn_db.stat.executeQuery("select count(*) from Changes");
                    while(s1.next()){
                        n3=s1.getInt("count(*)");
                    }
                    if(number>n3) {
                        Conn_db.stat.execute("insert into Changes values( " + number + ",'" + id + "'," + cno + ",'" + rec_ + "','" + des + "')");
                        JOptionPane.showMessageDialog(null,"添加成功！");
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"该信息已存在");
                    }
                }catch (Exception ee10){
                    ee10.printStackTrace();
                }
            }
        });
        //查询个人学籍变更信息
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs=null;
                String sql11="select distinct * from Changes inner join C on Changes.Change_no=C.Change_no";
                try{
                    int num=0;
                    int g4=0;
                    String target=t1.getText();
                    rs=Conn_db.stat.executeQuery(sql11);
                    while(rs.next()){
                        String id=rs.getString("Id");
                        if(target.equals(id)) {
                            g4=1;
                            int number = Integer.parseInt(rs.getString("Number"));
                            int change_no = rs.getInt("Changes.Change_no");
                            Date rec = rs.getDate("Rec_time");
                            String des = rs.getString("Changes.Description");
                            String des1 = rs.getString("C.Description");
                            Object[] str = new Object[]{number, id, rec, change_no, des1, des};
                            model.insertRow(num++, str);
                        }
                    }
                    if(g4==0){
                        JOptionPane.showMessageDialog(null,"该信息不存在！");
                    }

                }catch (Exception ee9){
                    ee9.printStackTrace();
                }

            }
        });
        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs=null;
                String sql11="select distinct * from Changes inner join C on Changes.Change_no=C.Change_no";
                try{
                    int num=0;
                    rs=Conn_db.stat.executeQuery(sql11);
                    while(rs.next()){
                        int number=Integer.parseInt(rs.getString("Number"));
                        String id=rs.getString("Id");
                        int change_no=rs.getInt("Changes.Change_no");
                        Date rec=rs.getDate("Rec_time");
                        String des=rs.getString("Changes.Description");
                        String des1=rs.getString("C.Description");
                        Object[] str=new Object[]{number,id,rec,change_no,des1,des};
                        model.insertRow(num++,str);
                    }

                }catch (Exception ee9){
                    ee9.printStackTrace();
                }
            }
        });


    }
}
class Delete_One_Changes  implements ActionListener{
    public void actionPerformed(ActionEvent e){
        JFrame frame5 = new JFrame("删除个别学籍信息");
        frame5.setSize(400, 250);
        frame5.setLocationRelativeTo(null);
        frame5.setLayout(null);
        frame5.setVisible(true);
        frame5.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel label = new JLabel("需删除的学生学号");
        JTextField text = new JTextField(11);
        panel.add(label);
        panel.add(text);
        frame5.add(panel);
        panel.setBounds(25, 50, 400, 50);
        JButton b5 = new JButton("确认");
        frame5.add(b5);
        b5.setBounds(150, 130, 80, 30);
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String target_id = text.getText();
                if (!target_id.equals("")) {
                    String sql = "delete from Changes where Id='" + target_id + "'";
                    try {
                        //查询表中是否存在该学生
                        ResultSet rs = Conn_db.stat.executeQuery("select * from Changes");
                        boolean flag = false;
                        while (rs.next()) {
                            String id = rs.getString("Id");
                            if (target_id.equals(id)) {
                                Conn_db.stat.execute(sql);
                                JOptionPane.showMessageDialog(null, "删除成功！");
                                frame5.setVisible(false);
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            JOptionPane.showMessageDialog(null, "抱歉，系统中没有该学生的学籍变更信息！");
                        }

                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error:您还没有输入要删除的学生信息的学号！");
                }
            }
        });
    }
}
class Delete_All_Changes  implements ActionListener{
    public void actionPerformed(ActionEvent e){
        JFrame frame6=new JFrame("删除全部学籍变更数据");
        frame6.setSize(400,100);
        frame6.setLocationRelativeTo(null);
        frame6.setLayout(new FlowLayout());
        frame6.setVisible(true);
        frame6.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel label=new JLabel("确定删除全部学籍变更数据吗？");
        JButton b1=new JButton("确定");
        JButton b2=new JButton("取消");
        frame6.add(label);
        frame6.add(b1);
        frame6.add(b2);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Conn_db.stat.execute("drop table Changes");
                    JOptionPane.showMessageDialog(null,"删除成功！");
                    Conn_db.stat.execute("create table Changes(Number int(11) primary key ,Id varchar(11),Change_no int(11),Rec_time Date,Description varchar(100))");
                    frame6.setVisible(false);
                }catch (Exception e6){
                    e6.printStackTrace();
                }
                int n0=1,sum=0;
                try {
                    ResultSet rss=Conn_db.stat.executeQuery("select count(*) from stu_infos");
                    while(rss.next()){
                        sum=rss.getInt("count(*)");}
                    while(n0<=sum) {
                        String sql33 ="insert into Changes values("+n0+ ",'0'," + "0,'1900-01-01','无')";
                        n0++;
                        Conn_db.stat.execute(sql33);
                    }
                }catch (Exception ee30){
                    ee30.printStackTrace();
                }

            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame6.setVisible(false);
            }
        });
    }
}