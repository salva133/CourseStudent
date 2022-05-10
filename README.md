# CourseStudent - Internes Übungsszenario

## KW19 ToDo

- Schreibe einen Test für Methode "createStudentBatch"
    - übergebe an die Test-Methode das Set
    - Prüfen, ob Daten gespeichert wurden (Repository-Methoden verwenden (FindBy...))

- Googlen, wie man Repos testet

- Mocking recherchieren

FRAGE AN MICH: Wie schreibt man einen Integration-Test?  
HINWEIS: Annotation vor der Klasse  
HINWEIS: Für das Testing mit Repo-Methoden gibt es auch elegantere Lösungen

## KW19 Fertig

- Für "return new StudentService(studentRepository,courseRepository).createStudentBatch(studentPojoList);" eine "
  elegantere" Lösung finden.
- StudentService und ggf. CourseService müssen über Constructor-Injection initiiert werden
- Die Repositories-Properties rauswerfen (werden auf die Services umgelegt)
- Schreibe eine Test-Klasse für StudentService
- Aus dem CourseStudentController alle Methoden extrahieren und zur Service-Klasse verschieben