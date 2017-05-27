.class MainBlock
.super java/lang/Object

.field myArray [I  ; declaration de l'array

; default constructor
.method public <init>()V
   .limit stack 3
 
   ; invoke super
   aload 0
   invokespecial java/lang/Object/<init>()V

   ; create array
   aload 0 
   ldc 10
   newarray int
   putfield MainBlock/myArray [I

   return
.end method

; main function
.method mainFunction()V
   .limit stack 4
  
   ; set myArray[3] = 123
   aload 0
   getfield MainBlock/myArray [I
   ldc 3
   ldc 123
   iastore

   ; print myArray[3]
   getstatic java/lang/System/out Ljava/io/PrintStream;  
   aload 0
   getfield MainBlock/myArray [I
   ldc 3
   iaload
   invokevirtual java/io/PrintStream/println(I)V

   return

.end method













