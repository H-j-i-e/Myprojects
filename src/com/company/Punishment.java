package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

class Punishment implements ActionListener{

    public void actionPerformed(ActionEvent e) {
        JFrame frame1=new JFrame("输入惩罚情况");
        frame1.setSize(1300,700);
        frame1.setLocationRelativeTo(null);
        frame1.setLayout(null);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setVisible(true);
        Font font=new Font("宋体", Font.BOLD,15);
        //定义基本组件以及显示框架
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

        //获得新的数据
        JLabel label1=new JLabel("记录号");label1.setFont(font);
        JLabel label2=new JLabel("学号");label2.setFont(font);
        JLabel label3=new JLabel("级别代码");label3.setFont(font);
        JLabel label4=new JLabel("记录时间");label4.setFont(font);
        JLabel label45=new JLabel("是否生效");label45.setFont(font);
        JLabel label5=new JLabel("描述");label5.setFont(font);
        JTextField text1=new JTextField(11);text1.setFont(font);
        JTextField text2=new JTextField(11);text2.setFont(font);
        JTextField text3=new JTextField(11);text3.setFont(font);
        JTextField text4=new JTextField(11);text4.setFont(font);
        JTextField text45=new JTextField(11);text45.setFont(font);
        JTextField text5=new JTextField(100);text5.setFont(font);
        frame1.add(label1);
        frame1.add(text1);
        frame1.add(label2);
        frame1.add(text2);
        frame1.add(label3);
        frame1.add(text3);
        frame1.add(label4);
        frame1.add(text4);
        frame1.add(label45);
        frame1.add(text45);
        frame1.add(label5);
        frame1.add(text5);

        label1.setBounds(200,450,80,30);
        text1.setBounds(290,450,150,30);
        label2.setBounds(470,450,80,30);
        text2.setBounds(540,450,150,30);
        label3.setBounds(720,450,80,30);
        text3.setBounds(790,450,150,30);
        label4.setBounds(950,450,80,30);
        text4.setBounds(1020,450,150,30);

        label45.setBounds(200,530,80,30);
        text45.setBounds(290,530,150,30);
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
                    int levels = Integer.parseInt(text3.getText());
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
                    String enable=text45.getText();
                    String description = text5.getText();
                    try {
                        Conn_db.stat.execute("update Punishment set Id='"+id+"',Levels="+levels+",Rec_time='"+rec_+"',Enable='"+enable+"',Description='"+description+"' where Number="+number);
                        JOptionPane.showMessageDialog(null,"添加成功！");
                    }catch (Exception ee2){
                        ee2.printStackTrace();
                    }
                }
            });
        }catch (Exception ee3){
            ee3.printStackTrace();
        }
        //查询个人处罚信息
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs=null;
                String sql1="select distinct * from Punishment inner join P on Punishment.Levels=P.Levels";
                try{
                    String target=t1.getText();
                    int num=0;
                    int g5=0;
                    rs=Conn_db.stat.executeQuery(sql1);
                    while(rs.next()){
                        String id=rs.getString("Id");
                        if(target.equals(id)) {
                            g5=1;
                            int number = Integer.parseInt(rs.getString("Number"));
                            int levels = rs.getInt("Levels");
                            Date rec = rs.getDate("Rec_time");
                            String ab = rs.getString("Enable");
                            String des = rs.getString("Punishment.Description");
                            String des1 = rs.getString("P.Description");
                            Object[] str = new Object[]{number, id, rec, levels, des1, ab, des};
                            model.insertRow(num++, str);
                        }
                    }
                    if(g5==0){
                        JOptionPane.showMessageDialog(null,"该学生的处罚信息不存在！");
                    }
                }catch (Exception ee4){
                    ee4.printStackTrace();
                }
            }
        });
        //查询所有的处罚信息
        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs=null;
                String sql1="select distinct * from Punishment inner join P on Punishment.Levels=P.Levels";
                try{
                    int num=0;
                    rs=Conn_db.stat.executeQuery(sql1);
                    while(rs.next()){
                        int number=Integer.parseInt(rs.getString("Number"));
                        String id=rs.getString("Id");
                        int levels=rs.getInt("Levels");
                        Date rec=rs.getDate("Rec_time");
                        String ab=rs.getString("Enable");
                        String des=rs.getString("Punishment.Description");
                        String des1=rs.getString("P.Description");
                        Object[] str=new Object[]{number,id,rec,levels,des1,ab,des};
                        model.insertRow(num++,str);
                    }

                }catch (Exception ee4){
                    ee4.printStackTrace();
                }
            }
        });

    }
}
//查询惩罚情况表
class Query_P implements ActionListener{
    public void actionPerformed(ActionEvent e){
        JFrame frame2=new JFrame("查询惩罚情况");
        frame2.setLayout(new FlowLayout());
        frame2.setSize(1700,800);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame2.setVisible(true);

        String[] cname=new String[]{"记录号","学号","记录时间","级别代码","级别惩罚","是否生效","描述"};
        DefaultTableModel model=new DefaultTableModel(cname,40);
        JTable table=new JTable(model);
        JScrollPane jsp=new JScrollPane(table){
            public Dimension getPreferredSize(){return new Dimension(1500,500);}
        };
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frame2.add(jsp);

        ResultSet rs=null;
        String sql1="select distinct * from Punishment inner join P on Punishment.Levels=P.Levels";
        try{
            int num=0;
            rs=Conn_db.stat.executeQuery(sql1);
            while(rs.next()){
                int number=Integer.parseInt(rs.getString("Number"));
                String id=rs.getString("Id");
                int levels=rs.getInt("Levels");
                Date rec=rs.getDate("Rec_time");
                String ab=rs.getString("Enable");
                String des=rs.getString("Punishment.Description");
                String des1=rs.getString("P.Description");
                Object[] str=new Object[]{number,id,rec,levels,des1,ab,des};
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
        }catch (Exception ee4){
            ee4.printStackTrace();
        }

    }
}
//新增惩罚情况
class Add_P implements ActionListener{
    public void actionPerformed(ActionEvent e){
        JFrame frame3=new JFrame("添加惩罚情况");
        frame3.setSize(1300,700);
        frame3.setLocationRelativeTo(null);//设置窗口位置为中心
        frame3.setLayout(null);
        frame3.setVisible(true);
        frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Font font=new Font("宋体", Font.BOLD,15);

        //定义基本组件以及显示框架
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

        String[] cname=new String[]{"记录号","学号","记录时间","级别代码","级别奖项","是否生效"};
        DefaultTableModel model=new DefaultTableModel(cname,40);
        JTable table=new JTable(model);
        JScrollPane jsp=new JScrollPane(table){
            public Dimension getPreferredSize(){return new Dimension(1200,350);}
        };
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frame3.add(jsp);
        jsp.setBounds(30,70,1200,350);

        JLabel label1=new JLabel("记录号"); label1.setFont(font);
        JLabel label2=new JLabel("学号");label2.setFont(font);
        JLabel label4=new JLabel("级别代码");label4.setFont(font);
        JLabel label5=new JLabel("记录时间");label5.setFont(font);
        JLabel label56=new JLabel("是否生效");label56.setFont(font);
        JLabel label6=new JLabel("描述");label6.setFont(font);

        JTextField text1=new JTextField(11);text1.setFont(font);
        JTextField text2=new JTextField(11);text2.setFont(font);
        JTextField text4=new JTextField(11);text4.setFont(font);
        JTextField text5=new JTextField(11);text5.setFont(font);
        JTextField text56=new JTextField(11);text56.setFont(font);
        JTextField text6=new JTextField(100);text6.setFont(font);

        frame3.add(label1);
        frame3.add(text1);
        frame3.add(label2);
        frame3.add(text2);
        frame3.add(label4);
        frame3.add(text4);
        frame3.add(label5);
        frame3.add(text5);
        frame3.add(label56);
        frame3.add(text56);
        frame3.add(label6);
        frame3.add(text6);

        label1.setBounds(200,450,80,30);
        text1.setBounds(290,450,150,30);
        label2.setBounds(470,450,80,30);
        text2.setBounds(540,450,150,30);
        label4.setBounds(720,450,80,30);
        text4.setBounds(790,450,150,30);
        label5.setBounds(950,450,80,30);
        text5.setBounds(1020,450,150,30);

        label56.setBounds(200,530,80,30);
        text56.setBounds(290,530,150,30);
        label6.setBounds(470,530,80,30);
        text6.setBounds(540,530,280,30);
        JButton b3=new JButton("确认添加");
        b3.setBounds(840,530,120,30);
        frame3.add(b3);

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int number=Integer.parseInt(text1.getText());
                String id=text2.getText();
                int levels_=Integer.parseInt(text4.getText());
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
                String ab=text56.getText();
                String des=text6.getText();
                int n4=0;
                ResultSet result2=null;
                try {
                    result2=Conn_db.stat.executeQuery("select count(*) from Punishment");
                    while(result2.next()){
                        n4=result2.getInt("count(*)");
                    }
                    if(number>n4) {
                        Conn_db.stat.execute("insert into Punishment values(" + number + ",'" + id + "'," + levels_ + ",'" + rec_ + "','" + ab + "','" + des + "')");
                        JOptionPane.showMessageDialog(null,"添加成功！");
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"该信息已存在");
                    }
                }catch (Exception ee5){
                    ee5.printStackTrace();
                }
            }
        });

        //查询个人处罚信息
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs=null;
                String sql1="select distinct * from Punishment inner join P on Punishment.Levels=P.Levels";
                try{
                    String target=t1.getText();
                    int num=0;
                    int g5=0;
                    rs=Conn_db.stat.executeQuery(sql1);
                    while(rs.next()){
                        String id=rs.getString("Id");
                        if(target.equals(id)) {
                            g5=1;
                            int number = Integer.parseInt(rs.getString("Number"));
                            int levels = rs.getInt("Levels");
                            Date rec = rs.getDate("Rec_time");
                            String ab = rs.getString("Enable");
                            String des = rs.getString("Punishment.Description");
                            String des1 = rs.getString("P.Description");
                            Object[] str = new Object[]{number, id, rec, levels, des1, ab, des};
                            model.insertRow(num++, str);
                        }
                    }
                    if(g5==0){
                        JOptionPane.showMessageDialog(null,"该学生的处罚信息不存在！");
                    }
                }catch (Exception ee4){
                    ee4.printStackTrace();
                }
            }
        });
        //查询所有的处罚信息
        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs=null;
                String sql1="select distinct * from Punishment inner join P on Punishment.Levels=P.Levels";
                try{
                    int num=0;
                    rs=Conn_db.stat.executeQuery(sql1);
                    while(rs.next()){
                        int number=Integer.parseInt(rs.getString("Number"));
                        String id=rs.getString("Id");
                        int levels=rs.getInt("Levels");
                        Date rec=rs.getDate("Rec_time");
                        String ab=rs.getString("Enable");
                        String des=rs.getString("Punishment.Description");
                        String des1=rs.getString("P.Description");
                        Object[] str=new Object[]{number,id,rec,levels,des1,ab,des};
                        model.insertRow(num++,str);
                    }

                }catch (Exception ee4){
                    ee4.printStackTrace();
                }
            }
        });
    }
}
class Delete_One_P implements ActionListener{
    public void actionPerformed(ActionEvent e){
        JFrame frame5 = new JFrame("删除个别学生处罚信息");
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
                    String sql = "delete from Punishment where Id='" + target_id + "'";
                    try {
                        //查询表中是否存在该学生
                        ResultSet rs = Conn_db.stat.executeQuery("select * from Punishment");
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
                            JOptionPane.showMessageDialog(null, "抱歉，系统中没有该学生的处罚信息！");
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
class Delete_All_P implements ActionListener{
    public void actionPerformed(ActionEvent e){
        JFrame frame6=new JFrame("删除全部处罚数据");
        frame6.setSize(400,100);
        frame6.setLocationRelativeTo(null);
        frame6.setLayout(new FlowLayout());
        frame6.setVisible(true);
        frame6.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel label=new JLabel("确定删除全部处罚数据吗？");
        JButton b1=new JButton("确定");
        JButton b2=new JButton("取消");
        frame6.add(label);
        frame6.add(b1);
        frame6.add(b2);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Conn_db.stat.execute("drop table Punishment");
                    JOptionPane.showMessageDialog(null,"删除成功！");
                    Conn_db.stat.execute("create table Punishment(Number int(11) primary key,Id varchar(11),Levels int(11),Rec_time Date,Enable varchar(10),Description varchar(100))");
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
                        String sql34 ="insert into Punishment values("+n0+ ",'0'," + "0,'1900-01-01','否','无')";
                        n0++;
                        Conn_db.stat.execute(sql34);
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