// 786
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class Tokenizer
{
    public static int i = 0;
    public static int line = 1;

    public static void main(String[] args) throws IOException {
        String code = Files.readString(Path.of("/home/gimi/IdeaProjects/Compiler-Construction/playground/code.flx"), StandardCharsets.US_ASCII);
        FileWriter myWriter = new FileWriter("playground/Tokens.tok");

        while (i<code.length()){
//            System.out.println(tokenize(nextWord(code)));
            myWriter.write(tokenize(nextWord(code)).toString()+'\n');
        }
        myWriter.close();
        float a = 9;
    }


    public static Token tokenize(String word) {

            if (Classifier.isIdentifier(word)){
                String[] keyword =  Classifier.isKeyword(word);
                if (keyword!=null){
                    if (keyword.length==1) return new Token(keyword[0],"",line);
                    return new Token(keyword[1],word,line);
                }
                return new Token("ID",word,line);
            }
            String[] operator = Classifier.isOperator(word);
            if (Classifier.isOperator(word)!=null) {
                if (operator.length==1) return new Token(operator[0],"",line);
                return new Token(operator[1],word,line);
            }
            if (Classifier.isPunctuator(word)) return new Token(word,"",line);
            if (Classifier.isString(word)) return new Token("String",word.substring(1,word.length()-1),line);
            if (Classifier.isChar(word)) return  new Token("char",word.substring(1,word.length()-1),line);
            if (Classifier.isInt(word)) return new Token("int",word,line);
            if (Classifier.isFloat(word)) return new Token("float",word,line);

        return new Token("invalid",word,line);
    }
    public static String nextWord(String input) {
        int length = input.length();
        String temp = "";
        char ch;

        while(i<length) {
            temp += input.charAt(i);
            i++;
//            "'"
            if (Classifier.validate("['\"]",temp))
//            if (temp.equals("\""))
            {
                char charOrString = temp.charAt(0);
                while(i<length) {
                    ch = input.charAt(i);
                        if (ch== '\\') {
                            temp+=ch;
                            i++;
                            if (i == length) return temp;
                            ch = input.charAt(i);
                            if (ch=='\n') {
                                line++;
                                return temp;
                            }
                            temp+=ch;
                            i++;
                        }
                        else if(ch==charOrString)   {
                            temp+=ch;
                            i++;
                            return temp;
                        }
                        else if(ch=='\n')  {
                            line++;
                            i++;
                            return temp;
                        }
                        else {
                            temp+=ch;
                            i++;
                        }
                }
                return temp;
            }
/*
            else if (temp.equals("'")) {
                //read char, length cqn either be 3 or 4, it already 1.
                int n = 1;
                int max = 3;
                while (i<length && n<max){
                    ch = input.charAt(i);
                    i++;

                    if(ch=='\n') {
                        line++;
                        return temp;
                    }

                    else if (ch=='\\'){
                        temp+=ch;
                        n++;
                        if (n==2) {
                            max=4;
                        }
                    }  else {
                        temp +=ch;
                        n++;
                    }
                }
                return temp;
            }
*/

            else if (temp.equals("\n")) {
                line++;
            }

            else if (temp.equals("\s")) {}

            else if (temp.equals("~")) {
                // "~" is a reserved symbol for single line comment, specified in the language manifesto
                while(i<length) {
                    if (input.charAt(i) ==  '\n') {
                        line++;
                        i++;
                        break;
                    }
                    i++;
                }
            }
            // multi-line comment  <<< body of the comment >>>
            else if (i+2<length && input.substring(i-1,i+2).equals("<<<")){
                i+=2;
                char[] sequence = new char[3];
                while (i<length) {

                    sequence[i%3] = input.charAt(i);
                    if (input.charAt(i)=='\n') {
                        line++;
                    }
                    if (">>>".equals(String.valueOf(sequence))){
                        // comment ends
                        i++;
                        break;
                    }
                    i++;
                }
            }

            else if (Classifier.isAlpha(temp)) {
                while (i<length){
                    ch = input.charAt(i);
                    if (Classifier.isBreaker(ch)|| ch=='.')
                    {
                        return temp;
                    }
                    i++;
                    temp+=ch;
                }
                return temp;
            }

            else if (Classifier.isInt(temp)) {
                while (i<length) {
                    ch = input.charAt(i);
                    if(
                            Classifier.isBreaker(ch)
                            || !Classifier.isInt(String.valueOf(ch)) && Classifier.isFloat(temp)
                            || ch=='.' && Classifier.validate("[1-9]+[A-Za-z_]+[A-Za-z0-9_]*",temp)
                    ) return temp;
                    i++;
                    temp+=ch;
                }
                return temp;
            }

            else if (temp.equals(".")) {
                // . has two interpretations. either a separator or a leading decimal point
                while (i<length) {
                    ch = input.charAt(i);
                    if (!Classifier.isInt(String.valueOf(ch))) return temp;
                    temp+=ch;
                    i++;
                }
                return temp;
            }

            //single character tokens
            else if (Classifier.validate("[@#$\\\\%\\^)(}{\\[\\]:;?`]",temp)) return temp;

            else if (Classifier.validate("[&\\|*+-<>=!]",temp)) {

                if (i==length) return temp;
                ch = input.charAt(i);
                if (Classifier.validate("\\|{2}|[+]{2}|[-]{2}|[=]{2}|[*]{2}|[<]{2}|[>]{2}|[&]{2}|[%*-+<>!]=|\\\\",temp+ch)) {
                    i++;
                    return temp+ch;
                }
                return temp;

            }
            temp="";
        }
        return  temp;
    }
}



