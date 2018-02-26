package EjercicioTecnico;

import java.util.Scanner;

public class MorseCode {

    public static void main(String[] args) throws ErrorDeFormatoException {
        
        //MAIN QUE USÉ PARA PRUEBAS
        
        /*System.out.println("Insercion humano");
        CadenaHumano humano = new CadenaHumano("Hola que tal");
        String salidaMorse = humano.translate2Morse();
        System.out.println(salidaMorse);
        
        System.out.println("Inserte morse");
        CadenaMorse morse2 = new CadenaMorse(".- -.1");
        String salidaHumano = morse2.translate2Human();
        System.out.println(salidaHumano);*/
        
        
        System.out.println("Inserte cadena");
        Scanner scanner = new Scanner(System.in);
        String cadenaEntrada = scanner.next();
        String finalOutput;
        
        
        CadenaDeBits mensaje = new CadenaDeBits(cadenaEntrada);
        String morseChainDecoded = mensaje.decodeBits2Morse();
                
        CadenaMorse morse = new CadenaMorse(morseChainDecoded);
        finalOutput = morse.translate2Human();
        System.out.println("En morse: " +morseChainDecoded);
        System.out.println("En humano: " +finalOutput);
        
    }
}
