```java
// check for semantic errors and print errors if any
boolean errorsDetected = false ;
if( SymbolTable . getInstance (). semanticErrorsDetected ()){
errorsDetected = true ;
}
if( abstractTreeElement . semanticErrorsDetected ()){
errorsDetected = true ;
}
if( errorsDetected ){
ErrorPrinter . getInstance (). printErrors() ;
System .exit(1) ;
}
```