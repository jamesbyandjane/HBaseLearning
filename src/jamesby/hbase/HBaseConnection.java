package jamesby.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class HBaseConnection {
	
	private static Connection _connection;
	
	private static Configuration getConfig() {
		Configuration configuration = HBaseConfiguration.create();
		//与 hbase-site-xml里面的配置信息 zookeeper.znode.parent 一致
		configuration.set("zookeeper.znode.parent","/hbase");   
		//hbase的zk服务地址
        configuration.set("hbase.zookeeper.quorum","47.105.54.170");  
        configuration.set("hbase.zookeeper.property.clientPort","2181"); 
        return configuration;
	}
	
	private synchronized static Connection getConnection() throws Exception {
		if (_connection!=null) {
			return _connection;
		}
		
		_connection = ConnectionFactory.createConnection(getConfig());
		return _connection;
	}
	
	public static Admin getAdmin() throws Exception{
		return getConnection().getAdmin();
	}
	
	public static void close(Admin admin) {
		try {
			admin.close();
		}catch(Exception e) {
			
		}
	}
	
	public static void main(String[] args) throws Exception{
		Admin admin = null;
		try {
			admin = getAdmin();
			
			TableName[] allTable =admin.listTableNames();	
            for (TableName tableName : allTable) {
                System.out.println(tableName.getNameAsString());
            }
            
		}finally {
			admin.close();
		}
	}
}
