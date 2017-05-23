.class public SimpleMethodInvocation  ; Déclaration de la classe
.super java/lang/Object ; Déclaration de la superClasse de la classe 

.method public static myPrintInt(I)V ; public static void printInt(Int local1)
   .limit stack 2     ; allocate stack big enough to hold 2 instructions

   ; push java.lang.System.out (type PrintStream) on the stack
   getstatic java/lang/System/out Ljava/io/PrintStream;
   
   ; push param 1 to the stack (so the method pushed one line above can use this var as param
   iload 0  

   ; invoke println (execute the method pushed two lines above)
   invokevirtual java/io/PrintStream/println(I)V

   return  ; to avoid the "Falling off the end of the code" error
  
.end method 


.method public static main([Ljava/lang/String;)V    ; déclaration de la méthode main

   ; I think we dont need to spefify the stack size because there only statics/constants elements used

   ldc 123456  ; load the 123 constant on the stack
   invokestatic SimpleMethodInvocation.myPrintInt(I)V    
   
   ; terminate main
   return

.end method   ; déclaration de la fin de méthode 
