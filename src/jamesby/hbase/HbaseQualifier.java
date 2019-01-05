package jamesby.hbase;

public class HbaseQualifier implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String familyName;
	private String qualifier;
	private byte[] value;
	
	public HbaseQualifier(String familyName,String qualifier,byte[] value) {
		this.familyName = familyName;
		this.qualifier = qualifier;
		this.value = value;
	}
	
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getQualifier() {
		return qualifier;
	}
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}
	public byte[] getValue() {
		return value;
	}
	public void setValue(byte[] value) {
		this.value = value;
	}
	
}
