.class public ArrayTestingAndFunctionParams  ; Déclaration de la classe
.super java/lang/Object ; Déclaration de la superClasse de la classe 

.method public static myPrintArray(II)V ; public static void printInt(Int local1)
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


.method public static main([Ljava/lang/String;)V   
   .limit stack 3
   .limit locals 1  

   ; int[2];
   ldc 2 ; push the 2 constant into 
   newarray int  ; create the int[2]
   astore 0  ; stores the array reference into the local 

   ; array[1] = 123 
   aload 0  ; load the array reference
   ldc 1  
   ldc 123
   iastore  ; store 123 into the [1] of the array (after that there is nothin into the stack)

   ; push java.lang.System.out (type PrintStream) on the stack
   getstatic java/lang/System/out Ljava/io/PrintStream;  

   ; get the array[1]
    aload 0  ; retrieve the array reference 
    ldc 1
    iaload ; get the array[1] and push it on the stack. (after this, there is only the array[1] value on the stack. 

   ; TODO: print the 123 value
    invokevirtual java/io/PrintStream/println(I)V   

   ; terminate main
   return

.end method   ; déclaration de la fin de méthode 
