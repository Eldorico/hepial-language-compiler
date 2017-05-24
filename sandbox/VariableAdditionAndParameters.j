.class public VariableAdditionAndParameters  ; Déclaration de la classe
.super java/lang/Object ; Déclaration de la superClasse de la classe 

.method public static myAddAndPrint(II)V ; public static void printInt(Int local1)
   .limit stack 3     ; allocate stack big enough to hold 3 elements. (one function, and 2 ints at maximum)
   .limit locals 2

   ; push java.lang.System.out (type PrintStream) on the stack
   getstatic java/lang/System/out Ljava/io/PrintStream;
   
   iload 0      ; push param 1 to the stack (so the method pushed one line above can use this var as param
   iload 1      ; push param 2 to the stack (so the method pushed one line above can use this var as param
   iadd

   ; invoke println (execute the method pushed two lines above)
   invokevirtual java/io/PrintStream/println(I)V

   return  ; to avoid the "Falling off the end of the code" error
  
.end method 


.method public static main([Ljava/lang/String;)V    ; déclaration de la méthode main
   .limit stack 2
   .limit locals 4  ; on utilise jusqu'à 4 cases de registre. ([0-3])

   ldc 12  ; load the 12 constant on the stack 
   istore 0  ; store the 12 constant on the 1rst register (it is not any more on the stack)
   ldc 6 ; load the 6 cst on the stack 
   istore 1 ; store the 6 constant on the 2cnd register 
   ldc 123 
   istore 2
   ldc 321 
   istore 3 

   iload 0 ; load the 12 cst on the stack
   iload 1 ; load the 6 cst on the stack 
   iadd ; add the two cst on the top on the stack (and add it to the stack). (the two previous constants are consumed from the addition and are not any more in the stack)
   istore 0 ; store the 12+6 cst on the stack (we dont need the 1 and 2 register anymore)

   ; push java.lang.System.out (type PrintStream) on the stack
   getstatic java/lang/System/out Ljava/io/PrintStream;

   iload 0 ; load the 12+6 cst on the stack 

   ; invoke println (execute the method pushed two lines above) to print 
   invokevirtual java/io/PrintStream/println(I)V

   iload 2   ; load 123
   iload 3   ; load 321

   invokestatic VariableAdditionAndParameters.myAddAndPrint(II)V    ; invoque the print fct to print the 12+6 value
   
   ; terminate main
   return

.end method   ; déclaration de la fin de méthode 
