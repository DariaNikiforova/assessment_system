package spbstu.iitu.kit.diplom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import spbstu.iitu.kit.diplom.database.ExpertWorksheet;
import spbstu.iitu.kit.diplom.database.StudentWorksheet;
import spbstu.iitu.kit.diplom.tomita.TomitaParser;
import spbstu.iitu.kit.diplom.tomita.XmlParser;

public class App {
    public App() {
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("manager1");
        EntityManager manager = factory.createEntityManager();
        /**manager.getTransaction().begin();
        manager.persist(new StudentWorksheet("1", "nomount"));
        manager.persist(new StudentWorksheet("2", "В зависимости от режима запуска. В режиме dedicate – да, в режиме shared – нет."));
        manager.persist(new StudentWorksheet("3", "Shared pool находится в sga. В нем содержаться результаты кодов запросов, кодов pl/sql."));
        manager.persist(new StudentWorksheet("4", "Каждые три секунды; При заполнении на треть"));
        manager.persist(new StudentWorksheet("5", "Выполненные команды"));
        manager.persist(new ExpertWorksheet("1", "Пользователь устанавливает соединение с сервером Oracle"));
        manager.persist(new ExpertWorksheet("2", "Да"));
        manager.persist(new ExpertWorksheet("3", "Разделяемый пул располагается в системной глобальной области (SGA). " +
                "Он содержит   план выполнения операторов SQL, " +
                "заголовки пакетов PL/SQL и процедур, строки словаря данных (для синтаксического анализа строк)."));
        manager.persist(new ExpertWorksheet("4", "При достижения 1Mb информации; " +
                "Каждые три секунды;" +
                "При завершении транзакции (commit); " +
                "При заполнении буфера на треть"));
        manager.persist(new ExpertWorksheet("5", "Команды, выполненные сервером Oracle"));
        manager.getTransaction().commit();**/
        List<StudentWorksheet> studentAnswers = manager
                .createNamedQuery("StudentWorksheet.getAnswers", StudentWorksheet.class).getResultList();
        for (StudentWorksheet worksheet : studentAnswers) {
            //Analysis of student answer
            Map<String, String> studentAnswerMap = new HashMap<>();
            if (TomitaParser.writeToFile(worksheet.getAnswer())) {
                studentAnswerMap.putAll(XmlParser.getAnswerPartsList(TomitaParser.executeAnalysis()));
            }

            //Analysis of expert answer
            Map<String, String> expertAnswerMap = new HashMap<>();
            ExpertWorksheet expertWorksheet = manager
                    .createNamedQuery("ExpertWorksheet.getAnswerByQuestionId", ExpertWorksheet.class)
                    .setParameter("questionId", worksheet.getQuestionId())
                    .getSingleResult();
            if (TomitaParser.writeToFile(expertWorksheet.getAnswer())) {
                expertAnswerMap.putAll(XmlParser.getAnswerPartsList(TomitaParser.executeAnalysis()));
            }
            int score=0;
            for (String keyword : expertAnswerMap.keySet()) {
                if (studentAnswerMap.containsKey(keyword) &&
                        studentAnswerMap.get(keyword).equals(expertAnswerMap.get(keyword))) {
                    score++;
                }
            }
        }

    }
}