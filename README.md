# LoanApplication
Prosty moduł do przetwarzania wniosków o kredyt (SpringBoot, Hibernate, REST)

usługi (POST na porcie 8080)
/loan/add/{kwota_kredytu}/{czas_kredytowania_w_dniach}
/loan/extend/{id_kredytu}

Wymagania:
- REST API
- Zdefiniowana minimalna i maksymalna kwota kredytu oraz minimalny i maksymalny czas kredytowania
- Dostępne operacje:
  - zawnioskowanie o kredyt:
    - jeśli kwota wykracza poza zdefiniowany przedział wniosek jest odrzucany
    - jeśli okres kredytowania wykracza poza zdefiniowany przedział wniosek jest odrzucany
    - jeśli wniosek jest składany pomiędzy godz. 00:00, a 6:00, a wnioskowana kwota jest równa maksymalnej kwocie wniosek jest odrzucany
    - oprocentowanie kredytu to 10% (nie zależy od okresu kredytowania)
  - przedłużenie okresu kredytowania istniejącego wniosku
    - czas kredytowania przedłużany jest o z góry założony okres
    - w wyniku przedłużenia uaktualniana jest data spłaty kredytu
    - nie ma ograniczenia na ilość przedłużeń pojedynczego kredytu
- Testy jednostkowe
- Jeden test integracyjny dla pozytywnego scenariusza (przyznano kredyt)
- Brak GUI
- Brak autoryzacji
- Brak zarządzania użytkownikami
