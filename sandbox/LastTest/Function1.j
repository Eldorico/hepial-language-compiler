.class Function1
.super java/lang/Object

.field myInt I 
.field mainBlock LMainBlock;

; constructor 
.method public <init>(LMainBlock;)V
   .limit locals 2 ; this, MainBlock
   .limit stack 3   

   ; super
   aload 0
   invokespecial java/lang/Object/<init>()V   

   ; put this.mainBlock = MainBock (param)
   aload 0
   aload 1
   putfield Function1/mainBlock  LMainBlock;

   return
.end method



.method mainFunction()V
   .limit stack 4

   ; set this.myInt to 321
   aload 0 ; stack = this
   ldc 321 ; stack = this, 321
   putfield Function1/myInt I

   ; print Wesh depuis F1
   getstatic java/lang/System/out Ljava/io/PrintStream;
   ldc "Wesh depuis F1!"
   invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

   ; print this.myInt 
   getstatic java/lang/System/out Ljava/io/PrintStream;
   aload 0
   getfield Function1/myInt I
   invokevirtual java/io/PrintStream/println(I)V

   ; print MainBlock.wesh
   getstatic java/lang/System/out Ljava/io/PrintStream;
   aload 0
   getfield Function1/mainBlock LMainBlock;
   getfield MainBlock/wesh I
   invokevirtual java/io/PrintStream/println(I)V

   return

.end method


