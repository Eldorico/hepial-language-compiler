.class public Division0  ; Déclaration de la classe
.super java/lang/Object ; Déclaration de la superClasse de la classe 

.method public static main([Ljava/lang/String;)V    ; déclaration de la méthode main

   ; allocate stack big enough to hold 2 items
   .limit stack 2
   
   ldc 3
   ldc 0
   idiv
   
   ; terminate main
   return

.end method   ; déclaration de la fin de méthode 
