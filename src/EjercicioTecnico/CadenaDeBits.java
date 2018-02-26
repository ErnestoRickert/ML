package EjercicioTecnico;

import java.util.HashMap;
import java.util.Map;

public class CadenaDeBits {

    private String cadenaEntrada;
    private static final String MAX_CANT_1           = "max1";
    private static final String MIN_CANT_1           = "min1";
    private static final String MIN_CANT_0           = "min0";
    private static final String MAX_CANT_0           = "max0";
    private static final String POSICION_INICIAL     = "posIni";
    private static final String POSICION_FINAL       = "posFin";
    private static final String TOLERANCIA_GUION     = "tolMax1";
    private static final String TOLERANCIA_PUNTO     = "tolMin1";
    private static final String TOLERANCIA_PAUSA_MIN = "tolMin0";
    private static final String TOLERANCIA_PAUSA_MAX = "tolMax0";
    
    public CadenaDeBits(String mensaje) {
        try{
            validacionInicial(mensaje);
        }catch(ErrorDeFormatoException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        this.cadenaEntrada = mensaje;
    }
    
    public String decodeBits2Morse(){
        
        char bitsArray[] = cadenaEntrada.toCharArray(); //Conversión String a array de caracteres
        HashMap<String,Integer> perfilUsuario = definirUsuario(bitsArray);
        
        String enMorse = decode(perfilUsuario, bitsArray);
        return enMorse;
    }
          
    private HashMap<String,Integer> definirUsuario(char[] cadena){
        Map<String,Integer> mapa = new HashMap<>();{
        //Inicializacion del mapa
            mapa.put(MAX_CANT_1, Integer.MIN_VALUE);
            mapa.put(MAX_CANT_0, Integer.MIN_VALUE);
            mapa.put(MIN_CANT_1, Integer.MAX_VALUE);
            mapa.put(MIN_CANT_0, Integer.MAX_VALUE);
            mapa.put(POSICION_INICIAL, Integer.MIN_VALUE);
            mapa.put(POSICION_FINAL, Integer.MIN_VALUE);
            mapa.put(TOLERANCIA_GUION, 0); //Tolerancia al punto - Permite que un punto sea también representado por un porcentaje del pulso mas corto que haya generado el humano.
            mapa.put(TOLERANCIA_PUNTO, 0); //Tolerancia al guion - Permite que un guion sea también representado por un porcentaje del pulso mas largo que haya generado el humano.
            mapa.put(TOLERANCIA_PAUSA_MIN, 0); //La pausa minima efectuada por operador, mas un diferencial que depende de esta variable, seran considerados pausas en la misma letra. De lo contrario, serán espacios entre palabas.
            mapa.put(TOLERANCIA_PAUSA_MAX, 0); //La pausa maxima efectuada por operador, menos un diferencial que depende de esta variable, seran considerados pausas en la misma letra. De lo contrario, serán espacios entre palabas.
        }    
        
        cargarMapa(mapa,cadena);
        
        return (HashMap<String, Integer>) mapa;
    }
    
    private void cargarMapa(Map<String,Integer> m, char[] cad){
        
        try{
            cargaInicioYFin(m, cad);
        }catch (ErrorDeFormatoException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        
        cargaMaxYMin(m, cad);
        calcularToleranciasParaUsuario(m);
    }
    
    private void validacionInicial(String mensaje) throws ErrorDeFormatoException {
        
        if (!(mensaje.matches("[0-1]+"))){
            throw new ErrorDeFormatoException ("Por favor ingrese solo 0 y 1. Cerrando el programa");
        } else if (mensaje.matches("[0]+")){
            throw new ErrorDeFormatoException ("Por favor, ingrese pulsos para decodificar. Cerrando el programa");
        }else if (mensaje.matches("[1]+")){
            throw new ErrorDeFormatoException ("Por favor suelte el dedo del pulsador :P. Cerrando el programa");
        }
            
    }
    
    private void cargaInicioYFin(Map<String,Integer> m,char[] cad) throws ErrorDeFormatoException{
        //Busqueda de primer uno en el mensaje. Define posicion inicial
        int i = 0;
        while (cad[i] == '0'){
            i++;
        }
        m.put(POSICION_INICIAL, i);
        //Busqueda de último uno en el mensaje. Define posicion final
        int j = cad.length - 1; //Se resta uno porque la funcion array.length indica el largo y no el indice de la ultima posicion
        while (cad[j] == '0'){
            j--;
        }
        m.put(POSICION_FINAL, j);
        
        if(m.get(POSICION_INICIAL) == m.get(POSICION_FINAL)){
            throw new ErrorDeFormatoException ("Se recibió un solo pulso. No se puede determinar el mensaje.");
        }
    }
    
    private void cargaMaxYMin(Map<String,Integer> m, char[] cad){
        //Contador consecutivos:
        for(int i = m.get(POSICION_INICIAL); i<m.get(POSICION_FINAL); i++){
            int cont = 1; /*Se inicializa contador en 1 porque se están evaluando cantidad de coincidencias y no de digitos.
                            La cantidad de coincidencias será siempre menor en 1 a la cantidad de digitos que coinciden     */
            if (!(i == cad.length-1)){
            
                while(cad[i]==cad[i+1]){
                    if (!(i == cad.length-2)){
                        i++;
                        cont++;
                    }else{
                        cont++;
                        break;
                    }
                }

                switch (cad[i]){
                    case '0':
                        if(cont>m.get(MAX_CANT_0)){
                            m.put(MAX_CANT_0, cont);
                        }
                        if(cont<m.get(MIN_CANT_0)){
                            m.put(MIN_CANT_0, cont);
                        }
                        break;
                    case '1':
                        if(cont>m.get(MAX_CANT_1)){
                            m.put(MAX_CANT_1, cont);
                        }
                        if(cont<m.get(MIN_CANT_1)){
                            m.put(MIN_CANT_1, cont);
                        }
                        break;
                }
            }
        }
    }
    
    private void calcularToleranciasParaUsuario(Map<String,Integer> m){
        calcularToleranciaSimbolos(m);
        calcularToleranciaEspacios(m);
    }
    
    private void calcularToleranciaSimbolos(Map<String,Integer> m){
        int diferencial = m.get(MAX_CANT_1) - m.get(MIN_CANT_1);
        
        switch(diferencial){
            case 0:
            case 1:
            case 2:
                m.put(TOLERANCIA_PUNTO, m.get(MIN_CANT_1));
                m.put(TOLERANCIA_GUION, m.get(MAX_CANT_1));
                break;
                
            case 3: 
            case 4: 
            case 5:
                m.put(TOLERANCIA_PUNTO, m.get(MIN_CANT_1) + 1);
                m.put(TOLERANCIA_GUION, m.get(MAX_CANT_1) - 1);
                break;
                
            default:
                int variacionPorcentualPunto = m.get(MIN_CANT_1) + 1 + ((int) Math.round(m.get(MIN_CANT_1) * 0.1));
                int variacionPorcentualGuion = m.get(MAX_CANT_1) - 1 - ((int) Math.round(m.get(MAX_CANT_1) * 0.1));
                
                m.put(TOLERANCIA_PUNTO, variacionPorcentualPunto);
                m.put(TOLERANCIA_GUION, variacionPorcentualGuion);
                break;
        }
    }
    
    
    private void calcularToleranciaEspacios(Map<String,Integer> m){
        int diferencial = m.get(MAX_CANT_0) - m.get(MIN_CANT_0);
        
        switch(diferencial){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                m.put(TOLERANCIA_PAUSA_MIN, m.get(MIN_CANT_0));
                m.put(TOLERANCIA_PAUSA_MAX, m.get(MAX_CANT_0));
                break;
            
            case 5:
            case 6:
            case 7:
                m.put(TOLERANCIA_PAUSA_MIN, m.get(MIN_CANT_0) + 1);
                m.put(TOLERANCIA_PAUSA_MAX, m.get(MAX_CANT_0) - 1);
                break;
            
            default:
                int variacionPorcentualMin = m.get(MIN_CANT_0) + 1 + ((int) Math.round(m.get(MIN_CANT_0) * 0.1));
                int variacionPorcentualMax = m.get(MAX_CANT_0) - 1 - ((int) Math.round(m.get(MAX_CANT_0) * 0.1));
                
                m.put(TOLERANCIA_PAUSA_MIN, variacionPorcentualMin);
                m.put(TOLERANCIA_PAUSA_MAX, variacionPorcentualMax);
            /*case 0: Letra; case 1: Palabra; case 2: Frase*/
        }
    }
    
    
    private String decode(Map<String,Integer> m, char[] cad){
        
        String cadenaMorse = "";
        int i = m.get(POSICION_INICIAL);

        while(i<=m.get(POSICION_FINAL)){
            int cont0 = 0;
            int cont1 = 0;
            
            while(cad[i] == '1'){
                if (!(i == cad.length-1)){
                    i++;
                    cont1++;
                }else{
                    cont1++;
                    break;
                }
            }
            
            if(cont1>=m.get(MIN_CANT_1) && cont1<=m.get(TOLERANCIA_PUNTO)){
                cadenaMorse += ".";
            }else if(cont1<=m.get(MAX_CANT_1) && cont1>=m.get(TOLERANCIA_GUION)){
                cadenaMorse += "-";
            }else{
                System.out.println("No cumple con un patron constante: (POS_VECTOR)" +i);
            }

            if(i == m.get(POSICION_FINAL)){
                break;
            }
            
            while(cad[i] == '0'){
                if (!(i == cad.length-1)){
                    i++;
                    cont0++;
                }else{
                    cont0++;
                    break;
                }
            }

            //*case 0: Letra; case 1: Palabra; case 2: Frase

            if(cont0>m.get(TOLERANCIA_PAUSA_MIN) && cont0<m.get(TOLERANCIA_PAUSA_MAX)){
                //Si es mayor a la tolerancia min, interpreta cambio de letra entonces imprime .
                cadenaMorse += " ";
            }else if (cont0>=m.get(TOLERANCIA_PAUSA_MAX) && m.get(MAX_CANT_0) != 1){
                cadenaMorse += "  ";
            }
        }
        
        try{
            evaluacionPatronIndefinido(cadenaMorse, m);
        }catch(ErrorDeFormatoException e){
            System.out.println(e.getMessage());
        }
        
        return cadenaMorse;
    }
    
    private void evaluacionPatronIndefinido (String cadenaIndefinida, Map<String, Integer> m) throws ErrorDeFormatoException{
        if(m.get(MAX_CANT_1) == m.get(MIN_CANT_1) && m.get(MIN_CANT_1)!= 1){
            String posibleCadena = cadenaIndefinida.replace('.', '-');
            throw new ErrorDeFormatoException (cadenaIndefinida, posibleCadena);
        }
        
        if(m.get(MAX_CANT_0) == m.get(MIN_CANT_0) && m.get(MIN_CANT_0)!= 1){
            String posibleCadena = cadenaIndefinida.replaceAll("\\s+", "");
            throw new ErrorDeFormatoException (cadenaIndefinida, posibleCadena);
        }
    }
    
}