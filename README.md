# CourseStudent - Internes Übungsszenario

## KW20 ToDo

1. fachliche Logik entwickeln (Notizbuch für Ansätze prüfen)

- Liste der Kursteilnehmer erzeugen
- Unit Test schreiben
    - falls das nicht klappt (oder als Fleißarbeit) mit Integration Test fortfahren
        - Integration Test  (Link: https://www.baeldung.com/spring-boot-testing)

2. Integration Test für Methode "createStudentBatch"
    - übergebe an die Test-Methode das Set
    - Prüfen, ob Daten gespeichert wurden (Repository-Methoden verwenden (FindBy...))
3. Integration Test für API (MockMVC)

- Wie wird eine Exception an die API weitergegeben?

4. Authorization entwickeln/implementieren

- user:password als Anfang
- Tokenbasierter Zugang recherchieren

## Fertig

- Exceptions testen (assertThrow)
- Für "return new StudentService(studentRepository,courseRepository).createStudentBatch(studentPojoList);" eine "
  elegantere" Lösung finden.
- StudentService und ggf. CourseService müssen über Constructor-Injection initiiert werden
- Die Repositories-Properties rauswerfen (werden auf die Services umgelegt)
- Schreibe eine Test-Klasse für StudentService
- Aus dem CourseStudentController alle Methoden extrahieren und zur Service-Klasse verschieben
- Googlen, wie man Repos testet
- Mocking recherchieren
- Exceptions mit HttpStatus mappen

Refactoring-Aufgaben:

- ID in StudentPojo entfernen
- HTTP-Status.CREATED an Methoden mappen
- sout durch Logger ersetzen
 