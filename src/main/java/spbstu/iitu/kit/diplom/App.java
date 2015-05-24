package spbstu.iitu.kit.diplom;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import spbstu.iitu.kit.diplom.database.ExpertWorksheet;
import spbstu.iitu.kit.diplom.database.StudentWorksheet;
import spbstu.iitu.kit.diplom.tomita.TomitaParser;
import spbstu.iitu.kit.diplom.tomita.XmlParser;
import spbstu.iitu.kit.diplom.tomita.dto.Fact;
import spbstu.iitu.kit.diplom.tomita.dto.Lead;

public class App {
    public App() {
    }

    static Lead studentAnswer = new Lead();
    static Lead expertAnswer = new Lead();

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("manager1");
        EntityManager manager = factory.createEntityManager();
        /*manager.getTransaction().begin();
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
         manager.getTransaction().commit();*/
        /*manager.getTransaction().begin();
        manager.persist(new Synonym("разделяемый пул", "shared pool"));
        manager.persist(new Synonym("находиться", "содержаться"));
        manager.persist(new Synonym("shared pool", "разделяемый пул"));
        manager.persist(new Synonym("содержаться", "находиться"));
        manager.persist(new Synonym("содержаться", "располагаться"));
        manager.persist(new Synonym("располагаться", "содержаться"));
        manager.persist(new Synonym("располагаться", "находиться"));
        manager.persist(new Synonym("идти", "проводиться"));
        manager.persist(new Synonym("проводиться", "идти"));
        manager.getTransaction().commit();*/
        List<StudentWorksheet> studentAnswers = manager
                .createNamedQuery("StudentWorksheet.getAnswers", StudentWorksheet.class).getResultList();
        for (StudentWorksheet worksheet : studentAnswers) {
            //Analysis of student answer

            if (TomitaParser.writeToFile(worksheet.getAnswer())) {
                studentAnswer = XmlParser.getAnswer(TomitaParser.executeAnalysis());
            }

            //Analysis of expert answer
            ExpertWorksheet expertWorksheet = manager
                    .createNamedQuery("ExpertWorksheet.getAnswerByQuestionId", ExpertWorksheet.class)
                    .setParameter("questionId", worksheet.getQuestionId())
                    .getSingleResult();
            if (TomitaParser.writeToFile(expertWorksheet.getAnswer())) {
                //expertAnswerMap.putAll(XmlParser.getAnswerPartsList(TomitaParser.executeAnalysis()));
                expertAnswer = XmlParser.getAnswer(TomitaParser.executeAnalysis());
            }
            int score = 0;

            int correctAnswerWordCount = expertAnswer.getSize();
            int maxGrade = 1;
            /*int totalResponseMatchCount = 0;
            totalResponseMatchCount += getTotalResponseMatchCount(expertAnswer.getNames(),
                    studentAnswer.getNames());
            totalResponseMatchCount += getTotalResponseMatchCount(expertAnswer.getPrivileges(),
                    studentAnswer.getPrivileges());
            totalResponseMatchCount += getTotalResponseMatchCount(expertAnswer.getNouns(),
                    studentAnswer.getNouns());
            totalResponseMatchCount += getTotalResponseMatchCount(expertAnswer.getVerbs(),
                    studentAnswer.getVerbs());
            totalResponseMatchCount += getTotalResponseMatchCount(expertAnswer.getParticiples(),
                    studentAnswer.getParticiples());
            totalResponseMatchCount += getTotalResponseMatchCount(expertAnswer.getAdjectives(),
                    studentAnswer.getAdjectives());
            totalResponseMatchCount += getTotalResponseMatchCount(expertAnswer.getParts(),
                    studentAnswer.getParts());*/
            int result = getResponseWordsMatchCount(manager);
            double grade = maxGrade * ((double) result/((double) expertAnswer.getObjectSize() + (double) expertAnswer.getSubjectSize())); //maxGrade * (totalResponseMatchCount/correctAnswerWordCount);
            System.out.format("Question №%s is %s", expertWorksheet.getQuestionId(), Double.toString(grade));
        }
    }

    private static int getTotalResponseMatchCount(List<Fact> expertFacts, List<Fact> studentFacts) {
        int responseMatchCount = 0;
        if (expertFacts.size() > 0) {
            if (studentFacts.size() > 0) {
                for (Fact expertNameFact : expertFacts) {
                    for (Fact studentNameFact : studentFacts) {
                        if (studentNameFact.getValue().toLowerCase().equals(expertNameFact.getValue().toLowerCase())) {
                            if (areSentencePositionsMatches(expertAnswer.getSubjects(),
                                    studentAnswer.getSubjects(), expertNameFact.getValue().toLowerCase())
                                    || areSentencePositionsMatches(expertAnswer.getObjects(),
                                    studentAnswer.getObjects(), expertNameFact.getValue().toLowerCase())
                                    || areSentencePositionsMatches(expertAnswer.getParticiples(),
                                    studentAnswer.getParticiples(), expertNameFact.getValue().toLowerCase()))
                            responseMatchCount++;
                            break;
                        }
                    }
                }
            }
        }
        return responseMatchCount;
    }

    private static boolean isMatched(List<Fact> expertFacts, String subject) {
        if (expertFacts.size() > 0) {
            for (Fact expertFact : expertFacts) {
                if (expertFact.getValue().toLowerCase().contains(subject)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean areSentencePositionsMatches(List<Fact> expertFacts, List<Fact> studentFacts, String eWord) {
        boolean isExpertWordExist = false;
        boolean isStudentWordExist = false;
        if (expertFacts.size() > 0) {
            if (studentFacts.size() > 0) {
                for (Fact expertFact : expertFacts) {
                    String expertText = expertFact.getValue().toLowerCase();
                    if (expertText.contains(eWord)) {
                        isExpertWordExist = true;
                        break;
                    }
                }
                for (Fact studentFact : studentFacts) {
                    String studentText = studentFact.getValue().toLowerCase();
                    if (studentText.contains(eWord)) {
                        isStudentWordExist = true;
                        break;
                    }
                }
            }
        }
        return isExpertWordExist && isStudentWordExist;
    }

    private static List<String> getSynonyms(EntityManager manager, String sourceWord) {
        return manager.createNamedQuery("Synonym.getSynonymList", String.class)
                .setParameter("source", sourceWord)
                .getResultList();
    }

    private static boolean areRelated(int eIndex, int sIndex, EntityManager manager, List<Fact> findInStudentsFacts, List<Fact> findInExpertFacts) {
        List<String> expertRelation;
        List<String> studentRelation;
        if (studentAnswer.getRelations().size() == 0 && expertAnswer.getRelations().size() == 0
                && studentAnswer.getObjects().size() == 0 && expertAnswer.getObjects().size() == 0) {
            return true;
        }
        if (studentAnswer.getRelations().size() != 0 && expertAnswer.getRelations().size() != 0) {
            if (expertAnswer.getRelations().size() > eIndex) {
                expertRelation = Arrays.asList(expertAnswer.getRelations().get(eIndex).getValue().split(" "));
            } else {
                return false;
            }
            if (studentAnswer.getRelations().size() > sIndex) {
                studentRelation = Arrays.asList(studentAnswer.getRelations().get(sIndex).getValue().split(" "));
            } else {
                return false;
            }
            for (String sWord : studentRelation) {
                if (expertRelation.contains(sWord)) {
                    return true;
                }
                if (isSynonymExists(sWord, manager, expertAnswer.getRelations())) {
                    return true;
                }
            }
        } else if (findInStudentsFacts.size() != 0 && findInExpertFacts.size() != 0) {
            if (findInExpertFacts.size() > eIndex) {
                expertRelation = Arrays.asList(findInExpertFacts.get(eIndex).getValue().split(" "));
            } else {
                return false;
            }
            if (findInStudentsFacts.size() > sIndex) {
                studentRelation = Arrays.asList(findInStudentsFacts.get(sIndex).getValue().split(" "));
            } else {
                return false;
            }
            for (String sWord : studentRelation) {
                if (expertRelation.contains(sWord)) {
                    return true;
                }
                if (isSynonymExists(sWord, manager, findInExpertFacts)) {
                    return true;
                }
            }
        } else if (findInStudentsFacts.size() != 0 && findInExpertFacts.size() == 0
                || findInStudentsFacts.size() == 0 && findInExpertFacts.size() != 0){
            return true;
        }
        return false;
    }

    private static boolean isSynonymExists(String sourceWord, EntityManager manager, List<Fact> facts) {
        for (String synonym : getSynonyms(manager, sourceWord)) {
            for (Fact expertFact : facts) {
                List<String> expertRParts = Arrays.asList(expertFact.getValue().toLowerCase().split(" "));
                if (expertRParts.contains(synonym.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int getResponseWordsMatchCount(EntityManager manager) {
        int responseMatchCount = 0;
        if (!studentAnswer.getSubjectsMap().isEmpty()) {
            Map<String, String> studentFacts = studentAnswer.getSubjectsMap();
            for (String key : studentFacts.keySet()) {
                int index = expertAnswer.getSubjectIndex(key);
                if (index >= 0) {
                    if (expertAnswer.getSubjectsMap().containsKey(key)) {
                        if (expertAnswer.getSubjectsMap().get(key).equals(studentFacts.get(key))
                                || isSynonymExists(key, manager, expertAnswer.getSubjects())) {
                            if (areRelated(index, studentAnswer.getSubjectIndex(key), manager,
                                    studentAnswer.getObjects(), expertAnswer.getObjects())) {
                                responseMatchCount++;
                            }
                        }
                    }
                } else if ((index = expertAnswer.getObjectIndex(key)) >= 0) {
                    if (expertAnswer.getObjectMap().containsKey(key)) {
                        if (expertAnswer.getObjectMap().get(key).equals(studentFacts.get(key))
                                || isSynonymExists(key, manager, expertAnswer.getObjects())) {
                            if (areRelated(index, studentAnswer.getSubjectIndex(key), manager,
                                    studentAnswer.getObjects(), expertAnswer.getObjects())) {
                                responseMatchCount++;
                            }
                        }
                    }
                } else if ((index = expertAnswer.getRelationIndex(key)) >= 0) {
                    if (expertAnswer.getRelationsMap().containsKey(key)) {
                        if (expertAnswer.getRelationsMap().get(key).equals(studentFacts.get(key))
                                || isSynonymExists(key, manager, expertAnswer.getRelations())) {
                            if (areRelated(index, studentAnswer.getSubjectIndex(key), manager,
                                    studentAnswer.getObjects(), expertAnswer.getObjects())) {
                                responseMatchCount++;
                            }
                        }
                    }
                }
            }
        }
        if (!studentAnswer.getObjectMap().isEmpty()) {
            Map<String, String> studentFacts = studentAnswer.getObjectMap();
            for (String key : studentFacts.keySet()) {
                int index = expertAnswer.getSubjectIndex(key);
                if (index >= 0) {
                    if (expertAnswer.getSubjectsMap().containsKey(key)) {
                        if (expertAnswer.getSubjectsMap().get(key).equals(studentFacts.get(key))
                                || isSynonymExists(key, manager, expertAnswer.getSubjects())) {
                            if (areRelated(index, studentAnswer.getObjectIndex(key), manager,
                                    studentAnswer.getSubjects(), expertAnswer.getSubjects())) {
                                responseMatchCount++;
                            }
                        }
                    }
                } else if ((index = expertAnswer.getObjectIndex(key)) >= 0) {
                    if (expertAnswer.getObjectMap().containsKey(key)) {
                        if (expertAnswer.getObjectMap().get(key).equals(studentFacts.get(key))
                                || isSynonymExists(key, manager, expertAnswer.getObjects())) {
                            if (areRelated(index, studentAnswer.getObjectIndex(key), manager,
                                    studentAnswer.getSubjects(), expertAnswer.getSubjects())) {
                                responseMatchCount++;
                            }
                        }
                    }
                } else if ((index = expertAnswer.getRelationIndex(key)) >= 0) {
                    if (expertAnswer.getRelationsMap().containsKey(key)) {
                        if (expertAnswer.getRelationsMap().get(key).equals(studentFacts.get(key))
                                || isSynonymExists(key, manager, expertAnswer.getRelations())) {
                            if (areRelated(index, studentAnswer.getObjectIndex(key), manager,
                                    studentAnswer.getSubjects(), expertAnswer.getSubjects())) {
                                responseMatchCount++;
                            }
                        }
                    }
                }
            }
        }
        return responseMatchCount;
    }
}