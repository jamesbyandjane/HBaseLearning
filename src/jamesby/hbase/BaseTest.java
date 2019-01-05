package jamesby.hbase;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"}) 
public class BaseTest { 
 
    @Autowired 
    private HbaseTemplate template; 
 
    @Test
    public void testDelete() {
    	this.delete("dept", "1", "f1");
    }
    
    public void delete(String tableName,String rowKey,String familyName) {
    	template.delete(tableName, rowKey, familyName);
    }
    
    @Test 
    public void testFind() { 
    	System.out.println("----------------find--------------------");
    	List<HbaseRow> findResultList = this.find("dept", "1", "3");
    	for(HbaseRow row:findResultList) {
    		System.out.println("rowkey="+row.getRowKey());
    		for(HbaseQualifier item:row.getQualifierList()) {
    			
    			System.out.println("rowkey="+row.getRowKey()+" family="+item.getFamilyName());
    			System.out.println("rowkey="+row.getRowKey()+" qualifier="+item.getQualifier());
    			System.out.println("rowkey="+row.getRowKey()+" value="+Bytes.toString(item.getValue()));
    			
    			
    		}
    	}
    	System.out.println("----------------find--------------------");

    } 
    
    
    @Test
    public void testGet() throws UnsupportedEncodingException {
    	HbaseRow row = this.get("dept", "1","f1");
    	if (row!=null) {
    		for(HbaseQualifier item:row.getQualifierList()) {
    			
    			System.out.println(item.getFamilyName());
    			System.out.println(item.getQualifier());
    			System.out.println(Bytes.toString(item.getValue()));
    			
    			
    		}
    	}
    }
 

    
    @Test 
    public void testPut() { 
    	
    	HbaseRow row1 = new HbaseRow("1");
    	row1
    	.addQualifier(new HbaseQualifier("f1","name",Bytes.toBytes("张三")))
    	.addQualifier(new HbaseQualifier("f1","sex",Bytes.toBytes("男")))
    	.addQualifier(new HbaseQualifier("f1","age",Bytes.toBytes("26")))
    	.addQualifier(new HbaseQualifier("f1","adr",Bytes.toBytes("上海")))
    	.addQualifier(new HbaseQualifier("f1","mobile",Bytes.toBytes("13916929615")));
    	
    	HbaseRow row2 = new HbaseRow("2");
    	row2
    	.addQualifier(new HbaseQualifier("f1","name",Bytes.toBytes("李四")))
    	.addQualifier(new HbaseQualifier("f1","sex",Bytes.toBytes("男")))
    	.addQualifier(new HbaseQualifier("f1","age",Bytes.toBytes("26")))
    	.addQualifier(new HbaseQualifier("f1","adr",Bytes.toBytes("北京")))
    	.addQualifier(new HbaseQualifier("f1","mobile",Bytes.toBytes("13916929616")));    	
    	
    	HbaseRow row3 = new HbaseRow("3");
    	row3
    	.addQualifier(new HbaseQualifier("f1","name",Bytes.toBytes("王五")))
    	.addQualifier(new HbaseQualifier("f1","sex",Bytes.toBytes("男")))
    	.addQualifier(new HbaseQualifier("f1","age",Bytes.toBytes("26")))
    	.addQualifier(new HbaseQualifier("f1","adr",Bytes.toBytes("天津")))
    	.addQualifier(new HbaseQualifier("f1","mobile",Bytes.toBytes("13916929617")));     	
    	
    	HbaseRow row4 = new HbaseRow("4");
    	row4
    	.addQualifier(new HbaseQualifier("f1","name",Bytes.toBytes("赵六")))
    	.addQualifier(new HbaseQualifier("f1","sex",Bytes.toBytes("男")))
    	.addQualifier(new HbaseQualifier("f1","age",Bytes.toBytes("26")))
    	.addQualifier(new HbaseQualifier("f1","adr",Bytes.toBytes("天津")))
    	.addQualifier(new HbaseQualifier("f1","mobile",Bytes.toBytes("13916929618")));      
    	
    	List<HbaseRow> rows = new ArrayList<HbaseRow>();
    	rows.add(row1);
    	rows.add(row2);
    	rows.add(row3);
    	rows.add(row4);
    	
    	this.put("dept",rows);
    } 
    
    
    /**
     * batch save
     * @param tableName
     * @param rows
     */
    public void put(String tableName,List<HbaseRow> rows) {
    	for(HbaseRow row:rows) {
    		for(HbaseQualifier qualifier:row.getQualifierList()) {
    			template.put(tableName, row.getRowKey(), qualifier.getFamilyName(), qualifier.getQualifier(), qualifier.getValue());
    		}
    	}
    }
    
