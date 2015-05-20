package spbstu.iitu.kit.diplom.tomita.dto;

/**
 * @author Daria Nikiforova
 */
public class Fact {
    private int id;
    private int leadId;
    private int position;
    private String value;

    public Fact(int id, int leadId, int position, String value) {
        this.id = id;
        this.leadId = leadId;
        this.position = position;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public int getLeadId() {
        return leadId;
    }

    public int getPosition() {
        return position;
    }

    public String getValue() {
        return value;
    }
}
