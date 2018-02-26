package EjercicioTecnico;

import java.util.HashMap;
import java.util.Map;

public class CadenaMorse {

    private String morseChain;
    private String[] subMorseChain;
    Map<String,Character> tablaMorse = new HashMap<>();
    
    
    
    public CadenaMorse(String cadenaMorseEntrada) throws ErrorDeFormatoException {
        this.morseChain = cadenaMorseEntrada;
        try{
            validacionInicial();
        }catch (ErrorDeFormatoException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        
        this.subMorseChain = morseChain.split(" ");
        cargaTablaMorse();

    }
    
    private void validacionInicial() throws ErrorDeFormatoException{

        for (int i = 0; i<morseChain.length(); i++){
            char c = morseChain.charAt(i);
            if(c != '-' && c != '.' && c != ' '){
                throw new ErrorDeFormatoException ("Por favor ingrese solo . y -. Cerrando el programa");
            }
        }
    }
    
    public String translate2Human(){
        String cadenaHumano = "";
        
        for(int i = 0; i<subMorseChain.length; i++){
            if(!subMorseChain[i].isEmpty()){
                cadenaHumano += tablaMorse.get(subMorseChain[i]);
            }else{
                cadenaHumano += " ";
            }
        }
        
        return cadenaHumano;
    }
    
    private void cargaTablaMorse(){
        
        tablaMorse.put(".-", 'A');
        tablaMorse.put("-...", 'B');
        tablaMorse.put("-.-.", 'C');
        tablaMorse.put("-..", 'D');
        tablaMorse.put(".", 'E');
        tablaMorse.put("..-.", 'F');
        tablaMorse.put("--.", 'G');
        tablaMorse.put("....", 'H');
        tablaMorse.put("..", 'I');
        tablaMorse.put(".---", 'J');
        tablaMorse.put("-.-", 'K');
        tablaMorse.put(".-..", 'L');
        tablaMorse.put("--", 'M');
        tablaMorse.put("-.", 'N');
        tablaMorse.put("---", 'O');
        tablaMorse.put(".--.", 'P');
        tablaMorse.put("--.-", 'Q');
        tablaMorse.put(".-.", 'R');
        tablaMorse.put("...", 'S');
        tablaMorse.put("-", 'T');
        tablaMorse.put("..-", 'U');
        tablaMorse.put("...-", 'V');
        tablaMorse.put(".--", 'W');
        tablaMorse.put("-..-", 'X');
        tablaMorse.put("-.--", 'Y');
        tablaMorse.put("--..", 'Z');
        tablaMorse.put("-----", '0');
        tablaMorse.put(".----", '1');
        tablaMorse.put("..---", '2');
        tablaMorse.put("...--", '3');
        tablaMorse.put("....-", '4');
        tablaMorse.put(".....", '5');
        tablaMorse.put("-....", '6');
        tablaMorse.put("--...", '7');
        tablaMorse.put("---..", '8');
        tablaMorse.put("----.", '9');
        tablaMorse.put(".-.-.-", '.');
    }
}
