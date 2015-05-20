package spbstu.iitu.kit.diplom.tomita;

import spbstu.iitu.kit.diplom.tomita.dto.Fact;

import java.util.ArrayList;

/**
 * @author Daria Nikiforova
 */
public class Answer {
    ArrayList<Fact> adjectives;
    ArrayList<Fact> nouns;
    ArrayList<Fact> verbs;
    ArrayList<Fact> others;
    ArrayList<Fact> parts;
    ArrayList<Fact> participles;
    ArrayList<Fact> relations;
    ArrayList<Fact> subjects;
    ArrayList<Fact> objects;

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

    public Answer() {
        adjectives = new ArrayList<>();
        nouns = new ArrayList<>();
        verbs = new ArrayList<>();
        others = new ArrayList<>();
        parts = new ArrayList<>();
        participles = new ArrayList<>();
        relations = new ArrayList<>();
        subjects = new ArrayList<>();
        objects = new ArrayList<>();
    }
}
