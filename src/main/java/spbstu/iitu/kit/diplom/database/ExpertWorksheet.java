package spbstu.iitu.kit.diplom.database;

import javax.persistence.*;

/**
 * Expert worksheet entity.
 * @author Daria Nikiforova
 */
@Entity
@Table
@NamedQuery(name="ExpertWorksheet.getAnswerByQuestionId",
        query="SELECT a FROM ExpertWorksheet a WHERE a.questionId = :questionId")
public class ExpertWorksheet  {
    /**
     * Worksheet id.
     */
    private int id;

    /**
     * Question id code.
     */
    private String questionId;
    /**
     * Normalized pattern.
     */
    private String answer;

    public ExpertWorksheet() {}
    /**
     * Constructor.
     * @param questionId workshhet id.
     * @param pattern normalized answer.
     */
    public ExpertWorksheet(String questionId, String pattern) {
        this.questionId = questionId;
        this.answer = pattern;
    }
    /**
     * Get worksheet id.
     * @return worksheet id.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    /**
     * Set worksheet id.
     * @param id worksheet id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get pattern.
     * @return <code>String</code> pattern.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Set pattern.
     * @param answer <code>String</code> pattern.
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
