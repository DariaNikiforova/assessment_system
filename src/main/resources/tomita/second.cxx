#encoding "utf-8"    // сообщаем парсеру о том, в какой кодировке написана грамматика
#GRAMMAR_ROOT S


NounWords -> Word<kwset=["разделяемый_пул","sga"]>;
NounWords -> Word<gram="S,nom">;
//NounWords -> Participle* Noun<gram="S,nom"> | Adj* Noun<gram="S,nom">;
NounWords -> Word<gram="SPRO",wfm="Он|Она|Они">;
NounWords -> Word<wff="да|Да|нет|Нет">;
NounWords -> AnyWord<wff="[a-zA-z]+">+;
NounWords -> AnyWord<wff="[a-zA-z]+_[a-zA-z]+">;
VerbWords -> Verb;
VP -> VerbWords (Word<gram="inf">+);
VP -> Word<gram="A,brev">;

SubjectGram -> (NounWords interp (SubjectTag.Subject)) VerbWords interp (RelationTag.Relation); 
SubjectGram -> (NounWords interp (SubjectTag.Subject)) VP interp (RelationTag.Relation); 
SubjectGram -> NounWords interp (SubjectTag.Subject) (VerbWords interp (RelationTag.Relation)); 
SubjectGram -> NounWords interp (SubjectTag.Subject) (VP interp (RelationTag.Relation)); 
NounAdditions -> (Prep) Word<gram='~V', gnc-agr[1]> Word<gram='~V',wff="([a-zA-z]+ | [a-zA-z]+_[a-zA-z]+)", gnc-agr[1]>*;
UniformAdditions -> NounAdditions+;
UniformAdditions -> Word<kwset=["разделяемый_пул","sga"], gram="~им">;
UniformAdditions -> (Prep) Word Word<gram='~V,partcp'>* ',' (Prep) Word Word<gram='~V,partcp'>*;
UniformAdditions -> (Prep) Word<gnc-agr[1]> Word<gram='~V'>* 'и' (Prep) Word<gnc-agr[1]> Word<gram='~V'>*;
UniformAdditions -> (Prep) Word<gnc-agr[1]> Word<gram='~V'>* 'или' (Prep) Word<gnc-agr[1]> Word<gram='~V'>*;


//S -> NounWords<gram='им', sp-agr[1]> interp (SubjectTag.Subject) VP<rt,sp-agr[1]> interp (RelationTag.Relation) (NounAdditions interp (ObjectTag.Object));

//S -> NounAdditions* interp (ObjectTag.Object) VP interp (RelationTag.Relation) NounAdditions interp (ObjectTag.Object);

S -> UniformAdditions* interp (ObjectTag.Object) VP interp (RelationTag.Relation) UniformAdditions+ interp (ObjectTag.Object) {weight = 0.7};
S -> NounWords<sp-agr[1]> interp (SubjectTag.Subject) (UniformAdditions+ interp (ObjectTag.Object)) VP<rt,sp-agr[1]> interp (RelationTag.Relation) (UniformAdditions+ interp (ObjectTag.Object));
S -> VP<sp-agr[1]> interp (RelationTag.Relation) NounWords<rt,sp-agr[1]> interp (SubjectTag.Subject) (UniformAdditions+ interp (ObjectTag.Object));

S -> Participle<sp-agr[1]> interp (RelationTag.Relation) NounWords<rt,sp-agr[1]> interp (SubjectTag.Subject) (UniformAdditions+ interp (ObjectTag.Object));
//S -> (NounWords interp (SubjectTag.Subject)) (",") Participle interp (RelationTag.Relation) NounAdditions interp (ObjectTag.Object);
S -> NounWords interp (SubjectTag.Subject) "," Participle interp (RelationTag.Relation) UniformAdditions+ interp (ObjectTag.Object);
S -> (NounWords interp (SubjectTag.Subject)) VP interp (RelationTag.Relation) NounAdditions interp (ObjectTag.Object) (",") Word<wff="который|которая|которое"> interp (SubjectTag.Subject) VP interp (RelationTag.Relation) NounAdditions interp (ObjectTag.Object);
//S -> (NounAdditions interp (ObjectTag.Object)) SubjectGram;
S -> (UniformAdditions+ interp (ObjectTag.Object)) NounWords<rt> interp (SubjectTag.Subject) {weight = 0.4};
//S -> NounAdditions interp (ObjectTag.Object) (SubjectGram);
//S -> UniformAdditions+ interp (ObjectTag.Object) (SubjectGram) {weight = 0.2};
//S -> (SubjectGram) NounAdditions interp (ObjectTag.Object);
S -> (SubjectGram interp (SubjectTag.Subject)) UniformAdditions+ interp (ObjectTag.Object) {weight = 0.3};
//S -> NounAdditions interp (ObjectTag.Object) Hyphen NounWords interp (SubjectTag.Subject);
S -> UniformAdditions+ interp (ObjectTag.Object) Hyphen NounWords interp (SubjectTag.Subject);
S -> SubjectGram (NounAdditions interp (ObjectTag.Object)) (",") Prep SubjectGram (NounAdditions interp (ObjectTag.Object));
S -> SubjectGram (UniformAdditions+ interp (ObjectTag.Object)) (",") Prep SubjectGram (UniformAdditions+ interp (ObjectTag.Object));
//S -> NounAdditions interp (ObjectTag.Object);
//S -> UniformAdditions+ interp (ObjectTag.Object);
