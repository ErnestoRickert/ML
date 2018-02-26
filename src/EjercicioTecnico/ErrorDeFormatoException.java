package EjercicioTecnico;

public class ErrorDeFormatoException extends Exception {

    public ErrorDeFormatoException(String aString) {
        super(aString);
    }
    
    public ErrorDeFormatoException (String aString, String anotherString){
        super ("No se pudo determinar si pretende escribir: " +aString + " o: " + anotherString);

    }
}
