package com.company;

import java.awt.*;
import java.awt.event.*;
import java.lang.invoke.SwitchPoint;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Login{
	private static void start (){
		login_entrance login=new login_entrance();//登陆界面
		try {
			new Conn_db("jdbc"); //连接对应的数据库
			new Mytable(); //建表
			new InitTable();//创建并初始化Reward表、Changes表、Punishmen表
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public static void main(String[] args){
		SwingUtilities.invokeLater(Login::start);
	}
}
class f{  //登录后进入功能主页面
	public static JFrame frame;
	public f(){}
	public f(String fname){
		frame=new JFrame(fname);
		frame.setSize(1700,850);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//对基本信息 stu_info的操作
		JButton b0=new JButton("查询全部学生信息");
		JButton b1=new JButton("查询个别学生信息");
		JButton b2=new JButton("添加学生信息");
		JButton b3=new JButton("修改学生信息");
		JButton b4=new JButton("删除个别学生信息");
		JButton b5=new JButton("删除全部学生信息");

		JButton b6=new JButton("首次输入奖励情况");
		JButton b7=new JButton("查询奖励情况");
		JButton b8=new JButton("增加奖励情况");
		JButton b77=new JButton("删除个别获奖信息");
		JButton b88=new JButton("删除全部获奖信息");

		JButton b9=new JButton("首次输入处罚情况");
		JButton b10=new JButton("查询处罚情况");
		JButton b11=new JButton("增加处罚情况");
		JButton b100=new JButton("删除个别处罚信息");
		JButton b111=new JButton("删除全部处罚信息");

		JButton b12=new JButton("首次输入学籍变更情况");
		JButton b13=new JButton("查询学籍变更情况");
		JButton b14=new JButton("增加变更情况");
		JButton b133=new JButton("删除个别学籍情况");
		JButton b144=new JButton("删除全部学籍情况");

		JButton b15=new JButton("操作全部信息");

		b0.addActionListener(new Query_all());
		b1.addActionListener(new Query_one());
		b2.addActionListener(new Add_info());
		b3.addActionListener(new Alter_info());
		b4.addActionListener(new Delete_one());
		b5.addActionListener(new Delete_all());
		b6.addActionListener(new Reward());
		b7.addActionListener(new Query_R());
		b8.addActionListener(new Add_R());
		b77.addActionListener(new Delete_One_R());
		b88.addActionListener(new Delete_All_R());
		b9.addActionListener(new Punishment());
		b10.addActionListener(new Query_P());
		b11.addActionListener(new Add_P());
		b12.addActionListener(new Change());
		b13.addActionListener(new Query_C());
		b14.addActionListener(new Add_C());
		b15.addActionListener(new Query_Personal_All());
		b133.addActionListener(new Delete_One_Changes());
		b144.addActionListener(new Delete_All_Changes());
		b100.addActionListener(new Delete_One_P());
		b111.addActionListener(new Delete_All_P());

		Font font=new Font("宋体",Font.BOLD,15);
		JPanel p1=new JPanel();
		JLabel label1=new JLabel("学生基本信息操作区");label1.setFont(font);
		p1.setBorder(BorderFactory.createLineBorder(Color.pink,3));
		frame.add(label1);
		p1.setLayout(null);
		p1.add(b0);
		p1.add(b1);
		p1.add(b2);
		p1.add(b3);
		p1.add(b4);
		p1.add(b5);//对个人信息的操作
		frame.add(p1);

		JPanel p2=new JPanel();
		p2.setBorder(BorderFactory.createLineBorder(Color.pink,3));
		JLabel label2=new JLabel("学生奖励信息操作区");label2.setFont(font);
		p2.setLayout(null);
		frame.add(label2);
		p2.add(b6);
		p2.add(b7);
		p2.add(b8);
		p2.add(b77);
		p2.add(b88);//奖励情况操作
		frame.add(p2);

		JPanel p3=new JPanel();
		p3.setBorder(BorderFactory.createLineBorder(Color.pink,3));
		JLabel label3=new JLabel("学生处罚信息操作区");label3.setFont(font);
		p3.setLayout(null);
		frame.add(label3);
		p3.add(b9);
		p3.add(b10);
		p3.add(b11);
		p3.add(b100);
		p3.add(b111);//惩罚情况操作
		frame.add(p3);

		JPanel p4=new JPanel();
		p4.setBorder(BorderFactory.createLineBorder(Color.pink,3));
		JLabel label4=new JLabel("学生学籍信息操作区");label4.setFont(font);
		p4.setLayout(null);
		frame.add(label4);
		p4.add(b12);
		p4.add(b13);
		p4.add(b14);
		p4.add(b133);
		p4.add(b144);//学籍变更操作
		frame.add(p4);

		JPanel p5=new JPanel();
		p5.setBorder(BorderFactory.createLineBorder(Color.pink,3));
		JLabel label5=new JLabel("学生综合信息操作区");label5.setFont(font);
		p5.setLayout(null);
		frame.add(label5);
		p5.add(b15);//全部信息操作
		frame.add(p5);

		//设计主界面整体排版
		label1.setBounds(80,50,200,100);
		p1.setBounds(350,50,990,100);//面板的排版

		label2.setBounds(80,200,200,100);
	    p2.setBounds(350,200,990,100);

	    label3.setBounds(80,350,200,100);
		p3.setBounds(350,350,990,100);

		label4.setBounds(80,500,200,100);
		p4.setBounds(350,500,990,100);

		label5.setBounds(80,650,200,100);
		p5.setBounds(350,650,990,100);

		//设计各个区内排版
		b0.setBounds(20,25,150,50);//面板内按钮的排版
		b1.setBounds(180,25,150,50);
		b2.setBounds(340,25,150,50);
		b3.setBounds(500,25,150,50);
		b4.setBounds(660,25,150,50);
		b5.setBounds(820,25,150,50);

		b6.setBounds(20,25,150,50);
		b7.setBounds(180,25,150,50);
		b8.setBounds(340,25,150,50);
		b77.setBounds(500,25,150,50);
		b88.setBounds(660,25,150,50);

		b9.setBounds(20,25,150,50);
		b10.setBounds(180,25,150,50);
		b11.setBounds(340,25,150,50);
		b100.setBounds(500,25,150,50);
		b111.setBounds(660,25,150,50);


		b12.setBounds(20,25,150,50);
		b13.setBounds(180,25,150,50);
		b14.setBounds(340,25,150,50);
		b133.setBounds(500,25,150,50);
		b144.setBounds(660,25,150,50);

		b15.setBounds(20,25,150,50);
	}
}
//将程序连接数据库
class Conn_db{
	String dbname;
	public static Statement stat=null;//此处定义为static变量，方便在各个类里引用
	public static int flag=0;
	public Conn_db(String dbname) throws Exception{//使用构造函数来选择对应的数据库，本代码中为了方便，默认使用jdbc数据库中的stu_infos表
		this.dbname=dbname;
		Connection conn=null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");//在mysql的较高版本中加载数据库驱动时需要写成该形式
			String url="jdbc:mysql://localhost:3306/"+dbname+"?serverTimezone=UTC";//其中设置了统一的标准世界时间
			String username="root"; //我本地的数据库名为root，密码为root
			String password="root";
			conn= DriverManager.getConnection(url,username,password);
			stat=conn.createStatement();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
//建表，此处只输入了少量的数据
class Mytable{
	public Mytable() throws Exception{
		String sql1="create table stu_infos(Id varchar(11) primary key,Name varchar(45),Sex varchar(10),Class varchar(11),SC varchar(11),Birthday Date,Address varchar(100))";
		String sql2="insert into stu_infos values('1','李平','男', '02', '05', '1999-01-01','湖南'),('2','王丫','女', '01', '05','1999-02-03', '广西'),('3','钟信','男', '03', '01','2000-05-12', '重庆'),('4','木林','女', '04', '01','2001-02-18' ,'北京'),('5','李文','男', '04', '05','2000-10-12', '上海'),('6','丽思','女', '01', '03','2000-03-19','大连')";
		String sql3="create table Class_infos(Class varchar(11) primary key, Class_name varchar(11),Monitor varchar(11))";
		String sql4="insert into Class_infos values('01','计算机科学与技术一班','1905010120'),('02','计算机科学与技术二班','1905010210'),('03','计算机科学与技术三班','1905010312'),('04','计算机科学与技术四班','1905010415'),('05','计算机科学与技术五班','1905010521'),('06','计算机科学与技术六班','1905010608'),('07','计算机科学与技术七班','1905010705')";
		String sql5="create table Depart(SC varchar(11) primary key,Depart_name varchar(30))";
		String sql6="insert into Depart values('01','资安学院'),('02','数学学院'),('03','化工学院'),('04','机电学院'),('05','计算机学院')";
		Conn_db.stat.execute(sql1);
		Conn_db.stat.execute(sql2);
		Conn_db.stat.execute(sql3);
		Conn_db.stat.execute(sql4);
		Conn_db.stat.execute(sql5);
		Conn_db.stat.execute(sql6);
	}
}
//查询全部人的全部信息
class Query_all implements ActionListener {
	public void actionPerformed(ActionEvent e){
		JFrame frame1=new JFrame("查询全部基本信息结果");//新定义一个窗口
		frame1.setSize(1700,800);
		frame1.setLayout(new FlowLayout());
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//设置此关闭类型，只关闭当先窗口
		frame1.setVisible(true);

		ResultSet rs=null;
		DefaultTableModel model1=null;
		JScrollPane jsp=null;
		String sql="select * from stu_infos inner join Class_infos on stu_infos.Class=Class_infos.Class inner join Depart on stu_infos.SC=Depart.SC";
		String[] cname=new String[]{"学号","姓名","性别","班级编号","班级名称","院系编号","院系名称","出生日期","籍贯"};
		model1=new DefaultTableModel(cname,40);
		JTable table=new JTable(model1);
		jsp=new JScrollPane(table){
			public Dimension getPreferredSize(){
				return new Dimension(1500,500);
			}
		};
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame1.add(jsp);
		try {
			rs = Conn_db.stat.executeQuery(sql);
			int num=0;
			while(rs.next()){
				String id=rs.getString("Id");
				String name=rs.getString("Name");
				String sex=rs.getString("Sex");
				String class_no=rs.getString("stu_infos.Class");
				String class_name=rs.getString("Class_infos.Class_name");
				String sc=rs.getString("stu_infos.SC");
				String sc_name=rs.getString("Depart.Depart_name");
				String birth=String.valueOf(rs.getDate("Birthday"));
				String address=rs.getString("Address");
				Object[] str=new Object[]{id,name,sex,class_no,class_name,sc,sc_name,birth,address};
				model1.insertRow(num++,str);
			}
			JButton b=new JButton("结束浏览");
			frame1.add(b);
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame1.setVisible(false);
				}
			});
		}catch (Exception e1){
			e1.printStackTrace();
		}
	}
}

class Query_one implements ActionListener {
	public void actionPerformed(ActionEvent e){
		JFrame frame2=new JFrame("查询个别学生信息");
		frame2.setSize(1700,800);
		frame2.setLayout(new BorderLayout());
		frame2.setVisible(true);
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel=new JPanel();
		panel.setSize(300,200);
		panel.setLayout(new FlowLayout());
		panel.setVisible(true);
		JLabel label1=new JLabel("选择查询字段");
		JComboBox combobox=new JComboBox();
		combobox.addItem("请下拉选择");
		combobox.addItem("学号");
		combobox.addItem("姓名");
		combobox.addItem("班级编号");
		combobox.addItem("院系编号");
		combobox.addItem("籍贯");
		JTextField text1=new JTextField(20);

		combobox.addActionListener(new ActionListener() {
									   @Override
			public void actionPerformed(ActionEvent e){
			String item = (String) combobox.getSelectedItem();
			switch(item){
				case "学号":Conn_db.flag=1;break;
				case "姓名":Conn_db.flag=2;break;
				case "班级编号":Conn_db.flag=3;break;
				case "院系编号":Conn_db.flag=4;break;
				case "籍贯":Conn_db.flag=5;break;
			}
			}
		});

		JButton b1=new JButton("查询");
		panel.add(label1);
		panel.add(combobox);
		panel.add(text1);
		panel.add(b1);
		frame2.add(panel,BorderLayout.NORTH);


		String[] cname=new String[]{"学号","姓名","性别","班级编号","班级名称","院系编号","院系名称","出生日期","籍贯"};
		DefaultTableModel model1=new DefaultTableModel(cname,1);
		JTable table=new JTable(model1);
		JScrollPane jsp2=new JScrollPane(table){
			public Dimension getPreferredSize(){
				return new Dimension(1500,500);
			}
		};
		frame2.add(jsp2);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sql="select * from stu_infos inner join Class_infos on stu_infos.Class=Class_infos.Class inner join Depart on stu_infos.SC=Depart.SC";
				try {
					ResultSet rs = Conn_db.stat.executeQuery(sql);
					String target=text1.getText();
					int num=0;
					while(rs.next()){
						String id0= rs.getString("Id");
						String name0 = rs.getString("Name");
						String sex0 = rs.getString("Sex");
						String cn0 = String.valueOf(rs.getInt("stu_infos.Class"));
						String cname0=rs.getString("Class_infos.Class_name");
						String sc0=rs.getString("stu_infos.SC");
						String scn=rs.getString("Depart.Depart_name");

						String birth0=String.valueOf(rs.getDate("Birthday"));
						String address0=rs.getString("Address");
						Object[] str = new Object[]{id0, name0, sex0, cn0, cname0,sc0,scn,birth0,address0};
						if(Conn_db.flag==1&&id0.equals(target))
						{ model1.insertRow(num++, str);}
						else if(Conn_db.flag==2&&name0.equals(target))
						{ model1.insertRow(num++, str);}
						else if(Conn_db.flag==3&&cn0.equals(target))
						{ model1.insertRow(num++, str);}
						else if(Conn_db.flag==4&&sc0.equals(target))
						{ model1.insertRow(num++, str);}
						else if(Conn_db.flag==5&&address0.equals(target))
						{ model1.insertRow(num++, str);}

					}
					JButton b2=new JButton("结束浏览");
					frame2.add(b2,BorderLayout.SOUTH);
					b2.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							frame2.setVisible(false);
						}
					});

				}catch (Exception e2){
					e2.printStackTrace();
				}
			}
		});


	}
}
//学生个人信息的输入操作,添加基本信息不包括流动情况和奖惩情况
class Add_info implements ActionListener {
	public void actionPerformed(ActionEvent e){
		JFrame frame3=new JFrame("添加个人信息");
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
		//设置一个查询框架
		String[] cname=new String[]{"学号","姓名","性别","班级编号","班级名称","院系编号","院系名称","出生日期","籍贯"};
		DefaultTableModel model1=new DefaultTableModel(cname,60);
		JTable table=new JTable(model1);
		JScrollPane jsp2=new JScrollPane(table){
			public Dimension getPreferredSize(){
				return new Dimension(1200,400);
			}
		};
		jsp2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame3.add(jsp2);
		jsp2.setBounds(30,70,1200,350);
		//获取新成员的数据
		JLabel label1=new JLabel("学号"); label1.setFont(font);
		JLabel label2=new JLabel("姓名");label2.setFont(font);
		JLabel label3=new JLabel("性别");label3.setFont(font);
		JLabel label4=new JLabel("班级编号");label4.setFont(font);
		JLabel label5=new JLabel("院系编号");label5.setFont(font);
		JLabel label6=new JLabel("出生日期");label6.setFont(font);
		JLabel label7=new JLabel("籍贯");label7.setFont(font);
		JTextField text1=new JTextField(20);text1.setFont(font);//学号
		JTextField text2=new JTextField(20);text2.setFont(font);//姓名
		JComboBox jb1=new JComboBox();//性别
		jb1.addItem("请选择性别");
		jb1.addItem("男");
		jb1.addItem("女");

		JTextField text4=new JTextField(20);text4.setFont(font);//班级编号
		JTextField text5=new JTextField(20);text5.setFont(font);//院系编号
		JTextField text6=new JTextField(20);text6.setFont(font);//出生日期
		JTextField text7=new JTextField(20);text7.setFont(font);//籍贯

		frame3.add(label1);
		frame3.add(text1);
		frame3.add(label2);
		frame3.add(text2);
		frame3.add(label3);
		frame3.add(jb1);
		frame3.add(label4);
		frame3.add(text4);
		frame3.add(label5);
		frame3.add(text5);
		frame3.add(label6);
		frame3.add(text6);
		frame3.add(label7);
		frame3.add(text7);
		label1.setBounds(120,450,50,30);
		text1.setBounds(180,450,150,30);
		label2.setBounds(350,450,50,30);
		text2.setBounds(410,450,150,30);
		label3.setBounds(580,450,50,30);
		jb1.setBounds(630,450,100,30);
		label4.setBounds(750,450,80,30);
		text4.setBounds(840,450,150,30);

		label5.setBounds(120,530,80,30);
		text5.setBounds(210,530,150,30);
		label6.setBounds(370,530,80,30);
		text6.setBounds(460,530,200,30);
		label7.setBounds(670,530,50,30);
		text7.setBounds(730,530,200,30);

		JButton b3=new JButton("确认添加");
		b3.setBounds(950,530,200,30);
		frame3.add(b3);
		//添加信息

		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id=text1.getText();
				String name=text2.getText();
				String sex=(String)jb1.getSelectedItem();
				String class_=text4.getText();
				String sc=text5.getText();
				String birthday=text6.getText();
				String add=text7.getText();
				String sql="insert into stu_infos values('"+id+"','"+name+"','"+sex+"','"+class_+"','"+sc+"','"+birthday+"','"+add+"')";
				try {
					int g=0;
						String id2=text1.getText();
						ResultSet se = Conn_db.stat.executeQuery("select count(*) from stu_infos where Id=" + id2);
						while (se.next()) {
							int n1 = se.getInt("count(*)");
							if (n1 > 0) {
								g=1;
								JOptionPane.showMessageDialog(null, "该学生信息已存在！");
							}
						}
					if(g==0) {
						Conn_db.stat.execute(sql);
						JOptionPane.showMessageDialog(null, "添加成功！");
					}
				}catch (Exception e3){
					e3.printStackTrace();
				}
			}
		});
		//查询个人信息
		but1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sql = "select * from stu_infos inner join Class_infos on stu_infos.Class=Class_infos.Class inner join Depart on stu_infos.SC=Depart.SC";
				try {
					ResultSet rs = Conn_db.stat.executeQuery(sql);
					String target = t1.getText();
					int num = 0;
					int g2=0;
					while (rs.next()) {

						String id0 = rs.getString("Id");
						if (target.equals(id0)) {
							g2=1;
							String name0 = rs.getString("Name");
							String sex0 = rs.getString("Sex");
							String cn0 = String.valueOf(rs.getInt("stu_infos.Class"));
							String cname0 = rs.getString("Class_infos.Class_name");
							String sc0 = rs.getString("stu_infos.SC");
							String scn = rs.getString("Depart.Depart_name");
							String birth0 = String.valueOf(rs.getDate("Birthday"));
							String address0 = rs.getString("Address");
							Object[] str = new Object[]{id0, name0, sex0, cn0, cname0, sc0, scn, birth0, address0};
							model1.insertRow(num++, str);
						}

					}
					if(g2==0){
						JOptionPane.showMessageDialog(null,"该学生信息不存在！");
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});


		//查询所有信息
		but2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sql="select * from stu_infos inner join Class_infos on stu_infos.Class=Class_infos.Class inner join Depart on stu_infos.SC=Depart.SC";
				try {
					ResultSet rs = Conn_db.stat.executeQuery(sql);
					int num=0;
					while(rs.next()){
						String id=rs.getString("Id");
						String name=rs.getString("Name");
						String sex=rs.getString("Sex");
						String class_no=rs.getString("stu_infos.Class");
						String class_name=rs.getString("Class_infos.Class_name");
						String sc=rs.getString("stu_infos.SC");
						String sc_name=rs.getString("Depart.Depart_name");
						String birth=String.valueOf(rs.getDate("Birthday"));
						String address=rs.getString("Address");
						Object[] str=new Object[]{id,name,sex,class_no,class_name,sc,sc_name,birth,address};
						model1.insertRow(num++,str);
					}

				}catch (Exception e1){
					e1.printStackTrace();
				}
			}
		});

		}
}
//修改基本信息
class Alter_info implements ActionListener {
	public void actionPerformed(ActionEvent e){
		JFrame frame4=new JFrame("修改个人信息");
		frame4.setSize(1300,500);
		frame4.setLocationRelativeTo(null);
		frame4.setLayout(null);
		frame4.setVisible(true);
		frame4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel label=new JLabel("需修改学生的学号");
		JTextField text=new JTextField(11);
		JButton b=new JButton("查询");
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(label);
		panel.add(text);
		panel.add(b);
		frame4.add(panel);
		panel.setBounds(300,10,800,50);
		//做一个查询框架
		String[] cname=new String[]{"学号","姓名","性别","班级编号","院系编号","出生日期","籍贯"};
		DefaultTableModel model=new DefaultTableModel(cname,1);
		JTable table =new JTable(model);
		JScrollPane jsp4=new JScrollPane(table){
			public Dimension getPreferredSize(){
				return new Dimension(1500,500);
			}
		};
		frame4.add(jsp4);
		jsp4.setBounds(150,70,1000,250);
		JButton b3=new JButton("进行修改");
		frame4.add(b3);
		b3.setBounds(600,380,200,50);
		//b是查询按钮
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sql="select * from stu_infos";
				try {
					ResultSet rs = Conn_db.stat.executeQuery(sql);
					String target_id=text.getText();
					while(rs.next()){
						String id=rs.getString("Id");
						if(id.equals(target_id)){
							String name=rs.getString("Name");
							String sex=rs.getString("Sex");
							String cno=rs.getString("Class");
							String sc=rs.getString("SC");
							String bir=String.valueOf(rs.getDate("Birthday"));
							String add=rs.getString("Address");
							Object[] str=new Object[]{id,name,sex,cno,sc,bir,add};
							model.insertRow(0,str);
							break;
						}
					}


				}catch (Exception e2){
					e2.printStackTrace();
				}
			}
		});
		//b3是“进行修改”按钮
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame jf=new JFrame("输入修改信息");
				jf.setSize(700,600);
				jf.setLocationRelativeTo(null);
				jf.setLayout(null);
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JButton bt=new JButton("确认修改");
				Font font=new Font("宋体", Font.BOLD,20);

				JLabel label1=new JLabel("学号"); label1.setFont(font);
				JLabel label2=new JLabel("姓名");label2.setFont(font);
				JLabel label3=new JLabel("性别");label3.setFont(font);
				JLabel label4=new JLabel("班级编号");label4.setFont(font);
				JLabel label5=new JLabel("院系编号");label5.setFont(font);
				JLabel label6=new JLabel("出生日期");label6.setFont(font);
				JLabel label7=new JLabel("籍贯");label7.setFont(font);
				JTextField text1=new JTextField(20);text1.setFont(font);
				JTextField text2=new JTextField(20);text2.setFont(font);
				JTextField text3=new JTextField(20);text3.setFont(font);
				JTextField text4=new JTextField(20);text4.setFont(font);
				JTextField text5=new JTextField(20);text5.setFont(font);
				JTextField text6=new JTextField(20);text6.setFont(font);
				JTextField text7=new JTextField(20);text7.setFont(font);

				jf.add(label1);
				jf.add(text1);
				jf.add(label2);
				jf.add(text2);
				jf.add(label3);
				jf.add(text3);
				jf.add(label4);
				jf.add(text4);
				jf.add(label5);
				jf.add(text5);
				jf.add(label6);
				jf.add(text6);
				jf.add(label7);
				jf.add(text7);

				label1.setBounds(200,50,200,50);
				text1.setBounds(300,50,200,50);
				label2.setBounds(200,110,200,50);
				text2.setBounds(300,110,200,50);
				label3.setBounds(200,170,200,50);
				text3.setBounds(300,170,200,50);
				label4.setBounds(200,230,200,50);
				text4.setBounds(300,230,200,50);
				label5.setBounds(200,290,200,50);
				text5.setBounds(300,290,200,50);
				label6.setBounds(200,350,200,50);
				text6.setBounds(300,350,200,50);
				label7.setBounds(200,410,200,50);
				text7.setBounds(300,410,200,50);
				//bt是确认修改按钮
				jf.add(bt);
				bt.setBounds(285,500,150,50);
				jf.setVisible(true);
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String id2=text1.getText();
						String name2=text2.getText();
						String sex2=text3.getText();
						String cno2=text4.getText();
						String sc2=text5.getText();
						String bir2=text6.getText();
						String add2=text7.getText();
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-DD");
						Date temp=new Date();
						String birth2=null;
						try {
							 temp=sdf.parse(bir2);
							 birth2=sdf.format(temp);
						} catch (ParseException parseException) {
							parseException.printStackTrace();
						}
						String sql1="delete from stu_infos where Id='"+id2+"'";//使用sql语句删除原来信息
						//使用sql语句插入信息
						try{
							Conn_db.stat.execute(sql1);
							Conn_db.stat.execute("insert into stu_infos values('"+id2+"','"+name2+"','"+sex2+"','"+cno2+"','"+sc2+"','"+birth2+"','"+add2+"')");
							JOptionPane.showMessageDialog(null, "修改信息成功！");

						}catch (Exception e4){
							e4.printStackTrace();
						}
						jf.setVisible(false);
					}
				});

			}
		});

	}
}
class Delete_one implements ActionListener {
	public void actionPerformed(ActionEvent e){
		JFrame frame5=new JFrame("删除个别学生信息");
		frame5.setSize(400,250);
		frame5.setLocationRelativeTo(null);
		frame5.setLayout(null);
		frame5.setVisible(true);
		frame5.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel label=new JLabel("需删除的学生学号");
		JTextField text=new JTextField(11);
		panel.add(label);
		panel.add(text);
		frame5.add(panel);
		panel.setBounds(25,50,400,50);
		JButton b5=new JButton("确认");
		frame5.add(b5);
		b5.setBounds(150,130,80,30);
		b5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String target_id=text.getText();
				if(!target_id.equals("")) {
					String sql = "delete from stu_infos where Id='" + target_id + "'";
					try {
						//查询表中是否存在该学生
						ResultSet rs=Conn_db.stat.executeQuery("select * from stu_infos");
						boolean flag=false;
						while(rs.next()){
							String id=rs.getString("Id");
							if(target_id.equals(id)){
								Conn_db.stat.execute(sql);
								JOptionPane.showMessageDialog(null, "删除成功！");
								frame5.setVisible(false);
								flag=true;
								break;
							}
						}
						if(!flag){
							JOptionPane.showMessageDialog(null,"抱歉，系统中没有该学生的信息！");
						}

					} catch (Exception e5) {
						e5.printStackTrace();
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"Error:您还没有输入要删除的学生信息的学号！");
				}
			}
		});
	}
}
class Delete_all implements ActionListener {
	public void actionPerformed(ActionEvent e){
		JFrame frame6=new JFrame("删除全部数据");
		frame6.setSize(400,100);
		frame6.setLocationRelativeTo(null);
		frame6.setLayout(new FlowLayout());
		frame6.setVisible(true);
		frame6.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel label=new JLabel("确定删除全部数据吗？");
		JButton b1=new JButton("确定");
		JButton b2=new JButton("取消");
		frame6.add(label);
		frame6.add(b1);
		frame6.add(b2);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Conn_db.stat.execute("drop table stu_infos");
					JOptionPane.showMessageDialog(null,"删除成功！");
					frame6.setVisible(false);
					Conn_db.stat.execute("create table stu_infos(Id varchar(11) primary key,Name varchar(45),Sex varchar(10),Class varchar(11),SC varchar(11),Birthday Date,Address varchar(100))");
				}catch (Exception e6){
					e6.printStackTrace();
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

class login_entrance{

	public login_entrance(){
		JFrame f = new JFrame("学生信息管理系统");
		f.setSize(1700,800);
		//f.setVisible(true);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Font font=new Font("宋体", Font.BOLD,25);

		ImageIcon background=new ImageIcon("C:\\Users\\黄婕\\Pictures\\网络图片\\timg.jpg");
		JLabel label=new JLabel(background);
		label.setBounds(0,0,background.getIconWidth(),background.getIconHeight());
		f.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));//LayeredPane为JFC/Swing容器添加了深度，允许组件的重叠
		JPanel p1 = (JPanel)f.getContentPane();//初始化一个内容面板，并转换为JPanel类型，这样才能使得这个面板可以设置透明
		p1.setOpaque(false);//将面板设置透明

		JPanel panel=new JPanel();
		panel.setLayout(null);
		panel.setOpaque(false);

		JLabel user=new JLabel("用户名：");
		user.setFont(font);
		user.setHorizontalAlignment(JTextField.CENTER);//设置文本框里的内容为居中

		JTextField t1=new JTextField(25);
		t1.setFont(font);

		JLabel password=new JLabel("密码：");
		password.setFont(font);
		password.setHorizontalAlignment(JTextField.CENTER);//设置文本框里的内容为居中

		panel.add(password);

		JPasswordField t2=new JPasswordField(25);
		t2.setFont(font);

		panel.add(t2);
		f.add(panel);
		f.add(t1);
		f.add(user);
		f.add(t2);
		f.add(password);
		JButton b1=new JButton("登陆");
		JButton b2=new JButton("取消");
		f.add(b1);
		f.add(b2);

		user.setBounds(450,200,200,50);
		password.setBounds(450,300,200,50);
		t1.setBounds(650,200,200,50);
		t2.setBounds(650,300,200,50);
		b1.setBounds(450,400,200,50);
		b2.setBounds(750,400,200,50);
		f.setVisible(true);

		//设置监听器
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name=t1.getText();
				String paword=t2.getText();
				if(name.equals("张三")&&paword.equals("123456")){
					new f("欢迎进入学生管理系统");
				}
				else{
					JOptionPane.showMessageDialog(null, "账号或者密码错误", "Error.mxy",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}

