# CourseStudent
 Internes Übungsszenario


##KW13

1. Zuerst ein Objekt vom Typ Course und dann ein Student Objekt erzeugen. Dann prüfen, ob die erzeugt werden.
   1. Objekte werden erzeugt. Habe drei distinkte Posts pro Tabelle geschrieben.
2. Exception einbauen.
   1. Exception "CreationFailedException" erstellt
3. Wenn sie nicht gespeichert werden, analysieren.
   1. Werden auf Kommando gespeichert. Kleine Auffälligkeit: Die ID zählt unabhängig von der Tabelle hoch, heißt: 
   Wenn der erste Datensatz auf der Course-Tabelle erzeugt wird, hat er die ID 1 (wie zu erwarten). 
   Wird dann der erste Datensatz auf der anderen Tabelle - Student - erzeugt, bekommt dieser die ID 2. 
   IDs sollten pro Tabelle zählen und nicht tabellenübergreifend.
4. Verknüpfung zu beiden Tabellen erzeugen, vorerst auskommentieren.