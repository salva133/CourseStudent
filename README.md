# CourseStudent - Internes Übungsszenario

## KW16

### Aufgaben bis Freitag:
- newStudents() erstellen
- Custom Exception
    - Student zu jung (<12y)
    - explizit ausgeben, welchen Student die Exception betrifft
    - die anderen gültigen Studenten sollen trotzdem erstellt werden (kein Stop der Applikation)
    - an den Client: "X Studenten wurden erstellt, jedoch war Student Y zu jung und wurde somit nicht erstellt"


"Chaining Exceptions" (auf jeden Fall versuchen anzuwenden)
https://www.baeldung.com/java-chained-exceptions

"Exceptions"
https://www.baeldung.com/java-exceptions

catches nach folgendem Konzept bauen:
1. Logging
2. Try to self repair
3. Inform client