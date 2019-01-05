package jamesby.hbase;

import java.util.ArrayList;
import java.util.List;

public class HbaseRow implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String rowKey;
	private List<HbaseQualifier> qualifierList=new ArrayList<HbaseQualifier>();
	
	public HbaseRow() {
		
	}
	
	public HbaseRow(String rowKey) {
		this.rowKey = rowKey;
	}
	
	public HbaseRow addQualifier(HbaseQualifier qualifier) {
		qualifierList.add(qualifier);
		return this;
	}
	
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public List<HbaseQualifier> getQualifierList() {
		return qualifierList;
	}
	public void setQualifierList(List<HbaseQualifier> qualifierList) {
		this.qualifierList = qualifierList;
	}
}
