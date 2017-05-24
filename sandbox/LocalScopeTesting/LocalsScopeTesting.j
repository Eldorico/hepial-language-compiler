.class LocalsScopeTesting
.super java/lang/Object

.method public static main([Ljava/lang/String;)V
   .limit stack 4

   ; load the MainBlock
   new MainBlock 
   dup 
   invokespecial MainBlock/<init>()V ; call constructor 
   astore 0

   ; invoke the MainFunction
   aload 0
   invokevirtual MainBlock/mainFunction()V

   return
.end method
