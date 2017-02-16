import java.util.Random;
class Enigma {
    private Plugboard p;
    private Reflector r;
    private Wheel[] w;
    Enigma(int[] wheels,char[] wheelStartPositions,char[][] plugboardConnections,int reflector) {
        this.r=new Reflector(reflector);
        innit(wheels,wheelStartPositions,plugboardConnections);
    }
    Enigma(int[] wheels,char[] wheelStartPositions,char[][] plugboardConnections,char[][] reflectorConnections){
        this.r=new Reflector(reflectorConnections);
        innit(wheels,wheelStartPositions,plugboardConnections);
    }
    private void innit(int[] wheels,char[] wheelStartPositions,char[][] plugboardConnections){
        this.p=new Plugboard(plugboardConnections);
        this.w=new Wheel[wheels.length];
        for (int i=0;i<wheels.length;i++){
            this.w[i]=new Wheel(wheels[i],wheelStartPositions[i]);
        }
    }
    String encrypt(String message){
        String[] messages=message.toUpperCase().split(" ");
        String output="";
        for (int i=0;i<messages.length;i++){
            output+=encrypt(messages[i],true);
            if (messages.length>1&&i!=messages.length-1){
                output+=" ";
            }
        }
        return output;
    }
    String encrypt(String message,boolean ignored){
        char[] characters=message.toUpperCase().toCharArray();
        for (int i=0;i<characters.length;i++){
            characters[i]=this.p.nextCharacter(characters[i]);
            for (Wheel aW : this.w) {
                characters[i] = aW.nextCharacter(characters[i]);
            }
            characters[i]=this.r.nextCharacter(characters[i]);
            for (int j = this.w.length - 1; j >= 0; j--){
                characters[i]=this.w[j].nextCharacterReturn(characters[i]);
            }
            characters[i]=this.p.nextCharacter(characters[i]);
            int j=0;
            while(this.w[j].rotate()&&j<this.w.length){
                j++;
            }
        }
        return toAString(characters);
    }
    private String toAString(char[] characters){
        String t="";
        for (char character : characters) {
            t += character;
        }
        return t;
    }
}
class Reflector{
    private char[] connections=new char[26];
    Reflector(int type) {
        switch (type){
            case 0:
                this.connections="EJMZALYXVBWFCRQUONTSPIKHGD".toCharArray();
                break;
            case 1:
                this.connections="YRUHQSLDPXNGOKMIEBFZCWVJAT".toCharArray();
                break;
            case 2:
                this.connections="FVPJIAOYEDRZXWGCTKUQSBNMHL".toCharArray();
                break;
            case 3:
                this.connections="ENKQAUYWJICOPBLMDXZVFTHRGS".toCharArray();
                break;
            case 4:
                this.connections="RDOBJNTKVEHMLFCWZAXGYIPSUQ".toCharArray();
                break;
        }
    }
    Reflector(char[][] connections) {
        for (char[] connection : connections) {
            this.connections[(int) connection[0] - 65] = connection[1];
            this.connections[(int) connection[1] - 65] = connection[0];
        }
        char[] remaining={};
        for (int i=0;i<this.connections.length;i++){
            if (this.connections[i]=='\u0000'){
                remaining=arrayAppend.addOn(remaining,(char)(i+65));
            }
        }
        while (remaining.length>0){
            int temp=new Random().nextInt(remaining.length);
            this.connections[temp]=remaining[0];
            this.connections[(int)this.connections[temp]-65]=remaining[temp];
            remaining=arrayAppend.addOn(arrayAppend.subSet(remaining,1,temp-1),arrayAppend.subSet(remaining,temp+1,remaining.length));
        }
    }
    char nextCharacter(char input){
        return this.connections[(int)input-65];
    }
}
class Wheel{
    private char[] alphabet;
    private int type;
    Wheel(int id, char startChar){ //1-8
        this.type=id;
        switch (id){
            case 0:
                this.alphabet="EKMFLGDQVZNTOWYHXUSPAIBRCJ".toCharArray();
            break;
            case 1:
                this.alphabet="AJDKSIRUXBLHWTMCQGZNPYFVOE".toCharArray();
            break;
            case 2:
                this.alphabet="BDFHJLCPRTXVZNYEIWGAKMUSQO".toCharArray();
            break;
            case 3:
                this.alphabet="ESOVPZJAYQUIRHXLNFTGKDCMWB".toCharArray();
            break;
            case 4:
                this.alphabet="VZBRGITYUPSDNHLXAWMJQOFECK".toCharArray();
            break;
            case 5:
                this.alphabet="JPGVOUMFYQBENHZRDKASXLICTW".toCharArray();
            break;
            case 6:
                this.alphabet="NZJHGRCXMYSWBOUFAIVLPEKQDT".toCharArray();
            break;
            case 7:
                this.alphabet="FKQHTLXOCBJSPDZRAMEWNIUYGV".toCharArray();
        }
        for (int i=0;i<this.alphabet.length;i++){
            if(this.alphabet[i]==startChar){
                this.alphabet=arrayAppend.addOn(arrayAppend.subSet(this.alphabet,i,25),arrayAppend.subSet(this.alphabet,0,i-1));
                i=this.alphabet.length;
            }
        }
    }
    char nextCharacter(char input){
        return this.alphabet[(int)input-65];
    }
    char nextCharacterReturn(char input){
        int i=0;
        while(true){
            if (input==alphabet[i]){
                return (char)(i+65);
            }
            i++;
        }
    }
    boolean rotate(){
        this.alphabet=arrayAppend.addOn(arrayAppend.subSet(this.alphabet,1,25),alphabet[0]);
        switch (this.type){
            case 0:
                return this.alphabet[25]=='Q';
            case 1:
                return this.alphabet[25]=='E';
            case 2:
                return this.alphabet[25]=='V';
            case 3:
                return this.alphabet[25]=='J';
            case 4:
                return this.alphabet[25]=='Z';
            case 5:case 6:case 7:
                return this.alphabet[25]=='Z'||this.alphabet[25]=='M';
            default:
                return false;
        }
    }
}
class Plugboard{
    private char[] alphabet;
    Plugboard(char[][] matches) {
        this.alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int i=0;i<matches[0].length;i++){
            this.alphabet[(int)matches[0][i]-65]=matches[1][i];
            this.alphabet[(int)matches[1][i]-65]=matches[0][i];
        }
    }
    char nextCharacter(char input){
        return this.alphabet[(int)input-65];
    }
}
class arrayAppend{
    static char[] addOn(char[] array, char value){
        if(array==null){
            return new char[]{value};
        }
        else{
            char[] temp = array;
            array = new char[array.length + 1];
            for (int i = 0; i < array.length - 1; i++) {
                array[i] = temp[i];
            }
            array[array.length - 1] = value;
            return array;
        }
    } //NULL HANDLED
    static char[] subSet(char[] array, int s, int n){
        char[] temp=new char[(n-s)+1];
        for (int i=s, p=0; i<n+1;i++,p++){
            temp[p]=array[i];
        }
        return temp;
    }
    static char[] addOn(char[] array, char[] n){
        char[] temp=new char[array.length+n.length];
        for (int i=0, j=-(array.length);i<temp.length;i++, j++){
            if (j<0){
                temp[i]=array[i];
            }
            else {
                temp[i]=n[j];
            }
        }
        return temp;
    }
}