#encoding "utf-8"    // сообщаем парсеру о том, в какой кодировке написана грамматика
#GRAMMAR_ROOT S


//NounWords -> Noun interp (NounTag.CanonicWord);
//AdjWords -> Adj interp (AdjTag.CanonicWord);
WordWords -> Word interp (OtherTag.CanonicWord);
VerbWords -> Verb;
VP -> VerbWords Word<gram="inf">+;

NounAdditions -> (Prep) Word Word*;
UniformAdditions -> (Prep) Word Word* ',' (Prep) Word Word*;
UniformAdditions -> (Prep) Word<gnc-agr[1]> Word* 'и' (Prep) Word<gnc-agr[1]> Word*;

//SubjectGram -> VerbWords Prep* WordWords+; 
SubjectGram -> VerbWords NounAdditions interp (ObjectTag.Object);
SubjectGram -> VerbWords UniformAdditions+ interp (ObjectTag.Object);
SubjectGram -> VP interp (VerbTag.CanonicWord) NounAdditions interp (ObjectTag.Object);
SubjectGram -> VP interp (VerbTag.CanonicWord) UniformAdditions+;
S -> SubjectGram;