package tool;
import java.sql.*;
import java.util.*;
import java.util.Date;

import bean.*;

public class JdbcConn {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/ddf?characterEncoding=utf-8";
	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "";
	   Connection conn=null;
	   Statement stmt=null;
	   public JdbcConn(){
		   try{
		   Class.forName(JDBC_DRIVER);
		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		   }catch(Exception e){
			   e.printStackTrace();
		   }
	   }
	   
	   public boolean add(String id,String name,String password){
		   String sql="insert into users(id,name,password) values('"+id+"','"+name+"','"+password+"')";
		   try {
			int i=stmt.executeUpdate(sql);
			System.out.println(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return true;
	   }
	   public boolean delete(){
		   return true;
	   }
	   public String find(){
		   return "";
	   }
	   
	   public ArrayList<User> list(){
		    ArrayList<User> users =new ArrayList<User>();  
		   String sql = "SELECT * FROM users";
		   try{
		      ResultSet rs = stmt.executeQuery(sql);
		      //STEP 5: Extract data from result set
		      while(rs.next()){
		    	  User u= new User(rs.getString("id"),
						  rs.getString("name"),
						  rs.getString("password"),
						  rs.getString("phone"),
						  rs.getString("qq"),
						  rs.getString("mail"),
						  rs.getInt("position"));
		    	  users.add(u);
		      }
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		   return users;
	   }
	public ArrayList<Admin> adminlist(){
		ArrayList<Admin> users =new ArrayList<Admin>();
		String sql = "SELECT * FROM admin";
		try{
			ResultSet rs = stmt.executeQuery(sql);
			//STEP 5: Extract data from result set
			while(rs.next()){
				Admin u= new Admin(rs.getString("id"),
						rs.getString("name"),
						rs.getString("password"));
				users.add(u);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return users;
	}
	public ArrayList<HomeWork> homeworkList(){
		ArrayList<HomeWork> work =new ArrayList<HomeWork>();
		String sql = "SELECT * FROM homework";
		try{
			ResultSet rs = stmt.executeQuery(sql);
			//STEP 5: Extract data from result set
			while(rs.next()){
				HomeWork h= new HomeWork(rs.getString("id"), rs.getString("subject"),rs.getString("date") );
				work.add(h);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return work;
	}


	public ArrayList<Grade> gradeList(){
		ArrayList<Grade> grades =new ArrayList<Grade>();
		String sql = "SELECT * FROM upgrade";
		try{
			ResultSet rs = stmt.executeQuery(sql);
			//STEP 5: Extract data from result set
			while(rs.next()){
				Grade g= new Grade(rs.getString("id"), rs.getString("subject") );
				grades.add(g);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return grades;
	}

	public boolean addpgrade(User u,String subject, int grade) throws SQLException {

		String sql = "UPDATE `grade` SET `"+subject+"`="+grade+" WHERE id="+u.getId();
		System.out.println(sql);
		int i=stmt.executeUpdate(sql);
		System.out.println("成功向user表中更新" + i + "条记录");
		return true;
	}
	public ArrayList<PersonGrade> getPensonGrade(String id){

		ArrayList<Grade> grade = gradeList();
		int i=0;
		ArrayList<PersonGrade> pg = new ArrayList<PersonGrade>();
		String sql = "SELECT  ";
		while (i<grade.size()){
			sql=sql+"`"+grade.get(i).getId()+"`";
			if(i!=grade.size()-1){
				sql=sql+",";
			}
			i++;
		}
		sql=sql+" FROM `grade` WHERE id="+id;
		//System.out.println(sql);
		try{
			ResultSet rs = stmt.executeQuery(sql);
			//STEP 5: Extract data from result set
			i=0;
			while(rs.next()){
				while (i<grade.size()){
					//System.out.println(rs.getString(grade.get(i).getId()));
					PersonGrade g = new PersonGrade(grade.get(i).getId(), grade.get(i).getSubject(), rs.getString(grade.get(i).getId()));
					pg.add(g);
					i++;
				}
			}
			//System.out.println(pg.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		return pg;
	}

	public boolean changeinfo(String id,String pass,String phone,String qq,String mail) throws SQLException {

		String sql = "UPDATE `users` SET `password`="+pass+",`phone`="+phone+",`qq`="+qq+",`mail`=\""+mail+"\" WHERE id="+id;
		System.out.println(sql);
		int i=stmt.executeUpdate(sql);
		System.out.println("成功向user表中更新" + i + "条记录");
		return true;
	}

	public ArrayList<Message> messagelist(){
		ArrayList<Message> mess =new ArrayList<Message>();
		String sql = "SELECT * FROM message";
		try{
			ResultSet rs = stmt.executeQuery(sql);
			//STEP 5: Extract data from result set
			while(rs.next()){
				Message m= new Message(rs.getInt("id"),
						rs.getString("title"),
						rs.getString("information"),
						rs.getString("url"),
						rs.getInt("level"));
				mess.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return mess;
	}
	public ArrayList<HomeWork> getPensonWork(String id){

		ArrayList<HomeWork> work = homeworkList();
		int i=0;
		ArrayList<HomeWork> no = new ArrayList<HomeWork>();
		String sql = "SELECT  ";
		while (i<work.size()){
			sql=sql+"`"+work.get(i).getId()+"`";
			if(i!=work.size()-1){
				sql=sql+",";
			}
			i++;
		}
		sql=sql+" FROM `uphomework` WHERE id="+id;
		//System.out.println(sql);
		try{
			ResultSet rs = stmt.executeQuery(sql);
			//STEP 5: Extract data from result set
			i=0;
			while(rs.next()){
				while (i<work.size()){
					if(rs.getString(work.get(i).getId()).equals("0")) {
						//System.out.println(rs.getString(grade.get(i).getId()));
						no.add(work.get(i));
					}
					i++;
				}
			}
			//System.out.println(pg.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		return no;
	}

	public boolean setwork(String id ,String work) throws SQLException {
		String sql = "UPDATE `uphomework` SET `"+work+"`=1 WHERE id="+id;
		System.out.println(sql);
		int i=stmt.executeUpdate(sql);
		System.out.println("成功向user表中更新" + i + "条记录");
		return true;

	}

	public String worksucnum(String id){
		int a = list().size();
		String sql = "SELECT id FROM uphomework WHERE `"+id+"`=1";
		System.out.println(sql);
		int i=0;
		try{

			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return i+"/"+a;
	}

	public boolean addmes(String title,String mes,String url,int level){
		String sql="insert into message(title,information,url,level) values('"+title+"','"+mes+"','"+url+"','"+level+"')";
		try {
			int i=stmt.executeUpdate(sql);
			System.out.println(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public ArrayList<String> nothomework(String id){
		String sql = "SELECT id FROM uphomework WHERE `"+id+"`=0";
		//System.out.println(sql);
		ArrayList<String> users = new ArrayList<String>();
		try{

			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				users.add(rs.getString("id"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return users;
	}
	public User getuserbyid(String id) throws SQLException {
		String sql = "SELECT * FROM users WHERE `id`="+id;
		User u=null;
		//System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			u = new User(rs.getString("id"),
					rs.getString("name"),
					rs.getString("password"),
					rs.getString("phone"),
					rs.getString("qq"),
					rs.getString("mail"),
					rs.getInt("position"));

		}
		return u;
	}

	public boolean addhomework(String subject , String date) throws SQLException {
		String sql="insert into homework(subject,date) values('"+subject+"','"+date+"')";
		try {
			int i=stmt.executeUpdate(sql);
			System.out.println(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getidbysub(subject);
		return true;

	}

	void getidbysub(String subject) throws SQLException {
		String sql = "SELECT id FROM homework WHERE `subject`=\""+subject+"\"";
		String s=null;
		System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) s=rs.getString("id");
		System.out.println(s);
		addtablemes("uphomework",s);
	}
	void addtablemes(String table,String mes){

		String sql = "alter table "+table+" add `"+mes+"` int(11) default 0";
		System.out.println(sql);
		int i= 0;
		try {
			i = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("成功向user表中更新" + i + "条记录");
	}
//	    public static void free()  
//	    {  
//	        
//	            try  
//	            {  
//	                if(stmt!=null)  
//	                    stmt.close();  
//	            } catch (SQLException e) {  
//	                // TODO Auto-generated catch block  
//	                e.printStackTrace();  
//	            }  
//	            finally  
//	            {  
//	                if(conn!=null)  
//	                    try {  
//	                        conn.close();  
//	                    } catch (SQLException e) {  
//	                        // TODO Auto-generated catch block  
//	                        e.printStackTrace();  
//	                    }  
//	            }  
//	        }  
	     
}
