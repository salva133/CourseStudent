# CourseStudent - Internes Übungsszenario

## KW17

1. newStudent() auf den neuen Stand bringen (Prüfungen finden jetzt im Objekt statt, an newStudentBatch() orientieren (im Prinzip abkupfern))
2. Liste mit failed records erzeugen und distinkt an den Client zurückgeben (Bsp. "5 out of 6 students have been created but the creation of "NAME" failed with "ERROR")
   1. Selektierbar (konfigurabel) machen, ob dem Client die Fehlermeldung ausgegeben wird
   2. Debug Flag / Debug Mode einrichten
3. ageLimit konfigurabel machen (entweder application.properties anpassen oder eine application.yaml anlegen und dies dann nutzen)
4. newStudentBatchWithCourse() anlegen
5. verlinkten Course im Student auf den courseName auflösen