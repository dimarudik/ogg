package sample.model;

public class BrokenTX {

    private Integer operationSeq;
    private String csn;

    public Integer getOperationSeq() {
        return operationSeq;
    }

    public void setOperationSeq(Integer operationSeq) {
        this.operationSeq = operationSeq;
    }

    public String getCsn() {
        return csn;
    }

    public void setCsn(String csn) {
        this.csn = csn;
    }

    public boolean isValid() {
        return operationSeq != null && !csn.isEmpty();
    }

}
