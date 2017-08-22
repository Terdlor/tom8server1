drop FUNCTION fun_j(character, character, character);

create or REPLACE  function fun_j(character) returns VARCHAR(50)
LANGUAGE plpgsql
AS $$
--  первый входной параметр ($1) - номер изделия
         --  если $1 задан - обновляется строка с таким номером
         --  если $1 не задан(null) - вставляется новая строка, номер берется из последовательности
--  второй входной параметр ($2)- наименование изделия - д.б. непустой
--  третий входной параметр ($3)- название города - д.б. непустой
DECLARE vns character(6);
BEGIN
 begin

            Return 'hvkevbk';
   end;

END;
$$;
