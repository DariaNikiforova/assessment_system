package spbstu.iitu.kit.diplom.tomita.dto;

import spbstu.iitu.kit.diplom.tomita.Stemmer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Daria Nikiforova
 */
public class Lead {

    public static final String NOUN = "noun";
    public static final String VERB = "verb";
    public static final String ADJECTIVE = "adjective";
    public static final String PARTCIPLE = "participle";
    public static final String PART = "part";
    public static final String NAME = "name";
    public static final String PRIVILEGE = "privilege";
    public static final String OTHER = "other";

    ArrayList<Fact> adjectives;
    ArrayList<Fact> nouns;
    ArrayList<Fact> verbs;
    ArrayList<Fact> others;
    ArrayList<Fact> parts;
    ArrayList<Fact> participles;
    ArrayList<Fact> relations;
    ArrayList<Fact> subjects;
    ArrayList<Fact> objects;
    ArrayList<Fact> names;
    ArrayList<Fact> privileges;

    public ArrayList<Fact> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Fact> objects) {
        this.objects = objects;
    }

    public ArrayList<Fact> getNouns() {
        return nouns;
    }

    public void setNouns(ArrayList<Fact> nouns) {
        this.nouns = nouns;
    }

    public ArrayList<Fact> getVerbs() {
        return verbs;
    }

    public void setVerbs(ArrayList<Fact> verbs) {
        this.verbs = verbs;
    }

    public ArrayList<Fact> getOthers() {
        return others;
    }

    public void setOthers(ArrayList<Fact> others) {
        this.others = others;
    }

    public ArrayList<Fact> getParts() {
        return parts;
    }

    public void setParts(ArrayList<Fact> parts) {
        this.parts = parts;
    }

    public ArrayList<Fact> getParticiples() {
        return participles;
    }

    public void setParticiples(ArrayList<Fact> participles) {
        this.participles = participles;
    }

    public ArrayList<Fact> getRelations() {
        return relations;
    }

    public void setRelations(ArrayList<Fact> relations) {
        this.relations = relations;
    }

    public ArrayList<Fact> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Fact> subjects) {
        this.subjects = subjects;
    }

    public ArrayList<Fact> getAdjectives() {
        return adjectives;
    }

    public void setAdjectives(ArrayList<Fact> adjectives) {
        this.adjectives = adjectives;
    }

    public ArrayList<Fact> getNames() {
        return names;
    }

    public void setNames(ArrayList<Fact> names) {
        this.names = names;
    }

    public ArrayList<Fact> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(ArrayList<Fact> privileges) {
        this.privileges = privileges;
    }

    public Lead() {
        adjectives = new ArrayList<>();
        nouns = new ArrayList<>();
        verbs = new ArrayList<>();
        others = new ArrayList<>();
        parts = new ArrayList<>();
        participles = new ArrayList<>();
        relations = new ArrayList<>();
        subjects = new ArrayList<>();
        objects = new ArrayList<>();
        names = new ArrayList<>();
        privileges = new ArrayList<>();
    }

    public int getSize() {
        return adjectives.size() + nouns.size() + verbs.size() + others.size()
                + parts.size() + participles.size() + relations.size() + subjects.size() + objects.size()
                + names.size() + privileges.size();
    }

    public int getObjectSize() {
        return getObjectMap().size();
    }

    public int getSubjectSize() {
        return getSubjectsMap().size();
    }

    public int getSubjectIndex(String word) {
        Stemmer stemmer = new Stemmer();
        String normWord =  stemmer.stem(word);
        int i = 0;
        for (Fact subject : subjects) {
            if (subject.getValue().toLowerCase().contains(normWord)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int getRelationIndex(String word) {
        Stemmer stemmer = new Stemmer();
        String normWord =  stemmer.stem(word);
        int i = 0;
        for (Fact relation : relations) {
            if (relation.getValue().toLowerCase().contains(normWord)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int getObjectIndex(String word) {
        Stemmer stemmer = new Stemmer();
        String normWord =  stemmer.stem(word);
        int i = 0;
        for (Fact object : objects) {
            if (object.getValue().toLowerCase().contains(normWord)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public Map<String, String> getSubjectsMap() {
        Map<String, String> facts = new HashMap<>();
        String word;
        for (Fact noun : nouns) {
            word = noun.getValue();
            if (getSubjectIndex(word) >= 0) {
                facts.put(noun.getValue(), NOUN);
            }
        }
        for (Fact verb : verbs) {
            word = verb.getValue();
            if (getSubjectIndex(word) >= 0) {
                facts.put(verb.getValue(), VERB);
            }
        }
        for (Fact participle : participles) {
            word = participle.getValue();
            if (getSubjectIndex(word) >= 0) {
                facts.put(participle.getValue(), PARTCIPLE);
            }
        }
        for (Fact adjective : adjectives) {
            word = adjective.getValue();
            if (getSubjectIndex(word) >= 0) {
                facts.put(adjective.getValue(), ADJECTIVE);
            }
        }
        for (Fact name : names) {
            word = name.getValue();
            if (getSubjectIndex(word) >= 0) {
                facts.put(name.getValue(), NAME);
            }
        }
        for (Fact privilege : privileges) {
            word = privilege.getValue();
            if (getSubjectIndex(word) >= 0) {
                facts.put(privilege.getValue(), PRIVILEGE);
            }
        }
        for (Fact part : parts) {
            word = part.getValue();
            if (getSubjectIndex(word) >= 0) {
                facts.put(part.getValue(), PART);
            }
        }
        return facts;
    }

    public Map<String, String> getRelationsMap() {
        Map<String, String> facts = new HashMap<>();
        String word;
        for (Fact noun : nouns) {
            word = noun.getValue();
            if (getRelationIndex(word) >= 0) {
                facts.put(noun.getValue(), NOUN);
            }
        }
        for (Fact verb : verbs) {
            word = verb.getValue();
            if (getRelationIndex(word) >= 0) {
                facts.put(verb.getValue(), VERB);
            }
        }
        for (Fact participle : participles) {
            word = participle.getValue();
            if (getRelationIndex(word) >= 0) {
                facts.put(participle.getValue(), PARTCIPLE);
            }
        }
        for (Fact adjective : adjectives) {
            word = adjective.getValue();
            if (getRelationIndex(word) >= 0) {
                facts.put(adjective.getValue(), ADJECTIVE);
            }
        }
        for (Fact name : names) {
            word = name.getValue();
            if (getRelationIndex(word) >= 0) {
                facts.put(name.getValue(), NAME);
            }
        }
        for (Fact privilege : privileges) {
            word = privilege.getValue();
            if (getRelationIndex(word) >= 0) {
                facts.put(privilege.getValue(), PRIVILEGE);
            }
        }
        for (Fact part : parts) {
            word = part.getValue();
            if (getRelationIndex(word) >= 0) {
                facts.put(part.getValue(), PART);
            }
        }
        return facts;
    }

    public Map<String, String> getObjectMap() {
        Map<String, String> facts = new HashMap<>();
        String word;
        for (Fact noun : nouns) {
            word = noun.getValue();
            if (getObjectIndex(word) >= 0) {
                facts.put(noun.getValue(), NOUN);
            }
        }
        for (Fact verb : verbs) {
            word = verb.getValue();
            if (getObjectIndex(word) >= 0) {
                facts.put(verb.getValue(), VERB);
            }
        }
        for (Fact participle : participles) {
            word = participle.getValue();
            if (getObjectIndex(word) >= 0) {
                facts.put(participle.getValue(), PARTCIPLE);
            }
        }
        for (Fact adjective : adjectives) {
            word = adjective.getValue();
            if (getObjectIndex(word) >= 0) {
                facts.put(adjective.getValue(), ADJECTIVE);
            }
        }
        for (Fact name : names) {
            word = name.getValue();
            if (getObjectIndex(word) >= 0) {
                facts.put(name.getValue(), NAME);
            }
        }
        for (Fact privilege : privileges) {
            word = privilege.getValue();
            if (getObjectIndex(word) >= 0) {
                facts.put(privilege.getValue(), PRIVILEGE);
            }
        }
        for (Fact part : parts) {
            word = part.getValue();
            if (getObjectIndex(word) >= 0) {
                facts.put(part.getValue(), PART);
            }
        }
        return facts;
    }
}
