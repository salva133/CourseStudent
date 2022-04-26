# CourseStudent - Internes Übungsszenario

## KW17 ToDo

1. newStudentBatchWithCourse() anlegen - noch in Arbeit, Issue ist
   offen: https://github.com/salva133/CourseStudent/issues/14
2. Liste mit failed records erzeugen und distinkt an den Client zurückgeben (Bsp. "5 out of 6 students have been created
   but the creation of "NAME" failed with "ERROR")
   1. Selektierbar (konfigurierbar) machen, ob dem Client die Fehlermeldung ausgegeben wird
   2. Debug Flag / Debug Mode einrichten
3. verlinkten Course im Student auf den courseName auflösen


## KW17 Fertig
- ageLimit konfigurierbar machen (entweder application.properties anpassen oder eine application.yaml anlegen und dies dann nutzen)
- newStudent() auf den neuen Stand bringen (Prüfungen finden jetzt im Objekt statt, an newStudentBatch() orientieren (im Prinzip abkupfern))
