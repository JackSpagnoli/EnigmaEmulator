class Enigma {

}
class Wheel{
    private char[] alphabet;
    Wheel(int id, char startChar){ //1-8
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
        char temp=this.alphabet[(int)input-65];
        rotate();
        return temp;
    }
    private void rotate(){
        this.alphabet=arrayAppend.addOn(arrayAppend.subSet(this.alphabet,1,25),alphabet[0]);
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