    /**
     * save one
     * @param tableName
     * @param row
     */
    public void put(String tableName,HbaseRow row) {
    	this.put(tableName, Arrays.asList(row));
    }
    
    
    /**
     * get one row
     * @param tableName
     * @param rowKey
     * @return
     */
    public HbaseRow get(String tableName,String rowKey) {
    	return template.get(tableName, rowKey, new RowMapper<HbaseRow>() {

			@Override
			public HbaseRow mapRow(Result result, int rowNum) throws Exception {
                List<Cell> cellList = result.listCells();
                if (cellList==null || cellList.isEmpty())
                	return null;
                
                HbaseRow row = new HbaseRow(rowKey);
                
                
                for(Cell cell:cellList){
                	
                	HbaseQualifier qualifier = new HbaseQualifier(Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength()),
                			Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength()),CellUtil.cloneValue(cell));
                	
                	row.addQualifier(qualifier);
                	
               
                }
                return row;
			}
    	});
    }
    
    /**
     * get one family
     * @param tableName
     * @param rowKey
     * @return
     */
    public HbaseRow get(String tableName,String rowKey,String familyName) {
    	return template.get(tableName, rowKey,familyName, new RowMapper<HbaseRow>() {

			@Override
			public HbaseRow mapRow(Result result, int rowNum) throws Exception {
                List<Cell> cellList = result.listCells();
                if (cellList==null || cellList.isEmpty())
                	return null;
                
                HbaseRow row = new HbaseRow(rowKey);
                
                
                for(Cell cell:cellList){
                	
                	HbaseQualifier qualifier = new HbaseQualifier(Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength()),
                			Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength()),CellUtil.cloneValue(cell));
                	
                	row.addQualifier(qualifier);
                	
               
                }
                return row;
			}
    	});
    }    
    
    /**
     * get one column
     * @param tableName
     * @param rowKey
     * @param familyName
     * @param qualifierArray
     * @return
     */
    public byte[] get(String tableName,String rowKey,String familyName,String qualifier) {
    	return template.get(tableName, rowKey,familyName,qualifier ,new RowMapper<byte[]>(){
            @Override
            public byte[] mapRow(Result result, int i) throws Exception {
                List<Cell> ceList = result.listCells();
               
                if(ceList!=null&&ceList.size()>0){
                    for(Cell cell:ceList){
                        return CellUtil.cloneValue(cell);
                    }
                }
                return null;
            }
        });
    }
     
    /**
     * Find with startRow and endRow
     * @param tableName
     * @param startRowKey
     * @param endRowKey
     * @return
     */
    public List<HbaseRow> find(String tableName,String startRowKey,String endRowKey) {
    	Scan scan=new Scan();
    	scan.setStartRow(Bytes.toBytes(startRowKey));
    	scan.setStopRow(Bytes.toBytes(endRowKey));
    	scan.setMaxResultSize(1000);

//    	PageFilter filter = new PageFilter(1);
//      scan.setFilter(filter);    	
    	
    	return template.find(tableName, scan, new RowMapper<HbaseRow>() {

			@Override
			public HbaseRow mapRow(Result result, int rowNum) throws Exception {
				List<Cell> cellList = result.listCells();
				if (cellList==null || cellList.isEmpty())
					return null;

				HbaseRow row = new HbaseRow();

                for(Cell cell:cellList){
                	if (null==row.getRowKey()) {
                		row.setRowKey(Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength()));
                	}
                	
                	//String value =Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    String family =  Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength());
                    String quali = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                    
                    row.addQualifier(new HbaseQualifier(family,quali,CellUtil.cloneValue(cell)));
                }
                return row;
			}
    	});
    }
    
    
    public static void main(String[] args) {
    	
    }
} 