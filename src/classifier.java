import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class classifier {
    public static boolean isAlpha(String word) {
        return validate("[A-Za-z_]+",word);
    }
    public static boolean isInt(String word ) { return validate("[0-9]+",word); }
    public static boolean  isFloat(String word) {return validate("[0-9]*[.][0-9]+",word);}
    public static boolean isBreaker(String word){
        // dot is not included, because dot can be interpreted in two ways.
        return classifier.validate("[?!@#$%^&*()-+=}{\"':;,`~<>/\\|\\[\\]\\s\\n\\t\\\\]",word);
    }
    public static boolean isIdentifier(String word) {
        String RE = "[_]+([0-9A-Za-z]_*)+|[A-Za-z][A-Za-z0-9_]*";
        return validate(RE,word);
    }
    public static boolean isChar(String word){
        String RE = "'\\\\[\"'\\\\nrstb]'|'[^'\\\\]'";
        return validate(RE,word);
    }
    public static boolean isString(String word){

        String RE = "\"(\\\\[\"'\\\\ntrsb]|[^\"\\\\])*\"";
        return validate(RE,word);
    }
    public  static boolean isPunctuator(String word) {
        return validate("[.,:;\\]\\[}{)(]",word);
    }
    public static String[] isKeyword(String word){

        String[][] keywords =  {
                {"int","datatype"},{"float","datatype"},{"double","datatype"},{"bool","datatype"},{"char","datatype"},
                {"long"},
                {"tribal","AM"},
                {"family","AM"},
                {"personal","AM"},
                {"self","SP"},
                {"parent","SP"},
                {"iterate"},
                {"continue"},
                {"break"},
                {"if"},
                {"else"},
                {"match"},
                {"option"},
                {"model"},
                {"contract"},
                {"fixed"},
                {"abstract"},
                {"try"},
                {"catch"},
                {"finally"},
                {"throws"},
                {"throw"},
                {"fulfills"},
                {"inherits"},
                {"sealed"},
                {"includes"},
        };
        for (int j = 0;j<keywords.length;j++ ){
            if (word.equals(keywords[j][0])) return keywords[j];
        }
        return null;
    }
    public static String[] isOperator(String word) {
        String[][] operators = {
                {"+","PM"}, {"-","PM"},
                {"*","DM"}, {"/","DM"},{"%","DM"},
                {">","rel"}, {"=>","rel"}, {"<=","rel"}, {"==","rel"}, {"!=","rel"},
                {"++","inc"}, {"--","inc"},
                {"="},
                {"&","bitwise"},{"|","bitwise"},{"^","bitwise"},{"<<","bitwise"},{">>","bitwise"},
                {"&&","logical"},{"||","logical"},
                {"!"},
        };
        for (int j = 0;j<operators.length;j++ ){
            if (word.equals(operators[j][0])) return operators[j];
        }
        return null;
    }
    public static boolean validate(String RE, String word) {
        Pattern pattern = Pattern.compile(RE);
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }
}
