package Model;

public class HLV {
    private int idHlv;
    private String nameHlv;

    private int idClb;

    public HLV() {

    }

    public HLV(int idHlv, String nameHlv, int idClb) {
        this.idHlv = idHlv;
        this.nameHlv = nameHlv;
        this.idClb = idClb;
    }

    public int getIdHlv() {
        return idHlv;
    }

    public void setIdHlv(int idHlv) {
        this.idHlv = idHlv;
    }

    public String getNameHlv() {
        return nameHlv;
    }

    public void setNameHlv(String nameHlv) {
        this.nameHlv = nameHlv;
    }

    public int getIdClb() {
        return idClb;
    }

    public void setIdClb(int idClb) {
        this.idClb = idClb;
    }
}
