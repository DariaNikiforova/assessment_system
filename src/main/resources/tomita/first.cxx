#encoding "utf-8"    // сообщаем парсеру о том, в какой кодировке написана грамматика
#GRAMMAR_ROOT S

S -> Participle interp (ParticipleTag.CanonicWord);
S -> AnyWord<gram="S",wff="[а-яА-я]+"> interp (NounTag.CanonicWord);
S -> Verb interp (VerbTag.CanonicWord);
S -> Adj interp (AdjTag.CanonicWord);
S -> Word<gram="A,brev"> interp (AdjTag.CanonicWord);
S -> Prep interp (OtherTag.CanonicWord);
S -> AnyWord<wff="[a-zA-z]+">+ interp (NameTag.Name);
S -> AnyWord<wff="[a-zA-z]+_[a-zA-z]+"> interp (PrivilegeTag.Privilege);
S -> Word<gram="PART"> interp (PartTag.Part);