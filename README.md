# CourseStudent - Internes Übungsszenario

## KW19 ToDo
- CourseStudentController: 
  - Die Repositories-Properties rauswerfen (werden auf die Services umgelegt)
  - Aus dem CourseStudentController alle Methoden extrahieren und zur Service-Klasse verschieben
  - StudentService und ggf. CourseService müssen über Constructor-Injection initiiert werden
  - Für "return new StudentService(studentRepository,courseRepository).createStudentBatch(studentPojoList);" eine "elegantere" Lösung finden.

- Schreibe eine Test-Klasse für StudentService
- Schreibe einen Test für Methode "createStudentBatch"
  - übergebe an die Test-Methode das Set
  - Prüfen, ob Daten gespeichert wurden (Repository-Methoden verwenden (FindBy...))

- Googlen, wie man Repos testet

- Mocking recherchieren


FRAGE AN MICH: Wie schreibt man einen Integration Test
HINWEIS: Annotation vor der Klasse
HINWEIS: Für das Testing mit Repo-Methoden gibt es auch elegantere Lösungen


## KW19 Fertig

