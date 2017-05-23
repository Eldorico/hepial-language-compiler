
.class public Greeter  ; Déclaration de la classe
.super java/lang/Object ; Déclaration de la superClasse de la classe 

.method public static main([Ljava/lang/String;)V    ; déclaration de la méthode main

   ; allocate stack big enough to hold 2 items
   .limit stack 2
   
   ; push java.lang.System.out (type PrintStream)
   getstatic java/lang/System/out Ljava/io/PrintStream;
   
   ; push string to be printed
   ldc "Hello World"

   ; invoke println
   invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
   
   ; terminate main
   return

.end method   ; déclaration de la fin de méthode 
