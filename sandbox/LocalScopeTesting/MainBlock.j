.class MainBlock
.super java/lang/Object

.field myInt I

; default constructor
.method public <init>()V
   aload 0
   invokespecial java/lang/Object/<init>()V
   return
.end method

.method mainFunction()V
   .limit stack 2 

   ; set myInt to 123
   aload 0 ; stack = this
   ldc 123 ; stack = this, 123
   putfield MainBlock/myInt I

   ; invoke myPrint
   aload 0
   invokevirtual MainBlock/myPrint()V

   return

.end method

.method myPrint()V
   .limit stack 2

   getstatic java/lang/System/out Ljava/io/PrintStream;
   
   aload 0
   getfield MainBlock/myInt I

   invokevirtual java/io/PrintStream/println(I)V

   return

.end method 













