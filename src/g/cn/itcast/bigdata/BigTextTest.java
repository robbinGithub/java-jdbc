package g.cn.itcast.bigdata;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import b.cn.itcast.utils.JdbcUtils;
/*
 *  jdbc 处理大数据

create table mytext(
   id int primary key auto_increment,
   textdata text
   
);

 */
public class BigTextTest {
	
	@Test
	public void testInsert() throws Exception{
		
		Connection conn = JdbcUtils.getConnection();
		
		String sql ="insert into mytext values(null,?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		File file = new File("src/jinpinlian.txt");
		Reader reader = new FileReader(file);
		
		stmt.setCharacterStream(1, reader,(int)file.length());
		
		int count = stmt.executeUpdate();
		System.out.println(count);
		JdbcUtils.release(null, stmt, conn);
	}
}
