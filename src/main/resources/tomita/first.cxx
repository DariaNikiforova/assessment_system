#encoding "utf-8"    // сообщаем парсеру о том, в какой кодировке написана грамматика
#GRAMMAR_ROOT S


NounWords -> Noun+ interp (NounTag.CanonicWord);
//AdjWords -> Adj interp (AdjTag.CanonicWord);
VerbWords -> Verb;
VP -> VerbWords Word<gram="inf">+;

SubjectGram -> NounWords interp (SubjectTag.Subject) VP interp (VerbTag.CanonicWord); 
S -> SubjectGram;
//Other -> Word interp (OtherTag.CanonicWord);
 
/**Bla -> NounWords Verb;
Bla1 -> NounWords<gnc-agr[1]> (",") Adj<gnc-agr[1]>;
Bla2 -> Bla1<gnc-agr[1]> Word<gnc-agr[1]>+;
Bla3 -> NounWords Other;
Bla4 -> Adj Word;

S ->  Bla | Bla1 | Bla2 | Bla3 | Bla4 | NounWords | AdjWords | Verbs | Other;**/