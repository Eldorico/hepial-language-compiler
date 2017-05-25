.class MainBlock
.super java/lang/Object

.field myInt I
.field wesh I

; default constructor
.method public <init>()V
   aload 0
   invokespecial java/lang/Object/<init>()V
   return
.end method

.method mainFunction()V
   .limit stack 3 

   ; set myInt to 123
   aload 0 ; stack = this
   ldc 123 ; stack = this, 123
   putfield MainBlock/myInt I

   ; set wesh to 227
   aload 0 ; stack = this
   ldc 227 ; stack = this, 227
   putfield MainBlock/wesh I

   ; invoke myPrint(MainBlock.myInt)
   aload 0
   aload 0
   getfield MainBlock/myInt I
   invokevirtual MainBlock/myPrint(I)V

   ; invoke function1
   new Function1 
   dup 
   aload 0
   invokespecial Function1/<init>(LMainBlock;)V ; call constructor 
   invokevirtual Function1/mainFunction()V ; call function1

   return

.end method

.method myPrint(I)V
   .limit stack 2
   .limit locals 2  ; place pour this et MainBlock.myInt

   getstatic java/lang/System/out Ljava/io/PrintStream;
   iload 1
   invokevirtual java/io/PrintStream/println(I)V

   return

.end method 













