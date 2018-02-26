package EjercicioTecnico;

import java.util.HashMap;
import java.util.Map;

public class CadenaHumano {
    
    String cadenaHumano;
    String[] subCadenaHumano;
    Map<Character, String> tablaMorse = new HashMap<>();
    
    public CadenaHumano(String frase) throws ErrorDeFormatoException{
        this.cadenaHumano = frase;
        
        try{
            validacionInicial();
        }catch(ErrorDeFormatoException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        
        String cadenaHumanoUpper = cadenaHumano.toUpperCase();
        this.subCadenaHumano = cadenaHumanoUpper.split(" ");
        
        cargaTablaMorse();
    }
    
    public String translate2Morse(){
        String cadenaMorse = "";
        
        for(int i = 0; i<subCadenaHumano.length; i++){
                for(int j = 0; j<subCadenaHumano[i].length();j++){
                    
                    cadenaMorse += tablaMorse.get(subCadenaHumano[i].charAt(j));
                    cadenaMorse += " ";
                }
            cadenaMorse += "  ";
        }
        
        return cadenaMorse;
    }
    
    private void validacionInicial() throws ErrorDeFormatoException {
        if(cadenaHumano.matches("^[a-zA-Z0-9]*$")){
            throw new ErrorDeFormatoException ("Por favor ingrese solo caracteres permitidos en morse. Cerrando el programa");
        }
    }
    
    private void cargaTablaMorse(){
        tablaMorse.put('A', ".-");
        tablaMorse.put('B', "-...");
        tablaMorse.put('C', "-.-.");
        tablaMorse.put('D', "-..");
        tablaMorse.put('E', ".");
        tablaMorse.put('F', "..-.");
        tablaMorse.put('G', "--.");
        tablaMorse.put('H', "....");
        tablaMorse.put('I', "..");
        tablaMorse.put('J', ".---");
        tablaMorse.put('K', "-.-");
        tablaMorse.put('L', ".-..");
        tablaMorse.put('M', "--");
        tablaMorse.put('N', "-.");
        tablaMorse.put('O', "---");
        tablaMorse.put('P', ".--.");
        tablaMorse.put('Q', "--.-");
        tablaMorse.put('R', ".-.");
        tablaMorse.put('S', "...");
        tablaMorse.put('T', "-");
        tablaMorse.put('U', "..-");
        tablaMorse.put('V', "...-");
        tablaMorse.put('W', ".--");
        tablaMorse.put('X', "-..-");
        tablaMorse.put('Y', "-.--");
        tablaMorse.put('Z', "--..");
        tablaMorse.put('0', "-----");
        tablaMorse.put('1', ".----");
        tablaMorse.put('2', "..---");
        tablaMorse.put('3', "...--");
        tablaMorse.put('5', ".....");
        tablaMorse.put('4', "....-");
        tablaMorse.put('6', "-....");
        tablaMorse.put('7', "--...");
        tablaMorse.put('8', "---..");
        tablaMorse.put('9', "----.");
        tablaMorse.put('.', ".-.-.-");
    }   
}

