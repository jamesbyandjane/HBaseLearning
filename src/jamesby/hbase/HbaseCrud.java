package jamesby.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
//import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
//import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
//import org.apache.hadoop.hbase.client.TableDescriptor;
//import org.apache.hadoop.hbase.client.TableDescriptorBuilder;

public class HbaseCrud {
	public void listTable() throws Exception{
		Admin admin = null;
		try {
			admin = HBaseConnection.getAdmin();
			
			TableName[] allTable =admin.listTableNames();	
            for (TableName tableName : allTable) {
                System.out.println(tableName.getNameAsString());
            }
            
		}finally {
			admin.close();
		}
	}
	
	public void createTable() throws Exception {
		Admin admin = null;
		try {
			admin = HBaseConnection.getAdmin();
			 // 创建表描述类
			 TableName tableName = TableName.valueOf("dept"); // 表名称
			 
			 
//			 ColumnFamilyDescriptor f1 = ColumnFamilyDescriptorBuilder.newBuilder("f1".getBytes()).build();
//			 ColumnFamilyDescriptor f2 = ColumnFamilyDescriptorBuilder.newBuilder("f2".getBytes()).build();
//			 List<ColumnFamilyDescriptor> list = new ArrayList<ColumnFamilyDescriptor>();
//			 list.add(f1);
//			 list.add(f2);
//			 
//			 TableDescriptor desc =TableDescriptorBuilder.newBuilder(tableName).setColumnFamilies(list).build();

//			 admin.createTable(desc); // 创建表
			 System.out.println("创建表成功！");			
		}finally {
			admin.close();
		}
	}
	public void update() throws Exception {
		Admin admin = null;
		try {
			admin = HBaseConnection.getAdmin();
			
            
		}finally {
			admin.close();
		}
	}
	public void insert() throws Exception {
		Admin admin = null;
		try {
			admin = HBaseConnection.getAdmin();
			
            
		}finally {
			admin.close();
		}
	}
	public void delete() throws Exception {
		Admin admin = null;
		try {
			admin = HBaseConnection.getAdmin();
			
            
		}finally {
			admin.close();
		}
	}
	
	public void query() throws Exception {
		Admin admin = null;
		try {
			admin = HBaseConnection.getAdmin();
			
            
		}finally {
			admin.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new HbaseCrud().listTable();
	}
}
