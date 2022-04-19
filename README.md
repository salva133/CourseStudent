# CourseStudent - Internes Übungsszenario
##KW15

- @ResponseStatus einbinden für POST-Methods, vielleicht mal nachprüfen welche Annotation für HTTP-Return Codes ist
- Exception muss auch das Feld des illegal argument benennen

##Anforderungen für diese Woche
####newStudentBatch()
- drei Studenten anlegen
- Explizit Studenten returnen, bei dem das Alter zu niedrig ist ("Dieser Student ist zu jung")
- Studenten unter 12 können keine Studenten sein (Custom Exception)
#
#Fertig
- Get- und PostMapping-Methoden implementieren,
  - GetMapping-Methode: Nach Student mit Namen suchen/mit Kurs suchen,
  - PostMapping: Kurs eintragen/ändern
  - Im Falle von Exceptions: HTTP-Error Codes mit einbinden
  - Recherchieren wie POJOs als JSON-Payload formatiert werden können (wie bekommen wir LocalDate von Spring Boot formatiert)
    Link von Oleg nutzen -> https://stackoverflow.com/questions/43684306/spring-deserializes-a-localdate-in-a-requestbody-differently-from-one-in-a-req

####newStudent()
- Studenten unter 12 können keine Studenten sein (Custom Exception)
- HTTPResponseCode nutzen (NOT_VALID e.g.)
- Explizit Studenten returnen, bei dem das Alter zu niedrig ist ("Dieser Student ist zu jung")

####newStudentWithCourse()
- Beim Erstellen von Studenten soll auch der Kurs angelegt werden
- Als Darstellung soll der CourseName angezeigt werden
- Ist der Kurs nicht über den Namen zu finden, muss eine Exception geworfen werden, aber der Datensatz soll trotzdem gespeichert werden. NULL im Fremdschlüssel ist zulässig.
