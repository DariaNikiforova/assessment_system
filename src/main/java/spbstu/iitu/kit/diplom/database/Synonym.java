package spbstu.iitu.kit.diplom.database;

import javax.persistence.*;

/**
 * Synonym entity.
 * @author Daria Nikiforova
 */
@Entity
@Table
@NamedQuery(name="Synonym.getSynonymList",
        query="SELECT s.synonym FROM Synonym s WHERE s.source = :source")
public class Synonym {
    /**
     * Worksheet id.
     */
    private int id;

    private String source;

    private String synonym;

    public Synonym(String source, String synonym) {
        this.source = source;
        this.synonym = synonym;
    }

    public Synonym() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }
}
