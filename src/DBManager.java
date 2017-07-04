import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class DBManager {
	static final String driverName = "org.gjt.mm.mysql.Driver";
	static final String dbURL = "jdbc:mysql://localhost:3306/youtube";
	
	String sql;
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	java.sql.PreparedStatement pstmt;
	int n=0;
	int duplicate = 0;
	//�����ڷ� DB����
	public DBManager(){
		try{
			con = DriverManager.getConnection(dbURL,"root","websys");
			stmt = con.createStatement();
		} catch (SQLException sqex) {
			sqex.printStackTrace();
		}
	}//DBManager() constructor

	//���� �����ϴ� �޼ҵ�
	public void runSQL(Article art){
		//SQL ����
		try {
			//preparedStatement�� ��ȣ����� ����ֱ� (setString() ��)
			pstmt = con.prepareStatement("insert into youtube_video (URL, Title, Content, Like, Dislike, Subscribe, Comment, Total, Date, Channel_URL, Channel) values (?,?,?,?,?,?,?,?,?,?,?);");
//			pstmt = con.prepareStatement("CALL insertArticle(?, ?, ?, ?, ?, ?)");
			/** SQL ����
			 * 1. URL
			 * 2. Title
			 * 3. Content
			 * 4. Like
			 * 5. Dislike
			 * 6. Subscribe
			 * 7. Comment
			 * 8. Total
			 * 9. Date
			 * 10. Channel_URL
			 * 11. Channel
			 */
			
			pstmt.setString(1, art.getUrl());
			pstmt.setString(2, art.getTitle());
			pstmt.setString(3, art.getContent());
			pstmt.setInt(4, art.getLike());
			pstmt.setInt(5, art.getDislike());
			pstmt.setInt(6, art.getSubscribe());
			pstmt.setInt(7, art.getComment());
			pstmt.setInt(8, art.getTotal());
			pstmt.setString(9, art.getDate());
			pstmt.setString(10, art.getChannel_url());
			pstmt.setString(11, art.getChannel());
			n++;
			
			if(pstmt.execute()){ //sql ���⼭�� �����... �����ϳ�!
				rs = pstmt.getResultSet(); //��� (NullPointerException)
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //runSQL
	
	public void closeDB(){
		if(rs!=null) {try{ rs.close(); } catch (Exception e){ e.printStackTrace(); }}
		if(stmt!=null) {try{ stmt.close(); } catch (Exception e){ e.printStackTrace(); }}
		if(con!=null) {try{ con.close(); } catch (Exception e){ e.printStackTrace(); }}
	}
}//class