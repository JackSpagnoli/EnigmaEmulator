import java.util.Random;

class Enigma {

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
        for (int i=0;i<connections.length;i++){
            this.connections[(int)connections[i][0]-65]=connections[i][1];
            this.connections[(int)connections[i][1]-65]=connections[i][0];
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
        String[][] temp=new csvReader().readCsv("Wheels.csv");
        char[][] tempc=new char[temp.length][26];
        for (int i=0;i<temp.length;i++){
            for (int j=0; j<26;j++){
                tempc[i][j]=temp[i][j].charAt(0);
            }
        }
        this.alphabet=tempc[id+1];
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
        for (int i=0;i<2;i++){ //2 Layers
            for (int j=0;j<matches.length;j++){ //Each character
                for (int z=0;z<this.alphabet.length;z++){ //Each letter
                    if (this.alphabet[z]==matches[i][j]&&i==0){
                        this.alphabet[z]=matches[1][j];
                    } else if (this.alphabet[z]==matches[i][j]&&i==1){
                        this.alphabet[z]=matches[0][j];
                    }
                }
            }
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