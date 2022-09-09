public class Token {
    String value;
    String _class;
    int line;

    Token(String _class, String value, int line){
        this._class=_class;
        this.value=value;
        this.line=line;
    }
    public String toString(){
        return '('+_class+','+value+','+line+')';
    }
}
