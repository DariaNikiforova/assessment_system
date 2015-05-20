#encoding "utf-8"    // сообщаем парсеру о том, в какой кодировке написана грамматика
#GRAMMAR_ROOT S


NounWords -> Noun<gram="S,nom">;
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
NounAdditions -> (Prep) Word Word<gram='~V,partcp'>*;
UniformAdditions -> (Prep) Word Word<gram='~V,partcp'>* ',' (Prep) Word Word<gram='~V,partcp'>*;
UniformAdditions -> (Prep) Word<gnc-agr[1]> Word<gram='~V'>* 'и' (Prep) Word<gnc-agr[1]> Word<gram='~V'>*;


S -> NounWords<gram='им', sp-agr[1]> interp (SubjectTag.Subject) VP<rt,sp-agr[1]> interp (RelationTag.Relation) (NounAdditions interp (ObjectTag.Object));
S -> NounWords<gram='им', sp-agr[1]> interp (SubjectTag.Subject) VP<rt,sp-agr[1]> interp (RelationTag.Relation) (UniformAdditions+ interp (ObjectTag.Object));
S -> NounAdditions* interp (ObjectTag.Object) VP interp (RelationTag.Relation) NounAdditions interp (ObjectTag.Object) {weight=0.7};
S -> UniformAdditions* interp (ObjectTag.Object) VP interp (RelationTag.Relation) UniformAdditions+ interp (ObjectTag.Object);
S -> (NounWords interp (SubjectTag.Subject)) (",") Participle interp (RelationTag.Relation) NounAdditions interp (ObjectTag.Object);
S -> (NounWords interp (SubjectTag.Subject)) (",") Participle interp (RelationTag.Relation) UniformAdditions+ interp (ObjectTag.Object);
S -> (NounWords interp (SubjectTag.Subject)) VP interp (RelationTag.Relation) NounAdditions interp (ObjectTag.Object) (",") Word<wff="который|которая|которое"> interp (SubjectTag.Subject) VP interp (RelationTag.Relation) NounAdditions interp (ObjectTag.Object);
S -> (NounAdditions interp (ObjectTag.Object)) SubjectGram;
S -> (UniformAdditions+ interp (ObjectTag.Object)) SubjectGram;
S -> NounAdditions interp (ObjectTag.Object) (SubjectGram);
S -> UniformAdditions+ interp (ObjectTag.Object) (SubjectGram);
S -> (SubjectGram) NounAdditions interp (ObjectTag.Object);
S -> (SubjectGram) UniformAdditions+ interp (ObjectTag.Object);
S -> NounAdditions interp (ObjectTag.Object) Hyphen NounWords interp (SubjectTag.Subject);
S -> UniformAdditions+ interp (ObjectTag.Object) Hyphen NounWords interp (SubjectTag.Subject);
S -> SubjectGram (NounAdditions interp (ObjectTag.Object)) (",") Prep SubjectGram (NounAdditions interp (ObjectTag.Object));
S -> SubjectGram (UniformAdditions+ interp (ObjectTag.Object)) (",") Prep SubjectGram (UniformAdditions+ interp (ObjectTag.Object));

//S -> NounAdditions interp (ObjectTag.Object);
//S -> UniformAdditions+ interp (ObjectTag.Object);
